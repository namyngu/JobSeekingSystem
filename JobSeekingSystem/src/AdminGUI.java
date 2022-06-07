import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

public class AdminGUI
{
    private JPanel panel1;
    private JPanel adminHomePanel;
    private JLabel labelWelcome;
    private JList userList;

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
//                System.out.println("name added to list " + name);
            }

//            this.userList.setVisible(true);



//            userList.setModel(list);


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

            }
        });
    }

    private void refreshList(String name)
    {
        userList.setVisible(true);
//        System.out.println("removing elements");
//        list.removeAllElements();
        System.out.println("adding name to list:" + name);
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
