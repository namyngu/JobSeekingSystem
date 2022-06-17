import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;

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

        // Initialize our fields so we can obtain necessary job information to display.
        jobList = control.getJobList();
        locationList = control.getLocationList();

        // Apply Job Details to the GUI.
        Job thisJob = new Job();
        int thisJobLocationID = -1;
        for (Job job : jobList) {
            if (job.getJobID() == jobID) {
                thisJob = job;
                thisJobLocationID = job.getLocationID();
                break;
            }
        }

        Location thisJobLocation = new Location();
        for (Location place : locationList) {
            if (place.getLocationID() == thisJobLocationID) {
                thisJobLocation = place;
                break;
            }
        }

        jobTitle.setText(thisJob.getJobTitle());
        jobEmployer.setText(thisJob.getEmployer());
        jobLocation.setText(thisJobLocation.toString());
        jobSalary.setText("$" + thisJob.getSalary());
        jobType.setText(thisJob.getJobType());

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
