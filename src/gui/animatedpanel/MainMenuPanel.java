
package gui.animatedpanel;

import gui.KeyInteractive;
import gui.MenuButtonsPanel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class MainMenuPanel extends AnimatedPanel implements KeyInteractive{
    private int width = 700, height = 700, rows = 3, columns = 3, buttonW = 128, buttonH = 128;
    int r=100,g=80,b=200, rD = 1, gD = 0, bD = 0;
    private MenuButtonsPanel buttonsPanel;
    
    public MainMenuPanel(ActionListener buttonListener){
        this.setLayout(null);
        buttonsPanel = new MenuButtonsPanel(buttonListener);
        
        Tree t = new Tree("mainmenu");
        t.getRoot().addChild("singleplayer");
        t.getRoot().addChild("multiplayer");
        t.getRoot().addChild("settings");
        t.getRoot().addChild("quit");
        t.getRoot().getChildAt(1).addChild("multijoin");
        t.getRoot().getChildAt(1).addChild("multihost");
        t.getRoot().getChildAt(2).addChild("language");

        buttonsPanel.setLayout(null);
        buttonsPanel.setBounds(215,0,300,700);
        buttonsPanel.setMenuTree(t);
        buttonsPanel.setVisible(true);
        this.add(buttonsPanel);
        
    }
    
    public void up(){
        buttonsPanel.up();
    }
    
    public void left(){
        buttonsPanel.left();
    }
    
    public void right(){
        buttonsPanel.right();
    }
    
    public void down(){
        buttonsPanel.down();
    }
    
    public void select(){
        buttonsPanel.select();
    }
    
    public void back(){
        buttonsPanel.back();
    }
    
    public void switchTab(){
        buttonsPanel.switchTab();
    }
    
    public void buttonPressed(String event){
        buttonsPanel.buttonPressed(event);
    }
    
    public void localize(){
        buttonsPanel.localize();
    }
    
    protected void update(){
        if(rD == 1){
            r++;
        }
        else{
            r--;
        }
        
        if(gD == 1){
            g++;
        }
        else{
            g--;
        }
        
        if(bD == 1){
            b++;
        }
        else{
            b--;
        }
        
        if(r>=255){
            rD = 0;
        }
        if(r<=0){
            rD=1;
        }
        
        if(g>=255){
            gD = 0;
        }
        if(g<=0){
            gD=1;
        }
        
        if(b>=255){
            bD = 0;
        }
        if(b<=0){
            bD=1;
        }
    }
    
    protected void render(){
        try {
            copyOfImage =  new BufferedImage(730, 700, BufferedImage.TYPE_INT_RGB);
            g2D = copyOfImage.createGraphics();
                    
            g2D.setColor(new Color(r,g,b));
            g2D.fillRect(0,0,730,700);
            
            this.paintComponents(g2D);
            
        }catch(Exception e){}
    };
    
    public class Tree<T> {
        private Node<T> root;

        public Tree(T rootData) {
            root = new Node<T>();
            root.data = rootData;
            root.parent = null;
            root.children = new ArrayList<Node<T>>();
        }
        
        public Node<T> getRoot(){
            return root;
        }
        
        public void print(){
            root.print();
        }

        public class Node<T> {
            private T data;
            private Node<T> parent;
            private ArrayList<Node<T>> children;
            
            public T getData(){
                return data;
            }
            
            public Node<T> getParent(){
                return parent;
            }
            
            public Node<T> getChildAt(int index){
                return children.get(index);
            }
            
            public Node<T> getChildByValue(T value){
                if(!this.isLeaf()){
                    for(int i=0; i<children.size(); i++){
                        if(children.get(i).getData().equals(value)){
                            return children.get(i);
                        }
                    }
                }
                
                return null;
            }
            
            public ArrayList<Node<T>> getChildren(){
                return children;
            }
            
            public boolean isRoot(){
                return parent == null;
            }
            
            public boolean isLeaf(){
                return children.size() <= 0;
            }
            
            public void addChild(T childData){
                Node<T> child = new Node<T>();
                child.data = childData;
                child.parent = this;
                child.children = new ArrayList<Node<T>>();
                children.add(child);
            }
            
            public void print(){
                System.out.println(data);
                for(int i=0; i<children.size(); i++){
                    children.get(i).print();
                }
            }
        }
    }
}
