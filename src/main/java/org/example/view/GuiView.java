package org.example.view;

import lombok.Setter;
import org.example.Images;
import org.example.ProfileTypes;
import org.example.controller.GameController;
import org.example.model.GameState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

@Service

@Setter
@Profile(ProfileTypes.GUI)
public class GuiView extends JFrame implements SwingyView {
    JFrame f;
    GameController gameController;
    @Value("${swingy.window.width}")
    int windowWidth;
    @Value("${swingy.window.height}")
    int windowHeight;
    GameState gameState;
    private ShowAction [] showActions;

    interface ShowAction {
        void show();
    }

    public GuiView() {
        initActionsArray();
    }

    private void initActionsArray() {
        showActions = new ShowAction[GameState.GAME_STATE_NUM.ordinal()];
        showActions[GameState.START_MENU.ordinal()] = () -> showStartMenu();
        showActions[GameState.CREATE_HERO.ordinal()] = () -> showNewHero();
        showActions[GameState.CHOOSE_HERO.ordinal()] = () -> showChooseHero();
    }

    private void setUpFrame() {
        setSize(windowWidth,windowHeight);//400 width and 500 height
        setTitle("Swingy");
        addWindowListener(new java.awt.event.WindowAdapter() {
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
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showNewHero() {
        cleanFrame();
    }

    public void showChooseHero() {
        cleanFrame();
    }

    private void cleanFrame() {
        getContentPane().removeAll();
        repaint();
    }

    public void updateGameState(GameState gameState) {
        if (gameState ==  this.gameState) {
            return;
        }
        showActions[gameState.ordinal()].show();
    }


    public void showStartMenu() {

        //setVisible(true);
        setUpFrame();
        f = this;

        //Welcome panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.PAGE_AXIS));

        //vertical strut
        welcomePanel.add(Box.createVerticalStrut(10));

        //Welcome text
        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setText(Messages.START_MESSAGE);
        welcomePanel.add(welcomeLabel);

        //vertical strut
        welcomePanel.add(Box.createVerticalStrut(20));

        //Welcome logo
        BufferedImage logo = getImage(Images.SWINGY_LOGO);
        JLabel picLabel = new JLabel(new ImageIcon(logo));
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(picLabel);

        //vertical strut
        welcomePanel.add(Box.createVerticalStrut(20));

        //Button new hero
        JButton newHeroButton =new JButton(Messages.START_CREATE_HERO);//creating instance of JButton
        newHeroButton.setBounds(130,100,100, 40);
        newHeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNewHero();
            }
        });
        newHeroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(newHeroButton);

        //vertical strut
        welcomePanel.add(Box.createVerticalStrut(10));

        //Button choose hero
        JButton chooseHeroButton = new JButton(Messages.START_CHOOSE_HERO);//creating instance of JButton
        chooseHeroButton.setBounds(330,100,100, 40);
        chooseHeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showChooseHero();
            }
        });
        chooseHeroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(chooseHeroButton);
        f.add(welcomePanel);
        f.setVisible(true);
    }

    private BufferedImage getImage(String name) {
        BufferedImage image;
        String imageFolder = "/img/";
        try {
            image = ImageIO.read(getClass().getResource(imageFolder + name));
        } catch (IOException | IllegalArgumentException exception) {
            System.err.println("Can't load image " + name);
            image = new BufferedImage(256, 256,
                    BufferedImage.TYPE_INT_RGB);
        }
        return image;
    }
}
