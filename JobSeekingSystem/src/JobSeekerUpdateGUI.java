import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class JobSeekerUpdateGUI {
    private JPanel updateSkillsPanel;
    private JTextField jobseekerPhoneInput;
    private JTextField jobseekerEmailInput;
    private JTextField textField3;
    private JList userSkillList;
    private JButton updateButton;
    private JLabel jobSeekerFullname;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;
    private JList allSkillList;
    private JButton addSkillButton;
    private JButton removeSkillButton;
    private JList list1;
    private DefaultListModel userSkillsModel;
    private DefaultListModel allSkillsModel;


    public JobSeekerUpdateGUI(JobseekerControl jsControl, JobSeekerHomeGUI jshomescreen) {
        userSkillsModel = new DefaultListModel<>();
        allSkillsModel = buildModel("SkillList.csv");
        JFrame frame = new JFrame("Job Title");

        frame.setContentPane(updateSkillsPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

        ArrayList<String> allSkills = new ArrayList<String>();;
        ArrayList<String> mySkills = jsControl.getSkills();

        //display name in profile
        jobSeekerFullname.setText(jsControl.getFullName());
        jobseekerEmailInput.setText(jsControl.getEmail());
        jobseekerPhoneInput.setText(jsControl.getPhone());
//        jobseekerLocationSelector.setText(parent.getLocation().toString());

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

                //get updated skills from user skills Jlist
                ArrayList<String> newSkillList = listToArrayList(userSkillList);

                //update jobseeker skills arraylist
                jsControl.setSkills(newSkillList);

                //update jobseeker home gui to display new skills
                jshomescreen.buildSkillList();

                //update jobseeker-skills file
                jsControl.saveSkills();


                //set email, phone & location on jobseeker
                jsControl.setEmail(jobseekerEmailInput.getText().trim());
                jsControl.setPhone(jobseekerPhoneInput.getText().trim());

                //update jobseeker home gui

                //save updated details to contact

                frame.dispose();

            }
        });
    }

    //add data to JList using ListModel and ArrayList of data
    private void buildList(ArrayList<String> list,DefaultListModel model, JList listGUI){

        for (int i = 0; i < list.size(); i++)
        {
            model.addElement(list.get(i));
        }
        listGUI.setModel(model);
    }

    // take skills from csv file and return a List Model
    public DefaultListModel buildModel(String fileName )  {
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

            } catch (FileNotFoundException e){
                System.out.println("File not found");
            } catch (IOException e){
                //code
            }

        return model;
        }

        //convert a Jlist into an ArrayList
        private ArrayList<String> listToArrayList(JList list)
        {
            ArrayList<String> arrList = new ArrayList<>();

            int listSize = list.getModel().getSize();

                for (int i = 0; i < listSize; i++)
                {
                    String skill = list.getModel().getElementAt(i).toString();;
                    arrList.add(skill);
                }

            return arrList;
        };

    }




