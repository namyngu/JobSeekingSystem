/**
 * This class represents the Create a Job function
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;

public class CreateJobGUI {
    private JLabel intJobIDLabel;
    private JLabel jobTitleLabel;
    private JTextField jobTitleText;
    private JLabel intRecIDLabel;
    private JLabel employerLabel;
    private JLabel jobTypeLabel;
    private JComboBox jobTypeMenu;
    private JLabel salaryLabel;
    private JTextField salaryText;
    private JLabel skillsLabel;
    private JComboBox skillsMenu;
    private JList skillsList;
    private JButton addSkillButton;
    private JButton removeSkillButton;
    private JLabel locationLabel;
    private JComboBox locationStateMenu;
    private JLabel postcodeLabel;
    private JComboBox postcodeMenu;
    private JTextArea descriptionText;
    private JComboBox categoryMenuPrimary;
    private JComboBox categoryMenuSecondary;
    private JLabel statusLabel;
    private JComboBox statusMenu;
    private JButton submitButton;
    private JTextField employerText;
    private JLabel statusMessageLabel;
    private JLabel categoryLabelPrimary;
    private JLabel categoryLabelSecondary;
    private JPanel createJobPanel;
    private JLabel descriptionWarning;
    private JLabel employerWarning;
    private JLabel jobTitleWarning;
    private JLabel salaryWarning;
    private JLabel categoryWarning;
    private JLabel locationWarning;
    private JLabel skillsWarning;
    private JPanel createPanel;
    private JScrollPane scrollCreateJob;
    private User recruiter;
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;
    private Job job;
    private RecruiterControl recruiterControl;
    private ArrayList<String> skills;
    private JobCategory category;

    /**
     * This is the Default constructor.
     */
    public CreateJobGUI() {

    }

    /**
     * This is a Non-default constructor for the class.
     * @param recruiterControl  RecruiterControl Object which is controlling the
     *                          Recruiter User.
     */
    public CreateJobGUI(RecruiterControl recruiterControl) throws IOException {
        JFrame frame = new JFrame("Create Job");
        frame.setSize ( 500, 300 );
        frame.setLocationRelativeTo ( null );
        frame.setContentPane(this.createJobPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(650,40);

        //Close button event
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Exit to Home?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    RecruiterHomeGUI recruiterHomeGUI = new RecruiterHomeGUI(recruiterControl, locationList);
                    frame.dispose();
                }
            }
        });

        this.recruiterControl = recruiterControl;
        this.recruiter = recruiterControl.getRecruiter();
        this.jobList = recruiterControl.getJobList();
        this.locationList = recruiterControl.getLocationList();
        this.jobCategoryList = recruiterControl.getJobCategoryList();
        category = new JobCategory();
        this.job = new Job();
        int numJobs = jobList.size() + 1;
        job.setJobID(numJobs);
        intJobIDLabel.setText(String.valueOf("Job ID: \n" + numJobs));
        job.setRecruiterID(recruiter.getUserID());

        populateSkills("SkillList.csv", skillsMenu);
        populateCategories("CategoryList.csv", categoryMenuPrimary, categoryMenuSecondary);

        DefaultListModel skillsListGUI = new DefaultListModel();
        skills = job.getSkills();

        salaryText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // if it's not a number, ignore the event
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField[] inputs = {jobTitleText, employerText, salaryText};
                JLabel[] warnings = {jobTitleWarning, employerWarning,salaryWarning };
                JLabel[] labels = {jobTitleLabel, employerLabel, salaryLabel};

                if (!Validation.validInputs(inputs, warnings, labels))
                {
                    return;
                };

                if (skillsList.getModel().getSize() == 0) {
                    Validation.invalidInputWarning(skillsWarning, "Jobs must require at least 1 skill");
                    return;
                }
                if (String.valueOf(locationStateMenu.getSelectedItem()).isEmpty() || String.valueOf(postcodeMenu.getSelectedItem()).isEmpty()) {
                    Validation.invalidInputWarning(locationWarning, "Jobs require location details");
                    return;
                }

                if (String.valueOf(categoryMenuSecondary.getSelectedItem()).isEmpty() || String.valueOf(categoryMenuSecondary.getSelectedItem()).isEmpty()) {
                    Validation.invalidInputWarning(categoryWarning, "Job category is required");
                    return;
                }
                if (descriptionText.getText().isEmpty()) {
                    Validation.invalidInputWarning(descriptionWarning, "Job description cannot be blank");
                    return;
                }
                frame.setVisible(false);
                try {
                    submitButtonActions(job);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                //write to JobList and JobCategory and JobSkill csv
                newJobToDatabase();
            }
        });

        addSkillButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                skillsListGUI.addElement(String.valueOf(skillsMenu.getSelectedItem()));
                skillsList.setModel(skillsListGUI);
            }
        });

        removeSkillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skillsListGUI.removeElement(skillsList.getSelectedValue());
                skillsList.setModel(skillsListGUI);
            }
        });

        categoryMenuPrimary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    populateSecondaryCategories("CategoryList.csv", categoryMenuPrimary, categoryMenuSecondary);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        locationStateMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    populatePostcode("Location.csv", locationStateMenu, postcodeMenu);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void createUIComponents() {

    }

    /**
     * This is the method to write a newly created job
     * to the database.
     */
    public void newJobToDatabase() {
        File_Control io = new File_Control();
        io.saveJob(job.getJobID(), job.getJobTitle(), job.getEmployer(), recruiter.getUserID(),
                job.getJobType(), job.getJobStatus(), job.getSalary(), job.getLocationID(), job.getJobDescription(), job.getSkills(), category);

        //update JobList
        jobList.add(job);
        //add job to recruiter's job list
        recruiterControl.getRecruiter().getJobs().add(job);
        //recruiterControl.setJobList(jobList);
        jobCategoryList.add(category);
        recruiterControl.getJobCategoryList().add(category);

        RecruiterHomeGUI recruiterHomeGUI = new RecruiterHomeGUI(recruiterControl, locationList);
        //close
    }

    /**
     * This is the method to generate a new jobID.
     */
    public int generateJobID() throws IOException {
        int numJobs = jobList.size() + 1;
        return numJobs;
    }

    /**
     * This is the method to populate the primary categories menu.
     */
    public void populateCategories(String fileName, JComboBox menuCatPrim, JComboBox menuCatSec) throws IOException {
        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        String returnString = "";
        String firstCategory = "";

        while (scan.hasNextLine()) {
            returnString = scan.nextLine();

            for (int i = 0; i < returnString.length(); i++) {
                if (returnString.charAt(i) != ',') {
                    firstCategory += returnString.charAt(i);
                }
                else {
                    break;
                }
            }

            menuCatPrim.addItem(firstCategory);
            firstCategory = "";
        }

        file.close();
        populateSecondaryCategories(fileName, menuCatPrim, menuCatSec);
    }

    /**
     * This is the method to populate the postcode menu.
     */
    public void populatePostcode(String fileName, JComboBox menuState, JComboBox menuPC) throws FileNotFoundException {
        menuPC.removeAllItems();
        String state = String.valueOf(menuState.getSelectedItem());
        //System.out.println("state is: " + state);

        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        String returnString = "";
        String postcode = "";
        String stateCheck = "";
        String city = "";

        while (scan.hasNextLine()) {
            returnString = scan.nextLine();
            //System.out.println("returnString is: " + returnString);
            String [] locationDetails = returnString.split(",");
            stateCheck = locationDetails[1];
            postcode = locationDetails[2];
            city = locationDetails[3];

            if (state.equals(stateCheck)) {
                menuPC.addItem(postcode + ", " + city);
            }

            stateCheck = "";
            postcode = "";
        }
    }

    /**
     * This is the menu to populate the secondary categories menu.
     */
    public void populateSecondaryCategories(String fileName, JComboBox menuCatPrim, JComboBox menuCatSec) throws FileNotFoundException {
        menuCatSec.removeAllItems();
        int index = menuCatPrim.getSelectedIndex();
        //System.out.println("index is: " + index);

        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        String returnString = "";
        String secondCategory = "";

        for (int i = 0; i < index + 1 ; i++) {
            returnString = scan.nextLine();
            //System.out.println("returnString is: " + returnString);
        }

        for (int i = 0; i < returnString.length(); i++) {
            if (returnString.charAt(i) != ',') {
                secondCategory += returnString.charAt(i);
            }
            else {
                menuCatSec.addItem(secondCategory);
                secondCategory = "";
            }
        }
        menuCatSec.removeItemAt(0);
    }

    /**
     * This is the method to populate the skills list.
     */
    public void populateSkills(String fileName, JComboBox menuSkill) throws IOException {
        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        String returnString = "";

        while (scan.hasNextLine()) {
            returnString = scan.nextLine();
            menuSkill.addItem(returnString);
            //System.out.println(returnString + " added to skills menu");
        }
        file.close();
    }

    /**
     * This is the method to set the inputs from the form to job and category objects.
     * @param job a job object that is overwritten to save the newly created job.
     */
    public void submitButtonActions(Job job) throws Exception {
        // Throw notification messages if needed data is missing.

        job.setJobTitle(jobTitleText.getText());
        //System.out.println("jobTitle has been set to: " + job.getJobTitle());
        job.setEmployer(employerText.getText());
        job.setJobType(String.valueOf(jobTypeMenu.getSelectedItem()));
        //System.out.println("JobType has been set to: " + job.getJobType());

        job.setSalary(Integer.parseInt(salaryText.getText()));
        //System.out.println("Salary has been set to: " + job.getSalary());

        for (int i = 0; i < skillsList.getModel().getSize(); i++) {
            skills.add(String.valueOf(skillsList.getModel().getElementAt(i)));
        }
        job.setSkills(skills);
        //System.out.println("Skills have been set to: " + job.getSkills());

        String state = String.valueOf(locationStateMenu.getSelectedItem());
        String selectedPostcode = String.valueOf(postcodeMenu.getSelectedItem());
        String postcode = "";
        for (int i = 0; i < 4; i++) {
            postcode += selectedPostcode.charAt(i);
            //System.out.println("postcode is: " + postcode);
        }

        int postCode = Integer.parseInt(postcode);

        String city = "";
        for (int i = 6; i < selectedPostcode.length(); i++) {
            city += selectedPostcode.charAt(i);
        }

        for (Location tmpLocation : locationList)
        {
            boolean check = true;
            //check state
            if (!tmpLocation.getState().equalsIgnoreCase(state))
            {
                check = false;
                continue;
            }
            if (tmpLocation.getPostcode() != postCode)
            {
                check = false;
                continue;
            }
            if (!tmpLocation.getCity().equalsIgnoreCase(city))
            {
                check = false;
                continue;
            }

            if (check)
            {
                job.setLocationID(tmpLocation.getLocationID());
                break;
            }
        }

        job.setJobDescription(String.valueOf(descriptionText.getText()));
        category.setJobID(job.getJobID());
        category.setJobPrimaryCategory(String.valueOf(categoryMenuPrimary.getSelectedItem()));
        category.setJobSubCategory(String.valueOf(categoryMenuSecondary.getSelectedItem()));
        job.setJobStatus(String.valueOf(statusMenu.getSelectedItem()));
    }
}