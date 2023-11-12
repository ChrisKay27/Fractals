package org.kaebe.sierpinkiTriangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class SierpinskiTriangle {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int DEPTH = 11;
    
    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        drawSierpinskiTriangle(g, DEPTH, 0, 0, WIDTH, HEIGHT);
        JFrame frame = new JFrame("Sierpinski Triangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }
    
    private static void drawSierpinskiTriangle(Graphics g, int depth, int x1, int y1, int x2, int y2) {
        if (depth == 0) {
            int[] xPoints = {x1, x2, (x1 + x2) / 2};
            int[] yPoints = {y1, y1, y2};
            g.fillPolygon(xPoints, yPoints, 3);
        } else {
            int x3 = (x1 + x2) / 2;
            int y3 = (y1 + y2) / 2;
            drawSierpinskiTriangle(g, depth - 1, x1, y1, x3, y3);
            drawSierpinskiTriangle(g, depth - 1, x3, y1, x2, y3);
            drawSierpinskiTriangle(g, depth - 1, x1, y3, x3, y2);
        }
    }
}
