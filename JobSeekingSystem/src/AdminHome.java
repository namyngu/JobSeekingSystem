import javax.swing.*;

public class AdminHome {
    private JPanel panel1;
    private JPanel adminHomePanel;
    private JLabel labelWelcome;
    private JList userList;
    private AdminControl adminControl;



    public AdminHome(AdminControl adminControl)
    {
        JFrame frame = new JFrame("AdminHome");
        frame.setContentPane(this.adminHomePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

//    public AdminHome(AdminControl adminControl)
//    {
//        this.adminControl = adminControl;
//
//    }


}
