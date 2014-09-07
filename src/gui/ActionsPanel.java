
package gui;

import i18n.Localizer;
import java.awt.Panel;
import java.util.HashMap;
import javax.swing.JLabel;
import map.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class ActionsPanel extends Panel{
    
    private int panelWidth = 50, slotHeight = 15;
    private View view;
    
    private HashMap labels, labelOrder, orderLabel;
    private int currentSlot, nextSlot, numSlots;
    
    private String[] labelNames = {"move", "attack", "wait", "ransack", "capture"};
    
    public ActionsPanel(View newView){
        
        setView(newView);
        
        this.setLayout(null);
        this.setBounds(0,0,panelWidth,slotHeight*5);
        
        labels = new HashMap();
        labelOrder = new HashMap();
        orderLabel = new HashMap();
        
        for(int i=0; i<labelNames.length; i++){
            JLabel label = new JLabel();
            label.setText(Localizer.translate("gui.ActionsPanel."+labelNames[i]));
            label.setFont(label.getFont().deriveFont((float)10.0));
            label.setBounds(0,slotHeight*i,panelWidth,slotHeight);
            this.add(label);
            labels.put(labelNames[i],label);
            labelOrder.put(labelNames[i],i);
            orderLabel.put(i,labelNames[i]);
        }
        
        numSlots = 5;
        
        currentSlot = 0;
        nextSlot = 0;
        
    }
    
    private void setView(View newView){
        view = newView;
    }
    
    private void swapLabels(String label1, String label2){
        if(!label1.equals(label2)){
            int p1 = (int)labelOrder.get(label1);
            int p2 = (int)labelOrder.get(label2);
        
            ((JLabel)labels.get(label1)).setBounds(0,slotHeight*p2,panelWidth,slotHeight);
            ((JLabel)labels.get(label2)).setBounds(0,slotHeight*p1,panelWidth,slotHeight);
        
            labelOrder.remove(label1);
            labelOrder.remove(label2);
            labelOrder.put(label1,p2);
            labelOrder.put(label2,p1);
        
            orderLabel.remove(p1);
            orderLabel.remove(p2);
            orderLabel.put(p1,label2);
            orderLabel.put(p2,label1);
        }
    }
    
    public void setActions(String[] actions){
        this.setBounds(0,0,panelWidth,slotHeight*actions.length);
        
        for(int i=0; i<actions.length; i++){
            swapLabels(actions[i],(String)orderLabel.get(i));
        }
        
        numSlots = actions.length;
        
        ((JLabel)labels.get((String)orderLabel.get(0))).setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        for(int i=1; i<numSlots; i++){
            ((JLabel)labels.get((String)orderLabel.get(i))).setBorder(null);
        }
        
        currentSlot = 0;
        nextSlot = 0;
    }
    
    public void setOnSquare(int row, int col, Map map){
        if(row > -1 && col > -1 && row < map.getRows() && col < map.getColumns() && map.getSquare(row,col).getUnit()!= null){
        
            int x = col > map.getColumns()/2 ? (col-view.getDisplayColumn())*32-panelWidth : (col-view.getDisplayColumn()+1)*32;
            int y = row > map.getRows()/2 ? (row-view.getDisplayRow()+1)*32-slotHeight*numSlots : (row-view.getDisplayRow())*32;
            
            this.setBounds(x,y,panelWidth,slotHeight*numSlots);
        }
    }
    
    private void update(){
        ((JLabel)labels.get((String)orderLabel.get(currentSlot))).setBorder(null);
        currentSlot = nextSlot;
        ((JLabel)labels.get((String)orderLabel.get(currentSlot))).setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
    }
    
    public void localize(){
        for(int i=0; i<labelNames.length; i++){
            ((JLabel)labels.get(labelNames[i])).setText(Localizer.translate("gui.ActionsPanel."+labelNames[i]));
        }
    }
    
    public void up(){
        nextSlot = currentSlot == 0 ? numSlots-1 : currentSlot-1;
        
        update();
    }
    
    public void down(){
        nextSlot = (currentSlot+1)%numSlots;
        
        update();
    }
    
    public String select(){
        return (String)orderLabel.get(currentSlot);
    }

}
