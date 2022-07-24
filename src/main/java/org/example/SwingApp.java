package org.example;

import javax.swing.*;


public class SwingApp extends JFrame{
    JFrame f;

    public SwingApp() {
        f = this;
        JButton b=new JButton("click");//creating instance of JButton
        b.setBounds(130,100,100, 40);

        this.add(b);//adding button in JFrame

        this.setSize(400,500);//400 width and 500 height
        this.setLayout(null);//using no layout managers
        //f.setVisible(true);//making the frame visible
        f.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(f,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }
}
