package org.example.view.gui;

import org.example.controller.GameController;
import org.example.model.GameState;
import org.example.model.entity.Entity;
import org.example.model.item.Item;
import org.example.service.battle.BattleResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class GuiRewardsWindow extends JDialog {

    public GuiRewardsWindow(JFrame owner, String title, GameController gameController, BattleResult rewards)
    {
        super(owner, title, true);

        setSize(500, 400);
        //setLocationRelativeTo(null);
        setLocation(new Point(
                owner.getLocation().x + owner.getSize().width / 2 - getWidth() / 2,
                owner.getLocation().y + owner.getSize().height / 2 - getHeight() / 2));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        JPanel infoPanel = new JPanel();
        JTextArea jTextArea = new JTextArea();
        jTextArea.setText(rewards.toString());
        jTextArea.setEditable(false);
        infoPanel.add(jTextArea);
        mainPanel.add(infoPanel);

        String [] itemNames = rewards.getRewards()
                        .stream()
                        .map(Entity::getName)
                        .toArray(String[]::new);
        CheckBoxGroup checkBoxGroup = new CheckBoxGroup(itemNames);
        mainPanel.add(checkBoxGroup);

        //vertical strut
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        // Ok button closes the dialog
        JButton grab = new JButton(rewards.getRewards().size() > 0 ? "Grab rewards" : "Return");
        grab.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                setVisible(false);
                for (JCheckBox cb : checkBoxGroup.checkBoxes) {
                    if (cb.isSelected() && !cb.getText().equals("all")) {
                        Item item =  rewards.getRewards().stream().filter(
                                i -> i.getName().equals(cb.getText())).findFirst().get();
                        gameController.equipItem(item);
                    }
                }
            }
        });

        buttonsPanel.add(grab);
        //horizontal strut
        buttonsPanel.add(Box.createHorizontalStrut(20));

        //vertical strut
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(buttonsPanel);

        //vertical strut
        mainPanel.add(Box.createVerticalStrut(10));

        add(mainPanel);
    }
    public class CheckBoxGroup extends JPanel {

        private JCheckBox all;
        private ArrayList<JCheckBox> checkBoxes;

        public CheckBoxGroup(String... options) {
            checkBoxes = new ArrayList<>(25);
            setLayout(new BorderLayout());
            JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
            all = new JCheckBox("Select All...");
            all.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (JCheckBox cb : checkBoxes) {
                        cb.setSelected(all.isSelected());
                    }
                }
            });
            header.add(all);
            add(header, BorderLayout.NORTH);

            JPanel content = new ScrollablePane(new GridBagLayout());
            content.setBackground(UIManager.getColor("List.background"));
            if (options.length > 0) {

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.weightx = 1;
                for (int index = 0; index < options.length - 1; index++) {
                    JCheckBox cb = new JCheckBox(options[index]);
                    cb.setOpaque(false);
                    checkBoxes.add(cb);
                    content.add(cb, gbc);
                }

                JCheckBox cb = new JCheckBox(options[options.length - 1]);
                cb.setOpaque(false);
                checkBoxes.add(cb);
                gbc.weighty = 1;
                content.add(cb, gbc);

            }

            add(new JScrollPane(content));
        }

        public class ScrollablePane extends JPanel implements Scrollable {

            public ScrollablePane(LayoutManager layout) {
                super(layout);
            }

            public ScrollablePane() {
            }

            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(150, 100);
            }

            @Override
            public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
                return 32;
            }

            @Override
            public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
                return 32;
            }

            @Override
            public boolean getScrollableTracksViewportWidth() {
                boolean track = false;
                Container parent = getParent();
                if (parent instanceof JViewport) {
                    JViewport vp = (JViewport) parent;
                    track = vp.getWidth() > getPreferredSize().width;
                }
                return track;
            }

            @Override
            public boolean getScrollableTracksViewportHeight() {
                boolean track = false;
                Container parent = getParent();
                if (parent instanceof JViewport) {
                    JViewport vp = (JViewport) parent;
                    track = vp.getHeight() > getPreferredSize().height;
                }
                return track;
            }

        }

    }

}
