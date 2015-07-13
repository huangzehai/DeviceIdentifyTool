/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.ui;

import com.u2apple.tool.constant.Constants;
import com.u2apple.tool.persistence.Pool;
import com.u2apple.tool.persistence.SshTunnel;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Adam
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });

        //Release resource when shutdown.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Pool.close();
                SshTunnel.close();
            }
        });
    }

    private static void init() {
        //Set window icon.
        Toolkit toolKit = Toolkit.getDefaultToolkit();
        Image image = toolKit.createImage(Application.class.getClassLoader().getResource(Constants.APPLICATION_ICON));
        //Setting look and feel.
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Setting frame.
        JFrame frame = new MainFrame();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setIconImage(image);
    }
}
