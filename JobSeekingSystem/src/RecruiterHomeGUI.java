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
import java.util.TreeMap;

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
    private ArrayList<Location> locationList;
    private JScrollPane recruiterInboxTable;
    private JScrollPane recruiterJobsTable;
    private JScrollPane seekerTable;
    private JLabel searchInstructionsText;

    public RecruiterHomeGUI(RecruiterControl parent, ArrayList<Location> locations){
        myParent = parent;
        locationList = locations;
        JFrame window = new JFrame("JSS: Recruiter Home");
        window.add(recruiterNav);
        searchInstructionsText.setVisible(false);

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

        // Add a listener so that double-clicking a searched candidate opens their Profile
        searchResults.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                Point point = mouseEvent.getPoint();
                int row = searchResults.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && searchResults.getSelectedRow() != -1) {
                    // Action to take after double clicking.
                    int selectedRow = searchResults.getSelectedRow();
                    int seekerID = Integer.parseInt(searchResults.getValueAt(selectedRow, 1).toString());
                    RecruiterViewCandidateGUI thisJobseeker = new RecruiterViewCandidateGUI(myParent, seekerID);
                }
            }
        });

        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Get data from the user's selections on the form.
                String location = textField1.getText();
                ListModel searchModel = candidateSkillList.getModel();
                ArrayList<String> searchSkills = new ArrayList<>();
                for (int x = 0; x < searchModel.getSize(); x ++) {
                    String skill = searchModel.getElementAt(x).toString();
                    searchSkills.add(skill);
                }

                // Pass the user's selections into a search to find matching jobSeekers.
                TreeMap<Integer, ArrayList<Jobseeker>> results = myParent.seekerSearch(location, searchSkills);

                /* Set the list of search results into the candidate search panel.
                 * First, set the columns up.
                 */
                String[] seekerListColumns = {"Match Score", "User ID", "First Name", "Last Name", "Location", "Skills"};

                // Set each Jobseeker up as a Row. Need to do some work to populate the location field.
                ArrayList<String[]> seekerListRows = new ArrayList<>();
                for (Integer key : results.keySet()) {
                    for (int i = 0; i < results.get(key).size(); i++) {
                        Jobseeker seeker = results.get(key).get(i);
                        String resultLocation = "";
                        for (Location place : locationList) {
                            if (place.getLocationID() == seeker.getLocation().getLocationID()) {
                                resultLocation = place.toString();
                                break;
                            }
                        }
                        String [] thisSeeker = {Integer.toString(key), Integer.toString(seeker.getUserID()),
                                seeker.getFirstName(), seeker.getLastName(), resultLocation,
                                String.join(",", seeker.getSkills())};
                        seekerListRows.add(thisSeeker);
                    }
                }

                // Convert the list of rows into a TableModel readable format.
                String[][] rows = seekerListRows.toArray(new String[0][0]);

                // Set the Table Model into the results table.
                DefaultTableModel freshModel = new DefaultTableModel(rows, seekerListColumns);
                searchResults.setModel(freshModel);

                /* If there are results, provide instructions to the User on
                 * how to view the candidate.
                 */
                if (!results.isEmpty()) {
                    searchInstructionsText.setVisible(true);
                } else {
                    searchInstructionsText.setVisible(false);
                }

                searchResults.repaint();
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
                    window.dispose();
                }
            }
        });

        createTable();
    }

    private void createTable()
    {
        ArrayList<Job> recruiterJobs = myParent.getRecruiter().getJobs();
        ArrayList<Job> activeJobs = new ArrayList<>();
        for (Job tmpJob : recruiterJobs)
        {
            if (!tmpJob.getJobStatus().equalsIgnoreCase("Archived"))
                activeJobs.add(tmpJob);
        }

        //ArrayList<Job> myJobs = findRecruiterJob(myParent.getJobList());
        String[][] data = new String[activeJobs.size()][7];

        for (int i = 0; i < activeJobs.size(); i++)
        {
            for (int j = 0; j < 6; j++)
            {
                switch (j)
                {
                    case 0:
                        data[i][j] = String.valueOf(activeJobs.get(i).getJobID());
                        break;

                    case 1:
                        data[i][j] = activeJobs.get(i).getJobTitle();
                        break;

                    case 2:
                        data[i][j] = activeJobs.get(i).getEmployer();
                        break;

                    case 3: {
                        try
                        {
                            Location location = File_Control.findLocation(myParent.getLocationList(), activeJobs.get(i).getLocationID());
                            data[i][j] = location.getCity();
                        }
                        catch (Exception e)
                        {
                            System.out.println("Error cannot find location.");
                        }
                        break;
                    }

                    case 4:
                        data[i][j] = String.valueOf(activeJobs.get(i).getJobStatus());
                        break;

                    case 5:
                        data[i][j] = activeJobs.get(i).getJobType();
                        break;

                    case 6: {
                        if (activeJobs.get(i).getApplications().size() == 0)
                            data[i][j] = "0";
                        else
                            data[i][j] = String.valueOf(activeJobs.get(i).getApplications().size());
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
}


