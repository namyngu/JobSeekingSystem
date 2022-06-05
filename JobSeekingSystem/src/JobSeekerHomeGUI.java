import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JobSeekerHomeGUI {


    private JButton profileButton;
    private JButton searchJobsButton;
    private JButton myApplicationsButton;
    private JButton messagesButton;
    private JButton jobSearchButton;
    private JButton applicationsButton;
    private JTable jobsTable;
    private JPanel JSHomePanel;
    private JTable jobTable;

    public JobSeekerHomeGUI() {

        JFrame frame = new JFrame("JSS: Job Seeker Home");

        frame.setContentPane(this.JSHomePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
        createTable();


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
    }

    private void createTable(){

        jobTable.setModel(new DefaultTableModel(
                null,
                new String[]{"JobID", "Title", "Employer", "Location", "Salary", "Type"}
        ));
    }



}
