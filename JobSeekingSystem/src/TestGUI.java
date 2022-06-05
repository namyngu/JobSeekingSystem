import javax.swing.*;

public class TestGUI
{
    private JTextPane textPane1;
    private JButton button1;
    private JPanel testPanel;
    private JLabel labelobe;

    public TestGUI()
    {
        JFrame frame1 = new JFrame("TestGUI");
        frame1.setContentPane(this.testPanel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);

    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("TestGUI");
        frame.setContentPane(new TestGUI().testPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
