package org.kaebe.fractals.mandelbrotFractalZoom;

import javax.swing.*;
import java.util.function.Consumer;

class MandelBrotControlPanel extends JPanel {
    public MandelBrotControlPanel(Consumer<ColoringScheme> coloringSchemeConsumer) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JLabel("Coloring Scheme:"));
        JComboBox<ColoringScheme> coloringSchemeJComboBox = new JComboBox<>(ColoringScheme.values());
        coloringSchemeJComboBox.setSelectedItem(ColoringScheme.CUSTOM1);
        coloringSchemeJComboBox.addActionListener(e -> coloringSchemeConsumer.accept((ColoringScheme) coloringSchemeJComboBox.getSelectedItem()));

        add(coloringSchemeJComboBox);
    }
}
