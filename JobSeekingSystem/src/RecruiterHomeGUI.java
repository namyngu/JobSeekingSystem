import javax.swing.*;

public class RecruiterHomeGUI {
    private JTabbedPane recruiterNav;
    private JPanel jobsContainer;
    private JTable jobsTable;
    private JPanel searchPanel;
    private JPanel applicationsPanel;
    private JPanel inboxPanel;
    private JPanel jobsPanel;
    private JPanel inboxContainer;
    private JPanel updateSkillsPanel;
    private JLabel candidateLocationLabel;
    private JTextField textField1;
    private JButton addSkillButton;
    private JButton removeSkillButton;
    private JButton searchButton;
    private JList allSkillList;
    private JList candidateSkillList;
    private JTable applicationsTable;
    private JTable inboxTable;

    public RecruiterHomeGUI(){
        JFrame window = new JFrame("JSS: Recruiter Home");
        window.add(recruiterNav);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(600,40,100,100);
        window.pack();
        window.setResizable(true);
        window.setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}


