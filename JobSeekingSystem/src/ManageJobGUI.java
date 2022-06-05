import javax.swing.*;

public class ManageJobGUI {
    private JPanel manageJobPanel;
    private JLabel formTitle;
    private JLabel jobIDLabel;
    private JLabel jobTitleLabel;
    private JTextField jobTitleText;
    private JLabel recruiterIDLabel;
    private JLabel employerLabel;
    private JLabel jobTypeLabel;
    private JComboBox jobTypeMenu;
    private JLabel salaryLabel;
    private JTextField salaryText;
    private JComboBox salaryMenu;
    private JLabel skillsLabel;
    private JComboBox skillsMenu;
    private JButton addSkillButton;
    private JList skillsList;
    private JButton removeSkillButton;
    private JLabel keywordsLabel;
    private JButton addKeywordsButton;
    private JList keywordsList;
    private JButton removeKeywordsButton;
    private JLabel applicationsLabel;
    private JLabel locationLabel;
    private JComboBox locationStateMenu;
    private JLabel postcodeLabel;
    private JTextField postcodeText;
    private JLabel descriptionLabel;
    private JFormattedTextField descriptionText;
    private JLabel categoryLabelPrimary;
    private JLabel categoryLabelSecondary;
    private JComboBox categoryMenuPrimary;
    private JComboBox categoryMenuSecondary;
    private JLabel hoursLabel;
    private JTextField hoursText;
    private JComboBox hoursMenu;
    private JLabel statusLabel;
    private JComboBox statusMenu;
    private JLabel statusMessageLabel;
    private JLabel intNumAppLabel;
    private JLabel intJobIDLabel;
    private JLabel intRecIDLabel;
    private JLabel empNameLabel;
    private JComboBox keywordsMenu;

    public ManageJobGUI() {
        JFrame frame1 = new JFrame("Manage Job");
        frame1.setContentPane(this.manageJobPanel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        /*
        each action needs to store in the variable for the job, then at the end, create a
        job object that stores all of the info and writes it to the job database
        */

        JFrame frame = new JFrame("Manage Job");
        frame.setContentPane(new ManageJobGUI().manageJobPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}
