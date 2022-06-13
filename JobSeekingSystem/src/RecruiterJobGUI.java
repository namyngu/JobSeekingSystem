import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RecruiterJobGUI {
    private JPanel jobPanel;
    private JPanel jobContainer;
    private JLabel jobTitle;
    private JLabel jobLocation;
    private JLabel jobSalary;
    private JLabel jobDescription;
    private JTable applicationsTable;
    private JScrollPane applicationScrollPane;
    private JButton editJobButton;
    private JPanel recruiterJobGUI;
    private JLabel applicantNum;
    private JLabel statusLabel;
    private JLabel locationLabel;
    private JLabel employerLabel;
    private JLabel jobTypeLabel;
    private JScrollPane descriptionScrollPane;
    private JTextPane jobDescriptionPane;

    private RecruiterControl control;
    private int jobID;
    private Job myJob;

    public RecruiterJobGUI(RecruiterControl control, int jobID)
    {
        this.control = control;
        this.jobID = jobID;

        JFrame frame = new JFrame("RecruiterJobGUI");
        frame.setContentPane(this.recruiterJobGUI);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocation(650,40);

        //find job from the jobID
        try
        {
            myJob = File_Control.findJob(control.getJobList(), jobID);
        }
        catch (Exception e)
        {
            System.out.println("ERROR cannot find job, exiting...");
            frame.dispose();
        }
        frame.setVisible(true);

        //setting parameters for job GUI
        jobTitle.setText(myJob.getJobTitle());
        //TODO: Posted date
        applicantNum.setText(String.valueOf(myJob.getApplications().size()));
        statusLabel.setText(myJob.getJobStatus());
        employerLabel.setText(myJob.getEmployer());

        //finding location of job from locationID
        Location jobLocation;
        for (Location tmpLocation : control.getLocationList())
        {
            if (tmpLocation.getLocationID() == myJob.getLocationID())
            {
                jobLocation = tmpLocation;
                locationLabel.setText(jobLocation.getCity() + ", " + jobLocation.getPostcode() + " " + jobLocation.getState());
                break;
            }
        }
        //TODO: format salary to make it look nice using regEx
        jobSalary.setText("$ " + myJob.getSalary());
        jobTypeLabel.setText((myJob.getJobType()));
        //removing double quotes at the start and end of a string, if it exists.
        String jobDescription = myJob.getJobDescription().replaceAll("^\"|\"$","");
        jobDescriptionPane.setText(jobDescription);
        jobDescriptionPane.setFocusable(false);
        createTable();

        editJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ManageJobGUI manageJob = new ManageJobGUI(control, myJob);
                    frame.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void createTable()
    {
        ArrayList<Application> applicationList = myJob.getApplications();

        String[][] data = new String[applicationList.size()][4];
        for (int i = 0; i < applicationList.size(); i++)
        {
            User jobseeker = null;
            //get applicant details
            try
            {
                int jobseekerID = applicationList.get(i).getSenderID();
                jobseeker = File_Control.findUser(control.getUserList(), jobseekerID);
            }
            catch (Exception e)
            {
                System.out.println("ERROR cannot find applicant/jobseeker");
            }

            for (int j = 0; j < 6; j++)
            {
                switch (j)
                {
                    case 0:
                        data[i][j] = String.valueOf(applicationList.get(i).getApplicationID());
                        break;

                    case 1:
                        data[i][j] = jobseeker.getFirstName() + " " + jobseeker.getLastName();
                        break;

                        //TODO: Do this once Brad has done the userContacts csv.
                    case 2:
                        break;

                        //TODO: find a way to display date of application.
                    case 3: {
                        break;
                    }

                    default:
                        System.out.println("Error");
                        break;
                }

            }
        }

        applicationsTable.setModel(new DefaultTableModel(data, new String[]{"Application ID","Name","Location","Date"}));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
