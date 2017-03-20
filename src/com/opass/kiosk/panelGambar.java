/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opass.kiosk;

/**
 *
 * @author agung
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 *
 * @author Razaq Nice
 */
public class panelGambar extends JPanel{

    private static final long serialVersionUID = 1L;
 
    private final Image image; // membuat variable image
     public panelGambar() {
        image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/background2.png")).getImage();
        //memanggil sumber daya gambar
    }
     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
     
        Graphics gd = (Graphics2D) g.create();
     
        gd.drawImage(image, 0,0,getWidth(),getHeight(), this);
        // menggambar image
        gd.dispose();
    }
}
