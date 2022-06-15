import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Validation {

    public static void invalidInputWarning(JLabel warningLabel, String message) {
        warningLabel.setText(message);
        warningLabel.setVisible(true);

        new Timer().schedule(new TimerTask() {

            public void run() {
                warningLabel.setVisible(false);
            }
        }, 2000L); // 300 is the delay in millis
    }

    public static boolean isInputBlank(JTextField input)
    {
        return input.getText().isEmpty();
    }
}

