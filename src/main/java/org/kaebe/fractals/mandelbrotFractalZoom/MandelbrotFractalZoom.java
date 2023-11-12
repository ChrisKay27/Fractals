package org.kaebe.fractals.mandelbrotFractalZoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.kaebe.fractals.mandelbrotFractalZoom.MandelbrotCalculator.MAX_ITERATIONS;


public class MandelbrotFractalZoom {

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1000;
    private final Icon icon;
    private final JLabel label;
    private final JPanel contentPane;
    private double scale = -3.978334271458669;
    private double xOffset = -0.6825430516844277;
    private double yOffset = 0.0033971134705536345;;
    private BufferedImage image = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
    private ColoringScheme coloringScheme = ColoringScheme.CUSTOM1;

    public MandelbrotFractalZoom(Runnable openMainWindow) {
        JFrame frame = new JFrame("Mandelbrot Fractal Zoom");
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                openMainWindow.run();
            }
        });
        frame.setSize(1920, 1080);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        frame.setContentPane(contentPane);
        icon = new ImageIcon(image);
        label = new JLabel(icon);
        contentPane.add(new JLabel("Click to zoom in, right click to zoom out"));
        contentPane.add(new MandelBrotControlPanel(coloringScheme -> {
            this.coloringScheme = coloringScheme;
            updateImage();
        }));
        contentPane.add(label);

        updateImage();

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if( e.getButton() == MouseEvent.BUTTON1 ) {
                    //Update scale and offsets
                    double x = (e.getX() - (double) WIDTH / 2) * scale / WIDTH + xOffset;
                    double y = (e.getY() - (double) HEIGHT / 2) * scale / HEIGHT + yOffset;
                    scale /= 2;
                    xOffset = x;
                    yOffset = y;
                    updateImage();

                }else if( e.getButton() == MouseEvent.BUTTON2 ) {

                }else if( e.getButton() == MouseEvent.BUTTON3 ) {
                    //Update scale and offsets
                    double x = (e.getX() - (double) WIDTH / 2) * scale / WIDTH + xOffset;
                    double y = (e.getY() - (double) HEIGHT / 2) * scale / HEIGHT + yOffset;
                    scale *= 2;
                    xOffset = x;
                    yOffset = y;
                    updateImage();
                }
                else
                    System.out.println("Unknown click");
            }
        });
    }


    private void updateImage() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        System.out.println("--------------------------");
        System.out.println("scale: " + scale);
        System.out.println("xOffset: " + xOffset);
        System.out.println("yOffset: " + yOffset);

        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final int[] avgIterations = new int[1];

        // Divide the image into horizontal strips for multithreading
        int stripHeight = HEIGHT / Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < HEIGHT; i += stripHeight) {
            int finalI = i;
            executor.submit(() -> {
                for (int px = 0; px < WIDTH; px++) {
                    for (int py = finalI; py < Math.min(finalI + stripHeight, HEIGHT); py++) {
                        // Map pixel to complex plane
                            /*
                            The real and imaginary parts (x and y) should be correctly mapped from the pixel coordinates to
                            the typical range of the Mandelbrot set. For a basic implementation, the real part (x) usually
                            ranges from -2.5 to 1, and the imaginary part (y) from -1 to 1.
                             */
                            int iterations = getIterations(px, py);

                            avgIterations[0] += iterations;
                            // Determine color based on iterations
                            Color color = getColor(iterations); // Implement this method based on your coloring scheme

                            // Set pixel color
                            image.setRGB(px, py, color.getRGB());
                        }
                }
            });
        }

        executor.shutdown();
        try {
            // Wait for all tasks to complete
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // Handle the interruption appropriately
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        // Assume this is part of a graphical drawing method
//        for (int px = 0; px < WIDTH; px++) {
//            for (int py = 0; py < HEIGHT; py++) {
//                // Map pixel to complex plane
//                /*
//                The real and imaginary parts (x and y) should be correctly mapped from the pixel coordinates to
//                the typical range of the Mandelbrot set. For a basic implementation, the real part (x) usually
//                ranges from -2.5 to 1, and the imaginary part (y) from -1 to 1.
//                 */
//                int iterations = getIterations(px, py);
//
//                avgIterations += iterations;
//                // Determine color based on iterations
//                Color color = getColor(iterations); // Implement this method based on your coloring scheme
//
//                // Set pixel color
//                image.setRGB(px, py, color.getRGB());
//            }
//        }
        avgIterations[0] /= WIDTH * HEIGHT;
        System.out.println("Average iterations: " + avgIterations[0]);

        SwingUtilities.invokeLater(()->{
            label.setIcon(new ImageIcon(image));
            contentPane.revalidate();
        });


    }

    private int getIterations(int px, int py) {
        double x = (px - (double) WIDTH / 2) * scale / WIDTH + xOffset;
        double y = (py - (double) HEIGHT / 2) * scale / HEIGHT + yOffset;
//                double x = (px - (double) WIDTH / 2) * scale + xOffset;
//                double y = (py - (double) HEIGHT / 2) * scale + yOffset;

        // Calculate Mandelbrot iterations for this point
        return MandelbrotCalculator.calculatePoint(x, y);
    }

    /**
     * Color the Pixels: Use the return value of calculatePoint to determine the color of each pixel.
     * The number of iterations it takes for a sequence to "escape" determines its color. If a point
     * never escapes (i.e., reaches MAX_ITERATIONS), it's usually colored black, indicating that it's part of the Mandelbrot set.
     * @param iterations
     * @return
     */
    private Color getColor(int iterations) {
        switch (coloringScheme){
            case CUSTOM1 -> {
                int map = (int) map(iterations, 0, MAX_ITERATIONS, 0, 256);
                if( map < 64 )
                    return new Color(map,map/2,0);
                if( map < 128 )
                    return new Color(0,map,0);
                if( map < 192 )
                    return new Color(0,map/2,map);
                return new Color(map);
            }
            case ITERATIONS_HSB_TO_RGB -> {
                int rgb = Color.HSBtoRGB((float) iterations / MAX_ITERATIONS, 1, 1);
                return new Color(rgb);
            }
        }
        return Color.BLACK;
    }

    private int map(int value, int maxValue, int newMin, int newMax) {
        return value * (newMax - newMin) / maxValue + newMin;
    }

    private long map(long x, long in_min, long in_max, long out_min, long out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
    public static void main(String[] args) {
        new MandelbrotFractalZoom(() -> System.exit(0));
    }


}
