package org.example.view.gui;

import org.example.controller.GameController;
import org.example.model.GameState;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiBeforeBattle extends JDialog {

    public GuiBeforeBattle(JFrame owner, String title, GameController gameController)
    {
        super(owner, title, true);

        setSize(500, 400);
        //setLocationRelativeTo(null);
        setLocation(new Point(
                owner.getLocation().x + owner.getSize().width / 2 - getWidth() / 2,
                owner.getLocation().y + owner.getSize().height / 2 - getHeight() / 2));


        StringBuilder builder = new StringBuilder();
        builder.append(String.format(
                "You`ve met a monster:\n %s \n(chance to win - %.2f%%)\n",
                gameController.getCurrentMonsterInfo(),
                gameController.getLastBattleProbability() * 100
        ));
        builder.append("You could fight him or retreat with 50% chance.\n");
        builder.append("What will you choose?\n");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //label text
        JTextArea jTextArea = new JTextArea();
        jTextArea.setText(builder.toString());
        jTextArea.setEditable(false);
        //setLocationRelativeTo(null);
        //jLabel.setLocation(new Point(
          //      mainPanel.getLocation().x, // + this.getSize().width / 2 - jLabel.getWidth() / 2,
            //    mainPanel.getLocation().y)); // + this.getSize().height / 2 - jLabel.getHeight() / 2));

        mainPanel.add(jTextArea);

        //vertical strut
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        // Ok button closes the dialog
        JButton attack = new JButton("Attack monster");
        attack.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                setVisible(false);
                gameController.startBattle();
            }
        });

        JButton retreat = new JButton("Retreat");
        retreat.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                setVisible(false);
                gameController.rollRetreat();
            }
        });

        buttonsPanel.add(attack);
        //horizontal strut
        buttonsPanel.add(Box.createHorizontalStrut(20));
        buttonsPanel.add(retreat);

        //vertical strut
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(buttonsPanel);

        //vertical strut
        mainPanel.add(Box.createVerticalStrut(10));

        add(mainPanel);
    }
}
