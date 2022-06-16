import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton submitApplicationButton;

    public JobSeekerJobGUI() {

        JFrame frame = new JFrame("Job Title");

        frame.setContentPane(jobContainer);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

    }

    public JobSeekerJobGUI(JobseekerControl control, int jobID)
    {
        JFrame frame = new JFrame("Job Title");
        frame.setContentPane(jobContainer);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

        submitApplicationButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String letter = coverLetter.getText();
                control.apply(jobID,letter);

            }
        });
    }
}
