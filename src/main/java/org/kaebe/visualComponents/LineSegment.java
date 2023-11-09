package org.kaebe.visualComponents;


import org.kaebe.Vector;

public class LineSegment extends VisualComponent{

    private double length;

    public LineSegment(Transform transform, double length) {
        super(transform);
        this.length = length;
    }

    public LineSegment(Vector start, Vector end) {
        super(new Transform(start));
        double magnitude = new Vector(end).minus(start).magnitude();
        getTransform().setRotation(new Vector(end).minus(start));
        length = magnitude;
    }

    public Vector getEnd() {
        return new Vector(getTransform().getRotation()).times(length).add(getTransform().getPosition());
    }

    public double getLength() {
        return length;
    }
}
