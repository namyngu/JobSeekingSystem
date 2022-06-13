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
    private User recruiter;
    private ArrayList<Job> jobList;
    private ArrayList<Location> locationList;
    private ArrayList<JobCategory> jobCategoryList;
    private Job job;
    private Location location;
    private JobCategory category;

    //public ManageJobGUI(User recruiter, ArrayList<Job> jobList, ArrayList<Location> locationList) throws IOException {
    public ManageJobGUI(Job job) throws IOException {
        JFrame frame1 = new JFrame("Manage Job");
        frame1.setContentPane(this.manageJobPanel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);

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
    }

        public void populateForm(Job job, DefaultListModel popSkillsList) {
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
            for (Location tmpLocation : locationList) {
                if (tmpLocation.getLocationID() == job.getLocationID()) {
                    locationStateMenu.setSelectedItem(tmpLocation.getState());
                    postcodeMenu.setSelectedItem(tmpLocation.getPostcode() + ", " + tmpLocation.getCity());
                    continue;
                }
            }

            descriptionText.setText(job.getJobDescription());

            //categories
            for (JobCategory tmpCategory : jobCategoryList) {
                if (tmpCategory.getJobID() == job.getJobID()) {
                    categoryMenuPrimary.setSelectedItem(tmpCategory.getJobPrimaryCategory());
                    categoryMenuSecondary.setSelectedItem(tmpCategory.getJobSubCategory());
                    continue;
                }
            }

            statusMenu.setSelectedItem(job.getJobStatus());
        }

                /*
                this.recruiter = recruiter;
                this.jobList = jobList;
                this.locationList = locationList;
                job = new Job();

                job.setRecruiterID(recruiter.getUserID());


                DefaultListModel skillsListGUI = new DefaultListModel();

                populateSkills("SkillList.csv", skillsMenu);
                populateCategories("CategoryList.csv", categoryMenuPrimary, categoryMenuSecondary);



                jobIDButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

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

                jobIDText.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                            e.consume();  // if it's not a number, ignore the event
                        }
                    }
                });
                jobIDButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        job.setJobID(Integer.parseInt(jobIDText.getText()));
                        populateJob(job.getJobID());
                        populateCategory(job.getJobID());
                        populateLocation(job.getLocationID());
                        populateForm(job, skillsListGUI);
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
            }

            private void createUIComponents() {
                // TODO: place custom component creation code here
            }

            public static void main(String[] args) {

        each action needs to store in the variable for the job, then at the end, create a
        job object that stores all of the info and writes it to the job database

                public void populateCategory(int jobID) {

                    JFrame frame = new JFrame("Manage Job");
                    frame.setContentPane(new ManageJobGUI().manageJobPanel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }

                public void populateJob(int jobID) {
                    for (Job tmpJob : jobList) {
                        if (tmpJob.getJobID() == job.getJobID()) {
                            System.out.println("Chosen job is: " + tmpJob.toString());
                            job.setJobTitle(tmpJob.getJobTitle());
                            job.setEmployer(tmpJob.getEmployer());
                            job.setJobType(tmpJob.getJobType());
                            job.setJobStatus(tmpJob.getJobStatus());
                            job.setSalary(tmpJob.getSalary());
                            job.setLocationID(tmpJob.getLocationID());
                            job.setJobDescription(tmpJob.getJobDescription());
                            job.setSkills(tmpJob.getSkills());
                            job.setApplications(tmpJob.getApplications());
                            job.setKeywords(tmpJob.getKeywords());
                        }
                    }
                }

                public void populateLocation(int locationID) {
                    location.setLocationID(locationID);
                    //location.
                }
                */
    }

