import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JobSeekerHomeGUI {

    private JobseekerControl myParent;
    private JButton profileButton;
    private JButton searchJobsButton;
    private JButton myApplicationsButton;
    private JButton messagesButton;
    private JTable jobsTable;
    private JPanel JSHomePanel;
    private JTable jobTable;
    private JTextField textField1;
    private JCheckBox fullTimeCheckBox;
    private JCheckBox casualCheckBox;
    private JButton searchButton;
    private JPanel searchPanel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTable jobSearchTable;
    private JComboBox comboBox3;
    private JTextField textField2;
    private JTabbedPane navbar;
    private JPanel home;
    private JPanel search;
    private JPanel applicationsPanel;
    private JPanel profile;
    private JLabel phoneText;
    private JList jobSeekerSkillsTable;
    private JPanel inboxPanel;
    private JPanel profilePanel;
    private JCheckBox partTimeCheckBox;
    private JButton editProfileButton;
    private JScrollPane jobseekerApplicationsTable;
    private JScrollPane recommendedJobsTable;
    private JScrollPane jobSeekerInboxTable;

    private Jobseeker jobseeker;



    public JobSeekerHomeGUI() {

        JFrame window = new JFrame("JSS: Job Seeker Home");
        window.add(navbar);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setBounds(600,40,100,100);
        window.pack();
        window.setResizable(true);
        window.setVisible(true);


        //test build table will condense into reusable method
        String[] jobListColumns = {"JobID", "Title", "Employer", "Location", "Salary", "Type"};
        String[][] jobListRows = {
                {"001", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"002", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"003", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"004", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"005", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"}
        };
        DefaultTableModel jobModel = new DefaultTableModel(jobListRows, jobListColumns);
        jobTable.setModel(jobModel);



        //Edit profile button to open edit profile GUI
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JobSeekerUpdateGUI updateGUI = new JobSeekerUpdateGUI();
            }
        });
    }

    public JobSeekerHomeGUI(User jobSeeker, JobseekerControl parent) {

        myParent = parent;
        JFrame window = new JFrame("JSS: Job Seeker Home");
        window.add(navbar);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(600,40,100,100);
        window.pack();
        window.setResizable(true);
        window.setVisible(true);



        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchDesc = textField1.getText();
                // TODO: Combo boxes need to be fixed up
                String catPrimary = "some category";
                //String catPrimary = comboBox3.getDropTarget();
                // TODO: Will we need secondary category? Or not?
                String catSecondary = "some other category";
                //String catSecondary = comboBox3.getDropTarget();
                String location = textField2.getText();
                boolean fullTime = fullTimeCheckBox.isSelected();
                boolean partTime = partTimeCheckBox.isSelected();
                boolean casual = casualCheckBox.isSelected();
                // TODO: Combo boxes need to be fixed up
                //float salMin = comboBox1.getDropTarget();
                //float salMax = comboBox2.getDropTarget();
                float salMin = 0.00F;
                float salMax = 100000.00F;
                // TODO: This class gets passed a USER, not a JOBSEEKER
                // TODO: How do we get the list of skills for the JOBSEEKER
                // TODO: if we only have access to the USER??
                // ArrayList<String> skills = jobseeker.getSkills();
                ArrayList<String> skills = new ArrayList<String>();

                // TODO: This method should return the jobs and now we need to display
                // TODO: them somehow
                myParent.jobSearch(searchDesc, catPrimary, catSecondary, location, fullTime,
                        partTime, casual, salMin, salMax, skills);
            }
        });

        /*Add these methods back in once the actual components exist on the GUI

            //navbar
            searchJobsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            myApplicationsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            messagesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            profileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });

        */
    }



}
