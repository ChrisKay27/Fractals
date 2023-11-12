package org.kaebe.fractals.kochSnowflake;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

// A class that represents a turtle with a position, direction and graphics context
class Turtle {
  private Graphics g; // the graphics object to draw on
  private double x; // the x-coordinate of the turtle
  private double y; // the y-coordinate of the turtle
  private double angle; // the angle of the turtle in radians

  // A constructor that takes a graphics object, an initial position and an initial direction as parameters
  public Turtle(Graphics g, double x, double y, double angle) {
    this.g = g;
    this.x = x;
    this.y = y;
    this.angle = Math.toRadians(angle);
  }

  // A method that moves the turtle forward by a given distance and draws a line
  public void forward(double distance) {
    // calculate the new position of the turtle
    double newX = x + distance * Math.cos(angle);
    double newY = y + distance * Math.sin(angle);
    // draw a line from the old position to the new position
    g.drawLine((int) x, (int) y, (int) newX, (int) newY);
    // update the position of the turtle
    x = newX;
    y = newY;
  }

  // A method that turns the turtle left by a given angle in degrees
  public void left(double angle) {
    // update the angle of the turtle
    this.angle += Math.toRadians(angle);
  }

  // A method that turns the turtle right by a given angle in degrees
  public void right(double angle) {
    // update the angle of the turtle
    this.angle -= Math.toRadians(angle);
  }
}

// A class that contains the main method
public class KochSnowflake {

  // A recursive method that draws a Koch curve with a given turtle, level of recursion and size
  public static void koch(Turtle t, int n, double size) {
    // base case: n is zero, move the turtle forward by size
    if (n == 0) {
      t.forward(size);
    }
    // recursive case: n is positive, divide the size by 3 and draw four Koch curves
    else {
      koch(t, n-1, size/3); // draw the first Koch curve
      t.left(60); // turn the turtle left by 60 degrees
      koch(t, n-1, size/3); // draw the second Koch curve
      t.right(120); // turn the turtle right by 120 degrees
      koch(t, n-1, size/3); // draw the third Koch curve
      t.left(60); // turn the turtle left by 60 degrees
      koch(t, n-1, size/3); // draw the fourth Koch curve
    }
  }

  // The main method
  public static void main(String[] args) {
    // create a BufferedImage object with the desired width, height and type
    BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
    // get the Graphics object from the BufferedImage
    Graphics2D g = (Graphics2D) image.getGraphics();
    // set the color and stroke of the Graphics object
    g.setColor(Color.BLACK);
    g.setStroke(new BasicStroke(1));
    // create a Turtle object with the Graphics object, the initial position and the initial direction
    Turtle turtle = new Turtle(g, 1000, 1000, -90);
    // call the recursive method three times with the Turtle object, the desired level of recursion and the desired size of the triangle
    // change the direction of the turtle by 120 degrees between each call
    koch(turtle, 5, 1000); // draw the first side of the triangle
    turtle.right(120); // turn the turtle right by 120 degrees
    koch(turtle, 5, 1000); // draw the second side of the triangle
    turtle.right(120); // turn the turtle right by 120 degrees
    koch(turtle, 5, 1000); // draw the third side of the triangle
    // create a JPanel object and override its paintComponent method to draw the BufferedImage object
    JPanel panel = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
      }
    };
    // create a JFrame object and add the JPanel object to it
    JFrame frame = new JFrame();
    frame.add(panel);
    // set the size, title and default close operation of the JFrame object
    frame.setSize(1920, 1080);
    frame.setTitle("Koch Snowflake");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // make the JFrame object visible
    frame.setVisible(true);
  }
}
