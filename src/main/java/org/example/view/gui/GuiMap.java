package org.example.view.gui;

import org.example.controller.VisibleMap;
import org.example.controller.VisibleMapCell;
import org.example.model.hero.HeroTypes;
import org.example.model.map.VisibleCellTypes;
import org.example.model.monster.MonsterTypes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GuiMap extends JPanel {

    public static final Ground[][] MAP = {
            {Ground.GRASS, Ground.GRASS, Ground.DIRT, Ground.WATER, Ground.WATER},
            {Ground.GRASS, Ground.DIRT, Ground.CITY, Ground.WATER, Ground.WATER},
            {Ground.GRASS, Ground.DIRT, Ground.CITY, Ground.WATER, Ground.WATER},
            {Ground.GRASS, Ground.DIRT, Ground.DIRT, Ground.DIRT, Ground.WATER},
            {Ground.GRASS, Ground.GRASS, Ground.DIRT, Ground.WATER, Ground.WATER},
    };
    private BufferedImage heavyBanditIcon;
    private BufferedImage mushroomIcon;
    private BufferedImage skeletonIcon;
    private BufferedImage warriorIcon;
    private BufferedImage rogueIcon;
    private BufferedImage mageIcon;

    private JLabel[][] labelGrid;

    public GuiMap(VisibleMap visibleMap) {
        heavyBanditIcon = getImage(MonsterTypes.HEAVY_BANDIT);
        mushroomIcon = getImage(MonsterTypes.MUSHROOM);
        skeletonIcon = getImage(MonsterTypes.SKELETON);
        warriorIcon = getImage(HeroTypes.WARRIOR);
        rogueIcon = getImage(HeroTypes.ROGUE);
        mageIcon = getImage(HeroTypes.MAGE);

        int gridSize = visibleMap.getVisibleSize() + 1;
        labelGrid = new JLabel[gridSize][gridSize];

        setLayout(new GridLayout(gridSize, gridSize));
        //add empty for [0][0]
        add(new JLabel());
        //populate header
        for (int i = 1; i <= visibleMap.getVisibleSize(); i++) {
            //column names
            labelGrid[0][i] = createTextLabel(String.valueOf(
                    visibleMap.getCells()[1][i].getPosition().getX()));
            add(labelGrid[0][i]);
        }

        for (int r = 1; r < labelGrid.length; r++) {
            //row names
            labelGrid[r][0] = createTextLabel(String.valueOf(
                    visibleMap.getCells()[r][1].getPosition().getY()));
            add(labelGrid[r][0]);

            for (int c = 1; c < labelGrid[r].length; c++) {
                labelGrid[r][c] = new JLabel();
                VisibleMapCell currCell = visibleMap.getCells()[c][r];
                setIconByType(currCell, labelGrid[r][c]);
                add(labelGrid[r][c]);
            }
        }
    }

    private BufferedImage getImage(String name) {
        BufferedImage image;
        String imageFolder = "/sprites/";
        try {
            image = ImageIO.read(getClass().getResource(imageFolder + name + ".png"));
        } catch (IOException | IllegalArgumentException exception) {
            System.err.println("Can't load image " + name);
            image = new BufferedImage(256, 256,
                    BufferedImage.TYPE_INT_RGB);
        }
        return image;
    }

    private JLabel createTextLabel(String text) {
        JLabel jLabel = new JLabel();
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel.setText(text);
        return jLabel;
    }

    private void setIconByType(VisibleMapCell currCell, JLabel jlabel) {
        if (currCell.getType().equals(MonsterTypes.HEAVY_BANDIT)) {
            jlabel.setIcon(new ImageIcon(heavyBanditIcon));
        } else if (currCell.getType().equals(MonsterTypes.MUSHROOM)) {
            jlabel.setIcon(new ImageIcon(mushroomIcon));
        } else if (currCell.getType().equals(MonsterTypes.SKELETON)) {
            jlabel.setIcon(new ImageIcon(skeletonIcon));
        } else if (currCell.getType().equals(VisibleCellTypes.GROUND)) {
            jlabel.setIcon(Ground.DIRT);
        } else if (currCell.getType().equals(HeroTypes.WARRIOR)) {
            jlabel.setIcon(new ImageIcon(warriorIcon));
        } else if (currCell.getType().equals(HeroTypes.ROGUE)) {
            jlabel.setIcon(new ImageIcon(rogueIcon));
        } else if (currCell.getType().equals(HeroTypes.MAGE)) {
            jlabel.setIcon(new ImageIcon(mageIcon));
        } else {
            jlabel.setIcon(Ground.WATER);
        }
    }

/*
    public static GuiMap createMap() {
        GuiMap mainPanel = new GuiMap();
        return mainPanel;
        //JFrame frame = new JFrame("GridExample");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(mainPanel);
        //mainPanel.pack();
        //frame.setLocationByPlatform(true);
        //frame.setVisible(true);
    }
*/
}

