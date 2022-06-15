import javax.swing.*;
import java.util.ArrayList;

public class RecruiterViewCandidateGUI {
    private JPanel profilePanel;
    private JPanel profile;
    private JLabel candidatePhone;
    private JList candidateSkillsTable;
    private JButton sendInviteButton;
    private JComboBox selectJob;
    private JPanel candidatePanel;
    private JLabel nameLabel;
    private JLabel candidateEmail;
    private JLabel candidateLocation;

    public RecruiterViewCandidateGUI(RecruiterControl control, int jobseekerID) {
        JFrame frame = new JFrame("CandidateGUI");
        frame.setContentPane(this.candidatePanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocation(650, 40);
        frame.setVisible(true);

        //Find jobseeker
        Jobseeker jobseeker = null;
        try {
            for (Jobseeker tmpJobseeker : control.getJobseekerList()) {
                if (tmpJobseeker.getUserID() == jobseekerID) {
                    jobseeker = tmpJobseeker;
                    break;
                }
            }
            //populate labels
            populateLabels(jobseeker);
            //populate skillList
            populateSkills(jobseeker);
            //populate jobComboBox

        } catch (Exception e) {
            System.out.println("Error cannot find candidate!");
        }
            //populate labels
            populateLabels(jobseeker);
            //populate skillList
            populateSkills(jobseeker);
            //populate jobComboBox
            populateJobCombo(control);
    }

    public void populateLabels (Jobseeker jobseeker)
    {
        //Populate labels
        nameLabel.setText(jobseeker.getFirstName() + " " + jobseeker.getLastName());
        candidatePhone.setText(jobseeker.getPhone());
        candidateEmail.setText(jobseeker.getEmail());
        candidateLocation.setText(jobseeker.getLocation().getCity() + " " + jobseeker.getLocation().getState() + " " + jobseeker.getLocation().getPostcode());
    }

    public void populateSkills(Jobseeker jobseeker)
    {
        DefaultListModel listModel = new DefaultListModel();
        ArrayList<String> skills = jobseeker.getSkills();
        for (String skill : skills)
        {
            listModel.addElement(skill);
        }
        candidateSkillsTable.setModel(listModel);
    }

    public void populateJobCombo(RecruiterControl control)
    {
        //get recruiter jobs
        ArrayList<Job> recruiterJobs = control.getRecruiter().getJobs();

        String[] jobLabel = new String[recruiterJobs.size()];
        for (int i = 0; i < recruiterJobs.size(); i++)
        {
            selectJob.addItem(recruiterJobs.get(i).getJobID() + ". " +recruiterJobs.get(i).getJobTitle());
        }
    }
}



