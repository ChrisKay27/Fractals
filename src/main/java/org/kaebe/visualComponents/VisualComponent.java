package org.kaebe.visualComponents;

import java.util.ArrayList;
import java.util.List;

public class VisualComponent {
    private VisualComponent parent;
    private List<VisualComponent> children = new ArrayList<>();
    private Transform transform;

    public VisualComponent(Transform transform) {
        this.transform = transform;
    }

    public VisualComponent getParent() {
        return parent;
    }

    public void setParent(VisualComponent parent) {
        this.parent = parent;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public List<VisualComponent> getChildren() {
        return children;
    }

    public void setChildren(List<VisualComponent> children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }
}
