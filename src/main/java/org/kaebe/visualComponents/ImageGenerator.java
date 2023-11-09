package org.kaebe;

import org.kaebe.visualComponents.LineSegment;
import org.kaebe.visualComponents.VisualComponent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ImageGenerator {

    private final List<VisualComponent> components = new ArrayList<>();
    private BufferedImage mainImage;
    private Consumer<BufferedImage> images;
    private double innerSpeed;
    private double middleSpeed;
    private double outerSpeed;
    private Supplier<Long> delay;

    private int dotSize = 2;
    private boolean stop;

    public ImageGenerator(int width, int height) {
        mainImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void generate(Consumer<BufferedImage> images, int frames, double innerSpeed, double middleSpeed, double outerSpeed, Supplier<Long> delay) {
        this.images = images;
        this.innerSpeed = innerSpeed;
        this.middleSpeed = middleSpeed;
        this.outerSpeed = outerSpeed;
        this.delay = delay;
        new Thread(()-> run(frames)).start();
    }

    private void run(int frames) {
        VisualComponent center = new VisualComponent(new Transform(new Vector(500, 500)));
        LineSegment lineSegment1 = new LineSegment(new Transform(new Vector(0, 0), new Vector(1,0)), 200);
        lineSegment1.setParent(center);
        center.getChildren().add(lineSegment1);
        components.add(center);

        LineSegment lineSegment2 = new LineSegment(new Transform(new Vector(0, 0), new Vector(1,0).rotate(120)), 200);
        lineSegment2.setParent(center);
        center.getChildren().add(lineSegment2);

        LineSegment lineSegment3 = new LineSegment(new Transform(new Vector(0, 0), new Vector(1,0).rotate(240)), 200);
        lineSegment3.setParent(center);
        center.getChildren().add(lineSegment3);

        addChildLine(addChildLine(lineSegment1));
        addChildLine(addChildLine(lineSegment2));
        addChildLine(addChildLine(lineSegment3));

        //upscaleEverything(center, 2);

        Graphics2D mainImageGraphics = mainImage.createGraphics();

        for (int i = 0; i < frames; i++) {
            BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawImage(this.mainImage, 0, 0, null);

            //recursively draw all lines
            drawAllLines(g2d, center.getTransform().getPosition(), center);

            drawEndPoints(mainImageGraphics, center.getTransform().getPosition(), center);

            rotateAllLines(center.getChildren(), 0);

            images.accept(image);

            try {
                Thread.sleep(delay.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if( stop )
                return;
        }

    }

    private void upscaleEverything(VisualComponent center, double scale) {
        center.getTransform().setScale(new Vector(scale, scale));

        for (VisualComponent child : center.getChildren()) {
            if( child instanceof LineSegment lineSeg) {
                lineSeg.getTransform().setScale(new Vector(scale, scale));
                if( lineSeg.hasChildren() )
                    upscaleEverything(child, scale);
            }
        }
    }

    private void drawEndPoints(Graphics2D g, Vector offset, VisualComponent component) {
        g.setColor(Color.RED);

        for (VisualComponent child : component.getChildren()) {
            if (child instanceof LineSegment lineSeg) {
                if( lineSeg.hasChildren() )
                    drawEndPoints(g, new Vector(offset).add(lineSeg.getEnd()).scale(component.getTransform().getScale()), child);
                else {
                    Vector end = new Vector((lineSeg).getEnd()).add(offset).scale(component.getTransform().getScale());
                    g.fillOval((int) end.getX() - dotSize / 2, (int) end.getY() - dotSize / 2, dotSize, dotSize);
                }
            }
        }

//        for (VisualComponent child : component.getChildren()) {
//            if( child instanceof LineSegment ) {
//                LineSegment lineSeg = (LineSegment) child;
//                Vector end = new Vector((lineSeg).getEnd()).add(offset);
//                List<VisualComponent> children = lineSeg.getChildren();
//                children.forEach(c -> {
//                    if( c instanceof LineSegment){
//                        LineSegment childLine = (LineSegment) c;
//                        Vector end2 = new Vector((childLine).getEnd()).add(end);
//                        g.fillOval((int) end2.getX() - dotSize/2, (int) end2.getY() - dotSize/2, dotSize, dotSize);
//                    }
//                });
////                Vector end = new Vector((lineSeg).getEnd()).add(offset);
////                g.fillOval((int) end.getX() - 5, (int) end.getY() - 5, 10, 10);
//            }
//        }
    }

    private LineSegment addChildLine(LineSegment lineSeg) {
        LineSegment childLine = new LineSegment(new Transform(new Vector(0, 0), new Vector(1,0)), lineSeg.getLength() * 0.5);
        childLine.setParent(lineSeg);
        lineSeg.getChildren().add(childLine);
        return childLine;
    }

    private void rotateAllLines(List<VisualComponent> components, int i) {

        for (VisualComponent component : components) {
            if( component.hasChildren() )
                rotateAllLines(component.getChildren(), i+1);

            if( component instanceof LineSegment) {
                if( i == 0 )
                    component.getTransform().getRotation().rotate(innerSpeed);
                else if( i == 1 )
                    component.getTransform().getRotation().rotate(middleSpeed);
                else if( i == 2 )
                    component.getTransform().getRotation().rotate(outerSpeed);
            }
        }
    }

    private void drawAllLines(Graphics2D g2d, Vector offset, VisualComponent parent) {

        for (VisualComponent child : parent.getChildren()) {

            if( child instanceof LineSegment ) {
                LineSegment lineSeg = (LineSegment) child;
                drawLineSegment(g2d, offset, lineSeg);

                if (child.hasChildren())
                    drawAllLines(g2d, new Vector(offset).add(lineSeg.getEnd()), child);
            }
        }
    }


    private void drawLineSegment(Graphics2D g2d, Vector offset, LineSegment child) {
        Vector start = new Vector(child.getTransform().getPosition()).add(offset);
        Vector end = new Vector((child).getEnd()).add(offset);

        // Draw line from start to end
        g2d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
    }

    public void stop() {
        stop = true;
    }
}
