/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opass.kiosk;

import com.alee.laf.WebLookAndFeel;
import com.opass.app.helper.JustOneLock;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Prasojo
 */
public class Kioskapp  implements Runnable{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(new Kiosk(device));
           JustOneLock ua = new JustOneLock("KioskApp");
         
         if (ua.isAppActive()) {
             System.out.println("Already active.");
             System.exit(1);
         }else{
        SwingUtilities.invokeLater(new Kioskapp());
         }
    }

    @Override
    public void run() {
    try {
            try {
                try {
                    MainExec();
                } catch (IOException ex) {
                    Logger.getLogger(Kioskapp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Kioskapp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Kioskapp.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
     
    void MainExec() throws UnsupportedLookAndFeelException, InterruptedException, IOException {
        try {
            UIManager.setLookAndFeel (WebLookAndFeel.class.getCanonicalName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Kioskapp.class.getName()).log(Level.SEVERE, null, ex);
        }        
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
    
        Kiosk ks = new Kiosk(device);
        ks.setVisible(true);
        
    }
}
    