import javax.print.attribute.IntegerSyntax;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
        window.setLocation(600,20);
        window.pack();
        window.setResizable(true);
        window.setVisible(true);


        DefaultListModel candidateListGUI = new DefaultListModel();
        DefaultListModel skillsListGUI = new DefaultListModel();

        try {
            populateSkills("SkillList.csv");
        }
        catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        for (int x = 0; x < allSkillList.getModel().getSize(); x++) {
            Object element = allSkillList.getModel().getElementAt(x);
            skillsListGUI.addElement(element);
        }

        //launch create job screen
//        createJobButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                CreateJobGUI createJobGUI = new CreateJobGUI();
//            }
//        } );

        addSkillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This method adds the new skill out of alphabetical order,
                // ideally we should sort this but that is TODO.
                Object element = allSkillList.getSelectedValue();
                candidateListGUI.addElement(element);
                candidateSkillList.setModel(candidateListGUI);
                skillsListGUI.removeElement(element);
                allSkillList.setModel(skillsListGUI);
            }
        });

        removeSkillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This method adds the new skill out of alphabetical order,
                // ideally we should sort this but that is TODO.
                Object element = candidateSkillList.getSelectedValue();
                candidateListGUI.removeElement(element);
                candidateSkillList.setModel(candidateListGUI);
                skillsListGUI.addElement(element);
                allSkillList.setModel(skillsListGUI);
            }
        });

        createJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CreateJobGUI createJob = new CreateJobGUI(parent);
                    window.dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
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

        //double click on jobs to bring up the jobs menu
        jobsTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                jobsTable = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = jobsTable.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && jobsTable.getSelectedRow() != -1) {
                    // Action to take after double clicking.
                    int selectedRow = jobsTable.getSelectedRow();
                    int ID = Integer.parseInt(jobsTable.getValueAt(selectedRow, 0).toString());
                    RecruiterJobGUI recruiterJobGUI= new RecruiterJobGUI(parent, ID);
                }
            }
        });

        createTable();
    }

    private void createTable()
    {
        ArrayList<Job> myJobs = findRecruiterJob(myParent.getJobList());
        String[][] data = new String[myJobs.size()][7];

        for (int i = 0; i < myJobs.size(); i++)
        {
            for (int j = 0; j < 6; j++)
            {
                switch (j)
                {
                    case 0:
                        data[i][j] = String.valueOf(myJobs.get(i).getJobID());
                        break;

                    case 1:
                        data[i][j] = myJobs.get(i).getJobTitle();
                        break;

                    case 2:
                        data[i][j] = myJobs.get(i).getEmployer();
                        break;

                    case 3: {
                        int locationID = myJobs.get(i).getLocationID();
                        String city = myParent.getLocationList().get(locationID).getCity();
                        data[i][j] = city;
                        break;
                    }

                    case 4:
                        data[i][j] = String.valueOf(myJobs.get(i).getJobStatus());
                        break;

                    case 5:
                        data[i][j] = myJobs.get(i).getJobType();
                        break;

                    case 6: {
                        if (myJobs.get(i).getApplications().size() == 0)
                            data[i][j] = "0";
                        else
                            data[i][j] = String.valueOf(myJobs.get(i).getApplications().size());
                        break;
                    }

                    default:
                        System.out.println("Error");
                        break;
                }

            }
        }

        jobsTable.setModel(new DefaultTableModel(data, new String[]{"JobID","Title","Employer","Location","Status","Type", "Applicants"}));
    }

    public void populateSkills(String fileName) throws IOException {
        FileReader file = new FileReader(fileName);
        Scanner scan = new Scanner(file);
        ArrayList<String> returnModel = new ArrayList<>();

        while (scan.hasNextLine()) {
            String addString = scan.nextLine();
            returnModel.add(addString);
        }
        allSkillList.setListData(returnModel.toArray());
        file.close();
    }

    //Method to search through all jobs to find the recruiter's jobs.
    private ArrayList<Job> findRecruiterJob(ArrayList<Job> jobList)
    {
        ArrayList<Job> myJob = new ArrayList<>();
        //search through all jobs for the recruiter's job
        for (Job tmpJob : jobList)
        {
            if (tmpJob.getRecruiterID() == myParent.getRecruiter().getUserID())
            {
                myJob.add(tmpJob);
            }
        }
        return myJob;
    }

}


