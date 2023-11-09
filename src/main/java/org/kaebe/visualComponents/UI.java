package org.kaebe.visualComponents;

import org.kaebe.visualComponents.ImageGenerator;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UI {

    private final List<BufferedImage> images = new ArrayList<>();
    private final int width = 1000, height = 1000;
    private long delay = 0;
    private boolean killCurrentThread;
    private ImageGenerator imageGenerator;
    private JLabel lastLabel;
    private int imageIndex = 0;
    private BufferedImage currentImage;
    private JPanel contentPane;
    private JFrame frame;

    public UI() {
        frame = new JFrame("Random Circle Spinner Thing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        frame.setContentPane(contentPane);

        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        contentPane.add(topBar);

        JTextField innerSpeedTextField = new JTextField("1");
        JTextField middleSpeedTextField = new JTextField("1");
        JTextField outerSpeedTextField = new JTextField("8");

        JButton generateButton = getGenerateButton(innerSpeedTextField, middleSpeedTextField, outerSpeedTextField);
        topBar.add(generateButton);
        topBar.add(innerSpeedTextField);
        topBar.add(middleSpeedTextField);
        topBar.add(outerSpeedTextField);

        JTextField delayTextField = new JTextField("0");
        delayTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                delay = Long.parseLong(delayTextField.getText());
            }
        });
        topBar.add(delayTextField);

//        JButton saveButton = new JButton("Save");
//        saveButton.addActionListener(e -> {
//            if( images.isEmpty() ) {
//                return;
//            }
//            new Thread(()->{
//                for (int i = 0; i < images.size(); i++) {
//                    BufferedImage image = images.get(i);
//
//                }
//            }).start();
//        });
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton getGenerateButton(JTextField innerSpeedTextField, JTextField middleSpeedTextField, JTextField outerSpeedTextField) {
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> {

            if(currentImage != null) {
                killCurrentThread = true;
                imageGenerator.stop();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                currentImage = null;
            }
            imageGenerator = new ImageGenerator(width, height);
            imageGenerator.generate(this::addImage, 100000, Double.parseDouble(innerSpeedTextField.getText()),
                    Double.parseDouble(middleSpeedTextField.getText()), Double.parseDouble(outerSpeedTextField.getText()), () -> delay);


//            new Thread(()->{
//                while (true) {
//                    if( killCurrentThread ) {
//                        killCurrentThread = false;
//                        return;
//                    }
//                    SwingUtilities.invokeLater(()-> {
//
//                        if( currentImage == null )
//                            return;
//
//                        BufferedImage image = currentImage;
//
//                        if (lastLabel != null)
//                            contentPane.remove(lastLabel);
//
//                        JLabel label = new JLabel(new ImageIcon(image));
//                        lastLabel = label;
//
//                        contentPane.add(label);
//                        frame.pack();
//                    });
//
//                    try {
//                        Thread.sleep(delay);
//                    } catch (InterruptedException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
//            }).start();
        });
        return generateButton;
    }

    private void addImage(BufferedImage bufferedImage) {
        currentImage = bufferedImage;

        SwingUtilities.invokeLater(()-> {

            if( currentImage == null )
                return;

            BufferedImage image = currentImage;

            if (lastLabel != null)
                contentPane.remove(lastLabel);

            JLabel label = new JLabel(new ImageIcon(image));
            lastLabel = label;

            contentPane.add(label);
            frame.pack();
            frame.setLocationRelativeTo(null);
        });
    }
}