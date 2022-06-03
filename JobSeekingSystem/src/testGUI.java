import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class testGUI
{
    private JButton openRegistrationButton;
    private JPanel panel1;

    private JSS jss;

    public testGUI(JSS jss)
    {
        this.jss = jss;
        JFrame frame = new JFrame("testGUI");
        frame.setContentPane(new testGUI(jss).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        openRegistrationButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                this.openReg();
            }

            private void openReg()
            {
//                jss.test();
            }
        });
    }
}
