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
    private JLabel keywordsLabel;
    private JComboBox keywordsMenu;
    private JList keywordsList;
    private JButton addKeywordsButton;
    private JButton removeKeywordsButton;
    private JLabel locationLabel;
    private JComboBox locationStateMenu;
    private JLabel postcodeLabel;
    private JTextField postcodeText;
    private JLabel descriptionLabel;
    private JFormattedTextField descriptionText;
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

    public CreateJobGUI() throws IOException {
        JFrame frame1 = new JFrame("Create Job");
        //frame1.setContentPane(this.createJobPanel);
        //frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame1.pack();
        //frame1.setVisible(true);

        Job job = new Job();
        populateSkills("JobSeekingSystem/src/Skills.csv");
        populateKeywords("JobSeekingSystem/src/KeywordList.csv");
        //populateCategories("JobSeekingSystem/src/Categories.csv");

        DefaultListModel skillsListGUI = new DefaultListModel();
        ArrayList<String> skills = job.getSkills();
        DefaultListModel keywordsListGUI = new DefaultListModel();
        ArrayList<String> keywords = job.getKeywords();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Submit button has been clicked");
                job.setJobTitle(jobTitleText.getText());
                System.out.println("jobTitle has been set to: " + job.getJobTitle());
                job.setJobType(String.valueOf(jobTypeMenu.getSelectedItem()));
                System.out.println("JobType has been set to: " + job.getJobType());
                job.setSalary("$" + salaryText.getText() + " " + String.valueOf(salaryMenu.getSelectedItem()));
                System.out.println("Salary has been set to: " + job.getSalary());

                for (int i = 0; i < skillsList.getModel().getSize(); i++) {
                    skills.add(String.valueOf(skillsList.getModel().getElementAt(i)));
                }
                job.setSkills(skills);
                System.out.println("Skills have been set to: " + job.getSkills());

                for (int i = 0; i < keywordsList.getModel().getSize(); i++) {
                    keywords.add(String.valueOf(keywordsList.getModel().getElementAt(i)));
                }
                job.setKeywords(keywords);
                System.out.println("Keywords have been set to: " + job.getKeywords());
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

        addKeywordsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                keywordsListGUI.addElement(String.valueOf(keywordsMenu.getSelectedItem()));
                keywordsList.setModel(keywordsListGUI);
            }
        });
        removeKeywordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keywordsListGUI.removeElement(keywordsList.getSelectedValue());
                keywordsList.setModel(keywordsListGUI);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) throws IOException {
        CreateJobGUI objCreateJob = new CreateJobGUI();

        JFrame frame = new JFrame("Create Job");
        frame.setContentPane(objCreateJob.createJobPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Job objJob = new Job();
        objJob.setJobID(objCreateJob.generateJobID("JobSeekingSystem/src/Jobs.csv"));
        objCreateJob.intJobIDLabel.setText(Integer.toString(objJob.getJobID()));
        objCreateJob.intRecIDLabel.setText(Integer.toString(objJob.getRecruiterID()));  //will come from recruiter account details
        objCreateJob.empNameLabel.setText(objJob.getEmployer());                        //will come from recruiter account details

    }

    public int generateJobID(String fileName) throws IOException {
        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        int numJobs = 1;

        while (scan.hasNextLine()) {
            System.out.println(scan.nextLine());
            numJobs++;
        }

        file.close();
        return numJobs;
    }

    public void populateCategories(String fileName) {

    }

    public void populateKeywords(String fileName) throws IOException {
        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        String returnString = "";

        while (scan.hasNextLine()) {
            returnString = scan.nextLine();
            keywordsMenu.addItem(returnString);
            //System.out.println(returnString + " added to keywords menu");
        }

        file.close();
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

}
