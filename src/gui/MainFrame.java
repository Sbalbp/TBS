
package gui;

import game.*;
import game.settings.Settings;
import god.Spell;
import gui.animatedpanel.MapPanel;
import i18n.Localizer;
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
    
    private MapPanel mapPanel;
    private UnitInfoPanel unitInfoPanel;
    private KeyInteractive currentPanel;
    
    public MainFrame(){
        super();
        
        Game.game.setFrame(this);
        
        this.setTitle(Localizer.translate("game.frameName"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        
        mapPanel = new MapPanel();
        mapPanel.setBounds(50,50,480,480);
        mapPanel.setVisible(false);
        mapPanel.initElements();
        this.getContentPane().add(mapPanel);
        
        unitInfoPanel = new UnitInfoPanel(730,50);
        this.getContentPane().add(unitInfoPanel);
        
        JButton boton1 = new JButton();
        boton1.setBounds(new Rectangle(580,50,100,50));
        boton1.setText("Up");
        boton1.setActionCommand("up");
        boton1.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton1);
        
        JButton boton2 = new JButton();
        boton2.setBounds(new Rectangle(540,110,100,50));
        boton2.setText("Left");
        boton2.setActionCommand("left");
        boton2.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton2);
        
        JButton boton3 = new JButton();
        boton3.setBounds(new Rectangle(600,110,100,50));
        boton3.setText("Right");
        boton3.setActionCommand("right");
        boton3.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton3);
        
        JButton boton4 = new JButton();
        boton4.setBounds(new Rectangle(580,170,100,50));
        boton4.setText("Down");
        boton4.setActionCommand("down");
        boton4.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton4);
        
        JButton boton5 = new JButton();
        boton5.setBounds(new Rectangle(580,300,100,50));
        boton5.setText("Enter");
        boton5.setActionCommand("enter");
        boton5.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton5);
        
        JButton boton6 = new JButton();
        boton6.setBounds(new Rectangle(580,400,100,50));
        boton6.setText("Back");
        boton6.setActionCommand("back");
        boton6.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton6);
        
        JButton boton7 = new JButton();
        boton7.setBounds(new Rectangle(580,500,100,50));
        boton7.setText("End");
        boton7.setActionCommand("end");
        boton7.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton7);
        
        JButton boton8 = new JButton();
        boton8.setBounds(new Rectangle(580,600,100,50));
        boton8.setText("Skill");
        boton8.setActionCommand("skill");
        boton8.addActionListener(new ButtonPressed());
        this.getContentPane().add(boton8);
    
        setActionBindings();
        setInputBindings();

        this.pack();
        this.setBounds(100,5,1000,700);
        this.setVisible(true);
        
    }
    
    public MapPanel getMapPanel(){
        return mapPanel;
    }
    
    public void setMapPanel(Map map){
        mapPanel.setMap(map);
    }
    
    public void runMapPanel(){
        Thread panelThread = new Thread(mapPanel);
        panelThread.setName("MapPanel Thread");
        panelThread.start();
        currentPanel = mapPanel;
        mapPanel.setVisible(true);
    }
    
    public void stopMapPanel(){
        mapPanel.setVisible(false);
        mapPanel.stop();
    }
     
    public void setUnitInfo(Unit unit){
        unitInfoPanel.setUnit(unit);
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
        }
    }
    
}
