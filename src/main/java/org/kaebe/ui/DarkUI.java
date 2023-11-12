package org.kaebe.ui;

public class DarkUI {

    public static void load() {
        try {
            javax.swing.UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (Exception e) {
            System.out.println("Failed to load dark UI: " + e.getMessage());
        }
    }
}
