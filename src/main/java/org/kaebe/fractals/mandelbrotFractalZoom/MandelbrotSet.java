package org.kaebe.fractals.mandelbrotFractalZoom;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MandelbrotSet extends JPanel {
    private BufferedImage image;
    private double zoom = 1;
    private double offsetX = -0.5;
    private double offsetY = 0;
    
    public MandelbrotSet() {
        setPreferredSize(new Dimension(800, 600));
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                double x = (e.getX() - getWidth() / 2.0) / (getWidth() / 4.0 * zoom) + offsetX;
                double y = (e.getY() - getHeight() / 2.0) / (getHeight() / 4.0 * zoom) + offsetY;

                if( e.getButton() == MouseEvent.BUTTON1 ) {
                    zoom *= 2;
                }
                else if( e.getButton() == MouseEvent.BUTTON3 ) {
                    zoom /= 2;
                }

                offsetX = x - (e.getX() - getWidth() / 2.0) / (getWidth() / 4.0 * zoom);
                offsetY = y - (e.getY() - getHeight() / 2.0) / (getHeight() / 4.0 * zoom);

                repaint();
            }
        });
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        }
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                double zx = 0;
                double zy = 0;
                double cX = (x - getWidth() / 2.0) / (getWidth() / 4.0 * zoom) + offsetX;
                double cY = (y - getHeight() / 2.0) / (getHeight() / 4.0 * zoom) + offsetY;
                int iter = 0;
                while (zx * zx + zy * zy < 4 && iter < 100) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
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
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mandelbrot Set");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MandelbrotSet());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}