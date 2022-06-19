/**
 * This class is the GUI form for when a Jobseeker wants to view more details about a particular Job.
 * @author  Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

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

    /**
     * This is the Default constructor for the class.
     */
    public JobSeekerJobGUI() {

        JFrame frame = new JFrame("Job Title");

        frame.setContentPane(jobContainer);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

    }

    /**
     * This is the Non-default constructor for the class.
     * @param control   a JobseekerControl Object which is controlling the currently
     *                  logged-in Jobseeker.
     * @param jobID     an Integer containing the ID number of the Job which the
     *                  Jobseeker is wanting to view details for.
     */
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
