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

        this.userNames = new ArrayList<String>();
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
            for (int i=0; i<numJobs; i+=1)
            {
                Job currentJob = allJobs.get(i);
                String title = currentJob.getJobTitle();
                int recruiterID = currentJob.getRecruiterID();
                User recruiter = this.program.getUserByID(recruiterID);
                String recruiterName = recruiter.getUserName();

                String toDisplay = "";

                toDisplay += title + " : " + recruiterName;

                refreshList(toDisplay,jobList,jobListModel);
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
                    if (locked == true)
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
