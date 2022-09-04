package org.example.view.gui;

import ch.qos.logback.core.Layout;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.example.Images;
import org.example.config.AppConfig;
import org.example.controller.GameController;
import org.example.model.GameState;
import org.example.model.hero.dto.HeroDTO;
import org.example.model.map.Directions;
import org.example.service.battle.BattleResult;
import org.example.view.Messages;
import org.example.view.SwingyView;
import org.example.view.ViewTypes;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

@Service
@Getter
@Setter
public class GuiView extends JFrame implements SwingyView {

    private interface ShowAction {
        void show();
    }

    private GameController gameController;
    private final AppConfig appConfig;
    GameState gameState;
    private ShowAction [] showActions;
    private boolean active;
    private String viewType;
    private JFrame f;

    public GuiView(AppConfig appConfig) {
        this.appConfig = appConfig;
        viewType = ViewTypes.GUI;
        initActionsArray();
        setUpFrame();
    }

    private void initActionsArray() {
        showActions = new ShowAction[GameState.GAME_STATE_NUM.ordinal()];
        showActions[GameState.START_MENU.ordinal()] = this::showStartMenu;
        showActions[GameState.CREATE_HERO.ordinal()] = this::showNewHero;
        showActions[GameState.CHOOSE_HERO.ordinal()] = this::showChooseHero;
        showActions[GameState.GAME_MAIN.ordinal()] = this::showGameMain;
        showActions[GameState.BEFORE_BATTLE.ordinal()] = this::showBeforeBattle;
        showActions[GameState.START_BATTLE.ordinal()] = this::showBattle;
        showActions[GameState.EXIT_MAP_QUESTION.ordinal()] = this::showExitMapQuestion;
        showActions[GameState.RETREAT.ordinal()] = this::showRetreat;
    }

    private void setUpFrame() {
        f = this;
        setSize(appConfig.getWindowWidth(),appConfig.getWindowHeight());
        setTitle("Swingy");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setVisible(false);
        //dispose();
        /*
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });

         */
    }
    
    private void addSwingyLogo(JPanel panel, String title) {

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        //vertical strut
        panel.add(Box.createVerticalStrut(20));

        //Welcome logo
        BufferedImage logo = getImage(Images.SWINGY_LOGO);
        JLabel picLabel = new JLabel(new ImageIcon(logo));
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(picLabel);

        //vertical strut
        panel.add(Box.createVerticalStrut(10));

        //Welcome text
        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setText(title);
        panel.add(welcomeLabel);



        //vertical strut
        panel.add(Box.createVerticalStrut(40));
    }

    private JLabel createTextLabel(String text) {
        JLabel jLabel = new JLabel();
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel.setText(text);
        return jLabel;
    }

    public void showGameMain() {
        int mapLevel = gameController.getCurrHero().getLevel();

        //gameMainPanel panel
        JPanel gamePanel = new JPanel();
        JPanel gameMainPanel = new JPanel();
        JPanel gameMapPanel = new JPanel();

        //vert strut between map upper border
        gamePanel.add(Box.createVerticalStrut(20));

        gameMapPanel.add(new GuiMap(gameController.getVisibleMap()));
        gameMainPanel.add(gameMapPanel);

        //horiz strut between map and info
        gameMainPanel.add(Box.createHorizontalStrut(30));

        //hero info
        JPanel heroInfoPanel = new JPanel();
        JTable heroInfoTable = new JTable(gameController.provideHeroInfoArray(), new String []{"param", "value"});
        Border border = heroInfoPanel.getBorder();
        Border margin = new EmptyBorder(100,10,10,10);
        heroInfoPanel.setBorder(new CompoundBorder(border, margin));

        heroInfoPanel.add(heroInfoTable);
        gameMainPanel.add(heroInfoPanel);

        gameMainPanel.setLayout(new BoxLayout(gameMainPanel, BoxLayout.X_AXIS));
        gamePanel.add(gameMainPanel);

        //vert strut between map and buttons
        gamePanel.add(Box.createVerticalStrut(20));

        //Move buttons panel
        JPanel movePanel = new JPanel();
        movePanel.setLayout(new BoxLayout(movePanel, BoxLayout.X_AXIS));
        addMoveButton(movePanel, Directions.NORTH, Messages.GO_NORTH);
        addMoveButton(movePanel, Directions.SOUTH, Messages.GO_SOUTH);
        addMoveButton(movePanel, Directions.EAST, Messages.GO_EAST);
        addMoveButton(movePanel, Directions.WEST, Messages.GO_WEST);
        addReturnButton(movePanel, GameState.START_MENU, "Return");
        addChangeViewButton(movePanel);
        gamePanel.add(movePanel);
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        add(gamePanel);
        setVisible(true);
        repaint();

        if (gameController.isBattle()) {
            return;
        }
    }

    public void showNewHero() {
        //newHeroPanel panel
        JPanel newHeroPanel = new JPanel();
        addSwingyLogo(newHeroPanel, Messages.CREATE_HERO_MESSAGE);

        //label input hero name
        JLabel inputNameLabel = new JLabel();
        inputNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputNameLabel.setText(Messages.CREATE_HERO_INPUT_NAME);
        newHeroPanel.add(inputNameLabel);

        //vertical strut
        newHeroPanel.add(Box.createVerticalStrut(20));

        //input name textbox
        JTextField nameField = new JTextField(30);
        newHeroPanel.add(nameField, BorderLayout.SOUTH);

        //vertical strut
        newHeroPanel.add(Box.createVerticalStrut(20));

        //label choose hero
        JLabel chooseClassLabel = new JLabel();
        chooseClassLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        chooseClassLabel.setText(Messages.CREATE_HERO_CHOOSE_CLASS);
        newHeroPanel.add(chooseClassLabel);

        //vertical strut
        newHeroPanel.add(Box.createVerticalStrut(20));

        //radio buttons "choose class"
        JRadioButton option1 = new JRadioButton("Warrior");
        option1.setActionCommand("Warrior");
        JRadioButton option2 = new JRadioButton("Rogue");
        option2.setActionCommand("Rogue");
        JRadioButton option3 = new JRadioButton("Mage");
        option3.setActionCommand("Mage");

        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);

        JPanel radioButtonPanel = new JPanel();
        BoxLayout radioButtonLayout = new BoxLayout(radioButtonPanel, BoxLayout.X_AXIS);
        radioButtonPanel.setLayout(radioButtonLayout);
        radioButtonPanel.add(option1);
        radioButtonPanel.add(option2);
        radioButtonPanel.add(option3);
        newHeroPanel.add(radioButtonPanel);

        //vertical strut
        newHeroPanel.add(Box.createVerticalStrut(20));


        //Button new hero

        JButton createHero =new JButton(Messages.START_CREATE_HERO);//creating instance of JButton
        createHero.setBounds(130,100,100, 40);
        createHero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String heroName = nameField.getText();

                if (StringUtils.isBlank(nameField.getText())) {
                    new GuiInfoWindow(f, "Invalid input", Messages.CREATE_HERO_INPUT_NAME).setVisible(true);
                    return;
                }

                if (group.getSelection() == null) {
                    new GuiInfoWindow(f, "Invalid input", Messages.CREATE_HERO_CHOOSE_CLASS).setVisible(true);
                    return;
                }
                String heroClass = group.getSelection().getActionCommand();

                if (gameController.isHeroExists(heroName)) {
                    new GuiInfoWindow(f, "Invalid input", String.format(Messages.CREATE_HERO_ALREADY_EXISTS,
                            heroName)).setVisible(true);
                    return;
                }

                String error = gameController.createHero(heroName, heroClass);
                if (StringUtils.isNotBlank(error)) {
                    new GuiInfoWindow(f, "Invalid input", error).setVisible(true);
                } else if (gameController.isHeroExists(heroName)) {
                    new GuiInfoWindow(f, "Info", 
                            String.format("New hero created: %s, the %s\n", heroName, heroClass)).setVisible(true);
                    gameController.updateGameState(GameState.CHOOSE_HERO);
                }
            }
        });
        createHero.setAlignmentX(Component.CENTER_ALIGNMENT);
        newHeroPanel.add(createHero);
        add(newHeroPanel);
        addReturnButton(newHeroPanel, GameState.START_MENU, Messages.RETURN);

        setVisible(true);
        repaint();
    }

    public void addReturnButton(JPanel panel, GameState state, String text) {
        //vertical strut
        panel.add(Box.createVerticalStrut(10));

        //button
        JButton chooseHeroButton = new JButton(text);//creating instance of JButton
        chooseHeroButton.setBounds(330,100,100, 40);
        chooseHeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameController.getCurrHero() != null) {
                    gameController.updateHero();
                }
                gameController.updateGameState(state);
            }
        });
        chooseHeroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(chooseHeroButton);
    }

    public void addChangeViewButton(JPanel panel) {
        //vertical strut
        panel.add(Box.createVerticalStrut(10));

        //button
        JButton changeViewButton = new JButton("Change view");//creating instance of JButton
        changeViewButton.setBounds(330,100,100, 40);
        changeViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.changeView(gameController.getCurrView());
            }
        });
        changeViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(changeViewButton);
    }

    public void addMoveButton(JPanel panel, Directions direction, String text) {
        //vertical strut
        panel.add(Box.createVerticalStrut(10));

        //button
        JButton moveButton = new JButton(text);//creating instance of JButton
        moveButton.setBounds(330,100,100, 40);
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isMoveExit = false;
                if (direction == Directions.NORTH) {
                    isMoveExit = gameController.moveNorth();
                } else if (direction == Directions.EAST) {
                    isMoveExit = gameController.moveEast();
                } else if (direction == Directions.SOUTH) {
                    isMoveExit = gameController.moveSouth();
                } else if (direction == Directions.WEST) {
                    isMoveExit = gameController.moveWest();
                }

                if (isMoveExit) {
                    return;
                }
                gameController.setUiChanged(true);
            }
        });
        moveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(moveButton);
    }

    public void showChooseHero() {
        setLayout(new FlowLayout());

        JPanel chooseHeroPanel = new JPanel();
        addSwingyLogo(chooseHeroPanel, Messages.CHOOSE_HERO_MESSAGE);

        ArrayList<HeroDTO> heroesList = gameController.getHeroes();
        String [] columns = HeroDTO.provideColumnNames();
        String [][] heroes = new String[heroesList.size()][columns.length];

        int i = 0;
        for (HeroDTO dto : gameController.getHeroes()) {
            int j = 0;
            heroes[i][j++] = String.valueOf(i + 1);
            heroes[i][j++] = dto.getName();
            heroes[i][j++] = dto.getType();
            heroes[i++][j++] = String.valueOf(dto.getLevel());
        }
        JTable jTable = new JTable(heroes, columns);
        //jTable.setSize(300,400);
        JScrollPane sp=new JScrollPane(jTable);
        chooseHeroPanel.add(sp);

        //vertical strut
        chooseHeroPanel.add(Box.createVerticalStrut(10));

        //action buttons panel
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));

        //choose hero button
        JButton chooseHeroButton = new JButton(Messages.START_CHOOSE_HERO);//creating instance of JButton
        chooseHeroButton.setBounds(330,100,100, 40);
        chooseHeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = jTable.getSelectedRow();
                if (row == -1) {
                    new GuiInfoWindow(f, "Invalid input", Messages.CHOOSE_HERO_MESSAGE).setVisible(true);
                    return;
                }

                int id = Integer.parseInt(jTable.getModel().getValueAt(row, column).toString());

                HeroDTO heroDTO = heroesList.get(id - 1);
                gameController.chooseHero(heroDTO);
                gameController.startGame();
            }
        });
        chooseHeroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionPanel.add(chooseHeroButton);

        //horizontal strut
        actionPanel.add(Box.createHorizontalStrut(10));

        //create new hero button
        JButton createHeroButton = new JButton(Messages.START_CREATE_HERO);//creating instance of JButton
        createHeroButton.setBounds(330,100,100, 40);
        createHeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.updateGameState(GameState.CREATE_HERO);
            }
        });
        createHeroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionPanel.add(createHeroButton);

        //horizontal strut
        actionPanel.add(Box.createHorizontalStrut(10));

        //delete hero button
        JButton deleteHeroButton = new JButton(Messages.CHOOSE_HERO_DELETE);//creating instance of JButton
        deleteHeroButton.setBounds(330,100,100, 40);
        deleteHeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = jTable.getSelectedRow();
                if (row == -1) {
                    new GuiInfoWindow(f, "Invalid input", Messages.CHOOSE_HERO_MESSAGE + " to delete").setVisible(true);
                    return;
                }

                int id = Integer.parseInt(jTable.getModel().getValueAt(row, column).toString());

                HeroDTO heroDTO = heroesList.get(id - 1);
                gameController.deleteHero(heroDTO);
                new GuiInfoWindow(f, "Info", "Hero has been deleted!").setVisible(true);
                gameController.setUiChanged(true); //dirty repaint
            }
        });
        deleteHeroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionPanel.add(deleteHeroButton);

        //horizontal strut
        actionPanel.add(Box.createHorizontalStrut(10));

        // return button
        addReturnButton(actionPanel, GameState.START_MENU, Messages.RETURN);
        chooseHeroPanel.add(actionPanel);

        //vertical strut
        chooseHeroPanel.add(Box.createVerticalStrut(10));

        add(chooseHeroPanel);
        setVisible(true);
        repaint();
    }

    private void cleanFrame() {
        getContentPane().removeAll();
        repaint();
    }

    public void updateGameState(GameState gameState) {
        if (gameController.isUiChanged() || this.gameState != gameState) {
            this.gameState = gameState;
            if (isActive()) {
                if (gameController.isUiChanged()) {
                    gameController.setUiChanged(false);
                }
                cleanFrame();
                showActions[gameState.ordinal()].show();
            }
        }
    }

    public void showExitMapQuestion() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JLabel label = new JLabel(Messages.EXIT_MAP_QUESTION);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        //vertical strut
        panel.add(Box.createVerticalStrut(10));

        //Button quit
        JPanel actionButtonsPanel = new JPanel();
        actionButtonsPanel.setLayout(new BoxLayout(actionButtonsPanel, BoxLayout.X_AXIS));
        JButton nextMapButton = new JButton("Go to next map");//creating instance of JButton
        nextMapButton.setBounds(130,100,100, 40);
        nextMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.loadNextMap();
            }
        });
        nextMapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionButtonsPanel.add(nextMapButton);

        //Button go to menu
        JButton toMenuButton = new JButton("Go to menu");//creating instance of JButton
        toMenuButton.setBounds(330,100,100, 40);
        toMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.returnToStartMenu();
            }
        });
        toMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionButtonsPanel.add(toMenuButton);
        panel.add(actionButtonsPanel);
        add(panel);
        setVisible(true);
    }

    public void showStartMenu() {

        //Welcome panel
        JPanel welcomePanel = new JPanel();
        addSwingyLogo(welcomePanel, Messages.START_WELCOME_MESSAGE);

        //Button start game
        JButton startGameButton = new JButton(Messages.START_START_GAME);//creating instance of JButton
        startGameButton.setBounds(330,100,100, 40);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gameController.updateGameState(GameState.CHOOSE_HERO);
            }
        });
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(startGameButton);


        //Button quit
        JButton quitButton = new JButton(Messages.START_QUIT);//creating instance of JButton
        quitButton.setBounds(130,100,100, 40);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        addChangeViewButton(welcomePanel);

        //vertical strut
        welcomePanel.add(Box.createVerticalStrut(10));

        welcomePanel.add(quitButton);


        //add panel to the frame
        add(welcomePanel);

        setVisible(true);
        repaint();
    }

    public void activate() {
        active = true;

        setVisible(true);
        repaint();

    }

    public void deactivate() {
        active = false;
        setVisible(false);
        dispose();
        repaint();
    }

    public void showBeforeBattle() {
        new GuiBeforeBattle(this,"You have met a monster!", gameController).setVisible(true);
    }

    public void showBattle() {
        BattleResult result = gameController.doBattle();
        if (result.isHeroWon() == false) {
            new GuiInfoWindow(f, "Battle result", Messages.HERO_DIED_GUI).setVisible(true);
            gameController.killHero();
        } else {
            new GuiRewardsWindow(f, "Battle result", gameController, result).setVisible(true);
            gameController.continueGame();
        }
    }

    public void showRetreat() {
        if (gameController.isRetreatSuccessful()) {
            new GuiInfoWindow(f, "Retreat", Messages.RETREAT_SUCCESSFULLY).setVisible(true);
            gameController.doRetreat();
        } else {
            new GuiInfoWindow(f, "Retreat", Messages.RETREAT_FAILURE).setVisible(true);
            gameController.startBattle();
        }
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
