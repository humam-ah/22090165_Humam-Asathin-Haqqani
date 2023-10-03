
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import gnu.io.*;
import java.io.*;
import java.util.*;

public class MainClass {
    private JFrame frame;
    private JLabel heightLabel;

    public MainClass() {
        frame = new JFrame("Pengukur Tinggi Badan");
        frame.setLayout(new FlowLayout());
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        heightLabel = new JLabel("Tinggi Badan: - cm");
        frame.add(heightLabel);

        connectToArduino();

        frame.setVisible(true);
    }

    private void connectToArduino() {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM3"); // Ganti dengan nama port Arduino Anda
            if (portIdentifier.isCurrentlyOwned()) {
                System.out.println("Port sudah digunakan");
            } else {
                CommPort commPort = portIdentifier.open(MainClass.class.getName(), 2000);

                if (commPort instanceof SerialPort) {
                    SerialPort serialPort = (SerialPort) commPort;
                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                    InputStream in = serialPort.getInputStream();
                    serialPort.addEventListener(new SerialReader(in));
                    serialPort.notifyOnDataAvailable(true);
                } else {
                    System.out.println("Port ini bukan port serial.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class SerialReader implements SerialPortEventListener {
        private InputStream in;

        public SerialReader(InputStream in) {
            this.in = in;
        }

        public void serialEvent(SerialPortEvent arg0) {
            int data;
            try {
                int len = 0;
                while ((data = in.read()) > -1) {
                    if (data == '\n') {
                        break;
                    }
                    len++;
                }
                byte[] receivedData = new byte[len];
                in.read(receivedData, 0, len);
                String distanceStr = new String(receivedData);
                heightLabel.setText("Tinggi Badan: " + distanceStr + " cm");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainClass();
            }
        });
    }
}
