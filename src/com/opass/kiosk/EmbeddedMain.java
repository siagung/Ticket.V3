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
 import javax.swing.*;
   import java.awt.event.*;
   import java.awt.*;

   public class EmbeddedMain extends JFrame implements ActionListener 
   {
      private JTextField[] tfld = new JTextField[4];// x,y,z;
      private String sigma = " "+(char)0x2211;
      private String[] str = {"X=","Y=","Z=",sigma+"="};
      private String[] txt = {
         "7",     "8",    "9",     
         "4",     "5",    "6",    
         "1",     "2",    "3",    
         "Reset", "0",  "Enter"};
      private JButton[] but = new JButton[12];

      public static void main (String[] args)
      {
         EmbeddedMain em = new EmbeddedMain();
      }

      public EmbeddedMain() //constructor begins, method for embedded main class
      {
         setTitle("Subtraction");
         setSize(420,350);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLocationRelativeTo(null);

         setLayout(new FlowLayout());
         JPanel copa = (JPanel)getContentPane();
         copa.setPreferredSize(dim(3*70+140+5*5, 4*70+5*5));
         copa.setBackground(new Color(100,255,100));

         for (int i = 0, j = 0, k = 1; i < but.length; i++, k++) {
            but[i] = new JButton(txt[i]);
            but[i].setPreferredSize(dim(70,70));
            but[i].setFont(new Font("default",0,18));
            but[i].setMargin(new Insets(0,0,0,0));
            but[i].addActionListener(this);
            but[i].setName(""+i);
            add(but[i]);

            if (k%3 == 0) {
               tfld[j]  = new JTextField(str[j]); 
               tfld[j].setPreferredSize(dim(140,70));
               add(tfld[j++]);
            }   
         }
         pack();
         setVisible(true);
      }

      public void actionPerformed(ActionEvent e)
      {
         Object src = e.getSource();
         if (src instanceof JButton) {
            JButton but = (JButton)src;
            String nm = but.getName();
            int nr = Integer.parseInt(nm)/3;
            String txt = but.getText();
            if (txt.equalsIgnoreCase("reset")) {
               for (int j = 0; j < tfld.length; j++)
                  tfld[j].setText(str[j]);
            }

            else if (txt.equalsIgnoreCase("enter")) {
            }

            else
               tfld[0].setText(tfld[0].getText() + txt);
         }  
      }

      public Dimension dim(int w, int h) {
         return new Dimension(w,h);
      }
   }
