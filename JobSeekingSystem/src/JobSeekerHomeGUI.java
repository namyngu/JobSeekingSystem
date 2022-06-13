import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private JComboBox primaryCategoryBox;
    private JComboBox secondaryCategoryBox;
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

    public void populateCategories() throws IOException {
        File_Control IO = new File_Control();
        String catList = IO.readFile("CategoryList.csv");
        String[] categories = catList.split("\n");

        for (String category : categories) {
            String[] breakCategories = category.split(",");
            primaryCategoryBox.addItem(breakCategories[0]);
        }
    }

    public void populateSecondaryCategories(String primaryCategory) throws IOException {
        File_Control IO = new File_Control();
        String catList = IO.readFile("CategoryList.csv");
        String[] categories = catList.split("\n");

        for (String category: categories) {

            String[] breakCategories = category.split(",");
                if (breakCategories[0].equals(primaryCategory)) {
                for (int x = 1; x < breakCategories.length; x++) {
                    secondaryCategoryBox.addItem(breakCategories[x]);
                }
            }
        }
    }

    public JobSeekerHomeGUI(JobseekerControl parent, ArrayList<JobCategory> categories,
                            ArrayList<Location> locations) {
        JobSeekerHomeGUI home = this;
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

        // Try populate categories to search in.
        try {
            populateCategories();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

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
                String catPrimary = String.valueOf(primaryCategoryBox.getSelectedItem());
                String catSecondary = String.valueOf(secondaryCategoryBox.getSelectedItem());
                String location = textField2.getText();
                boolean fullTime = fullTimeCheckBox.isSelected();
                boolean partTime = partTimeCheckBox.isSelected();
                boolean casual = casualCheckBox.isSelected();
                int salMin = Integer.parseInt(comboBox1.getSelectedItem().toString());
                int salMax = Integer.parseInt(comboBox2.getSelectedItem().toString());
                ArrayList<String> skills = jobseeker.getSkills();

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

        primaryCategoryBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    populateSecondaryCategories(String.valueOf(primaryCategoryBox.getSelectedItem()));
                }
                catch (Exception x) {
                    System.out.println("Problem loading secondary categories!");
                    x.printStackTrace();
                }
            }
        });

        /*Add these methods back in once the actual components exist on the GUI

            //navbar
            searchJobsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JobSeekerUpdateGUI updateGUI = new JobSeekerUpdateGUI(myParent, home );
            }
        });
    }

        */
    }

    public void buildSkillList(){
        jsSkillsModel = new DefaultListModel<>();
        ArrayList<String> skills = myParent.getSkills();
        System.out.println("In JS Home GUI");
        System.out.println(skills);

        for (int i = 0; i < skills.size(); i++)
        {
            jsSkillsModel.addElement("- "+skills.get(i));
        }

        System.out.println();

        jsSkillsTable.setModel(jsSkillsModel);
    }






}
