package org.kaebe.runThis;

import org.kaebe.visualComponents.UI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Options");
        JPanel contentPane = new JPanel();
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocation(500,20);


        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel mandelbrotPanel = new JPanel();
        mandelbrotPanel.setLayout(new BoxLayout(mandelbrotPanel, BoxLayout.X_AXIS));

        JButton mandelbrotButton = new JButton("Mandelbrot");
        mandelbrotButton.addActionListener(e -> {
            frame.setVisible(false);
            new Thread(() -> {
                org.kaebe.mandelbrotFractalZoom.MandelbrotFractalZoom mandelbrotFractalZoom = new org.kaebe.mandelbrotFractalZoom.MandelbrotFractalZoom();
            }).start();
        });
        mandelbrotPanel.add(mandelbrotButton);

        ImageIcon imageIcon = new ImageIcon("src/main/resources/mandelbrot.png");
        JLabel mandelbrotLabel = new JLabel(imageIcon);
        mandelbrotPanel.add(mandelbrotLabel);
        contentPane.add(mandelbrotPanel);


        JPanel circlePanel = new JPanel();
        JButton circleButton = new JButton("Circle Thingy");
        circleButton.addActionListener(e -> {
            frame.setVisible(false);
            new Thread(() -> {
                org.kaebe.visualComponents.UI ui = new org.kaebe.visualComponents.UI();
            }).start();
        });
        circlePanel.add(circleButton);

        ImageIcon circleImageIcon = new ImageIcon("src/main/resources/circle.png");
        JLabel circleLabel = new JLabel(circleImageIcon);
        circlePanel.add(circleLabel);
        contentPane.add(circlePanel);

        frame.pack();
        frame.setVisible(true);
    }
}