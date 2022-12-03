package com.pharmacy;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;
import com.pharmacy.Views.LoginPage;

import javax.swing.UnsupportedLookAndFeelException;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        // setting UI theme and LookAndFeel of the application
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatMaterialDarkerIJTheme());
        } catch (UnsupportedLookAndFeelException lookAndFeelException) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, lookAndFeelException);
        }

        /* Create and display the form */
        EventQueue.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
