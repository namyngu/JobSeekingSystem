import javax.swing.*;

public class RecruiterHomeGUI {
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
    private JLabel welcomeMSG;

    public RecruiterHomeGUI()
    {

    }
    public RecruiterHomeGUI(User recruiter)
    {
        JFrame homeFrame = new JFrame("RecruiterHomeGUI");
        homeFrame.setContentPane(this.recruiterHome);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setBounds(600,40,100,100);
        homeFrame.pack();

        homeFrame.setResizable(true);
        homeFrame.setVisible(true);
    }
}
