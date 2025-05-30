/**
 * This class is the Job Seeker Home GUI, the jobseeker will use to interact with the system.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
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
    private JLabel instructionText;
    private JLabel searchInstructionText;
    private JButton logoutButton;
    private JList inboxList;
    private JLabel skillsMessage;
    private JButton replyButton;
    private JTextField replyTextField;
    private JTextArea messageTextArea;
    private JScrollPane scrollSearch;
    private JTable applicationsTable;
    private DefaultListModel jsSkillsModel;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;

    private DefaultListModel inboxListModel;
    private ArrayList<Message> messages;

    public int searchCount;

    /**
     * This is the method to populate the categories menu.
     */
    public void populateCategories() throws IOException {
        File_Control IO = new File_Control();
        String catList = IO.readFile("CategoryList.csv");
        String[] categories = catList.split("\n");

        for (String category : categories) {
            String[] breakCategories = category.split(",");
            primaryCategoryBox.addItem(breakCategories[0]);
        }
    }

    /**
     * This is the method to populate the secondary categories menu.
     */
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

    /**
     * This is the Non-Default constructor.
     * @param parent    a JobseekerControl object to manage the jobseeker object
     * @param categories a JobCategory Arraylist to store the job categories
     * @param locations a Location ArrayList to store the locations
     * @param seeker a Jobseeker object that stores the users details
     */
    public JobSeekerHomeGUI(JobseekerControl parent, ArrayList<JobCategory> categories,
                            ArrayList<Location> locations, Jobseeker seeker) {
        JobSeekerHomeGUI home = this;
        jobseeker = seeker;
        myParent = parent;
        locationList = locations;
        jobCategoryList = categories;
        searchInstructionText.setVisible(false);
        comboBox2.setSelectedIndex(12);
        JFrame window = new JFrame("JSS: Job Seeker Home");
        window.add(navbar);
        searchCount = 0;
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(600,40,100,100);
        window.pack();
        window.setResizable(true);
        window.setVisible(true);

        createApplicationsTable();

        inboxListModel = new DefaultListModel();
        inboxList.setModel(inboxListModel);

        //retrieve messages for inbox
        if (myParent.checkMessages() == false)
        {
            refreshList("Clear inbox...",inboxList,inboxListModel);
        }
        else
        {
//            JSS program = new JSS();
//            this.userMessages = pr
            messages = myParent.fetchMessages();

            for (Message each: messages)
            {

                String toDisplay = "";

                File_Control io = new File_Control();
                String senderName = io.fileSearchId(each.getSenderID(), "users.csv").get(2);
                //String senderName = myParent.retrieveUserName();

                toDisplay += senderName + " Re: " + each.getHeader();

                refreshList(toDisplay,inboxList,inboxListModel);
            }
        }

        //double click on jobs to bring up the jobs menu
        applicationsTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                applicationsTable = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = applicationsTable.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && applicationsTable.getSelectedRow() != -1) {
                    // Action to take after double clicking.
                    int selectedRow = applicationsTable.getSelectedRow();
                    int ID = Integer.parseInt(applicationsTable.getValueAt(selectedRow, 0).toString());
                    JobSeekerJobGUI jobSeekerJobGUI= new JobSeekerJobGUI(parent, ID);
                    window.dispose();
                }
            }
        });



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
        if(jsSkillsModel.getSize() == 0)
        {
            skillsMessage.setText("Listing your skills allows JSS to provide job recommendations relevant to you. \nPlease select the Edit Profile button to add new skills.");
        }

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

                // Display instructions for double-clicking Jobs, if there are results.
                if (!searchResults.isEmpty()) {
                    searchInstructionText.setVisible(true);
                } else {
                    searchInstructionText.setVisible(false);
                }
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

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(window, "Are you sure you want to Logout?","Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    Start program = new Start();
                    window.dispose();
                }
            }
        });
        replyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Message replyTo = messages.get(inboxList.getSelectedIndex());

                int senderID = replyTo.getReceiverID();
               int receiverID = replyTo.getSenderID();
               String header = "Re: ";
               String body = replyTextField.getText();
               LocalDate sentDate = LocalDate.now();
                Message message = new Message(senderID,receiverID,header,body,sentDate);
               if ( myParent.sendMessageNoID(message))
               {
                   PromptGUI confirm = new PromptGUI("Reply sent");
                    replyTextField.setText("");
               }
            }
        });
        inboxList.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                int selected = inboxList.getSelectedIndex();
                String display = messages.get(selected).getHeader();
                display += "\n\n";
                display = messages.get(selected).getBody();
                messageTextArea.setText(display);

            }
        });
    }

    /**
     * This is the method to create the applications table.
     */
    private void createApplicationsTable()
    {
        ArrayList<Application> myApps = new ArrayList<>();
        File_Control io = new File_Control();

        String rawInput = "";
        try {
            rawInput = io.readFile("messages.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rawInput = rawInput.replaceAll("~", "n");
        String[] messageString = rawInput.split("\n");

        try {
            for (int i = 0; i < messageString.length; i++) {
                if (messageString[i].isEmpty()) {
                    System.out.println("Warning: empty line in messages.csv at line: " + i + ", skipping...");
                    continue;
                }

                String[] messageDetails = messageString[i].split(",");
                messageDetails[5] = messageDetails[5].replaceAll("`", ",");

                int messageID = Integer.parseInt(messageDetails[0]);
                int sender = Integer.parseInt(messageDetails[2]);
                int messageTo = Integer.parseInt(messageDetails[3]);
                String dateStr = messageDetails[7];
                LocalDate date = LocalDate.parse(dateStr);
                if (sender == myParent.getJobseeker().getUserID()) {
                    System.out.println("messageDetails[4] is: " + messageDetails[4]);
                    boolean hasReceived = false;
                    if (messageDetails[1].equalsIgnoreCase("sent")) {
                        hasReceived = true;
                    }
                    if (messageDetails[4].contains("Application")) {
                        int jobRef = Integer.parseInt(messageDetails[6]);




                        myApps.add(new Application(messageID, hasReceived, sender, messageTo, "Application", messageDetails[5], jobRef, date));
                        System.out.println("adding application");
                    }
                    System.out.println("messageTo is: " + messageTo);
                    System.out.println("userID is: " + myParent.getJobseeker().getUserID());
                    System.out.println("messageID is: " + messageID);
                }
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        String appInput = "";
        try {
            appInput = io.readFile("Application.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] appString = appInput.split("\n");

        try {
            for (int i = 1; i < appString.length; i++) {
                if (appString[i].isEmpty()) {
                    System.out.println("Warning: empty line in Application.csv at line: " + i + ", skipping...");
                    continue;
                }

                String[] appDetails = appString[i].split(",");
                String status = appDetails[2];
                System.out.println("status is: " + status);

                for (Application tmpApp : myApps) {
                    int x = 0;
                    System.out.println("appDetails[0] is: " + appDetails[0]);
                    System.out.println("tmpApp.getMessageID() is: " + tmpApp.getMessageID());
                    if (Integer.parseInt(appDetails[0]) == tmpApp.getMessageID()) {
                        tmpApp.setStatus(status);
                        myApps.set(x, tmpApp);
                        break;
                    }
                    x++;
                }
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        for (Application tmpApp : myApps) {
            tmpApp.display();
            System.out.println("status is: " + tmpApp.getStatus());
        }


        int columns = 4;
        String[][] data = new String[myApps.size()][columns];
        ArrayList<Job> jobList = myParent.getJobList();

        for (int i = 0; i < myApps.size(); i++)
        {
            for (int j = 0; j < columns; j++)
            {
                switch (j)
                {
                    case 0:
                        data[i][j] = String.valueOf(myApps.get(i).getJobRef());
                        break;

                    case 1:
                        for (Job tmpJob : jobList) {
                            if (myApps.get(i).getJobRef() == tmpJob.getJobID()) {
                                data[i][j] = tmpJob.getJobTitle();
                                break;
                            }
                        }
                        break;

                    case 2:
                        for (Job tmpJob : jobList) {
                            if (myApps.get(i).getJobRef() == tmpJob.getJobID()) {
                                data[i][j] = tmpJob.getEmployer();
                                break;
                            }
                        }
                        break;

                    case 3: {
                        data[i][j] = myApps.get(i).getStatus();
                        break;
                    }

                    default:
                        System.out.println("Error");
                        break;
                }

            }
        }

        applicationsTable.setModel(new DefaultTableModel(data, new String[]{"JobID","Title","Employer","Status"}));
    }

    /**
     * This is the method to display recommended jobs.
     */
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

        // Display instructions for double-clicking Jobs, if there are results.
        if (!searchResults.isEmpty()) {
            instructionText.setVisible(true);
        } else {
            instructionText.setVisible(false);
        }
    }

    /**
     * This is the method to build the skill list.
     */
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

    /**
     * This is the method to build contact info.
     */
    public void buildContactInfo()
    {
        jobSeekerFullname.setText(myParent.getFullName());
        jobseekerEmail.setText(myParent.getEmail());
        jobseekerPhone.setText(myParent.getPhone());
        jobseekerLocation.setText(myParent.getLocation().toString());
    }

    /**
     * This is the method to refresh a list.
     */
    private void refreshList(String content, JList list, DefaultListModel listModel)
    {
        list.setVisible(true);
        listModel.addElement(content);
    }


}
