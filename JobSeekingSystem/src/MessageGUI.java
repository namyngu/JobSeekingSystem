import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageGUI
{
    private JPanel messageJPanel;
    private JTextField messageTextField;
    private JButton sendButton;
    private JTextField headerTextField;

   private int sender;
   private int destination;

//
//
//
//    public MessageGUI (int sender, int desitnation)
//    {
//        this.destination = desitnation;
//        this.sender = sender;
//
//        JFrame frame = new JFrame("MessageGUI");
//        frame.setContentPane(this.messageJPanel);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//        sendButton.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//             getMessage(sender,destination);
//
//            }
//        });
//
//    }
//
//    public Message getMessage(int origin, int destination)
//    {
//
//        String header = headerTextField.getText();
//        String body = messageTextField.getText();
//        Message send = new Message(origin,destination,header,body);
//        return send;
//    }
}
