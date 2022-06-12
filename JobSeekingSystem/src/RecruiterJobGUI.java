import javax.swing.*;

public class RecruiterJobGUI {
    private JPanel jobPanel;
    private JPanel jobContainer;
    private JLabel jobTitle;
    private JLabel jobLocation;
    private JLabel jobSalary;
    private JLabel jobType;
    private JLabel jobDescription;
    private JTable table1;
    private JScrollPane jobApplicationsTable;
    private JButton editJobButton;
    private JPanel recruiterJobGUI;

    private RecruiterControl control;
    private int jobID;
    private Job myJob;

    public RecruiterJobGUI(RecruiterControl control, int jobID)
    {
        this.control = control;
        this.jobID = jobID;
        //find job from the jobID


        JFrame frame = new JFrame("RecruiterJobGUI");
        frame.setContentPane(this.recruiterJobGUI);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocation(650,40);

        try
        {
            myJob = File_Control.findJob(control.getJobList(), jobID);
        }
        catch (Exception e)
        {
            System.out.println("ERROR cannot find job, exiting...");
            frame.dispose();
        }

        frame.setVisible(true);
    }
}
