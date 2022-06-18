import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JLabel salaryLabel;

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


        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                new RecruiterHomeGUI(control, control.getLocationList());
                frame.dispose();
            }
        });

        //find job from the jobID
        try
        {
            myJob = control.findJob(control.getJobList(), jobID);
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
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //double click on jobs to bring up the jobs menu
        applicationsTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                applicationsTable = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = applicationsTable.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && applicationsTable.getSelectedRow() != -1) {
                    // Action to take after double clicking.
                    int selectedRow = applicationsTable.getSelectedRow();
                    //get aoplicationID from table
                    int applicationID = Integer.parseInt(applicationsTable.getValueAt(selectedRow, 0).toString());

                    //get jobseeker details from the selected application
                    for ( Application tmpApplication: myJob.getApplications())
                    {
                        if (tmpApplication.getMessageID() == applicationID)
                        {
                            //get jobseeker ID
                            int jobseekerID = tmpApplication.getSenderID();
                            //Launch candidate profile
                            RecruiterViewCandidateGUI candidateGUI = new RecruiterViewCandidateGUI(control, jobseekerID);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void createTable()
    {
        ArrayList<Application> applicationList = myJob.getApplications();

        String[][] data = new String[applicationList.size()][5];
        for (int i = 0; i < applicationList.size(); i++)
        {
            User jobseeker = null;
            //get applicant details
            try
            {
                int jobseekerID = applicationList.get(i).getSenderID();
                jobseeker = findUser(control.getUserList(), jobseekerID);
            }
            catch (Exception e)
            {
                System.out.println("ERROR cannot find applicant/jobseeker");
            }

            for (int j = 0; j < 5; j++)
            {
                switch (j)
                {
                    case 0:
                        data[i][j] = String.valueOf(applicationList.get(i).getMessageID());
                        break;

                    case 1:
                        data[i][j] = jobseeker.getFirstName() + " " + jobseeker.getLastName();
                        break;

                        //Get email from csv.
                    case 2: {

                        String content = "";
                        File_Control io = new File_Control();
                        try {
                            content = io.readFile("user-contact.csv");
                        } catch (Exception e) {
                            System.out.println("ERROR cannot read user-contact info!");
                        }
                        String[] userContact = content.split("\n");
                        for (int k = 0; k < userContact.length; k++) {
                            String[] userContactDetails = userContact[k].split(",");
                            if (userContactDetails[0].equalsIgnoreCase(String.valueOf(jobseeker.getUserID()))) {
                                data[i][j] = userContactDetails[2];
                                break;
                            }
                        }
                        break;
                    }

                    case 3: {
                        data[i][j] = applicationList.get(i).getSentDate().toString();
                        break;
                    }

                    case 4: {
                        data[i][j] = applicationList.get(i).getStatus();
                    }

                    default:
                        System.out.println("Error");
                        break;

                }

            }
        }

        applicationsTable.setModel(new DefaultTableModel(data, new String[]{"Application ID","Name","Email","Applied at","Status"}));
    }

    /**
     * This method looks for a specific User in the list of Users and returns it.
     * @param userList  an ArrayList of Users in the system.
     * @param ID        an Integer containing the User ID number to be searched for.
     * @return          a User which matches the specified ID number.
     * @throws Exception Exceptions are thrown if the specified User cannot be found.
     */
    public User findUser(ArrayList<User> userList, int ID) throws Exception
    {
        User myUser = null;
        for (User tmpUser : userList)
        {
            if (tmpUser.getUserID() == ID)
            {
                myUser = tmpUser;
                return myUser;
            }
        }
        throw new Exception("Error: User doesn't exist!");
    }
}
