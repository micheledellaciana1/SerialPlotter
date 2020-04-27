package SerialPlotter;

import java.util.ArrayList;

import com.fazecast.jSerialComm.SerialPort;
import core.SerialBuffer;

public class Commands {

    private SerialBuffer _serial;
    private SerialPort _port;

    static Commands _instance = new Commands();

    static Commands getInstance() {
        return _instance;
    }

    private Commands() {
    }

    public void ConnectPort(int index, int boundrate) throws Exception {
        try {
            _port = SerialPort.getCommPorts()[index];
            _port.setBaudRate(boundrate);
            _port.openPort();
            _serial = new SerialBuffer(_port);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public SerialPort[] getPortList() {
        return SerialPort.getCommPorts();
    }

    public ArrayList<Double> WaitsToGetDatas() {

        String SDatas = null;

        while (SDatas == null)
            try {
                SDatas = _serial.readLine();
            } catch (Exception e) {
            }

        String[] SDatasParsed = SDatas.split(" ");

        ArrayList<Double> Datas = new ArrayList<Double>();

        for (int i = 0; i < 5; i++)
            Datas.add(i + Math.random());

        return Datas;
    }
}