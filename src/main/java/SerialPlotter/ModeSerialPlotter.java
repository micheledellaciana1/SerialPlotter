package SerialPlotter;

import java.util.ArrayList;
import java.util.Vector;

import core.AMode;
import core.ChartFrame;
import core.LoopManager;
import core.ModChartPanel;
import core.TaskManager;

import java.awt.geom.Point2D;

public class ModeSerialPlotter extends AMode {

    static public ChartFrame frame = new ChartFrame(new ModChartPanel(), "Serial Plotter");

    private ArrayList<Vector<Point2D>> _datas;
    static private ModeSerialPlotter _instance = new ModeSerialPlotter("SerialPlotter", 0);
    static private TaskManager _TaskManager = new TaskManager("TMSerialPlotter");

    public static ModeSerialPlotter getInstance() {
        return _instance;
    }

    public static TaskManager getTaskManager() {
        return _TaskManager;
    }

    private ModeSerialPlotter(String arg0, double arg1) {
        super(arg0, arg1);
        _datas = new ArrayList<Vector<Point2D>>();
        frame.setJMenuBar(MenuBarChart.getInstance());
        frame.setVisible(true);
    }

    @Override
    public void loop() {
        double time = (System.currentTimeMillis() - LoopManager.startingTime) * 0.001;
        ArrayList<Double> values = Commands.getInstance().WaitsToGetDatas();

        for (int i = _datas.size(); i < values.size(); i++) {
            Vector<Point2D> temp = new Vector<Point2D>();
            _datas.add(temp);
            frame.addSeries(temp, "Serie " + i);
        }

        for (int i = 0; i < values.size(); i++)
            _datas.get(i).add(new Point2D.Double(time, values.get(i)));

        super.run();
    }
}