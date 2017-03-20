/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opass.kiosk;

import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import javax.swing.ImageIcon;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import redis.clients.jedis.Jedis;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author agung
 */
public class Kiosk extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private static String JEDIS_SERVER;
    private static String FRONT_SERVER;
    private String JAM_OPR;
    private String MENIT_OPR;
    private String PESAN_OPR;
    private static QueuePropertyValues qp;
    private static Properties prop;
    private static Jedis jedis;
    private static Jedis jedisFront;

    /**
     * @return the JAM_OPR
     */
    public String getJAM_OPR() {
        return JAM_OPR;
    }

    /**
     * @param aJAM_OPR the JAM_OPR to set
     */
    public void setJAM_OPR(String aJAM_OPR) {
        JAM_OPR = aJAM_OPR;
    }

    /**
     * @return the MENIT_OPR
     */
    public String getMENIT_OPR() {
        return MENIT_OPR;
    }

    /**
     * @param aMENIT_OPR the MENIT_OPR to set
     */
    public void setMENIT_OPR(String aMENIT_OPR) {
        MENIT_OPR = aMENIT_OPR;
    }

    /**
     * @return the PESAN_OPR
     */
    public String getPESAN_OPR() {
        return PESAN_OPR;
    }

    /**
     * @param aPESAN_OPR the PESAN_OPR to set
     */
    public void setPESAN_OPR(String aPESAN_OPR) {
        PESAN_OPR = aPESAN_OPR;
    }

    GraphicsDevice device;

    /**
     * Creates new form kiosk
     *
     * @param device
     * @throws java.io.IOException
     */
    public Kiosk(GraphicsDevice device) throws IOException {
        this.device = device;
        qp = new QueuePropertyValues();
        prop = new Properties(qp.getProperties());
        JEDIS_SERVER = prop.getProperty("JEDIS_SERVER");
        FRONT_SERVER = prop.getProperty("FRONT_SERVER");
        JAM_OPR = prop.getProperty("JAM_OPR");
        MENIT_OPR = prop.getProperty("MENIT_OPR");
        PESAN_OPR = prop.getProperty("PESAN_OPR");
        qp.closeProperties();
        jedis = new Jedis(JEDIS_SERVER);
        try {
            jedisFront = new Jedis(FRONT_SERVER);
        } catch (Exception ex) {
            //eat All Error
        }
        //Date time = new Date(System.currentTimeMillis());
        System.out.println("Kiosk started...");
        initComponents();
        ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
        btnA.setIcon(image);
        ImageIcon imageB = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
        btnB.setIcon(imageB);
        ImageIcon imageC = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
        btnC.setIcon(imageC);
        Date currentDate = new Date();
        long unixTime = System.currentTimeMillis() / 1000L;
        //System.out.println(currentDate.getTime() / 1000);
        //System.out.println(unixTime);
        //System.out.println(new Date(unixTime));
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        //System.out.println(currentTimestamp);

        int i = (int) new Date().getTime();
        //System.out.println("Integer : " + i);
        //System.out.println("Long : " + new Date().getTime());
        //System.out.println("Long only Date : " + new Date().getDate());
        //System.out.println("Long only month : " + new Date().getMonth());
        //System.out.println("Long only year : " + new Date().getYear());
        //System.out.println("Long date : " + new Date(new Date().getTime()));
        //System.out.println("Int Date : " + new Date(i));
        //System.out.println(new Date(new Date().getTime()));

    }

    public void startFullScreen() {
        device.setFullScreenWindow(this);
    }

    public static Process cetakTicket(int type) throws IOException {
        String vNo = "0";
        String vT = "";
        String vSisa = "0";
        switch (type) {
            case 1:
                jedis.incr("counter_npwp");
                vNo = jedis.get("counter_npwp");
                try {
                    jedisFront.rpush("q_npwp", vNo);
                    //jedisFront.incr("countnpwp");
                    jedisFront.incr("totalnpwp");
                } catch (Exception ex) {
                    /*eat All error*/
                }
                vT = "NPWP";
                vSisa = jedis.llen("q_npwp").toString();
                break;
            case 2:

                jedis.incr("counter_lainlain");
                vNo = jedis.get("counter_lainlain");
                try {
                    jedisFront.rpush("q_lain", vNo);
                    //jedisFront.incr("countlain");
                    jedisFront.incr("totallain");
                } catch (Exception ex) {
                    /*eat All error*/
                }
                vT = "SURAT_LAIN-LAIN";
                vSisa = jedis.llen("q_lain").toString();
                break;
            case 3:
                jedis.incr("counter_sppt");
                vNo = jedis.get("counter_sppt");
                try {
                    jedisFront.rpush("q_sppt", vNo);
                    //jedisFront.incr("countsppt");
                    jedisFront.incr("totalsppt");
                } catch (Exception ex) {
                    /*eat All error*/
                }
                vT = "SPT_MASA/SPT_TAHUNAN";
                vSisa = jedis.llen("q_sppt").toString();
                break;
            default:
                vT = "UNDIFIEND";
                vNo = "0";
                vSisa = "0";
                break;
        }

        System.out.println("args.py " + vT + " " + vNo + " " + vSisa);
        // return Runtime.getRuntime().exec("python ticket2.py " + vT + " " + vNo);
        //return vSisa;

        return Runtime.getRuntime().exec("python args.py " + vT + " " + vNo + " " + vSisa);

        //return Runtime.getRuntime().exec("ls");
    }

    private boolean cek_jam(String vH, String vM) {
        boolean vOk = false;
        int vJam;
        int vMenit;
        int vJamAktif;
        int vJamServer;

        Calendar c1 = Calendar.getInstance();
        //System.out.println(c1.getTime().getHours());
        //System.out.println(c1.getTime().getMinutes());
        //System.out.println("HOURS :"+vH);
        //System.out.println("MENIT :"+vM);
        vJam = c1.getTime().getHours();
        vMenit = c1.getTime().getMinutes();
        vJamAktif = (Integer.valueOf(vH) * 60) + Integer.valueOf(vM);
        vJamServer = (vJam * 60) + vMenit;
        //System.out.println("=============== AGUNG PUNYA ===================");
        //System.out.println("JAM AKTIF : "+vJamAktif);
        //System.out.println("SERVER  : "+vJamServer);
        //System.out.println("Menit : "+vMenit);

        if (vJamServer < vJamAktif) {
            //System.out.println("TRUE");
            vOk = true;
        } else {
            //System.out.println("FALSE");
            vOk = false;
        }

        return vOk;
    }

    public static Process cetakTicket_j(int type) throws IOException {
        String vNo = "0";
        String vT = "";
        /*
            String vNo = "0";
        //vlayan++;
        jedis.incr("counter_layanan");
        vNo = jedis.get("counter_layanan");
        jedisFront.rpush("qlayan", vNo);
         System.out.println("Message received:" + vNo);
         */
        switch (type) {
            case 1:
                //jedis.incr("counter_pembayaran");
                //vNo = jedis.get("counter_pembayaran");
                jedis.incr("counter_pembayaran");
                vNo = jedis.get("counter_pembayaran");
                try {
                    jedisFront.rpush("qbayar", vNo);
                    jedisFront.incr("countbayar");
                } catch (Exception ex) {
                    /*eat All error*/
                }
                vT = "NPWP";
                break;
            case 2:

                jedis.incr("counter_lainlain");
                vNo = jedis.get("counter_lainlain");
                try {
                    jedisFront.rpush("qlain", vNo);
                    jedisFront.incr("countlayan");
                } catch (Exception ex) {
                    /*eat All error*/
                }
                vT = "SURAT LAIN-LAIN";
                break;
            case 3:
                //jedis.incr("counter_layanan");
                //vNo = jedis.get("counter_layanan");
                jedis.incr("counter_layanan");
                vNo = jedis.get("counter_layanan");
                try {
                    //jedisFront.rpush("qlayan", vNo);
                    jedisFront.rpush("qlayan", vNo);
                    jedisFront.incr("countlayan");
                } catch (Exception ex) {
                    /*eat All error*/
                }
                vT = "SPT MASA/ SPT TAHUNAN";
                break;
            default:
                vNo = "0";
                break;
        }

        //System.out.println(vT + " " + vNo);
        // return Runtime.getRuntime().exec("python ticket2.py " + vT + " " + vNo);
        return Runtime.getRuntime().exec("python args.py " + vT + " " + vNo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGambar1 = new com.opass.kiosk.panelGambar();
        btnA = new javax.swing.JLabel();
        setBtnC(new javax.swing.JLabel());
        btnB = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1024, 768));

        btnA.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        btnA.setForeground(new java.awt.Color(0, 51, 51));
        btnA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"))); // NOI18N
        btnA.setText("1. NPWP                             ");
        btnA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnAMouseReleased(evt);
            }
        });

        getBtnC().setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        getBtnC().setForeground(new java.awt.Color(0, 51, 51));
        getBtnC().setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"))); // NOI18N
        getBtnC().setText("3. SPT MASA / TAHUNAN");
        getBtnC().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnCMouseReleased(evt);
            }
        });

        btnB.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        btnB.setForeground(new java.awt.Color(0, 51, 51));
        btnB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"))); // NOI18N
        btnB.setText("2. SURAT LAIN-LAIN                 ");
        btnB.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnBMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnBMouseReleased(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/opass/kiosk/image/tnt_icon_14.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGambar1Layout = new javax.swing.GroupLayout(panelGambar1);
        panelGambar1.setLayout(panelGambar1Layout);
        panelGambar1Layout.setHorizontalGroup(panelGambar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGambar1Layout.createSequentialGroup()
                .addContainerGap(444, Short.MAX_VALUE)
                .addGroup(panelGambar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(getBtnC(), javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(panelGambar1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGambar1Layout.setVerticalGroup(panelGambar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGambar1Layout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addComponent(btnA, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnB, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(getBtnC(), javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGambar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGambar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCMousePressed
        ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/push2.png"));
        getBtnC().setIcon(image);
    }//GEN-LAST:event_btnCMousePressed

    private void btnCMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCMouseReleased
        try {
            if (cek_jam(getJAM_OPR(), getMENIT_OPR())) {
                cetakTicket(3);
            } else {
                JOptionPane.showMessageDialog(this, getPESAN_OPR());
            }
            //ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
            // btnC.setIcon(image);
        } catch (IOException ex) {
            Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
            getBtnC().setIcon(image);

        }

    }//GEN-LAST:event_btnCMouseReleased

    private void btnBMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBMousePressed
        ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/push2.png"));
        getBtnB().setIcon(image);
    }//GEN-LAST:event_btnBMousePressed

    private void btnBMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBMouseReleased
        String vBol = jedis.get("rest_lain");
        if (vBol.equals("1")) {
            if (cek_jam(getJAM_OPR(), getMENIT_OPR())) {
                try {
                    cetakTicket(2);
                    ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
                    getBtnB().setIcon(image);
                } catch (IOException ex) {
                    Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);
                    //ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
                    //btnB.setIcon(image);
                } finally {
                    ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
                    btnB.setIcon(image);
                }
            } else {
                JOptionPane.showMessageDialog(this, getPESAN_OPR());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mohon Maaf Antrian LAIN-LAIN Sedang Istirahat... \n Terimkasih atas Kunjungan Anda!");
            ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
            getBtnB().setIcon(image);
        }

    }//GEN-LAST:event_btnBMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            DialogServer dialog = new DialogServer(new javax.swing.JFrame(), true, jedis, this);
            dialog.setVisible(true);
        });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAMouseReleased
       String vBol = jedis.get("rest_npwp");
        if (vBol.equals("1")) {
            try {
                if (cek_jam(getJAM_OPR(), getMENIT_OPR())) {
                    cetakTicket(1);
                } else {
                    JOptionPane.showMessageDialog(this, getPESAN_OPR());
                }
                //ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
                //btnA.setIcon(image);
            } catch (java.io.IOException ex) {
                Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);
                //ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
                //btnA.setIcon(image);
            } finally {
                ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
                btnA.setIcon(image);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mohon Maaf Antrian NPWP Sedang Istirahat... \n Terimkasih atas Kunjungan Anda!");
            ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/pushed2.png"));
            btnA.setIcon(image);
        }
    }//GEN-LAST:event_btnAMouseReleased

    private void btnAMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAMousePressed
        ImageIcon image = new ImageIcon(getClass().getResource("/com/opass/kiosk/image/push2.png"));
        btnA.setIcon(image);
    }//GEN-LAST:event_btnAMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnA;
    private javax.swing.JLabel btnB;
    private javax.swing.JLabel btnC;
    private javax.swing.JButton jButton1;
    private com.opass.kiosk.panelGambar panelGambar1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the btnA
     */
    public javax.swing.JLabel getBtnA() {
        return btnA;
    }

    /**
     * @return the btnB
     */
    public javax.swing.JLabel getBtnB() {
        return btnB;
    }

    /**
     * @param btnB the btnB to set
     */
    public void setBtnB(javax.swing.JLabel btnB) {
        this.btnB = btnB;
    }

    /**
     * @return the btnC
     */
    public javax.swing.JLabel getBtnC() {
        return btnC;
    }

    /**
     * @param btnC the btnC to set
     */
    public void setBtnC(javax.swing.JLabel btnC) {
        this.btnC = btnC;
    }
}
