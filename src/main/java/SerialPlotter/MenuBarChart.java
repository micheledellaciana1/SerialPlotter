package SerialPlotter;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.data.xy.XYDataset;

import core.ATask;
import core.DataManager;

/* Very simple serial logger for CSV data stream
Input stream: 
data0 dat1 data2 '\n'
data0 dat1 data2 '\n'
data0 dat1 data2 '\n'
data0 dat1 data2 '\n'
*/

public class MenuBarChart extends JMenuBar {
    static private MenuBarChart _instance = new MenuBarChart();

    private MenuBarChart() {
        super();
        add(MenuFile());
    }

    static public MenuBarChart getInstance() {
        return _instance;
    }

    private JMenu MenuFile() {
        JMenu MenuFile = new JMenu("File");

        MenuFile.add(new AbstractAction("Export .txt") {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter(null, "txt"));
                fileChooser.setDialogTitle("Save CSV");

                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    final File fileToSave = fileChooser.getSelectedFile();

                    ModeSerialPlotter.getTaskManager().addTask(new ATask() {
                        @Override
                        public void execution() {
                            XYDataset dataset = ModeSerialPlotter.getInstance().frame.GetChartPanel().getChart()
                                    .getXYPlot().getDataset();
                            DataManager DManager = new DataManager();

                            DManager.addColoumn("Time");

                            for (int i = 0; i < dataset.getSeriesCount(); i++) {
                                DManager.addColoumn(((String) dataset.getSeriesKey(i)).replace(" ", "_"));
                                for (int j = 0; j < dataset.getItemCount(i); j++) {
                                    if (i == 0)
                                        DManager.add(i, (double) dataset.getX(i, j));
                                    DManager.add(i + 1, (double) dataset.getY(i, j));
                                }
                            }

                            DManager.save(fileToSave.getAbsolutePath());
                        }
                    });
                }
            }
        });
        return MenuFile;
    }
}