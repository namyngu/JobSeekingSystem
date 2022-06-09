import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class CreateJobGUI {
    private JPanel createJobPanel;
    private JLabel formTitle;
    private JLabel jobIDLabel;
    private JLabel intJobIDLabel;
    private JLabel jobTitleLabel;
    private JTextField jobTitleText;
    private JLabel recruiterIDLabel;
    private JLabel intRecIDLabel;
    private JLabel employerLabel;
    private JLabel empNameLabel;
    private JLabel jobTypeLabel;
    private JComboBox jobTypeMenu;
    private JLabel salaryLabel;
    private JTextField salaryText;
    private JComboBox salaryMenu;
    private JLabel skillsLabel;
    private JComboBox skillsMenu;
    private JList skillsList;
    private JButton addSkillButton;
    private JButton removeSkillButton;
    private JLabel locationLabel;
    private JComboBox locationStateMenu;
    private JLabel postcodeLabel;
    private JComboBox postcodeMenu;
    private JLabel descriptionLabel;
    private JTextArea descriptionText;
    private JLabel categoryLabelPrimary;
    private JLabel categoryLabelSecondary;
    private JComboBox categoryMenuPrimary;
    private JComboBox categoryMenuSecondary;
    private JLabel hoursLabel;
    private JTextField hoursText;
    private JComboBox hoursMenu;
    private JLabel statusLabel;
    private JComboBox statusMenu;
    private JLabel statusMessageLabel;
    private JButton submitButton;
    private JScrollPane descriptionScroll;
    private JScrollPane jobTitleScroll;

    public CreateJobGUI() throws IOException {
        JFrame frame = new JFrame("Create Job");
        frame.setContentPane(this.createJobPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Job job = new Job();
        job.setJobID(generateJobID("JobSeekingSystem/Jobs.csv"));
        intJobIDLabel.setText(Integer.toString(job.getJobID()));
        intRecIDLabel.setText(Integer.toString(job.getRecruiterID()));  //will come from recruiter account details
        empNameLabel.setText(job.getEmployer());                        //will come from recruiter account details

        populateSkills("JobSeekingSystem/Skills.csv");
        populateCategories("JobSeekingSystem/Categories.csv");

        DefaultListModel skillsListGUI = new DefaultListModel();
        ArrayList<String> skills = job.getSkills();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Submit button has been clicked");
                frame.setVisible(false);
                try {
                    job.setJobID(generateJobID("JobSeekingSystem/Jobs.csv"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                job.setJobTitle(jobTitleText.getText());
                //System.out.println("jobTitle has been set to: " + job.getJobTitle());
                job.setJobType(String.valueOf(jobTypeMenu.getSelectedItem()));
                //System.out.println("JobType has been set to: " + job.getJobType());
                job.setSalary("$" + salaryText.getText() + " " + String.valueOf(salaryMenu.getSelectedItem()));
                //System.out.println("Salary has been set to: " + job.getSalary());

                for (int i = 0; i < skillsList.getModel().getSize(); i++) {
                    skills.add(String.valueOf(skillsList.getModel().getElementAt(i)));
                }
                job.setSkills(skills);
                //System.out.println("Skills have been set to: " + job.getSkills());

                job.setLocationState(String.valueOf(locationStateMenu.getSelectedItem()));
                //System.out.println("locationState has been set to: " + job.getLocationState());

                String selectedPostcode = String.valueOf(postcodeMenu.getSelectedItem());
                String postcode = "";
                for (int i = 0; i < 4; i++) {
                    postcode += selectedPostcode.charAt(i);
                }
                job.setPostCode(postcode);
                //System.out.println("postCode has been set to: " + job.getPostCode());

                job.setJobDescription(updateJobDescription(job.getJobDescription(), String.valueOf(descriptionText.getText())));
                //System.out.println("jobDescription has been set to: " + job.getJobDescription());

                job.setJobCategoryPrimary(String.valueOf(categoryMenuPrimary.getSelectedItem()));
                //System.out.println("categoryPrimary has been set to: " + job.getJobCategoryPrimary());

                job.setJobCategorySecondary(String.valueOf(categoryMenuSecondary.getSelectedItem()));
                //System.out.println("categorySecondary has been set to: " + job.getJobCategorySecondary());

                job.setWorkingHours(hoursText.getText());
                //System.out.println("workingHours has been set to: " + job.getWorkingHours() + " " + String.valueOf(hoursMenu.getSelectedItem()));

                String status = String.valueOf(statusMenu.getSelectedItem());
                if (status.equals("Draft")) {
                    job.setAdvertised(false);
                    job.setArchived(false);
                }
                if (status.equals("Published")) {
                    job.setAdvertised(true);
                    job.setArchived(false);
                }
                if (status.equals("Archived")) {
                    job.setAdvertised(false);
                    job.setArchived(true);
                }

                //write to file

                try {
                    updateFile("JobSeekingSystem/Jobs.csv", job);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //close
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
                    populateSecondaryCategories("JobSeekingSystem/Categories.csv");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        locationStateMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    populatePostcode("JobSeekingSystem/src/Postcodes.csv");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /*
    public static void main(String[] args) throws IOException {
        CreateJobGUI objCreateJob = new CreateJobGUI();
    }
    */

    public int generateJobID(String fileName) throws IOException {
        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        String returnString = "";
        int numJobs = 1;

        while (scan.hasNextLine()) {
            returnString = scan.nextLine();
            //System.out.println(scan.nextLine());
            numJobs++;
        }

        file.close();
        return numJobs;
    }

    public void populateCategories(String fileName) throws IOException {
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

            categoryMenuPrimary.addItem(firstCategory);
            firstCategory = "";
        }

        file.close();
        populateSecondaryCategories(fileName);
    }

    public void populatePostcode(String fileName) throws FileNotFoundException {
        postcodeMenu.removeAllItems();
        String state = String.valueOf(locationStateMenu.getSelectedItem());
        //System.out.println("state is: " + state);

        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        String returnString = "";
        String postcode = "";
        String stateCheck = "";

        while (scan.hasNextLine()) {
            returnString = scan.nextLine();
            //System.out.println("returnString is: " + returnString);

            for (int i = 0; i < returnString.length(); i++) {
                if (returnString.charAt(i) != ',') {
                    stateCheck += returnString.charAt(i);
                    //System.out.println("stateCheck is: " + stateCheck);
                }
                else {
                    i++;
                    if (state.equals(stateCheck)) {
                        //System.out.println("they are equal");
                        while (i < returnString.length()) {
                            postcode += returnString.charAt(i);
                            //System.out.println("postcode is: " + postcode);
                            i++;
                        }
                        postcodeMenu.addItem(postcode);
                        stateCheck = "";
                        returnString = "";
                        postcode = "";
                        //System.out.println("added to menu");
                        break;
                    }
                    else {
                        postcode = "";
                        //System.out.println("didnt work, state is: " + state + "\n" + "stateCheck is: " + stateCheck);
                        stateCheck = "";
                        break;
                    }
                }
            }
        }
    }

    public void populateSecondaryCategories(String fileName) throws FileNotFoundException {
        categoryMenuSecondary.removeAllItems();
        int index = categoryMenuPrimary.getSelectedIndex();
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
                categoryMenuSecondary.addItem(secondCategory);
                secondCategory = "";
            }
        }
        categoryMenuSecondary.removeItemAt(0);
    }

    public void populateSkills(String fileName) throws IOException {
        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        String returnString = "";

        while (scan.hasNextLine()) {
            returnString = scan.nextLine();
            skillsMenu.addItem(returnString);
            //System.out.println(returnString + " added to skills menu");
        }
        file.close();
    }



    public void updateFile(String fileName, Job job) throws IOException {

        FileWriter file = new FileWriter(fileName, true);
        file.append("\n");
        file.append(String.valueOf(job.getJobID()) + ",");
        file.append(String.valueOf(job.getJobTitle()) + ",");
        file.append(String.valueOf(job.getRecruiterID()) + ",");
        file.append(String.valueOf(job.getEmployer()) + ",");
        file.append(String.valueOf(job.getJobType()) + ",");
        file.append(String.valueOf(job.getAdvertised()) + ",");
        file.append(String.valueOf(job.getSalary()) + ",");
        file.append(String.valueOf(job.getSkills()) + ",");
        file.append(String.valueOf(job.getApplications()) + ",");
        file.append(String.valueOf(job.getKeywords()) + ",");
        file.append(String.valueOf(job.getLocationState()) + ",");
        file.append(String.valueOf(job.getPostCode()) + ",");
        file.append(String.valueOf("\"" + job.getJobDescription()) + "\"" + ",");
        file.append(String.valueOf(job.getJobCategoryPrimary()) + ",");
        file.append(String.valueOf(job.getJobCategorySecondary()) + ",");
        file.append(String.valueOf(job.getWorkingHours()) + ",");
        file.append(String.valueOf(job.getAdvertised()) + ",");
        file.append(String.valueOf(job.isArchived()));
        //file.append("\n");
        file.close();
    }

    public ArrayList<String> updateJobDescription(ArrayList<String> jobDescAL, String input) {
        //System.out.println("jobDescAL is: " + jobDescAL);
        //System.out.println("input is: " + input);
        String line = "";

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '\n') {
                jobDescAL.add(line);
                line = "";
                //System.out.println("jobDescAL is: " + jobDescAL);
            }
            else {
                line += input.charAt(i);
                //System.out.println("line is: " + line);
            }
        }
        jobDescAL.add(line);
        //System.out.println("Finished, jobDescAL is: " + jobDescAL);
        return jobDescAL;
    }
}