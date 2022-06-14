import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestGUI
{
    private JTextPane textPane1;
    private JButton button1;
    private JPanel testPanel;
    private JLabel labelobe;
    private JTextField coverLetterField;

    JSS program;
    JobseekerControl control;

    public TestGUI()
    {
        JFrame frame1 = new JFrame("TestGUI");
        frame1.setContentPane(this.testPanel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);


    }

    public TestGUI(JSS jss, JobseekerControl control)
    {
        this.program = jss;
        this.control = control;

        JFrame frame = new JFrame("TestGUI");
        frame.setContentPane(this.testPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        labelobe.setText(control.getFullName());



        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int jobID = 1;
                String coverLetter = coverLetterField.getText();
//                System.out.println("this is the text: " + coverLetter);
                control.apply(jobID,coverLetter);
            }
        });
    }


}
