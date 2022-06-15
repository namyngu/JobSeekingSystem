import javax.swing.*;

public class RecruiterViewCandidateGUI {
    private JPanel profilePanel;
    private JPanel profile;
    private JLabel phoneText;
    private JList candidateSkillsTable;
    private JButton sendInviteButton;
    private JComboBox selectJob;
    private RecruiterControl myParent;

    /**
     * This is the Default constructor for the class.
     */
    public RecruiterViewCandidateGUI()
    {

    }

    /**
     * This is a Non-default constructor for the class.
     * @param parent
     * @param jobSeekerID
     */
    public RecruiterViewCandidateGUI(RecruiterControl parent, int jobSeekerID) {

        myParent = parent;
        JFrame window = new JFrame("JSS: View Job Seeker");

        window.add(profilePanel);
        window.setSize(400,400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(600,20);
        window.pack();
        window.setResizable(true);
        window.setVisible(true);

    }
}
