import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobSeekerUpdateGUI {
    private JPanel updateSkillsPanel;
    private JTextField phoneInput;
    private JTextField emailInput;
    private JTextField locationInput;
    private JList userSkillList;
    private JButton updateButton;
    private JLabel jobSeekerFullname;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;
    private JList allSkillList;
    private JButton addSkillButton;
    private JButton removeSkillButton;
    private JList locationList;
    private JLabel phoneWarning;
    private JLabel emailWarning;
    private JLabel locationWarning;
    private JLabel skillWarning;
    private DefaultListModel userSkillsModel;
    private DefaultListModel allSkillsModel;

    private DefaultListModel locationModel;

    private ArrayList<Location> locationsArr;

    private JobseekerControl jsControl;

    public JobSeekerUpdateGUI(JobseekerControl jsControl, JobSeekerHomeGUI jshomescreen, ArrayList<Location> locations) {
        locationsArr = locations;
        this.jsControl = jsControl;
        buildLocationList();
        userSkillsModel = new DefaultListModel<>();
        allSkillsModel = buildModel("SkillList.csv");
        JFrame frame = new JFrame("Job Title");

        frame.setContentPane(updateSkillsPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

        ArrayList<String> allSkills = new ArrayList<String>();
        ;
        ArrayList<String> mySkills = jsControl.getSkills();

        //display contact info in profile
        displayContactInfo();


//        //build user skill list
        buildList(mySkills, userSkillsModel, userSkillList);

        //build all skills list
        buildList(allSkills, allSkillsModel, allSkillList);




        //action listener on button to add skill to user and remove from all skills list
        addSkillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userSkillsModel.addElement(allSkillList.getSelectedValue());

                allSkillsModel.removeElement(allSkillList.getSelectedValue());
            }
        });

        //action listener on button to move skill from user back to all skills list
        removeSkillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                allSkillsModel.addElement(userSkillList.getSelectedValue());
                userSkillsModel.removeElement(userSkillList.getSelectedValue());

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean validInput = true;

                //check no inputs are empty

                JTextField[] inputs = {phoneInput, emailInput};
                JLabel[] warnings = {phoneWarning, emailWarning};
                JLabel[] labels = {phoneLabel, emailLabel};

                validInput = Validation.validInputs(inputs, warnings, labels);

                validInput = Validation.isValidEmail(emailInput.getText(), emailWarning);
                validInput = Validation.isValidPhoneNo(phoneInput.getText(), phoneWarning);
                if (userSkillList.getModel().getSize() == 0) {
                    Validation.invalidInputWarning(skillWarning, "You must list at least 1 skill");
                    return;
                }

                if (locationInput.getText().isEmpty()) {
                    Validation.invalidInputWarning(locationWarning, "Location details are required.");
                    validInput = false;
                }

                if (validInput) {
                    //get updated skills from user skills Jlist
                    ArrayList<String> newSkillList = listToArrayList(userSkillList);

                    //update jobseeker skills arraylist
                    jsControl.setSkills(newSkillList);

                    //update jobseeker home gui to display new skills
                    jshomescreen.buildSkillList();

                    //update jobseeker-skills file
                    jsControl.saveSkills();

                    //set email, phone & location on jobseeker object
                    jsControl.setEmail(emailInput.getText().trim());
                    jsControl.setPhone(phoneInput.getText().trim());
                    jsControl.setLocation(locationsArr.get(locationList.getSelectedIndex()));
                    System.out.println(locationsArr.get(locationList.getSelectedIndex()).getLocationID());

                    //update jobseeker home gui to reflect new contact information
                    System.out.println("Location updated to:");
                    System.out.println(jsControl.getLocation().toString());

                //update jobseeker home gui
                jshomescreen.buildContactInfo();
                //save updated details to contact to database
                jsControl.saveContactInfo();
                    //update jobseeker home gui
                    jshomescreen.buildContactInfo();
                    //save updated details to contact to database

                    jsControl.saveContactInfo();

                // With my new skills, run the Recommended Jobs search again
                jshomescreen.displayRecommendedJobs();

                frame.dispose();
                    frame.dispose();
                }

            }
        });
        locationList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {

                locationInput.setText(locationModel.getElementAt(locationList.getSelectedIndex()).toString());


            }
        });
    }

    //add data to JList using ListModel and ArrayList of data
    private void buildList(ArrayList<String> list, DefaultListModel model, JList listGUI) {

        for (int i = 0; i < list.size(); i++) {
            model.addElement(list.get(i));
        }
        listGUI.setModel(model);
    }

    private void buildLocationList() {
        locationModel = new DefaultListModel<>();


        for (int i = 0; i < locationsArr.size(); i++) {
            locationModel.addElement(locationsArr.get(i));
        }

        System.out.println();

        locationList.setModel(locationModel);
    }

    // take skills from csv file and return a List Model
    public DefaultListModel buildModel(String fileName) {
        DefaultListModel model = new DefaultListModel<>();

        try {
            FileReader file = new FileReader(fileName);
            Scanner scan = new Scanner(file);
            String returnString = "";

            while (scan.hasNextLine()) {
                returnString = scan.nextLine();
                model.addElement(returnString);
            }

            file.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            //code
        }

        return model;
    }

    //convert a Jlist into an ArrayList
    private ArrayList<String> listToArrayList(JList list) {
        ArrayList<String> arrList = new ArrayList<>();

        int listSize = list.getModel().getSize();

        for (int i = 0; i < listSize; i++) {
            String skill = list.getModel().getElementAt(i).toString();
            ;
            arrList.add(skill);
        }

        return arrList;
    }

    ;

    public void displayContactInfo() {
        jobSeekerFullname.setText(jsControl.getFullName());
        emailInput.setText(jsControl.getEmail());
        phoneInput.setText(jsControl.getPhone());

        //loop through locations to find matching location ID
        for (int i = 0; i < locationsArr.size(); i++) {
            Location currentLocation = locationsArr.get(i);
            if (currentLocation.getLocationID() == jsControl.getLocation().getLocationID()) {
                //on match pre-select on the location list & fill location input
                locationList.setSelectedIndex(i);
                locationInput.setText(locationModel.getElementAt(locationList.getSelectedIndex()).toString());

            }

        }
//
    }

}




