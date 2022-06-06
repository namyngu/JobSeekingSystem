import javax.swing.*;

public class JobGUI {
    private JPanel jobPanel;
    private JLabel jobTitle;
    private JLabel jobEmployer;
    private JLabel jobLocation;
    private JLabel jobSalary;
    private JLabel jobType;
    private JLabel jobDescription;
    private JTextArea coverLetter;

    public JobGUI() {

        JFrame frame = new JFrame("Job Title");

        frame.setContentPane(jobPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);


    }
}
