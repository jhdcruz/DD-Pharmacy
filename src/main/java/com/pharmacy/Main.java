package com.pharmacy;

import com.formdev.flatlaf.FlatDarkLaf;
import com.pharmacy.Views.LoginPage;

import javax.swing.UnsupportedLookAndFeelException;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        // setting UI theme and LookAndFeel of the application
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException lookAndFeelException) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.INFO, null, lookAndFeelException);
        }

        EventQueue.invokeLater(LoginPage::new);
    }
}
