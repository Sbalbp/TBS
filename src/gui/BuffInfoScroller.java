
package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import unit.Buff;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class BuffInfoScroller extends JScrollPane{
    
    private int width = 140, height = 128, bPanelHeight = 32;
    private ArrayList<Buff> buffs;
    private JPanel buffsContainer;
    
    public BuffInfoScroller(){
        this(10,10,140,128);
    }
    
    public BuffInfoScroller(int x, int y, int setWidth, int setHeight){
        super();
        
        width = setWidth;
        height = setHeight;
        
        buffsContainer = new JPanel();
        buffsContainer.setLayout(null);
        buffsContainer.setPreferredSize(new Dimension(width-12,400));
        
        this.setViewportView(buffsContainer);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setBounds(x,y,width,height);
    }

    public void setBuffs(ArrayList <Buff> newBuffs){
        buffs = newBuffs;
        update();
    }
    
    public void update(){
        BuffPanel bPanel;
        
        buffsContainer.removeAll();
        buffsContainer.setPreferredSize(new Dimension(width-12,bPanelHeight*buffs.size()));
        
        for(int i=0; i<buffs.size(); i++){
            bPanel = new BuffPanel(0,bPanelHeight*i,width-14,bPanelHeight);
            bPanel.setBuff(buffs.get(i));
            buffsContainer.add(bPanel);
        }
    }
    
    public void localize(){
        if(buffsContainer.getComponentCount() > 0){
            ((BuffPanel)buffsContainer.getComponent(0)).localize();
        }
    }
    
}
