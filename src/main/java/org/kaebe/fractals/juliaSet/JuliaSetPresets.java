package org.kaebe.fractals.juliaSet;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class JuliaSetPresets extends JPanel {
    public JuliaSetPresets(Consumer<Integer> cReal, Consumer<Integer> cImaginary) {
        JComboBox<String> presets = new JComboBox<>();
        presets.addItem("Default");
        presets.addItem("1");
        presets.addItem("2");
        presets.addItem("3");
        presets.addItem("4");
        presets.addItem("5");
        presets.addItem("6");
        presets.addItem("7");

        presets.addItemListener(e -> {
            String item = (String) e.getItem();
            switch(item){
                case "Default":
                    cReal.accept((int) (-0.3984375 * JuliaSet.SLIDER_RESOLUTION));
                    cImaginary.accept((int) (-0.546875 * JuliaSet.SLIDER_RESOLUTION));
                    break;
                case "1":
                    cReal.accept((int) (-0.7269 * JuliaSet.SLIDER_RESOLUTION));
                    cImaginary.accept((int) (0.1889 * JuliaSet.SLIDER_RESOLUTION));
                    break;
                case "2":
                    cReal.accept((int) (-0.8 * JuliaSet.SLIDER_RESOLUTION));
                    cImaginary.accept((int) (0.156 * JuliaSet.SLIDER_RESOLUTION));
                    break;
                case "3":
                    cReal.accept((int) (-0.74543 * JuliaSet.SLIDER_RESOLUTION));
                    cImaginary.accept((int) (0.99 * JuliaSet.SLIDER_RESOLUTION));
                    break;
                case "4":
                    cReal.accept((int) (-0.984375 * JuliaSet.SLIDER_RESOLUTION));
                    cImaginary.accept((int) (0.3203125 * JuliaSet.SLIDER_RESOLUTION));
                    break;
                case "5":
                    cReal.accept((int) (-0.1 * JuliaSet.SLIDER_RESOLUTION));
                    cImaginary.accept((int) (0.651 * JuliaSet.SLIDER_RESOLUTION));
                    break;
                case "6":
                    cReal.accept((int) (-0.3984375 * JuliaSet.SLIDER_RESOLUTION)); //
                    cImaginary.accept((int) (0.5703125 * JuliaSet.SLIDER_RESOLUTION)); //
                    break;
                case "7":
                    //😊
                    cReal.accept((int) (-.109 * JuliaSet.SLIDER_RESOLUTION));
                    cImaginary.accept((int) (.89 * JuliaSet.SLIDER_RESOLUTION));
                    break;
            }
        });
        presets.setSelectedIndex(0);
        add(presets);

    }
}
