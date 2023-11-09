package org.kaebe;

public class Transform {
    private Vector position;

    /** Always unit vector!  */
    private Vector rotation;

    private Vector scale = new Vector(1, 1);

    public Transform(Vector position) {
        this.position = position;
        rotation = new Vector(1, 0);
    }

    public Transform(Vector position, Vector rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getRotation() {
        return rotation;
    }

    public void setRotation(Vector rotation) {
        this.rotation = rotation.getUnitVector();
    }

    public Vector getScale() {
        return scale;
    }

    public void setScale(Vector scale) {
        this.scale = scale;
    }
}
