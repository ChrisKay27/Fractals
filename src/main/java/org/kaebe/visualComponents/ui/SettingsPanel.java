package org.kaebe.visualComponents.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsPanel extends JPanel {

    private List<SettingSelectionPanel> options = new ArrayList<>();
    private int currentSettingIndex = 0;

    @Override
    public Component add(Component comp) {
        if( comp instanceof SettingSelectionPanel)
            options.add((SettingSelectionPanel) comp);

        return super.add(comp);
    }

    @Override
    public void remove(Component comp) {
        if( comp instanceof SettingSelectionPanel)
            options.remove((SettingSelectionPanel) comp);

        super.remove(comp);
    }

    public SettingSelectionPanel getNextSettings() {
        if( currentSettingIndex >= options.size() )
            return null;

        System.out.println("Getting settings " + currentSettingIndex);

        return options.get(currentSettingIndex++);
    }

    public void reset() {
        currentSettingIndex = 0;
    }
}
