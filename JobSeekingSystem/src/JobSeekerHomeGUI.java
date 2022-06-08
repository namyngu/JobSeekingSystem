import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JobSeekerHomeGUI {


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
    private JTable searchResults;
    private JComboBox comboBox3;
    private JTextField textField2;
    private JTabbedPane navbar;
    private JPanel home;
    private JPanel search;
    private JPanel applicationsPanel;
    private JPanel profile;
    private JLabel phoneText;
    private JList list1;
    private JPanel inboxPanel;
    private JPanel profilePanel;
    private JButton editProfileButton;

    private Jobseeker jobseeker;



    public JobSeekerHomeGUI() {

        JFrame window = new JFrame("JSS: Job Seeker Home");
        window.add(navbar);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setBounds(600,40,100,100);
        window.pack();
        window.setResizable(true);
        window.setVisible(true);


        //test build table will condense into reusable method
        String[] jobListColumns = {"JobID", "Title", "Employer", "Location", "Salary", "Type"};
        String[][] jobListRows = {
                {"001", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"002", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"003", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"004", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"},
                {"005", "Software Developer", "Google", "San Francisco","$300,000", "Full Time"}
        };
        DefaultTableModel jobModel = new DefaultTableModel(jobListRows, jobListColumns);
        jobTable.setModel(jobModel);



        //Edit profile button to open edit profile GUI
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JobSeekerUpdateGUI updateGUI = new JobSeekerUpdateGUI();
            }
        });
    }



}
