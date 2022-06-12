import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

    private JPanel inboxPanel;
    private JPanel profilePanel;
    private JCheckBox partTimeCheckBox;
    private JButton editProfileButton;
    private JScrollPane jobseekerApplicationsTable;
    private JScrollPane recommendedJobsTable;
    private JScrollPane jobSeekerInboxTable;
    private JLabel jobSeekerFullname;

    private Jobseeker jobseeker;

    private JList jsSkillsTable;
    private JLabel resultsHeading;
    private JScrollPane searchResultsScroll;
    private DefaultListModel jsSkillsModel;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;

    public int searchCount;


    public JobSeekerHomeGUI(JobseekerControl parent, ArrayList<JobCategory> categories,
                            ArrayList<Location> locations) {

        myParent = parent;
        locationList = locations;
        jobCategoryList = categories;
        JFrame window = new JFrame("JSS: Job Seeker Home");
        window.add(navbar);
        searchCount = 0;
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(600,40,100,100);
        window.pack();
        window.setResizable(true);
        window.setVisible(true);

        //display name in profile
        jobSeekerFullname.setText(parent.getFullName());

        //display skills in profile
        buildSkillList();

        //test build table will condense into reusable method
     String[] jobListColumns = {"JobID", "Title", "Employer", "Location", "Salary", "Type"};
        String[][] jobListRows = {
                {"001", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"002", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"003", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"004", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"005", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"}
        };
        DefaultTableModel jtest = new DefaultTableModel(jobListRows, jobListColumns);
        jobTable.setModel(jtest);

        searchResultsScroll.setVisible(false);
        //initial search table
        DefaultTableModel jobSearchModel = new DefaultTableModel(null, jobListColumns);
        jobSearchTable.setModel(jobSearchModel);



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
                int salMin = Integer.parseInt(comboBox1.getSelectedItem().toString());
                int salMax = Integer.parseInt(comboBox2.getSelectedItem().toString());
                // TODO: This class gets passed a USER, not a JOBSEEKER
                // TODO: How do we get the list of skills for the JOBSEEKER
                // TODO: if we only have access to the USER??
                // ArrayList<String> skills = jobseeker.getSkills();
                ArrayList<String> skills = new ArrayList<String>();

                ArrayList<Job> searchResults = myParent.jobSearch(searchDesc, catPrimary, catSecondary, location, fullTime,
                        partTime, casual, salMin, salMax, skills);

                String[] jobListColumns = {"JobID", "Title", "Employer", "Location", "Salary", "Type"};
                ArrayList<String[]> jobListRows = new ArrayList<>();
                for (Job job : searchResults) {
                    int resultNum = 1;
                    String resultLocation = locationList.get(job.getLocationID()-1).toString();
                    String[] thisJob = {Integer.toString(resultNum), job.getJobTitle(), job.getEmployer(),
                            resultLocation, Integer.toString(job.getSalary()), job.getJobType()};
                    jobListRows.add(thisJob);
                }

                String[][] rows = jobListRows.toArray(new String[0][0]);

                DefaultTableModel freshModel = new DefaultTableModel(rows, jobListColumns);
                jobSearchTable.setModel(freshModel);
                searchResultsScroll.setVisible(true);
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


    private void buildSkillList(){
        jsSkillsModel = new DefaultListModel<>();
        ArrayList<String> skills = myParent.getSkills();
        System.out.println("In JS Home GUI");
        System.out.println(skills);

        for (int i = 0; i < skills.size(); i++)
        {
            jsSkillsModel.addElement(skills.get(i));
        }

        System.out.println();

        jsSkillsTable.setModel(jsSkillsModel);
    }



}
