import javax.swing.*;
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
//                System.out.println("name added to list " + name);
            }



//            userList.setModel(list);


        }
        catch (Exception e)
        {
            e.printStackTrace();
            PromptGUI error = new PromptGUI("Contact developer", e.toString());
        }
    }

//    public AdminHome(AdminControl adminControl)
//    {
//        this.adminControl = adminControl;
//
//    }


}
