import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

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
    private JLabel jobseekerPhone;

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
    private JLabel jobseekerLocation;
    private JLabel jobseekerEmail;
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

        secondaryCategoryBox.removeAllItems();
        secondaryCategoryBox.setSelectedItem("Category");

        for (String category: categories) {

            String[] breakCategories = category.split(",");
                if (breakCategories[0].equals(primaryCategory)) {
                    secondaryCategoryBox.addItem("All");
                for (int x = 1; x < breakCategories.length; x++) {
                    secondaryCategoryBox.addItem(breakCategories[x]);
                }
            }
        }
    }

    public JobSeekerHomeGUI(JobseekerControl parent, ArrayList<JobCategory> categories,
                            ArrayList<Location> locations, Jobseeker seeker) {
        JobSeekerHomeGUI home = this;
        jobseeker = seeker;
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

        //display contact information in profile
        buildContactInfo();
        //display skills in profile
        buildSkillList();

        // Display recommended jobs for this Job seeker
        displayRecommendedJobs();

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

                TreeMap<Integer, ArrayList<Job>> searchResults = myParent.jobSearch(searchDesc, catPrimary, catSecondary, location, fullTime,
                        partTime, casual, salMin, salMax, skills);

                String[] jobListColumns = {"Match Score", "Job ID #", "Title", "Employer", "Location", "Salary", "Type"};
                ArrayList<String[]> jobListRows = new ArrayList<>();

                for (Integer key : searchResults.keySet()) {
                    for (int i = 0; i < searchResults.get(key).size(); i++) {
                        Job job = searchResults.get(key).get(i);
                        String resultLocation = "";
                        for (Location place : locationList) {
                            if (place.getLocationID() == job.getLocationID()) {
                                resultLocation = place.toString();
                                break;
                            }
                        }
                        String [] thisJob = {Integer.toString(key), Integer.toString(job.getJobID()),
                                    job.getJobTitle(), job.getEmployer(), resultLocation,
                                    Integer.toString(job.getSalary()), job.getJobType()};
                        jobListRows.add(thisJob);
                    }
                }

                String[][] rows = jobListRows.toArray(new String[0][0]);

                DefaultTableModel freshModel = new DefaultTableModel(rows, jobListColumns);
                jobSearchTable.setModel(freshModel);
                searchResultsScroll.setVisible(true);
                searchPanel.repaint();
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
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JobSeekerUpdateGUI updateGUI = new JobSeekerUpdateGUI(myParent, home, locations );
            }
        });

        // Add a listener so that double-clicking a search result opens that Job
        jobSearchTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                Point point = mouseEvent.getPoint();
                int row = jobSearchTable.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && jobSearchTable.getSelectedRow() != -1) {
                    // Action to take after double clicking.
                    int selectedRow = jobSearchTable.getSelectedRow();
                    int jobID = Integer.parseInt(jobSearchTable.getValueAt(selectedRow, 1).toString());
                    JobSeekerJobGUI JobSeekerJobGUI= new JobSeekerJobGUI(myParent, jobID);
                }
            }
        });

        // Add a listener so that double-clicking a recommended job opens that Job
        jobTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                Point point = mouseEvent.getPoint();
                int row = jobTable.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && jobTable.getSelectedRow() != -1) {
                    // Action to take after double clicking.
                    int selectedRow = jobTable.getSelectedRow();
                    int jobID = Integer.parseInt(jobTable.getValueAt(selectedRow, 1).toString());
                    JobSeekerJobGUI JobSeekerJobGUI= new JobSeekerJobGUI(myParent, jobID);
                }
            }
        });

    }


    public void displayRecommendedJobs() {
        // Obtain a list of recommended jobs for this seeker.
        TreeMap<Integer, ArrayList<Job>> searchResults = myParent.recommendedSearch();

        /* Set the list of recommended jobs into the recommended jobs panel.
         * First, set the columns up.
         */
        String[] jobListColumns = {"Match Score", "Job ID #", "Title", "Employer", "Location", "Salary", "Type"};

        // Set each Job up as a Row. Need to do some work to populate the location field.
        ArrayList<String[]> jobListRows = new ArrayList<>();

        // Set the job details up into the fields in the row.
        for (Integer key : searchResults.keySet()) {
            for (int i = 0; i < searchResults.get(key).size(); i++) {
                Job job = searchResults.get(key).get(i);
                String resultLocation = "";

                for (Location place : locationList) {
                    if (place.getLocationID() == job.getLocationID()) {
                        resultLocation = place.toString();
                        break;
                    }
                }

                String [] thisJob = {Integer.toString(key), Integer.toString(job.getJobID()),
                        job.getJobTitle(), job.getEmployer(), resultLocation,
                        Integer.toString(job.getSalary()), job.getJobType()};

                // Add this row to the list of rows.
                jobListRows.add(thisJob);
            }
        }

        // Convert the list of rows into a TableModel readable format.
        String[][] rows = jobListRows.toArray(new String[0][0]);

        // Set the Table Model into the results table.
        DefaultTableModel freshModel = new DefaultTableModel(rows, jobListColumns);
        jobTable.setModel(freshModel);
        jobTable.repaint();
        searchResultsScroll.setVisible(false);

        // Setup the initial search table.
        DefaultTableModel jobSearchModel = new DefaultTableModel(null, jobListColumns);
        jobSearchTable.setModel(jobSearchModel);
    }

    public void buildSkillList(){
        jsSkillsModel = new DefaultListModel<>();
        ArrayList<String> skills = myParent.getSkills();

        for (int i = 0; i < skills.size(); i++)
        {
            jsSkillsModel.addElement("- "+skills.get(i));
        }

        System.out.println();

        jsSkillsTable.setModel(jsSkillsModel);
    }


    public void buildContactInfo()
    {
        jobSeekerFullname.setText(myParent.getFullName());
        jobseekerEmail.setText(myParent.getEmail());
        jobseekerPhone.setText(myParent.getPhone());
        jobseekerLocation.setText(myParent.getLocation().toString());
    }






}
