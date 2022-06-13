import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminGUI
{
    private JPanel panel1;
    private JPanel adminHomePanel;
    private JList userList;
    private JTextArea userDetailsText;
    private JLabel jobsLabel;
    private JList list1;
    private JTextArea jobDetailsText;
    private JButton lockUserButton;
    private JButton sendWarningButton;
    private JList inboxList;
    private JTextArea mailTextArea;
    private JButton replyButton;
    private JButton deleteButton;
    private JLabel inboxLabel;
    private JTextField replyTextField;
//    private JButton newMessageButton;

    private AdminControl adminControl;
    private JSS program;

    private DefaultListModel userListModel;
    private ArrayList<String> userNames;

    private DefaultListModel mailListModel;
//    private  ArrayList<Message> userMessages;


    public AdminGUI(AdminControl adminControl, JSS program)
    {

        this.adminControl = adminControl;
        this.program = program;

        this.userNames = new ArrayList<String>();
        this.userListModel = new DefaultListModel();
        this.userList.setModel(this.userListModel);
//        this.userMessages = new ArrayList<Message>();
        ArrayList<Message> userMessages = this.adminControl.relayMessages();
        this.mailListModel = new DefaultListModel();
        this.inboxList.setModel(this.mailListModel);

        JFrame frame = new JFrame("AdminHome");
        frame.setContentPane(this.adminHomePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //retrieve messages for inbox

        int userID = this.adminControl.adminID();
        System.out.println("59 userID without -1 is " + this.adminControl.adminID());
        System.out.println("60 retrieving messages for user " + userID);

        if (this.program.checkMessages(userID) == false)
        {
            refreshList("Clear inbox...",inboxList,mailListModel);


        }
        else
        {
//            ArrayList<Message> userMessages = this.adminControl.relayMessages();

            for (Message each: userMessages)
            {
                String toDisplay = "";

              int senderID = each.getSenderID();
               String senderName = program.retrieveUsername(senderID);
                System.out.println("ADMIN GUI 80 sender ID IS  " + each.getSenderID());
                System.out.println("ADMIN GUI 80 sender is "+senderName+ "subject is " + each.getHeader());
                toDisplay += senderName + " Re: " + each.getHeader();
                refreshList(toDisplay,inboxList,mailListModel);
            }
        }

        //requests num users from JSS and populates list that size of usernames only

        try{
            int numUsers = program.countUsers();
            for (int i=0; i<numUsers; i+=1)
            {
                String name = this.program.retrieveUsername(i+1);
                this.userNames.add(name);
                this.refreshList(name);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PromptGUI error = new PromptGUI("Contact developer", e.toString());
        }
        userList.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {

                int userNumber = userList.getSelectedIndex();
                try
                {

//                    userDetailsText.setText("User ID is: " + userNumber);
                    String selected = program.retrieveUserDetails(userNumber);
                    boolean locked = program.checkLocked(userNumber);
                    if (locked == true)
                    {
                        userDetailsText.setBackground(Color.red);
                        lockUserButton.setText("UNLOCK ACCOUNT");
                    }
                    else
                    {
                        userDetailsText.setBackground(Color.white);
                        lockUserButton.setText("LOCK ACCOUNT");
                    }
                    userDetailsText.setText(selected);


                }
                catch (Exception x)
                {
                    x.printStackTrace();
                    PromptGUI error = new PromptGUI("Error, contact developer", x.toString());
                }
            }
        });

        lockUserButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int userIndex = userList.getSelectedIndex();
                adminControl.switchLock(userIndex);
                if(program.checkLocked(userIndex))
                {
                    userDetailsText.setBackground(Color.red);
                    lockUserButton.setText("UNLOCK ACCOUNT");
                }
                else
                {
                    userDetailsText.setBackground(Color.white);
                    lockUserButton.setText("LOCK ACCOUNT");
                }
            }
        });
        sendWarningButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                int userIndex = userList.getSelectedIndex()+1;
                System.out.println("line 158 index selected is " + userList.getSelectedIndex() );
                System.out.println("159 userIndex from this is " + userIndex);
                System.out.println("warning user line 154" + userIndex);

                adminControl.blockedMessage(adminControl.adminID(),userIndex);

                //check messages are working bugfix:
             program.checkMessages(userList.getSelectedIndex());
            }
        });
        inboxList.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                int selected = inboxList.getSelectedIndex();
                Message toDisplay = adminControl.relayMessages().get(selected);
                String title = toDisplay.getHeader().toUpperCase();
                String content = toDisplay.getBody();
                String display = title + "\n\n" + content;
                mailTextArea.setText(display);
            }
        });
//        newMessageButton.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
////                MessageGUI sendMessage = new MessageGUI();
//            }
//        });
//        replyButton.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//
//                MessageGUI reply = new MessageGUI(sender,destination);
//                Message toSend = reply.getMessage(sender,destination);
//            }
//        });
        replyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int sender = adminControl.adminID();
                System.out.println("206 admin sender Id is: " + sender);

                int indexNumber = inboxList.getSelectedIndex();
                Message replyTo = userMessages.get(indexNumber);
                int replyToID = replyTo.getSenderID();

                System.out.println("208 destin ID is: " + replyToID);
                String header = "Admin Reply";
                String body = replyTextField.getText();
                adminControl.createMessage(sender,replyToID,header,body);

            }
        });
    }

    //generic refreshList option to be used by most lists
private void refreshList(String content, JList list, DefaultListModel listModel)
{
    list.setVisible(true);
    listModel.addElement(content);
}

//customised for locking list

    private void refreshList(String name)
    {
        userList.setVisible(true);
        userDetailsText.setBackground(Color.white);
//        System.out.println("removing elements");
//        list.removeAllElements();
//        System.out.println("adding name to list:" + name);
        userListModel.addElement(name);
//        for (String each: userNames)
//        {
//
//            list.addElement(each.toString());
//        }
    }

//    public AdminHome(AdminControl adminControl)
//    {
//        this.adminControl = adminControl;
//
//    }


}
