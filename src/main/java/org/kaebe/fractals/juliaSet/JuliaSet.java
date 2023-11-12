package org.kaebe.fractals.juliaSet;

import org.kaebe.runThis.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class JuliaSet extends JPanel {
    public static final int SLIDER_RESOLUTION = 128;
    private BufferedImage image;
    private double cReal = -0.7;
    private double cImaginary = 0.27015;
    
    public JuliaSet() {
        setPreferredSize(new Dimension(1920, 1080));
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        }
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                double zx = 1.5 * (x - getWidth() / 2.0) / (0.5 * getWidth());
                double zy = (y - getHeight() / 2.0) / (0.5 * getHeight());
                int iter = 0;
                while (zx * zx + zy * zy < 4 && iter < 100) {
                    double tmp = zx * zx - zy * zy + cReal;
                    zy = 2.0 * zx * zy + cImaginary;
                    zx = tmp;
                    iter++;
                }
                if (iter == 100) {
                    image.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    image.setRGB(x, y, Color.HSBtoRGB(iter / 100f, 1, 1));
                }
            }
        }
        g.drawImage(image, 0, 0, null);
    }
    
    public static void createWindow(Runnable openMainWindow) {
        JFrame frame = new JFrame("Julia Set");
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                openMainWindow.run();
            }
        });

        JuliaSet juliaSet = new JuliaSet();

        JPanel contentPane = new JPanel();
        frame.setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        contentPane.add(controls);

        JSlider cRealSlider = new JSlider(-SLIDER_RESOLUTION, SLIDER_RESOLUTION);
        cRealSlider.setValue(-70);
        cRealSlider.addChangeListener(e -> {
            juliaSet.cReal = (double) cRealSlider.getValue() / SLIDER_RESOLUTION;
            juliaSet.repaint();
            System.out.println("cReal:" + cRealSlider.getValue() / (double) SLIDER_RESOLUTION);
        });
        controls.add(cRealSlider);

        JSlider cImaginarySlider = new JSlider(-SLIDER_RESOLUTION, SLIDER_RESOLUTION);
        cImaginarySlider.setValue(27);
        cImaginarySlider.addChangeListener(e -> {
            juliaSet.cImaginary = (double) cImaginarySlider.getValue() / SLIDER_RESOLUTION;
            juliaSet.repaint();
            System.out.println("cImaginary:" + cImaginarySlider.getValue() / (double) SLIDER_RESOLUTION);
        });
        controls.add(cImaginarySlider);

        controls.add(new JuliaSetPresets(cRealSliderValue -> {
            cRealSlider.setValue(cRealSliderValue);
            juliaSet.cReal = cRealSliderValue / 100.0;
            juliaSet.repaint();
        }, cImaginarySliderValue -> {
            cImaginarySlider.setValue(cImaginarySliderValue);
            juliaSet.cImaginary = cImaginarySliderValue / 100.0;
            juliaSet.repaint();
        }));

        frame.getContentPane().add(juliaSet);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
