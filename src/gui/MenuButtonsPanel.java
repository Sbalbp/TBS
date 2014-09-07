
package gui;

import game.settings.Settings;
import gui.animatedpanel.MainMenuPanel;
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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class MenuButtonsPanel extends JPanel implements KeyInteractive{
    private int currentSlot=0, nextSlot=0, childrenSize;
    private MainMenuPanel.Tree menuTree;
    private MainMenuPanel.Tree.Node currentNode;
    private JLabel menuName;
    private JPanel buttonsPanel;
    private ArrayList<JButton> buttons;
    private double buttonRelativeWidth = 0.7; 
    private ActionListener listener;
    private MenuButtonsPanelMouseEvent mouseListener = new MenuButtonsPanelMouseEvent();
    
    public MenuButtonsPanel(ActionListener buttonListener){
        super();
        
        listener = buttonListener;
        
        menuName = new JLabel();
        menuName.setFont(menuName.getFont().deriveFont((float)18.0));
        menuName.setHorizontalAlignment(SwingConstants.CENTER);
        menuName.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        this.add(menuName);  
        
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(null);
        buttonsPanel.setBackground(new Color(0,0,0,0));
        this.add(buttonsPanel);
    }
    
    private void update(){
        buttons.get(currentSlot).setBorderPainted(false);
        currentSlot = nextSlot;
        buttons.get(currentSlot).setBorderPainted(true);
    }
    
    private void setComponents(){
        ArrayList<MainMenuPanel.Tree.Node> children;
        int i;
        int width = this.getWidth(), height = this.getHeight();
        int buttonW = (int)(width*buttonRelativeWidth), buttonH, buttonSeparation, maxH = (int)(buttonW*0.3);
        
        if(currentNode != null){
            children = currentNode.getChildren();
            buttons = new ArrayList<JButton>();
            
            childrenSize = children.size();
            if(!currentNode.isRoot()){
                childrenSize++;
            }
            
            menuName.setBounds((int)(width*0.2),(int)(height*0.05),(int)(width*0.6),(int)(height*0.1));
            menuName.setText(Localizer.translate("gui.MenuButtonsPanel."+(String)currentNode.getData()));
            
            buttonsPanel.setBounds(0,(int)(height*0.2),width,(int)(height*0.8));
            buttonsPanel.removeAll();
            
            buttonH = (int)(height*0.6/childrenSize);
            if(buttonH > maxH){
                buttonH = maxH;
            }
            buttonSeparation = (int)((height*0.8-(buttonH*(childrenSize)))/(childrenSize+1));
            for(i=0; i<children.size(); i++){
                JButton button = new JButton();
                button.setBounds((int)(width*(1-buttonRelativeWidth)/2),buttonSeparation+i*(buttonH+buttonSeparation),buttonW, buttonH);
                button.setText(Localizer.translate("gui.MenuButtonsPanel."+(String)children.get(i).getData()));
                button.setFocusable(false);
                button.setBorder(javax.swing.BorderFactory.createMatteBorder((int)(buttonH*0.15), (int)(buttonH*0.15), (int)(buttonH*0.15), (int)(buttonH*0.15), new ImageIcon(Settings.get("assets.image.route")+"/tiles/tile1.gif")));
                button.setBorderPainted(false);
                button.setActionCommand("menuButtonsPanel."+((String)children.get(i).getData()).replace("gui.MenuButtonsPanel.",""));
                button.setName(""+i);
                button.addActionListener(listener);
                button.addMouseListener(mouseListener);
                buttons.add(button);
                buttonsPanel.add(button);
            }
            
            if(!currentNode.isRoot()){
                JButton button = new JButton();
                button.setBounds((int)(width*(1-buttonRelativeWidth)/2),buttonSeparation+i*(buttonH+buttonSeparation),buttonW, buttonH);
                button.setText(Localizer.translate("gui.MenuButtonsPanel.back"));
                button.setFocusable(false);
                button.setBorder(javax.swing.BorderFactory.createMatteBorder((int)(buttonH*0.15), (int)(buttonH*0.15), (int)(buttonH*0.15), (int)(buttonH*0.15), new ImageIcon(Settings.get("assets.image.route")+"/tiles/tile1.gif")));
                button.setBorderPainted(false);
                button.setActionCommand("menuButtonsPanel.back");
                button.setName(""+i);
                button.addActionListener(listener);
                button.addMouseListener(mouseListener);
                buttons.add(button);
                buttonsPanel.add(button);
            }
        }
        
        this.setBorder(javax.swing.BorderFactory.createMatteBorder(height, 0, 0, 0, new ImageIcon(Settings.get("assets.image.route")+"/tiles/tile0.gif")));

        currentSlot = nextSlot = 0;
        update();
    }
    
    public void setMenuTree(MainMenuPanel.Tree newTree){
        menuTree = newTree;
        currentNode = menuTree.getRoot();
        setComponents();
    }
    
    public void buttonPressed(String event){
        event = event.replace("menuButtonsPanel.","");
        
        switch(event){
            case "back":
                if(!currentNode.isRoot()){
                    currentNode = currentNode.getParent();
                    setComponents();
                }
                break;
            default:
                if(!currentNode.getChildByValue(event).isLeaf()){
                    currentNode = currentNode.getChildByValue(event);
                    setComponents();
                }
        }
    }
    
    private class MenuButtonsPanelMouseEvent implements MouseListener {
        public void mousePressed(MouseEvent ev) {
        }

        public void mouseReleased(MouseEvent ev) {
            
        }

        public void mouseEntered(MouseEvent ev) {
            
            try{
                nextSlot = Integer.parseInt(ev.getComponent().getName());
                update();
            }
            catch(NumberFormatException e){}
        }

        public void mouseExited(MouseEvent ev) {
        }

        public void mouseClicked(MouseEvent ev) {
        }
    }
    
    public void up(){
        nextSlot = currentSlot-1;
        if(nextSlot < 0){
            nextSlot = childrenSize-1;
        }
        update();
    }
    
    public void left(){

    }
    
    public void right(){

    }
    
    public void down(){
        nextSlot = (currentSlot+1)%childrenSize;
        update();
    }
    
    public void select(){
        listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, buttons.get(currentSlot).getActionCommand()));
    }
    
    public void back(){
        listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "menuButtonsPanel.back"));
    }
    
    public void localize(){
        int i;
        ArrayList<MainMenuPanel.Tree.Node> children;
        
        menuName.setText(Localizer.translate("gui.MenuButtonsPanel."+(String)currentNode.getData()));
    
        children = currentNode.getChildren();
        for(i=0; i<children.size(); i++){
            buttons.get(i).setText(Localizer.translate("gui.MenuButtonsPanel."+(String)children.get(i).getData()));
        }
        if(!currentNode.isRoot()){
            buttons.get(i).setText(Localizer.translate("gui.MenuButtonsPanel.back"));
        }
        
    }   
}
