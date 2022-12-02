package com.inventory;

import com.inventory.Views.*;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }

}
