
package gui;

import game.settings.Settings;
import i18n.Localizer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import map.Map;
import map.MapUtils;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class MapSelectPanel extends JLayeredPane implements KeyInteractive{
    private int width = 730, height = 700;
    private int currentTab = 0, nextTab = 0, currentSlot = 0, nextSlot = 0;
    private int currentElement = -1, nextElement = -1;
    private boolean online = false;
    private String server = null;
    private JButton leftButton, rightButton, upButton, downButton, backButton;
    private JLayeredPane namePanel;
    private JLabel nPlayersLabel, mapName;
    private JPanel gamesContainer;
    private ArrayList<ArrayList<Map>> mapList;
    private ArrayList<HostedGamePanel> gamePanels;
    private JScrollPane scroller;
    private ServerSelectPanel serverSelectPanel;
    private ActionListener listener;
    private MapPreviewPanel mapPreview;
    private MapSelectPanelMouseEvent mouseListener = new MapSelectPanelMouseEvent();
    
    public MapSelectPanel(ActionListener buttonListener){
        super();
        
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setOpaque(true);

        listener = buttonListener;
        
        mapPreview = new MapPreviewPanel(175,200,300,300);
        mapPreview.setName("0");
        mapPreview.addMouseListener(mouseListener);
        this.add(mapPreview, new Integer(0),-1);
        
        nPlayersLabel = new JLabel();
        nPlayersLabel.setText((currentTab+2)+" "+Localizer.translate("gui.MapSelectPanel.players"));
        nPlayersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nPlayersLabel.setFont(nPlayersLabel.getFont().deriveFont((float)20.0));
        nPlayersLabel.setForeground(Color.WHITE);
        nPlayersLabel.setBounds(265,50,200,50);
        this.add(nPlayersLabel, new Integer(0),-1);
        
        namePanel = new JLayeredPane();
        namePanel.setLayout(null);
        namePanel.setBorder(null);
        namePanel.setBounds(265,130,200,50);
        namePanel.setBackground(Color.WHITE);
        
        mapName = new JLabel();
        mapName.setText("");
        mapName.setHorizontalAlignment(SwingConstants.CENTER);
        mapName.setFont(nPlayersLabel.getFont().deriveFont((float)20.0));
        mapName.setForeground(Color.WHITE);
        mapName.setBounds(0,0,200,50);
        namePanel.add(mapName, new Integer(0),-1);
        
        this.add(namePanel, new Integer(0),-1);
        
        upButton = new JButton();
        upButton.setBorderPainted(false);
        upButton.setContentAreaFilled(false);
        upButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_up_0.gif"));
        upButton.setRolloverIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_up_0_rollover.gif"));
        upButton.setBounds(495,260,80,80);
        upButton.setActionCommand("mapSelectPanel.up");
        upButton.addActionListener(listener);
        this.add(upButton, new Integer(0),-1);
        
        downButton = new JButton();
        downButton.setBorderPainted(false);
        downButton.setContentAreaFilled(false);
        downButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_down_0.gif"));
        downButton.setRolloverIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_down_0_rollover.gif"));
        downButton.setBounds(495,360,80,80);
        downButton.setActionCommand("mapSelectPanel.down");
        downButton.addActionListener(listener);
        this.add(downButton, new Integer(0),-1);
        
        leftButton = new JButton();
        leftButton.setBorderPainted(false);
        leftButton.setContentAreaFilled(false);
        leftButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_left_0.gif"));
        leftButton.setRolloverIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_left_0_rollover.gif"));
        leftButton.setBounds(180,40,80,80);
        leftButton.setActionCommand("mapSelectPanel.left");
        leftButton.addActionListener(listener);
        this.add(leftButton, new Integer(0),-1);
        
        rightButton = new JButton();
        rightButton.setBorderPainted(false);
        rightButton.setContentAreaFilled(false);
        rightButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_right_0.gif"));
        rightButton.setRolloverIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_right_0_rollover.gif"));
        rightButton.setBounds(470,40,80,80);
        rightButton.setActionCommand("mapSelectPanel.right");
        rightButton.addActionListener(listener);
        this.add(rightButton, new Integer(0),-1);
        
        backButton = new JButton();
        backButton.setName("1");
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/back_0.gif"));
        backButton.setBounds(width-120,height-80,100,60);
        backButton.setActionCommand("mapSelectPanel.back");
        backButton.addActionListener(listener);
        backButton.addMouseListener(mouseListener);
        this.add(backButton, new Integer(0),-1);
        
    }
    
    private void loadMaps(){
        mapList = new ArrayList<ArrayList<Map>>();
        for(int i=2; i<11; i++){
            mapList.add(MapUtils.getAllMapsByNPlayers(i));
        }
        
        for(int i=0; i<mapList.size(); i++){
            if(mapList.get(i).size()>0){
                nextTab = i;
                break;
            }
        }
        nextSlot = 0;
        
        updateMaps();
    }
    
    public boolean isOnlineSelection(){
        return online;
    }
    
    public void setOnlineSelection(boolean value){
        online = value;
    }
    
    private void previousTab(){
        int tab;
        for(int i=currentTab-1; i>currentTab-9; i--){
            tab = i<0 ? i+9 : i;
            if(mapList.get(tab).size() > 0){
                nextTab = tab;
                nextSlot = 0;
                updateMaps();
                break;
            }
        }
    }
    
    private void nextTab(){
        int tab;
        for(int i=currentTab+1; i<currentTab+9; i++){
            tab = i%9;
            if(mapList.get(tab).size() > 0){
                nextTab = tab;
                nextSlot = 0;
                updateMaps();
                break;
            }
        }
    }
    
    private void previousSlot(){
        nextSlot = currentSlot-1 < 0 ? mapList.get(currentTab).size()-1 : currentSlot-1;
        updateMaps();
    }
    
    private void nextSlot(){
        nextSlot = (currentSlot+1)%mapList.get(currentTab).size();
        updateMaps();
    }
    
    private void updateMaps(){
        currentSlot = nextSlot;
        currentTab = nextTab;
        mapName.setText(mapList.get(currentTab).get(currentSlot).getName());
        nPlayersLabel.setText((currentTab+2)+" "+Localizer.translate("gui.MapSelectPanel.players"));
        mapPreview.setMap(mapList.get(currentTab).get(currentSlot));
    }
    
    private void update(){
        switch(currentElement){
            case 0:
                namePanel.setBorder(null);
                break;
            case 1:
                backButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/back_0.gif"));
                break;
        }
        currentElement = nextElement;
        switch(currentElement){
            case 0:
                namePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
                break;
            case 1:
                backButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/back_0_rollover.gif"));
                break;
        }
    }
    
    public void up(){
        previousSlot();
    }
    
    public void left(){
        previousTab();
    }
    
    public void right(){
        nextTab();
    }
    
    public void down(){
        nextSlot();
    }
    
    public void select(){
        if(currentElement == 1){
            back();
        }
        else{
            
        }
    }
    
    public void back(){
        listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, backButton.getActionCommand()));
    }
    
    public void switchTab(){
        nextElement = (currentElement+1)%2;
        update();
    }
    
    public void buttonPressed(String event){
        event = event.replace("mapSelectPanel.","");
        
        switch(event){
            case "up":
                up();
                break;
            case "left":
                left();
                break;
            case "right":
                right();
                break;
            case "down":
                down();
                break;
            case "back":
                back();
                break;
        }
    }
    
    public void localize(){
        nPlayersLabel.setText((currentTab+2)+" "+Localizer.translate("gui.MapSelectPanel.players"));
    }
    
    @Override
    public void setVisible(boolean aFlag){
        if(aFlag){
            loadMaps();
            nextElement = 0;
            update();
        }
        else{
            mapList = null;
        }
        super.setVisible(aFlag);
    }
    
    private class MapSelectPanelMouseEvent implements MouseListener {
        public void mousePressed(MouseEvent ev) {
        }

        public void mouseReleased(MouseEvent ev) {
            
        }

        public void mouseEntered(MouseEvent ev) {
            try{
                nextElement = Integer.parseInt(ev.getComponent().getName());
                update();
            }
            catch(NumberFormatException e){}
        }

        public void mouseExited(MouseEvent ev) {
            nextElement = -1;
            update();
        }

        public void mouseClicked(MouseEvent ev) {
        }
    }
}
