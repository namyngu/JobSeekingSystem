import javax.swing.*;
import java.util.ArrayList;

public class JobSeekerUpdateGUI {
    private JPanel updateSkillsPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JList skillList;
    private JButton updateButton;
    private JButton cancelButton;
    private JLabel clientName;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;

    public JobSeekerUpdateGUI() {

        JFrame frame = new JFrame("Job Title");

        frame.setContentPane(updateSkillsPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

        ArrayList<String> allSkills = new ArrayList<String>();
        allSkills.add("Java");
        allSkills.add("React");
        allSkills.add("CSS");

        ArrayList<String> mySkills = new ArrayList<String>();

        mySkills.add("React");


        buildSkillList(allSkills, mySkills);

    }


    private void buildSkillList(ArrayList<String> allSkills, ArrayList<String> userSkills){

        DefaultListModel model = new DefaultListModel();

        //for each skill in allSkills create a checkbox
        for (int i = 0; i < allSkills.size(); i++)
        {
            model.addElement(allSkills.get(i));
            // Printing and display the elements in ArrayList
            System.out.print(allSkills.get(i) + " ");
        }

        skillList.setModel(model);

    }
        //loop through user skills

        //if user skill === current skill check box

    }




