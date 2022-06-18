/**
 * This class is the main GUI an Admin User will use to interact with the system.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminGUI
{
    private JPanel panel1;
    private JPanel adminHomePanel;
    private JList userList;
    private JTextArea userDetailsText;
    private JLabel jobsLabel;
    private JList jobList;
    private JTextArea jobDetailsText;
    private JButton lockUserButton;
    private JButton sendWarningButton;
    private JList inboxList;
    private JTextArea mailTextArea;
    private JButton replyButton;
    private JButton deleteButton;
    private JLabel inboxLabel;
    private JTextField replyTextField;
    private JButton removeJobButton;
    private JLabel userFirstname;
    private JButton logoutButton;
    // private JButton newMessageButton;
    private AdminControl adminControl;
    private JSS program;
    private DefaultListModel userListModel;
    private ArrayList<String> userNames;
    private DefaultListModel mailListModel;
    private  ArrayList<Message> userMessages;
    private DefaultListModel jobListModel;
    private ArrayList<Job> allJobs;

    /**
     * This is the Default constructor.
     */
    public AdminGUI() {
        program = new JSS();
        userListModel = new DefaultListModel();
        userMessages = new ArrayList<>();
        jobListModel = new DefaultListModel();
        allJobs = new ArrayList<>();
    }

    /**
     * This is a Non-default constructor for the class.
     * @param adminControl an AdminControl Object which is controlling the
     *                     Administrator User.
     * @param program      a JSS Object which represents the main system.
     */
    public AdminGUI(AdminControl adminControl, JSS program)
    {

        this.adminControl = adminControl;
        this.program = program;

        this.userNames = new ArrayList<>();
        this.userListModel = new DefaultListModel();
        this.userList.setModel(this.userListModel);

        this.userMessages = this.adminControl.retrieveMessages();
        this.mailListModel = new DefaultListModel();
        this.inboxList.setModel(this.mailListModel);
        this.userFirstname.setText("Hi, "+ adminControl.getAdminFirstName());

        this.jobListModel = new DefaultListModel();
        this.jobList.setModel(this.jobListModel);
        this.allJobs = program.getJobList();

        JFrame frame = new JFrame("AdminHome");
        frame.setSize(500,500);
        adminHomePanel.setSize(500,500);
        frame.setBounds(250,250,250,250);
        frame.setContentPane(this.adminHomePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //get jobs list to display

        try{
            int numJobs = allJobs.size();
            for (Job currentJob : allJobs) {
                String title = currentJob.getJobTitle();
                int recruiterID = currentJob.getRecruiterID();
                User recruiter = this.program.getUserByID(recruiterID);
                String recruiterName = recruiter.getUserName();

                String toDisplay = "";

                toDisplay += title + " : " + recruiterName;

                refreshList(toDisplay, jobList, jobListModel);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PromptGUI error = new PromptGUI("Contact developer", e.toString());
        }

        //retrieve messages for inbox

        int userID = this.adminControl.adminID();


        if (this.program.checkMessages(userID) == false)
        {
            refreshList("Clear inbox...",inboxList,mailListModel);


        }
        else
        {
            for (Message each: userMessages)
            {
                String toDisplay = "";

              int senderID = each.getSenderID();
               String senderName = program.retrieveUsername(senderID);

                toDisplay += senderName + " Re: " + each.getHeader();

                refreshList(toDisplay,inboxList,mailListModel);
            }
        }

        //requests num users from JSS and populates list that size of usernames only

        try{
            int numUsers = program.countUsers();
            for (int i=0; i<numUsers; i+=1)
            {
                String name = this.program.retrieveUsername(i+1);
                this.userNames.add(name);
                this.refreshList(name);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PromptGUI error = new PromptGUI("Contact developer", e.toString());
        }
        userList.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {

                int userNumber = userList.getSelectedIndex();
                try
                {

//                    userDetailsText.setText("User ID is: " + userNumber);
                    String selected = program.retrieveUserDetails(userNumber);
                    boolean locked = program.checkLocked(userNumber);
                    if (locked)
                    {
                        userDetailsText.setBackground(Color.red);
                        lockUserButton.setText("UNLOCK ACCOUNT");
                    }
                    else
                    {
                        userDetailsText.setBackground(Color.white);
                        lockUserButton.setText("LOCK ACCOUNT");
                    }
                    userDetailsText.setText(selected);


                }
                catch (Exception x)
                {
                    x.printStackTrace();
                    PromptGUI error = new PromptGUI("Error, contact developer", x.toString());
                }
            }
        });

        lockUserButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int userIndex = userList.getSelectedIndex();
                adminControl.switchLock(userIndex);
                if(program.checkLocked(userIndex))
                {
                    userDetailsText.setBackground(Color.red);
                    lockUserButton.setText("UNLOCK ACCOUNT");
                }
                else
                {
                    userDetailsText.setBackground(Color.white);
                    lockUserButton.setText("LOCK ACCOUNT");
                }
            }
        });
        sendWarningButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                int userIndex = userList.getSelectedIndex()+1;


                adminControl.blockedMessage(adminControl.adminID(),userIndex);

                //check messages are working bugfix:
             program.checkMessages(userList.getSelectedIndex());
            }
        });
        inboxList.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                int selected = inboxList.getSelectedIndex();

                Message toDisplay = userMessages.get(selected);


                String title = toDisplay.getHeader().toUpperCase();
                String content = toDisplay.getBody();
                String display = title + "\n\n" + content;
                mailTextArea.setText(display);
            }
        });

        replyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int sender = adminControl.adminID();


                int indexNumber = inboxList.getSelectedIndex();
                Message replyTo = userMessages.get(indexNumber);
                int replyToID = replyTo.getSenderID();


                String header = "Admin Reply";
                String body = replyTextField.getText();
                adminControl.createMessage(sender,replyToID,header,body);

            }
        });
        jobList.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                int selected = jobList.getSelectedIndex();
                Job forDisplay = allJobs.get(selected);
                String display = "";
                display += "Job Title: " + forDisplay.getJobTitle();
                display += "\nJob Status: " + forDisplay.getJobStatus();
                display += "\nJob Location: " + forDisplay.getLocationID();
                display += "\nEmployer: " + forDisplay.getEmployer();
                display += "\nSalary: " +forDisplay.getSalary();
                display += "\nJob Type: " +forDisplay.getJobType();
                display += "\nCurrent Applications: " + forDisplay.getApplications().size();

                jobDetailsText.setText(display);
            }
        });
        removeJobButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int jobID = jobList.getSelectedIndex()+1;
                if (adminControl.removeJob(jobID))
                {
                    PromptGUI prompt = new PromptGUI("Job removed and applications all rejected");
                }
                else
                {
                    PromptGUI prompt = new PromptGUI("Error job not removed, contact developer");
                }
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to Logout?","Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    Start program = new Start();
                    frame.dispose();
                }
            }
        });
    }

    /**
     * This is the display method for the class.
     */
    public void display() {
        System.out.println("AdminGUI: " + this);
    }

    /**
     * This is the Accessor method for the adminControl field.
     * @return an AdminControl Object which is controlling the current Admin.
     */
    public AdminControl getAdminControl() {
        return adminControl;
    }

    /**
     * This is the Accessor method for the adminHomePanel field.
     * @return a JPanel containing the Home screen for the User.
     */
    public JPanel getAdminHomePanel() {
        return adminHomePanel;
    }

    /**
     * This is the Accessor method for the allJobs field.
     * @return  an ArrayList of Jobs containing all the Jobs currently
     *          in the system.
     */
    public ArrayList<Job> getAllJobs() {
        return allJobs;
    }

    /**
     * This is the Accessor method for the deleteButton field.
     * @return a JButton used to delete a Message from the inbox.
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * This is the Accessor method for the inboxLabel field.
     * @return a JLabel containing the label for the inbox.
     */
    public JLabel getInboxLabel() {
        return inboxLabel;
    }

    /**
     * This is the Accessor method for the inboxList field.
     * @return a JList containing all the Messages for the inbox.
     */
    public JList getInboxList() {
        return inboxList;
    }

    /**
     * This is the Accessor method for the jobDetailsText field.
     * @return a JTextArea containing the details for the selected Job.
     */
    public JTextArea getJobDetailsText() {
        return jobDetailsText;
    }

    /**
     * This is the Accessor method for the jobList field.
     * @return a JList containing all the Jobs.
     */
    public JList getJobList() {
        return jobList;
    }

    /**
     * This is the Accessor method for the jobListModel field.
     * @return a DefaultListModel used to populate all the Jobs.
     */
    public DefaultListModel getJobListModel() {
        return jobListModel;
    }

    /**
     * This is the Accessor method for the jobsLabel field.
     * @return a JLabel containing the label for the Jobs section.
     */
    public JLabel getJobsLabel() {
        return jobsLabel;
    }

    /**
     * This is the Accessor method for the lockUserButton field.
     * @return a JButton to be used to lock the selected User.
     */
    public JButton getLockUserButton() {
        return lockUserButton;
    }

    /**
     * This is the Accessor method for the logoutButton field.
     * @return a JButton to be used to logout.
     */
    public JButton getLogoutButton() {
        return logoutButton;
    }

    /**
     * This is the Accessor method for the mailListModel field.
     * @return a DefaultListModel used to populate all of the mail.
     */
    public DefaultListModel getMailListModel() {
        return mailListModel;
    }

    /**
     * This is the Accessor method for the mailTextArea field.
     * @return a JTextArea which displays all of the mail.
     */
    public JTextArea getMailTextArea() {
        return mailTextArea;
    }

    /**
     * This is the Accessor method for the panel1 field.
     * @return a JPanel.
     */
    public JPanel getPanel1() {
        return panel1;
    }

    /**
     * This is the Accessor method for the program field.
     * @return a JSS Object representing the main system.
     */
    public JSS getProgram() {
        return program;
    }

    /**
     * This is the Accessor method for the removeJobButton field.
     * @return a JButton used to remove Jobs from the list.
     */
    public JButton getRemoveJobButton() {
        return removeJobButton;
    }

    /**
     * This is the Accessor method for the replyButton field.
     * @return a JButton used to reply to mail.
     */
    public JButton getReplyButton() {
        return replyButton;
    }

    /**
     * This is the Accessor method for the replyTextField field.
     * @return a JTextField for the reply Message.
     */
    public JTextField getReplyTextField() {
        return replyTextField;
    }

    /**
     * This is the Accessor method for the sendWarningButton field.
     * @return a JButton used to send a warning to the selected User.
     */
    public JButton getSendWarningButton() {
        return sendWarningButton;
    }

    /**
     * This is the Accessor method for the userDetailsText field.
     * @return a JTextArea which displays the User details.
     */
    public JTextArea getUserDetailsText() {
        return userDetailsText;
    }

    /**
     * This is the Accessor method for the userFirstname field.
     * @return a JLabel for the first name.
     */
    public JLabel getUserFirstname() {
        return userFirstname;
    }

    /**
     * This is the Accessor method for the userList field.
     * @return a JList containing a list of the Users.
     */
    public JList getUserList() {
        return userList;
    }

    /**
     * This is the Accessor method for the userListModel field.
     * @return a DefaultListModel used to populate the list of Users.
     */
    public DefaultListModel getUserListModel() {
        return userListModel;
    }

    /**
     * This is the Accessor method for the userMessages field.
     * @return an ArrayList of Messages.
     */
    public ArrayList<Message> getUserMessages() {
        return userMessages;
    }

    /**
     * This is the Accessor method for the userNames field.
     * @return  an ArrayList of Strings containing all the usernames for the
     *          Users in the system.
     */
    public ArrayList<String> getUserNames() {
        return userNames;
    }

    /**
     * This is the Mutator method for the adminControl field.
     * @param adminControl an AdminControl Object which is controlling
     *                     the current Admin User.
     */
    public void setAdminControl(AdminControl adminControl) {
        this.adminControl = adminControl;
    }

    /**
     * This is the Mutator method for the adminHomePanel field.
     * @param adminHomePanel a JPanel containing the home screen for the
     *                       Admin User.
     */
    public void setAdminHomePanel(JPanel adminHomePanel) {
        this.adminHomePanel = adminHomePanel;
    }

    /**
     * This is the Mutator method for the allJobs field.
     * @param allJobs an ArrayList of Jobs containing all the Jobs in the system.
     */
    public void setAllJobs(ArrayList<Job> allJobs) {
        this.allJobs = allJobs;
    }

    /**
     * This is the Mutator method for the deleteButton field.
     * @param deleteButton a JButton used to delete mail from the inbox.
     */
    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    /**
     * This is the Mutator method for the inboxLabel field.
     * @param inboxLabel a JLabel for the inbox.
     */
    public void setInboxLabel(JLabel inboxLabel) {
        this.inboxLabel = inboxLabel;
    }

    /**
     * This is the Mutator method for the inboxList field.
     * @param inboxList a JList containing the Messages in the inbox.
     */
    public void setInboxList(JList inboxList) {
        this.inboxList = inboxList;
    }

    /**
     * This is the Mutator method for the jobDetailsText field.
     * @param jobDetailsText a JTextArea used to display the details of the
     *                       currently selected Job.
     */
    public void setJobDetailsText(JTextArea jobDetailsText) {
        this.jobDetailsText = jobDetailsText;
    }

    /**
     * This is the Mutator method for the jobList field.
     * @param jobList a JList to display all the Jobs.
     */
    public void setJobList(JList jobList) {
        this.jobList = jobList;
    }

    /**
     * This is the Mutator method for the jobListModel field.
     * @param jobListModel a DefaultListModel used to populate a list of
     *                     Jobs.
     */
    public void setJobListModel(DefaultListModel jobListModel) {
        this.jobListModel = jobListModel;
    }

    /**
     * This is the Mutator method for the jobsLabel field.
     * @param jobsLabel a JLabel for the Jobs area.
     */
    public void setJobsLabel(JLabel jobsLabel) {
        this.jobsLabel = jobsLabel;
    }

    /**
     * This is the Mutator method for the lockUserButton field.
     * @param lockUserButton a JButton used to lock the currently selected User.
     */
    public void setLockUserButton(JButton lockUserButton) {
        this.lockUserButton = lockUserButton;
    }

    /**
     * This is the Mutator method for the logoutButton field.
     * @param logoutButton a JButton used to log out of the system.
     */
    public void setLogoutButton(JButton logoutButton) {
        this.logoutButton = logoutButton;
    }

    /**
     * This is the Mutator method for the mailListModel field.
     * @param mailListModel a DefaultListModel used to populate the mail
     *                      in the inbox.
     */
    public void setMailListModel(DefaultListModel mailListModel) {
        this.mailListModel = mailListModel;
    }

    /**
     * This is the Mutator method for the mailTextArea field.
     * @param mailTextArea a JTextArea used to display the list of mail.
     */
    public void setMailTextArea(JTextArea mailTextArea) {
        this.mailTextArea = mailTextArea;
    }

    /**
     * This is the Mutator method for the panel1 field.
     * @param panel1 a JPanel.
     */
    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    /**
     * This is the Mutator method for the program field.
     * @param program a JSS Object representing the main system.
     */
    public void setProgram(JSS program) {
        this.program = program;
    }

    /**
     * This is the Mutator method for the removeJobButton field.
     * @param removeJobButton a JButton used to remove Jobs from the list of Jobs.
     */
    public void setRemoveJobButton(JButton removeJobButton) {
        this.removeJobButton = removeJobButton;
    }

    /**
     * This is the Mutator method for the replyButton field.
     * @param replyButton a JButton used to reply to Messages.
     */
    public void setReplyButton(JButton replyButton) {
        this.replyButton = replyButton;
    }

    /**
     * This is the Mutator method for the replyTextField field.
     * @param replyTextField a JTextField used to send Message replies.
     */
    public void setReplyTextField(JTextField replyTextField) {
        this.replyTextField = replyTextField;
    }

    /**
     * This is the Mutator method for the sendWarningButton field.
     * @param sendWarningButton a JButton used to send a warning to a User.
     */
    public void setSendWarningButton(JButton sendWarningButton) {
        this.sendWarningButton = sendWarningButton;
    }

    /**
     * This is the Mutator method for the userDetailsText field.
     * @param userDetailsText a JTextArea which contains the details for the User.
     */
    public void setUserDetailsText(JTextArea userDetailsText) {
        this.userDetailsText = userDetailsText;
    }

    /**
     * This is the Mutator method for the userFirstname field.
     * @param userFirstname a JLabel for the User's first name.
     */
    public void setUserFirstname(JLabel userFirstname) {
        this.userFirstname = userFirstname;
    }

    /**
     * This is the Mutator method for the userList field.
     * @param userList a JList used to list all the Users in the system.
     */
    public void setUserList(JList userList) {
        this.userList = userList;
    }

    /**
     * This is the Mutator method for the userListModel field.
     * @param userListModel a DefaultListModel used to populate the list of Users.
     */
    public void setUserListModel(DefaultListModel userListModel) {
        this.userListModel = userListModel;
    }

    /**
     * This is the Mutator method for the userMessages field.
     * @param userMessages An ArrayList of Messages.
     */
    public void setUserMessages(ArrayList<Message> userMessages) {
        this.userMessages = userMessages;
    }

    /**
     * This is the Mutator method for the userNames field.
     * @param userNames an ArrayList of Strings containing the usernames of
     *                  the Users in the system.
     */
    public void setUserNames(ArrayList<String> userNames) {
        this.userNames = userNames;
    }

    /**
     * This method is a generic method to refresh lists displayed on the form.
     * @param content   a String containing the list content.
     * @param list      a JList which is the list to be refreshed.
     * @param listModel a DefaultListModel loaded into the list.
     */
    private void refreshList(String content, JList list, DefaultListModel listModel)
    {
        list.setVisible(true);
        listModel.addElement(content);
    }

    /**
     * This method refreshes the User lock list specifically.
     * @param name a String representing a User to add to the list.
     */
    private void refreshList(String name)
    {
        userList.setVisible(true);
        userDetailsText.setBackground(Color.white);

        userListModel.addElement(name);
    }
}
