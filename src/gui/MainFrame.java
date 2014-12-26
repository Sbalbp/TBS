
package gui;

import game.*;
import game.settings.Settings;
import god.Spell;
import gui.animatedpanel.MainMenuPanel;
import gui.animatedpanel.MapPanel;
import i18n.Localizer;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import map.*;
import unit.Unit;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class MainFrame extends JFrame{

    private LanguageSelectPanel languagePanel;
    private MainMenuPanel menuPanel;
    private GameListPanel gameListPanel;
    private MapSelectPanel mapSelectPanel;
    private MapPanel mapPanel;
    private UnitInfoPanel unitInfoPanel;
    private KeyInteractive currentPanel;
    
    private ButtonPressed buttonListener;
    
    public MainFrame(){
        super();
        
        Game.game.setFrame(this);
        
        this.setTitle(Localizer.translate("game.frameName"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        
        buttonListener = new ButtonPressed();
        
        mapPanel = new MapPanel();
        mapPanel.setBounds(0,0,480,480);
        mapPanel.setVisible(false);
        mapPanel.initElements();
        this.getContentPane().add(mapPanel);
        
        unitInfoPanel = new UnitInfoPanel(480,0);
        unitInfoPanel.setVisible(false);
        this.getContentPane().add(unitInfoPanel);
        
        languagePanel = new LanguageSelectPanel(buttonListener);
        languagePanel.setVisible(false);
        this.getContentPane().add(languagePanel);
        
        gameListPanel = new GameListPanel(buttonListener);
        gameListPanel.setBounds(0,0,730,700);
        gameListPanel.setVisible(false);
        this.add(gameListPanel);
        
        mapSelectPanel = new MapSelectPanel(buttonListener);
        mapSelectPanel.setBounds(0,0,730,700);
        mapSelectPanel.setVisible(false);
        this.add(mapSelectPanel);
        
        menuPanel = new MainMenuPanel(buttonListener);
        menuPanel.setBounds(0,0,730,700);
        menuPanel.setVisible(false);
        this.getContentPane().add(menuPanel);
        
        JButton boton1 = new JButton();
        boton1.setBounds(new Rectangle(770,50,100,50));
        boton1.setText("Up");
        boton1.setActionCommand("up");
        boton1.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton1);
        
        JButton boton2 = new JButton();
        boton2.setBounds(new Rectangle(730,110,100,50));
        boton2.setText("Left");
        boton2.setActionCommand("left");
        boton2.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton2);
        
        JButton boton3 = new JButton();
        boton3.setBounds(new Rectangle(790,110,100,50));
        boton3.setText("Right");
        boton3.setActionCommand("right");
        boton3.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton3);
        
        JButton boton4 = new JButton();
        boton4.setBounds(new Rectangle(770,170,100,50));
        boton4.setText("Down");
        boton4.setActionCommand("down");
        boton4.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton4);
        
        JButton boton5 = new JButton();
        boton5.setBounds(new Rectangle(770,300,100,50));
        boton5.setText("Enter");
        boton5.setActionCommand("enter");
        boton5.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton5);
        
        JButton boton6 = new JButton();
        boton6.setBounds(new Rectangle(770,400,100,50));
        boton6.setText("Back");
        boton6.setActionCommand("back");
        boton6.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton6);
        
        JButton boton7 = new JButton();
        boton7.setBounds(new Rectangle(770,500,100,50));
        boton7.setText("End");
        boton7.setActionCommand("end");
        boton7.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton7);
        
        JButton boton8 = new JButton();
        boton8.setBounds(new Rectangle(770,600,100,50));
        boton8.setText("Skill");
        boton8.setActionCommand("skill");
        boton8.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton8);
    
        setActionBindings();
        setInputBindings();
      
        this.getContentPane().setPreferredSize(new Dimension(1000,700));
        this.pack();
        this.setLocation(100,5);
        this.setVisible(true);
        
        if(Settings.get("language_set").contains("yes")){
            runMenuPanel();
            //currentPanel = mapPanel;
            //runMapPanel();
        }
        else{
            currentPanel = languagePanel;
            ((JPanel)currentPanel).setVisible(true);
        }
    }
    
    public MapPanel getMapPanel(){
        return mapPanel;
    }
    
    public void setMapPanel(Map map){
        mapPanel.setMap(map);
    }
    
    public UnitInfoPanel getUnitInfoPanel(){
        return unitInfoPanel;
    }
    
    public void runMenuPanel(){
        Thread panelThread = new Thread(menuPanel);
        panelThread.setName("MenuPanel Thread");
        currentPanel = menuPanel;
        menuPanel.start();
        panelThread.start();
        menuPanel.setVisible(true);
    }
    
    public void stopMenuPanel(){
        menuPanel.setVisible(false);
        menuPanel.stop();
    }
    
    public void runMapPanel(){
        Thread panelThread = new Thread(mapPanel);
        panelThread.setName("MapPanel Thread");
        currentPanel = mapPanel;
        mapPanel.start();
        panelThread.start();
        mapPanel.setVisible(true);
        unitInfoPanel.setVisible(true);
    }
    
    public void stopMapPanel(){
        unitInfoPanel.setVisible(false);
        mapPanel.setVisible(false);
        mapPanel.stop();
    }
     
    public void setUnitInfo(Unit unit){
        unitInfoPanel.setUnit(unit);
    }
    
    public void localize(){
        this.setTitle(Localizer.translate("game.frameName"));
        gameListPanel.localize();
        menuPanel.localize();
        mapPanel.localize();
        mapSelectPanel.localize();
        unitInfoPanel.localize();
    }
    
    private void setActionBindings(){
        JPanel component = (JPanel)this.getContentPane();

        component.getActionMap().put("up",
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    currentPanel.up();
                }
            }
        );
        
        component.getActionMap().put("left",
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    currentPanel.left();
                }
            }
        );
        
        component.getActionMap().put("right",
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    currentPanel.right();
                }
            }
        );
        
        component.getActionMap().put("down",
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    currentPanel.down();
                }
            }
        );
        
        component.getActionMap().put("select",
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    currentPanel.select();
                }
            }
        );
        
        component.getActionMap().put("back",
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    currentPanel.back();
                }
            }
        );
        
        component.getActionMap().put("switch_tab",
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    currentPanel.switchTab();
                }
            }
        );
    }
    
    private void setInputBindings(){
        JPanel component = (JPanel)this.getContentPane();
        
        component.getInputMap().clear();

        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(Settings.getKey("up"),0,false), "up");
        
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(Settings.getKey("left"),0,false), "left");
        
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(Settings.getKey("right"),0,false), "right");
        
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(Settings.getKey("down"),0,false), "down");
        
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(Settings.getKey("select"),0,false), "select");
        
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(Settings.getKey("back"),0,false), "back");
        
        component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(Settings.getKey("switch_tab"),0,false), "switch_tab");
    }
    
    private class ButtonPressed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ("up".equals(e.getActionCommand())) {
                currentPanel.up();
            }
            if ("left".equals(e.getActionCommand())) {
                currentPanel.left();
            }
            if ("right".equals(e.getActionCommand())) {
                currentPanel.right();
            }
            if ("down".equals(e.getActionCommand())) {
                currentPanel.down();
            }
            if ("enter".equals(e.getActionCommand())) {
                currentPanel.select();
            }
            if ("back".equals(e.getActionCommand())) {
                currentPanel.back();
            }
            if ("end".equals(e.getActionCommand())) {
                Game.game.endTurn();
            }
            if ("skill".equals(e.getActionCommand())) {
                mapPanel.setSpell(new Spell());
            }
            
            if(e.getActionCommand().contains("menuButtonsPanel.")){
                switch(e.getActionCommand().replace("menuButtonsPanel.","")){
                    case "singleplayer":
                        stopMenuPanel();
                        mapSelectPanel.setOnlineSelection(false);
                        currentPanel = mapSelectPanel;
                        mapSelectPanel.setVisible(true);
                        break;
                    case "language":
                        stopMenuPanel();
                        currentPanel = languagePanel;
                        languagePanel.setVisible(true);
                        break;
                    case "multijoin":
                        stopMenuPanel();
                        currentPanel = gameListPanel;
                        gameListPanel.setVisible(true);
                        break;
                    default:
                        ((MainMenuPanel)currentPanel).buttonPressed(e.getActionCommand());
                }
            }
            
            if(e.getActionCommand().contains("mapSelectPanel.")){
                switch(e.getActionCommand().replace("mapSelectPanel.","")){
                    case "back":
                        if(mapSelectPanel.isOnlineSelection()){
                            // TO DO
                        }
                        else{
                            runMenuPanel();
                            currentPanel = menuPanel;
                        }
                        mapSelectPanel.setVisible(false);
                        break;
                    default:
                        ((MapSelectPanel)currentPanel).buttonPressed(e.getActionCommand());
                        break;
                }
            }
            
            if(e.getActionCommand().contains("languageSelectPanel.")){
                switch(e.getActionCommand().replace("languageSelectPanel.","")){
                    case "back":
                        if(!Settings.get("language_set").equals("no")){
                            runMenuPanel();
                            currentPanel = menuPanel;
                            languagePanel.setVisible(false);
                        }
                        break;
                    case "left":
                        ((LanguageSelectPanel)currentPanel).buttonPressed(e.getActionCommand());
                        break;
                    case "right":
                        ((LanguageSelectPanel)currentPanel).buttonPressed(e.getActionCommand());
                        break;
                    default:
                        if(Settings.get("language_set").equals("no")){
                            Settings.set("language_set","yes");
                        }
                        Settings.set("language",((LanguageSelectPanel)currentPanel).getLanguage(Integer.parseInt(e.getActionCommand().replace("languageSelectPanel.",""))));
                        Settings.saveSettings();
                        Localizer.setLanguage(((LanguageSelectPanel)currentPanel).getLanguage(Integer.parseInt(e.getActionCommand().replace("languageSelectPanel.",""))));
                        localize();
                        runMenuPanel();
                        currentPanel = menuPanel;
                        languagePanel.setVisible(false);
                }
            }
            
            if(e.getActionCommand().contains("joinGamePanel.")){
                switch(e.getActionCommand().replace("joinGamePanel.","")){
                    case "back":
                        if(!((GameListPanel)currentPanel).selectingServer()){
                            runMenuPanel();
                            currentPanel = menuPanel;
                            gameListPanel.setVisible(false);
                        }
                        else{
                            ((GameListPanel)currentPanel).buttonPressed(e.getActionCommand()); 
                        }
                        break;
                    default:
                        ((GameListPanel)currentPanel).buttonPressed(e.getActionCommand()); 
                }
            }
        }
    }
    
}
