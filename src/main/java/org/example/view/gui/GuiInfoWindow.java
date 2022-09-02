package org.example.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuiInfoWindow extends JDialog
{
    public GuiInfoWindow(JFrame owner, String title, String text)
    {
        super(owner, title, true);
        setLocationRelativeTo(null);

        // add HTML label to center
        add(new JLabel(text, SwingConstants.CENTER));

        // Ok button closes the dialog

        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                setVisible(false);
            }
        });

        // add Ok button to southern border

        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);

        setSize(250, 150);
    }
}
