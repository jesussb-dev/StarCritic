package com.starcritic.dam_proyect;

import com.formdev.flatlaf.FlatDarkLaf;
import com.starcritic.dam_proyect.controller.MainNavigationController;
import com.starcritic.dam_proyect.data.BackgroundWork;
import com.starcritic.dam_proyect.model.Model;
import com.starcritic.dam_proyect.view.MainNavigationFrame;
import com.starcritic.dam_proyect.view.UIStyle;
import java.awt.Insets;
import javax.swing.UIManager;

/**
 * @author Jesús Santos Baquero
 */
public class Main {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        UIManager.put("Button.arc", 16);
        UIManager.put("Component.arc", 12);
        UIManager.put("ProgressBar.arc", 16);
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("TitlePane.unifiedBackground", true);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Button.margin", new Insets(6, 16, 6, 16));
        UIManager.put("TextField.margin", new Insets(4, 8, 4, 8));
        UIManager.put("PasswordField.margin", new Insets(4, 8, 4, 8));
        UIManager.put("ComboBox.margin", new Insets(4, 8, 4, 8));
        UIManager.put("TabbedPane.tabHeight", 32);
        UIManager.put("TabbedPane.selectedBackground", UIStyle.BG_CARD);
        UIStyle.installGlobalDefaults();

        Model model = new Model();

        Runtime.getRuntime().addShutdownHook(new Thread(BackgroundWork::shutdown));

        MainNavigationFrame view = new MainNavigationFrame();
        new MainNavigationController(model, view);
        view.setVisible(true);
    }
}
