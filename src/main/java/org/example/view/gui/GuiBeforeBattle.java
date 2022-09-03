package org.example.view.gui;

import org.example.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiBeforeBattle extends JDialog {
    public GuiBeforeBattle(JFrame owner, String title, String text, GameController gameController)
    {
        super(owner, title, true);

        setSize(400, 300);
        int x = owner.getLocation().x + owner.getSize().width / 2 - getWidth() / 2;
        int y = owner.getLocation().y + owner.getSize().height / 2 - getHeight() / 2;
        //setLocationRelativeTo(null);
        setLocation(new Point(
                owner.getLocation().x + owner.getSize().width / 2 - getWidth() / 2,
                owner.getLocation().y + owner.getSize().height / 2 - getHeight() / 2));

        StringBuilder builder = new StringBuilder();
        builder.append(String.format(
                "You`ve met a monster - %s (chance to win - %.2f%%)\n",
                gameController.getCurrentMonsterInfo(),
                gameController.getLastBattleProbability() * 100
        ));
        builder.append("You could fight him or retreat with 50% chance. What will you choose?");
        // add HTML label to center
        add(new JLabel(builder.toString(), SwingConstants.CENTER));



        // Ok button closes the dialog
        JButton attack = new JButton("Attack monster");
        attack.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                setVisible(false);
            }
        });

        JButton retreat = new JButton("Retreat");
        retreat.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                setVisible(false);
            }
        });

        // add buttons to southern border

        JPanel panel = new JPanel();
        panel.add(attack);
        panel.add(retreat);
        add(panel, BorderLayout.SOUTH);


    }
}
