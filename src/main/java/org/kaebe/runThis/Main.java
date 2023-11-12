package org.kaebe.runThis;

import org.kaebe.fractals.juliaSet.JuliaSet;
import org.kaebe.fractals.mandelbrotFractalZoom.MandelbrotFractalZoom;
import org.kaebe.ui.DarkUI;
import org.kaebe.ui.DoNotClickPanel;
import org.kaebe.visualComponents.ui.CircleThingyUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        DarkUI.load();

        JFrame frame = new JFrame("Options");
        JPanel contentPane = new JPanel();
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0,0);

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        contentPane.add(row1);

        JPanel mandelbrotPanel = getMandelbrotPanel(frame);
        row1.add(mandelbrotPanel);

        JPanel circlePanel = getCirclePanel(frame);
        row1.add(circlePanel);

        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        contentPane.add(row2);

        JPanel juliaPanel = getJuliaPanel(frame);
        row2.add(juliaPanel);

//        DoNotClickPanel doNotClickPanel = new DoNotClickPanel();
//        row2.add(doNotClickPanel);

        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private static JPanel getJuliaPanel(JFrame frame) {
        JPanel juliaPanel = new JPanel();
        juliaPanel.setLayout(new BoxLayout(juliaPanel, BoxLayout.X_AXIS));
        JButton juliaButton = new JButton("Julia Set");
        juliaButton.addActionListener(e -> {
            frame.setVisible(false);
            JuliaSet.createWindow(() -> frame.setVisible(true));
        });
        juliaPanel.add(juliaButton);

        ImageIcon juliaImageIcon = new ImageIcon("src/main/resources/julia.png");
        JLabel circleLabel = new JLabel(juliaImageIcon);
        juliaPanel.add(circleLabel);
        return juliaPanel;
    }

    private static JPanel getCirclePanel(JFrame frame) {
        JPanel circlePanel = new JPanel();
        JButton circleButton = new JButton("Circle Thingy");
        circleButton.addActionListener(e -> {
            frame.setVisible(false);
            new Thread(() -> {
                CircleThingyUI ui = new CircleThingyUI(()->frame.setVisible(true));
                ui.setLocationRelativeTo(circleButton);

            }).start();
        });
        circlePanel.add(circleButton);

        ImageIcon circleImageIcon = new ImageIcon("src/main/resources/circle2.png");
        JLabel circleLabel = new JLabel(circleImageIcon);
        circlePanel.add(circleLabel);
        return circlePanel;
    }

    private static JPanel getMandelbrotPanel(JFrame frame) {
        JPanel mandelbrotPanel = new JPanel();
        mandelbrotPanel.setLayout(new BoxLayout(mandelbrotPanel, BoxLayout.X_AXIS));

        JButton mandelbrotButton = new JButton("Mandelbrot");
        mandelbrotButton.addActionListener(e -> {
            frame.setVisible(false);
            new Thread(() -> {
                MandelbrotFractalZoom mandelbrotFractalZoom = new MandelbrotFractalZoom(()->frame.setVisible(true));
            }).start();
        });
        mandelbrotPanel.add(mandelbrotButton);

        ImageIcon imageIcon = new ImageIcon("src/main/resources/mandelbrot.png");
        JLabel mandelbrotLabel = new JLabel(imageIcon);
        mandelbrotPanel.add(mandelbrotLabel);
        return mandelbrotPanel;
    }
}