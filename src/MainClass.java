
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

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainClass();
            }
        });
    }
}