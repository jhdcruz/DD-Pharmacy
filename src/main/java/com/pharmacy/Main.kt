package com.pharmacy

import com.formdev.flatlaf.FlatLightLaf
import com.pharmacy.views.LoginPage
import java.awt.EventQueue
import java.util.logging.Level
import java.util.logging.Logger
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Setting UI theme and LookAndFeel of the application
        try {
            UIManager.setLookAndFeel(FlatLightLaf())
        } catch (lookAndFeelException: UnsupportedLookAndFeelException) {
            Logger.getLogger(LoginPage::class.java.name)
                .log(Level.SEVERE, "Unable to set look and feel.", lookAndFeelException)
        }

        EventQueue.invokeLater { LoginPage() }
    }
}
