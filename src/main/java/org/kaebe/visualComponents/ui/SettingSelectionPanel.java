package org.kaebe.visualComponents.ui;

import javax.swing.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SettingSelectionPanel extends JPanel{

    private final JTextField innerSpeedTextField;
    private final JTextField middleSpeedTextField;
    private final JTextField outerSpeedTextField;
    private final JButton deleteButton;
    private final JTextField runTimeTextField;
    public SettingSelectionPanel(Consumer<JPanel> deleteMe) {
        setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
        innerSpeedTextField = new JTextField("       1");
        middleSpeedTextField = new JTextField("      1");
        outerSpeedTextField = new JTextField("       8");

        innerSpeedTextField.setToolTipText("Inner Speed");
        middleSpeedTextField.setToolTipText("Middle Speed");
        outerSpeedTextField.setToolTipText("Outer Speed");

        JLabel innerSpeedLabel = new JLabel("Inner Speed");
        JLabel middleSpeedLabel = new JLabel("Middle Speed");
        JLabel outerSpeedLabel = new JLabel("Outer Speed");
        JLabel delayLabel = new JLabel("RunTime (ms)");

        add(innerSpeedLabel);
        add(innerSpeedTextField);
        add(middleSpeedLabel);
        add(middleSpeedTextField);
        add(outerSpeedLabel);
        add(outerSpeedTextField);

        runTimeTextField = new JTextField("   10000");
        runTimeTextField.setToolTipText("RunTime (ms)");
        add(delayLabel);
        add(runTimeTextField);

        JLabel presetLabel = new JLabel("Presets");
        JComboBox<String> presets = new JComboBox<>();
        presets.addItem("1");
        presets.addItem("2");
        presets.addItem("3");
        presets.addItem("4");
        presets.addItem("5");
        presets.addItem("6");
        presets.addItem("7");
        presets.addItemListener(e -> {
            String selectedItem = (String) presets.getSelectedItem();
            if( selectedItem == null )
                return;

            switch (selectedItem){
                case "1": {
                    innerSpeedTextField.setText("       1");
                    middleSpeedTextField.setText("      1");
                    outerSpeedTextField.setText("       8");
                    runTimeTextField.setText("   10000");
                    break;
                }
                case "2": {
                    innerSpeedTextField.setText("       1");
                    middleSpeedTextField.setText("      2");
                    outerSpeedTextField.setText("       6.1");
                    runTimeTextField.setText("   10000");
                    break;
                }
                case "3": {
                    innerSpeedTextField.setText("       .1");
                    middleSpeedTextField.setText("      1");
                    outerSpeedTextField.setText("       10");
                    runTimeTextField.setText("   10000");
                    break;
                }
                case "4": {
                    innerSpeedTextField.setText("       2");
                    middleSpeedTextField.setText("      2");
                    outerSpeedTextField.setText("       2");
                    runTimeTextField.setText("   10000");
                    break;
                }
                case "5": {
                    innerSpeedTextField.setText("       1");
                    middleSpeedTextField.setText("      -1");
                    outerSpeedTextField.setText("       .1");
                    runTimeTextField.setText("   10000");
                    break;
                }
                case "6": {
                    innerSpeedTextField.setText("       .5");
                    middleSpeedTextField.setText("      1.5");
                    outerSpeedTextField.setText("       3.111111");
                    runTimeTextField.setText("   10000");
                    break;
                }
                case "7": {
                    innerSpeedTextField.setText("       1");
                    middleSpeedTextField.setText("      -2");
                    outerSpeedTextField.setText("       -.25");
                    runTimeTextField.setText("   10000");
                    break;
                }
            }
//            if( e.getStateChange() == ItemEvent.SELECTED ) {
//                generateButton.doClick();
//            }
        });
        presets.setToolTipText("Presets");
        add(presetLabel);
        add(presets);

        deleteButton = new JButton("X");
        deleteButton.addActionListener(e -> deleteMe.accept(this));
        add(deleteButton);
    }

    public long getRunTime() {
        return Long.parseLong(runTimeTextField.getText().trim());
    }


    public double getInnerSpeed() {
        return Double.parseDouble(innerSpeedTextField.getText());
    }

    public double getMiddleSpeed() {
        return Double.parseDouble(middleSpeedTextField.getText());
    }

    public double getOuterSpeed() {
        return Double.parseDouble(outerSpeedTextField.getText());
    }

    public void removeDeleteButton() {
        remove(deleteButton);
    }
}
