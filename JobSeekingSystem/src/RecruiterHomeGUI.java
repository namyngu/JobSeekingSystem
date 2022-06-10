import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class RecruiterHomeGUI {
    private RecruiterControl myParent;
    private JTabbedPane recruiterNav;
    private JPanel jobsContainer;
    private JTable jobsTable;
    private JPanel searchPanel;
    private JPanel inboxPanel;
    private JPanel jobsPanel;
    private JPanel inboxContainer;
    private JPanel updateSkillsPanel;
    private JLabel candidateLocationLabel;
    private JTextField textField1;
    private JButton addSkillButton;
    private JButton removeSkillButton;
    private JButton searchButton;
    private JList allSkillList;
    private JList candidateSkillList;
    private JTable inboxTable;
    private JTable searchResults;
    private JButton createJobButton;
    private JScrollPane recruiterInboxTable;
    private JScrollPane recruiterJobsTable;

    public RecruiterHomeGUI(RecruiterControl parent){
        myParent = parent;
        JFrame window = new JFrame("JSS: Recruiter Home");
        window.add(recruiterNav);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(600,40,100,100);
        window.pack();
        window.setResizable(true);
        window.setVisible(true);


        //launch create job screen
//        createJobButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                CreateJobGUI createJobGUI = new CreateJobGUI();
//            }
//        } );
        createJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CreateJobGUI createJob = new CreateJobGUI();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String location = textField1.getText();
                ListModel searchModel = candidateSkillList.getModel();
                ArrayList<String> searchSkills = new ArrayList<>();
                for (int x = 0; x < searchModel.getSize(); x ++) {
                    String skill = searchModel.getElementAt(x).toString();
                    searchSkills.add(skill);
                }

                // This method should return a list of jobSeekers and then
                // we need to display them somehow.
                myParent.seekerSearch(location, searchSkills);
            }
        });

    }


}


