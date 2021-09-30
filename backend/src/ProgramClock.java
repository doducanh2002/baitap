import javax.swing.*;
import java.text.*;
import java.util.*;

public class ProgramClock implements Runnable {

    Thread thread;
    String time = "";
    JButton button;
    JFrame frame;

    ProgramClock() {
        frame = new JFrame();

        thread = new Thread(this);
        thread.start();

        button = new JButton();
        button.setBounds(100, 100, 100, 50);

        frame.add(button);
        frame.setSize(300, 400);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void run() {

            while (true) {
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                time = formatter.format(new Date());
                printTime();
            }
    }

    public void printTime() {
        button.setText(time);
    }

    public static void main(String[] args) {
        new ProgramClock();
    }
}
