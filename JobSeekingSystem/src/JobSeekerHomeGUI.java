import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public JobSeekerHomeGUI() {

    }

    public JobSeekerHomeGUI(User jobSeeker) {

        JFrame homeframe = new JFrame("JSS: Job Seeker Home");

        homeframe.setContentPane(this.JSHomePanel);
        homeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeframe.setBounds(600,40,100,100);
        homeframe.pack();

        homeframe.setResizable(true);
        homeframe.setVisible(true);
        createTable();


        /*Add these methods back in once the actual components exist on the GUI

            //navbar
            searchJobsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            myApplicationsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            messagesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            profileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });

        */
    }


    //create table
    private void createTable(){

        jobTable.setModel(new DefaultTableModel(
                null,
                new String[]{"JobID", "Title", "Employer", "Location", "Salary", "Type"}
        ));
    }

}
