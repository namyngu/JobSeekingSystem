import javax.swing.*;

public class HomeGUI {
    private JLabel yourJobLabel;
    private JButton messagesButton;
    private JButton createJobButton;
    private JPanel recruiterHome;
    private JButton homeButton;
    private JButton searchCandidatesButton;
    private JButton logoutButton;
    private JPanel headerPanel;
    private JButton viewButton;
    private JPanel jobPanel1;
    private JLabel jobStatus;
    private JPanel jobPanel2;
    private JLabel jobTitle;
    private JLabel employerLabel;
    private JLabel postedLabel;
    private JLabel applicantsLabel;
    private JLabel newApplicantsLabel;
    private JLabel jobTitle2;
    private JLabel employerLabel2;
    private JLabel postedLabel2;
    private JLabel applicantsLabel2;
    private JLabel newApplicantsLabel2;
    private JLabel jobStatusLabel2;
    private JButton viewButton2;

    public HomeGUI()
    {

    }
    public HomeGUI(User recruiter)
    {
        JFrame homeFrame = new JFrame("HomeGUI");
        homeFrame.setContentPane(this.recruiterHome);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setBounds(600,50,1000,800);
        homeFrame.pack();

        homeFrame.setResizable(true);
        homeFrame.setVisible(true);
    }
}
