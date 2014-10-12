
package gui;

import connection.ClientThread.CurrentState;
import game.Game;
import game.settings.Settings;
import i18n.Localizer;
import java.awt.Color;
import java.awt.Dimension;
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

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class GameListPanel extends JLayeredPane implements KeyInteractive{
    private int width = 730, height = 700;
    private int currentTab = 0, nextTab = 0;
    private boolean serverSelect = false, connecting = false;;
    private String server = null;
    private JButton serverButton, backButton;
    private JLabel serverName;
    private JPanel gamesContainer;
    private ArrayList<HostedGamePanel> gamePanels;
    private JScrollPane scroller;
    private ServerSelectPanel serverSelectPanel;
    private ActionListener listener;
    private HostGamePanelMouseEvent mouseListener = new HostGamePanelMouseEvent();
    
    public GameListPanel(ActionListener buttonListener){
        super();
        
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setOpaque(true);
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        listener = buttonListener;
        
        serverName = new JLabel();
        setServerName();
        serverName.setHorizontalAlignment(SwingConstants.LEFT);
        serverName.setFont(serverName.getFont().deriveFont((float)16.0));
        serverName.setForeground(Color.WHITE);
        serverName.setBounds((int)(width*0.3+70),22,(int)(width*0.7-70),60);
        this.add(serverName, new Integer(0),-1);
        
        serverButton = new JButton();
        serverButton.setName("0");
        serverButton.setBorderPainted(false);
        serverButton.setContentAreaFilled(false);
        serverButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/switch_0.gif"));
        serverButton.setBounds((int)(width*0.3),22,60,60);
        serverButton.setActionCommand("joinGamePanel.server");
        serverButton.addMouseListener(mouseListener);
        serverButton.addActionListener(listener);
        this.add(serverButton, new Integer(0),-1);
                
        backButton = new JButton();
        backButton.setName("2");
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/back_0.gif"));
        backButton.setBounds(width-120,height-80,100,60);
        backButton.setActionCommand("joinGamePanel.back");
        backButton.addMouseListener(mouseListener);
        backButton.addActionListener(listener);
        this.add(backButton, new Integer(0),-1);
        
        scroller = new JScrollPane();
        scroller.setName("1");
        scroller.setBounds((int)(width*0.1/2),(int)(height*0.15),(int)(width*0.9),(int)(height*0.7));
        scroller.addMouseListener(mouseListener);
        
        gamesContainer = new JPanel();
        gamesContainer.setLayout(null);
        gamesContainer.setBackground(Color.GREEN);
        gamesContainer.setPreferredSize(new Dimension((int)(width*0.9)-12,0));//(int)(height*0.75)-12));
        
        gamePanels = new ArrayList<HostedGamePanel>();
        
        scroller.setViewportView(gamesContainer);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scroller, new Integer(0), -1);
        
        serverSelectPanel = new ServerSelectPanel(listener);
        serverSelectPanel.setOpaque(true);
        serverSelectPanel.setVisible(false);
        serverSelectPanel.setBounds((width-serverSelectPanel.getWidth())/2,(height-serverSelectPanel.getHeight())/2,serverSelectPanel.getWidth(),serverSelectPanel.getHeight());
        this.add(serverSelectPanel, new Integer(1), -1);
        
        update();
    }
    
    private void setServerName(){
        serverName.setText(Localizer.translate("gui.HostGamePanel.server")+": "+(server == null ? (" "+Localizer.translate("gui.HostGamePanel.noserver")) : server));
    }
    
    private void update(){
        switch(currentTab){
            case 0:
                serverButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/switch_0.gif"));
                break;
            case 1:
                scroller.setBorder(null);
                break;
            case 2:
                backButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/back_0.gif"));
                break;
        }
        currentTab = nextTab;
        switch(currentTab){
            case 0:
                serverButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/switch_0_rollover.gif"));
                break;
            case 1:
                scroller.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5,5, 5, Color.YELLOW));
                break;
            case 2:
                backButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/back_0_rollover.gif"));
                break;
        }
    }
    
    public void addGames(ArrayList<Integer> games){
        for(int i=0; i<games.size(); i++){
            HostedGamePanel p = new HostedGamePanel();
            p.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            p.setBounds(0,60*gamePanels.size(),(int)(width*0.9-12),60);
            gamesContainer.add(p);
            gamePanels.add(p);
        }

        gamesContainer.setPreferredSize(new Dimension((int)(width*0.9)-12,60*gamePanels.size()));
        scroller.setViewportView(gamesContainer);
    }
    
    public boolean selectingServer(){
        return serverSelect;
    }
    
    public void showSelectServerPanel(){
        serverSelectPanel.setVisible(true);
        serverSelect = true;
        serverSelectPanel.focusOnText();
    }
    
    public void hideSelectServerPanel(){
        serverSelectPanel.setVisible(false);
        serverSelect = false;
        nextTab = -1;
        update();
    }
    
    public void serverSelected(){
        connecting = true;
        serverSelectPanel.showConnecting();
        Game.game.runClient(serverSelectPanel.getServerName());
        
        Thread t = new Thread() {
            public void run(){
                while(Game.game.getClientState() == CurrentState.CONNECTING){
                    try{
                        Thread.sleep(2000);
                    }
                    catch(Exception e){}
                }

                if(Game.game.getClientState() == CurrentState.RECEIVING_GAMES){
                    server = serverSelectPanel.getServerName();
                    setServerName();
                    hideSelectServerPanel();
                }
                else{
                    serverSelectPanel.showConnectionFail();
                }
                
                connecting = false;
            }
        };
        t.start();
    }
    
    public void up(){
    }
    
    public void left(){

    }
    
    public void right(){

    }
    
    public void down(){
    }
    
    public void select(){
        if(!serverSelect){
            switch(currentTab){
                case 0:
                    listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, serverButton.getActionCommand()));
                    break;
                case 1:
                    break;
                case 2:
                    listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, backButton.getActionCommand()));
                    break;
            }
        }
        else{
            if(!connecting){
                listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, serverSelectPanel.getButtonCommand()));
            }
        }
    }
    
    public void back(){
        if(serverSelect){
            if(!connecting){
                hideSelectServerPanel();
            }
        }
        else{
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, backButton.getActionCommand()));
        }
    }
    
    public void switchTab(){
        if(!serverSelect){
            nextTab = (currentTab+1)%3;
            update();
        }
    }
    
    public void buttonPressed(String event){
        event = event.replace("joinGamePanel.","");
        
        switch(event){
            case "server":
                serverSelectPanel.setServerName(server);
                serverSelectPanel.showConnectForm();
                showSelectServerPanel();
                break;
            case "back":
                if(serverSelect && !connecting){
                    hideSelectServerPanel();
                }
                else{
                    back();
                }
                break;
            case "setServer":
                if(serverSelect && !connecting){
                    serverSelected();
                }
                break;
            case "failedOk":
                if(serverSelect && !connecting){
                    serverSelectPanel.showConnectForm();
                }
                break;
        }
    }
    
    public void localize(){
        setServerName();
        serverSelectPanel.setServerName(server);
        serverSelectPanel.localize();
    }
    
    private class HostGamePanelMouseEvent implements MouseListener {
        public void mousePressed(MouseEvent ev) {
        }

        public void mouseReleased(MouseEvent ev) {
            
        }

        public void mouseEntered(MouseEvent ev) {
            if(!serverSelect){
                try{
                    nextTab = Integer.parseInt(ev.getComponent().getName());
                    update();
                }
                catch(NumberFormatException e){}
            }
        }

        public void mouseExited(MouseEvent ev) {
            if(!serverSelect){
                nextTab = -1;
                update();
            }
        }

        public void mouseClicked(MouseEvent ev) {
        }
    }
    
    public class ReceiveGamesThread extends Thread{
        private boolean run;
        
        public ReceiveGamesThread(){
            run = true;
        }
        
        public void run(){
            while(run){
                try{
                    Thread.sleep(3000);
                }catch(InterruptedException e){}
                
                ArrayList<Integer> a = new ArrayList<Integer>();
                a.add(0);
                a.add(1);
                addGames(a);
            }
        }
    }
}
