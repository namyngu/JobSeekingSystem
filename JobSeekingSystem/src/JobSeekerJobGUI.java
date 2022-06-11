import javax.swing.*;

public class JobSeekerJobGUI {
    private JPanel jobContainer;
    private JLabel jobTitle;
    private JLabel jobEmployer;
    private JLabel jobLocation;
    private JLabel jobSalary;
    private JLabel jobType;
    private JLabel jobDescription;
    private JTextArea coverLetter;
    private JPanel jobPanel;

    public JobSeekerJobGUI() {

        JFrame frame = new JFrame("Job Title");

        frame.setContentPane(jobContainer);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);



    }
}
