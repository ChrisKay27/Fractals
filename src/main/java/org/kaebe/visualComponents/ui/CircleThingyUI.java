package org.kaebe.visualComponents.ui;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CircleThingyUI extends JFrame {

    private final List<BufferedImage> images = new ArrayList<>();

    private boolean killCurrentThread;
    //private JLabel lastLabel;
    private ImageIcon lastImageIcon;
    private int imageIndex = 0;
    private BufferedImage currentImage;
    private JPanel contentPane;

    private boolean doOnce = true;


    public CircleThingyUI(Runnable openMainWindow) {
        super("Random Circle Spinner Thing");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                openMainWindow.run();
            }
        });
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setContentPane(contentPane);

        ControlPanel topBar = new ControlPanel(this::addImage, this::pack);

        contentPane.add(topBar);


        lastImageIcon = new ImageIcon();
        JLabel imageLabel = new JLabel(lastImageIcon);
        contentPane.add(imageLabel);

        pack();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //topBar.generate();
    }


    private void addImage(BufferedImage bufferedImage) {
        currentImage = bufferedImage;

        SwingUtilities.invokeLater(()-> {

            if( currentImage == null )
                return;

            BufferedImage image = currentImage;

//            if (lastLabel != null)
//                contentPane.remove(lastLabel);


            //JLabel label = new JLabel(new ImageIcon(image));

            //lastLabel = label;

            //contentPane.add(label);

            lastImageIcon.setImage(image);

            repaint();
            pack();

            if( doOnce ) {
                //setLocationRelativeTo(null);
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                doOnce = false;
            }
        });
    }
}