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
    private JLabel labelWelcome;
    private JList userList;
    private JTextArea userDetailsText;
    private JLabel jobsLabel;
    private JList list1;
    private JTextArea jobDetailsText;
    private JButton lockUserButton;
    private JButton sendWarningButton;

    private AdminControl adminControl;
    private JSS program;

    private DefaultListModel list;
    private ArrayList<String> userNames;


    public AdminGUI(AdminControl adminControl, JSS program)
    {
        this.adminControl = adminControl;
        this.program = program;
        this.userNames = new ArrayList<String>();
        this.list = new DefaultListModel();
        this.userList.setModel(this.list);

        JFrame frame = new JFrame("AdminHome");
        frame.setContentPane(this.adminHomePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //requests num users from JSS and populates list that size of usernames only

        try{
            int numUsers = program.countUsers();
            for (int i=0; i<numUsers; i+=1)
            {
                String name = this.program.retrieveUsername(i);
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
                int userIndex = userList.getSelectedIndex();
                adminControl.blockedMessage(adminControl.adminID(),userIndex);

                //check messages are working bugfix:
             //program.checkMessages(1);
            }
        });
    }

    private void refreshList(String name)
    {
        userList.setVisible(true);
        userDetailsText.setBackground(Color.white);
//        System.out.println("removing elements");
//        list.removeAllElements();
//        System.out.println("adding name to list:" + name);
        list.addElement(name);
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
