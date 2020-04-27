package SerialPlotter;

import javax.swing.JOptionPane;

import core.LoopManager;
import core.TaskManager;

public class App {
    public static void main(String[] args) {

        while (true) {
            String labelPanel = "Input Port index and BoundRate: \n Ports available: \n";
            for (int i = 0; i < Commands.getInstance().getPortList().length; i++) {
                labelPanel += Integer.toString(i) + ". " + Commands.getInstance().getPortList()[i].getSystemPortName()
                        + "\n";
            }

            String Input = JOptionPane.showInputDialog(null, labelPanel);

            String[] splitted = Input.split(" ");
            try {
                System.out.println(splitted[0] + " " + splitted[1]);
                Commands.getInstance().ConnectPort(Integer.valueOf(splitted[0]),
                        Integer.valueOf(Integer.valueOf(splitted[1])));
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LoopManager loopM = new LoopManager();
        loopM.add_mode(ModeSerialPlotter.getTaskManager());

        ModeSerialPlotter.getInstance().setPeriod(10);
        loopM.add_mode(ModeSerialPlotter.getInstance());

        Thread t0 = new Thread(loopM);
        t0.start();

        Thread t1 = new Thread(ModeSerialPlotter.getInstance().frame.getUpdaterChart(1000 / 30));
        t1.start();
    }
}
