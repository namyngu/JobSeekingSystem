import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ManageJobGUI extends CreateJobGUI {
    private JPanel manageJobPanel;
    private JLabel formTitle;
    private JLabel jobIDLabel;
    private JLabel jobTitleLabel;
    private JTextField jobTitleText;
    private JLabel recruiterIDLabel;
    private JLabel employerLabel;
    private JLabel jobTypeLabel;
    private JComboBox jobTypeMenu;
    private JLabel salaryLabel;
    private JTextField salaryText;
    private JLabel skillsLabel;
    private JComboBox skillsMenu;
    private JButton addSkillButton;
    private JList skillsList;
    private JButton removeSkillButton;
    private JLabel applicationsLabel;
    private JLabel locationLabel;
    private JComboBox locationStateMenu;
    private JLabel postcodeLabel;
    private JLabel descriptionLabel;
    private JLabel categoryLabelPrimary;
    private JLabel categoryLabelSecondary;
    private JComboBox categoryMenuPrimary;
    private JComboBox categoryMenuSecondary;
    private JLabel statusLabel;
    private JComboBox statusMenu;
    private JLabel statusMessageLabel;
    private JLabel intNumAppLabel;
    private JLabel intJobIDLabel;
    private JLabel intRecIDLabel;
    private JTextField jobIDText;
    private JLabel jobIDMessage;
    private JTextField employerText;
    private JLabel salaryTextLabel;
    private JComboBox postcodeMenu;
    private JScrollPane descriptionScroll;
    private JTextArea descriptionText;
    private JLabel salaryMessage;
    private JButton jobIDButton;
    private JButton submitButton;
    private ArrayList<Job> jobList;
    private Job job;
    private Location location;
    private JobCategory category;
    private RecruiterControl control;
    private ArrayList<String> skills;

    //public ManageJobGUI(User recruiter, ArrayList<Job> jobList, ArrayList<Location> locationList) throws IOException {
    public ManageJobGUI(RecruiterControl control, Job myJob) throws IOException {
        this.control = control;
        this.job = myJob;
        location = new Location();
        category = new JobCategory();
        skills = new ArrayList<>();

        JFrame frame = new JFrame("Manage Job");
        frame.setContentPane(this.manageJobPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setLocation(650, 40);
        frame.setVisible(true);

        DefaultListModel skillsListGUI = new DefaultListModel();
        populateSkills("SkillList.csv", skillsMenu);
        populateCategories("CategoryList.csv", categoryMenuPrimary, categoryMenuSecondary);
        populateForm(job, skillsListGUI);


        salaryText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // if it's not a number, ignore the event
                }
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

        //Event on close
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Exit to Home?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    RecruiterHomeGUI recruiterHomeGUI = new RecruiterHomeGUI(control, control.getLocationList());
                    frame.dispose();
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Submit button has been clicked");
                frame.setVisible(false);

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

                for (Location tmpLocation : control.getLocationList())
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

                //Comment out for now.
                //job.setJobDescription(updateJobDescription(job.getJobDescription(), String.valueOf(descriptionText.getText())));
                //System.out.println("jobDescription has been set to: " + job.getJobDescription());

                job.setJobDescription(String.valueOf(descriptionText.getText()));

                JobCategory category = new JobCategory(job.getJobID(), String.valueOf(categoryMenuPrimary.getSelectedItem()), String.valueOf(categoryMenuSecondary.getSelectedItem()));

                job.setJobStatus(String.valueOf(statusMenu.getSelectedItem()));

                //write to JobList and JobCategory and JobSkill csv
                File_Control io = new File_Control();
                try {
                    io.updateJob(job.getJobID(), job.getJobTitle(), job.getEmployer(), job.getRecruiterID(),
                            job.getJobType(), job.getJobStatus(), job.getSalary(), job.getLocationID(), job.getJobDescription(), job.getSkills(), category);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                /*
                //update JobList
                jobList.add(job);

                RecruiterHomeGUI recruiterHomeGUI = new RecruiterHomeGUI(control, control.getLocationList());
                //close

                 */
            }
        });


    }

        public void populateForm(Job job, DefaultListModel popSkillsList) throws IOException {
            intNumAppLabel.setText(Integer.toString(job.getApplications().size()));
            intJobIDLabel.setText(Integer.toString(job.getJobID()));
            intRecIDLabel.setText(Integer.toString(job.getRecruiterID()));
            jobTitleText.setText(job.getJobTitle());
            employerText.setText(job.getEmployer());

            jobTypeMenu.setSelectedItem(job.getJobType());
            salaryText.setText(String.valueOf(job.getSalary()));
            //skills list
            for (String skill : job.getSkills()) {
                popSkillsList.addElement(skill);
                skillsList.setModel(popSkillsList);
            }
            //location list
            for (Location tmpLocation : control.getLocationList()) {
                if (tmpLocation.getLocationID() == job.getLocationID()) {
                    location.setLocationID(tmpLocation.getLocationID());
                    location.setState(tmpLocation.getState());
                    location.setPostcode(tmpLocation.getPostcode());
                    location.setCity(tmpLocation.getCity());
                    break;
                }
            }

            locationStateMenu.setSelectedItem(location.getState());
            populatePostcode("Location.csv", locationStateMenu, postcodeMenu);
            postcodeMenu.setSelectedItem(location.getPostcode() + ", " + location.getCity());

            String tmpDescription = "";
            for (int i = 1; i < job.getJobDescription().length() - 1; i++) {
                tmpDescription += job.getJobDescription().charAt(i);
            }
            descriptionText.setText(tmpDescription);

            //categories
            for (JobCategory tmpCategory : control.getJobCategoryList()) {
                if (tmpCategory.getJobID() == job.getJobID()) {
                    category.setJobID(tmpCategory.getJobID());
                    category.setJobPrimaryCategory(tmpCategory.getJobPrimaryCategory());
                    category.setJobSubCategory(tmpCategory.getJobSubCategory());
                    break;
                }
            }

            categoryMenuPrimary.setSelectedItem(category.getJobPrimaryCategory());
            populateCategories("CategoryList.csv", categoryMenuPrimary, categoryMenuSecondary);
            categoryMenuSecondary.setSelectedItem(category.getJobSubCategory());
            statusMenu.setSelectedItem(job.getJobStatus());
        }



        private void createUIComponents() {
            // TODO: place custom component creation code here
        }
    }

