/**
 * This class is the GUI form for when a Recruiter would like to view a particular
 * Jobseeker's profile.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    /**
     * This is the Default constructor for this class.
     */
    public RecruiterViewCandidateGUI(){
    }

    /**
     * This is a Non-default constructor for the class.
     * @param control       a RecruiterControl Object controlling the Recruiter.
     * @param jobseekerID   an Integer containing the ID number of the Jobseeker
     *                      which the Recruiter would like to view details of.
     */
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
        sendInviteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Job> recruiterJobs = control.getRecruiter().getJobs();
                int selectedIndex = selectJob.getSelectedIndex();
                Job selectedJob = recruiterJobs.get(selectedIndex);
                int selectedJobID = selectedJob.getJobID();
                String jobName = selectedJob.getJobTitle();

                control.sendInvite(jobseekerID,selectedJobID, jobName);
            }
        });
    }

    /**
     * This is the display method for the class.
     */
    public void display(){
        System.out.println("Recruiter View Candidate GUI: " + this);
    }

    /**
     * This is the Accessor method for the candidateEmail field.
     * @return a JLabel containing the candidate email address.
     */
    public JLabel getCandidateEmail() {
        return candidateEmail;
    }

    /**
     * This is the Accessor method for the candidateLocation field.
     * @return a JLabel containing the candidate location.
     */
    public JLabel getCandidateLocation() {
        return candidateLocation;
    }

    /**
     * This is the Accessor method for the candidatePanel field.
     * @return a JPanel containing all the candidate information.
     */
    public JPanel getCandidatePanel() {
        return candidatePanel;
    }

    /**
     * This is the Accessor method for the candidatePhone field.
     * @return a JLabel containing the candidate phone number.
     */
    public JLabel getCandidatePhone() {
        return candidatePhone;
    }

    /**
     * This is the Accessor method for the candidateSkillsTable field.
     * @return a JList which can be populated with all the candidate's skills.
     */
    public JList getCandidateSkillsTable() {
        return candidateSkillsTable;
    }

    /**
     * This is the Accessor method for the nameLabel field.
     * @return a JLabel containing the candidate's name.
     */
    public JLabel getNameLabel() {
        return nameLabel;
    }

    /**
     * This is the Accessor method for the profile field.
     * @return a JPanel for the candidate's profile.
     */
    public JPanel getProfile() {
        return profile;
    }

    /**
     * This is the Accessor method for the profilePanel field.
     * @return a JPanel for the candidate's profile.
     */
    public JPanel getProfilePanel() {
        return profilePanel;
    }

    /**
     * This is the Accessor method for the selectJob field.
     * @return  a JComboBox which can be used to select a Job to invite
     *          this candidate to apply for.
     */
    public JComboBox getSelectJob() {
        return selectJob;
    }

    /**
     * This is the Accessor method for the sendInviteButton field.
     * @return a JButton the Recruiter can use to send an Invitation.
     */
    public JButton getSendInviteButton() {
        return sendInviteButton;
    }

    /**
     * This method populates the Jobs this Recruiter has advertised, so they can
     * invite a Jobseeker to apply.
     * @param control a RecruiterControl Object which has access to this Recruiter's
     *                list of Jobs.
     */
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

    /**
     * This method populates the basic Jobseeker information into the form.
     * @param jobseeker a Jobseeker Object which the Recruiter would like to
     *                  view details of.
     */
    public void populateLabels (Jobseeker jobseeker)
    {
        //Populate labels
        nameLabel.setText(jobseeker.getFirstName() + " " + jobseeker.getLastName());
        candidatePhone.setText(jobseeker.getPhone());
        candidateEmail.setText(jobseeker.getEmail());
        candidateLocation.setText(jobseeker.getLocation().getCity() + " " + jobseeker.getLocation().getState() + " " + jobseeker.getLocation().getPostcode());
    }

    /**
     * This method populates the Jobseeker skill information into the form.
     * @param jobseeker a Jobseeker Object which the Recruiter would like to
     *                  view details of.
     */
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

    /**
     * This is the Mutator method for the candidateEmail field.
     * @param candidateEmail a JLabel containing the candidate email address.
     */
    public void setCandidateEmail(JLabel candidateEmail) {
        this.candidateEmail = candidateEmail;
    }

    /**
     * This is the Mutator method for the candidateLocation field.
     * @param candidateLocation a JLabel containing the condidate's location.
     */
    public void setCandidateLocation(JLabel candidateLocation) {
        this.candidateLocation = candidateLocation;
    }

    /**
     * This is the Mutator method for the candidatePanel field.
     * @param candidatePanel a JPanel containing all the candidate information.
     */
    public void setCandidatePanel(JPanel candidatePanel) {
        this.candidatePanel = candidatePanel;
    }

    /**
     * This is the Mutator method for the candidatePhone field.
     * @param candidatePhone a JLabel containing the candidate's phone number.
     */
    public void setCandidatePhone(JLabel candidatePhone) {
        this.candidatePhone = candidatePhone;
    }

    /**
     * This is the Mutator method for the candidateSkillsTable field.
     * @param candidateSkillsTable a JList which can be populated with all the
     *                             candidate's skills.
     */
    public void setCandidateSkillsTable(JList candidateSkillsTable) {
        this.candidateSkillsTable = candidateSkillsTable;
    }

    /**
     * This is the Mutator method for the nameLabel field.
     * @param nameLabel a JLabel containing the candidate's name.
     */
    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    /**
     * This is the Mutator method for the profile field.
     * @param profile a JPanel to contain the candidate's profile.
     */
    public void setProfile(JPanel profile) {
        this.profile = profile;
    }

    /**
     * This is the Mutator method for the profilePanel field.
     * @param profilePanel a JPanel to contain the candidate's profile.
     */
    public void setProfilePanel(JPanel profilePanel) {
        this.profilePanel = profilePanel;
    }

    /**
     * This is the Mutator method for the selectJob field.
     * @param selectJob a JComboBox to select a Job to invite the candidate
     *                  to apply for.
     */
    public void setSelectJob(JComboBox selectJob) {
        this.selectJob = selectJob;
    }

    /**
     * This is the Mutator method for the sendInviteButton field.
     * @param sendInviteButton a JButton which can be used to send the Jobseeker
     *                         an Invitation for the selected Job.
     */
    public void setSendInviteButton(JButton sendInviteButton) {
        this.sendInviteButton = sendInviteButton;
    }
}



