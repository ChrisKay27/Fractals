package org.kaebe.visualComponents.ui;

import org.kaebe.visualComponents.ImageGenerator;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ControlPanel extends JPanel {

    private boolean changeGenerateButtonText = true;
    private final int width = 1000, height = 1000;
    private long delay = 0;
    private final SettingsPanel settingsPanel;
    private final List<SettingSelectionPanel> settingSelectionPanels = new ArrayList<>();
    private ImageGenerator imageGenerator;
    private JButton generateButton;
    private Runnable pack;

    public ControlPanel(Consumer<BufferedImage> addImage, Runnable pack) {
        this.pack = pack;
        setLayout(new BoxLayout( this, BoxLayout.X_AXIS));

        settingsPanel = new SettingsPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        SettingSelectionPanel settingSelection = new SettingSelectionPanel(i->{});
        settingSelection.removeDeleteButton();
        settingSelectionPanels.add(settingSelection);
        settingsPanel.add(settingSelection);

        generateButton = getGenerateButton(addImage);
        add(generateButton);

        add(getNewAddSettingsPanelButton());

        add(settingsPanel);


//        JButton saveButton = new JButton("Save");
//        saveButton.addActionListener(e -> {
//            if( images.isEmpty() ) {
//                return;
//            }
//            new Thread(()->{
//                for (int i = 0; i < images.size(); i++) {
//                    BufferedImage image = images.get(i);
//                }
//            }).start();
//        });
    }

    private JButton getNewAddSettingsPanelButton() {
        JButton newAddSettingsPanelButton = new JButton("New");
        newAddSettingsPanelButton.addActionListener(e -> {
            settingsPanel.add(new SettingSelectionPanel(panel -> {
                settingsPanel.remove(panel);
                settingsPanel.revalidate();
            }));
            settingsPanel.revalidate();
            pack.run();
        });
        return newAddSettingsPanelButton;
    }


    private JButton getGenerateButton(Consumer<BufferedImage> addImage) {
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> {

            if( changeGenerateButtonText ){
                generateButton.setText("Restart");
                changeGenerateButtonText = false;
            }

            if(imageGenerator != null) {
                imageGenerator.stop();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

            imageGenerator = new ImageGenerator(width, height);

            new Thread(()->{
                while( true ) {

                    SettingSelectionPanel currentSettings = settingsPanel.getNextSettings();

                    if( currentSettings == null ) {
                        settingsPanel.reset();
                        imageGenerator.stop();
                        break;
                    }

                    imageGenerator.generate(addImage, 1000000, currentSettings.getInnerSpeed(),
                            currentSettings.getMiddleSpeed(), currentSettings.getOuterSpeed(), currentSettings.getRunTime());

                    try {
                        System.out.println("Sleeping for " + currentSettings.getRunTime());
                        Thread.sleep(currentSettings.getRunTime());
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }).start();
        });
        return generateButton;
    }



    public void generate() {
        generateButton.doClick();
    }
}
