
package gui;

import game.Game;
import i18n.Localizer;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import unit.*;
import utils.AssetsManager;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class UnitCreatePanel extends Panel{
    
    private int width = 250, height = 320;
    private int slots = 6, slotHeight = (height-20)/slots;
    private int currentSlot, lastSlot, currentPage, lastPage;
    private String[] units = {"footman","archer","footman","footman","footman","footman","footman"};
    private int[] costs = {100,140,2000,100,100,100,100};
    private JPanel[] unitPanel;
    private JLabel[] iconLabel, nameLabel, priceLabel;
    private JLabel page;
    
    public UnitCreatePanel(int fatherWidth, int fatherHeight){
        super();

        currentSlot = 0;
        currentPage = 0;
        lastPage = (units.length-1)/slots;
        lastSlot = currentPage < units.length/slots ? slots-1 : (units.length%slots)-1;
        
        this.setLayout(null);
        this.setBounds(((fatherWidth-width)/2),((fatherHeight-height)/2),width,height);
        this.setVisible(false);
        
        initElements();
        update();
    }
    
    private void initElements(){
        
        unitPanel = new JPanel[slots];
        iconLabel = new JLabel[slots];
        nameLabel = new JLabel[slots];
        priceLabel = new JLabel[slots];
        
        for(int i=0; i<slots; i++){
            unitPanel[i] = new JPanel();
            unitPanel[i].setLayout(null);
            unitPanel[i].setBounds(0,slotHeight*i,width,slotHeight);
            unitPanel[i].setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            
            iconLabel[i] = new JLabel();
            iconLabel[i].setLayout(null);
            iconLabel[i].setBounds(10,(slotHeight-32)/2,32,32);
            iconLabel[i].setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            unitPanel[i].add(iconLabel[i]);
            
            nameLabel[i] = new JLabel();
            nameLabel[i].setLayout(null);
            nameLabel[i].setBounds(50,(slotHeight-20)/2,120,20);
            nameLabel[i].setFont(nameLabel[i].getFont().deriveFont((float)10.0));
            nameLabel[i].setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            unitPanel[i].add(nameLabel[i]);
            
            priceLabel[i] = new JLabel();
            priceLabel[i].setLayout(null);
            priceLabel[i].setBounds(180,(slotHeight-20)/2,60,20);
            priceLabel[i].setFont(priceLabel[i].getFont().deriveFont((float)10.0));
            priceLabel[i].setHorizontalAlignment(SwingConstants.RIGHT);
            priceLabel[i].setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            unitPanel[i].add(priceLabel[i]);
            
            this.add(unitPanel[i]);
        }
        
        page = new JLabel();
        page.setLayout(null);
        page.setBounds(0,height-20,width,20);
        page.setFont(page.getFont().deriveFont((float)12.0));
        page.setHorizontalAlignment(SwingConstants.CENTER);
        page.setBorder(javax.swing.BorderFactory.createLineBorder(Color.white));
        this.add(page);
            
    }
    
    private void update(){
        int it;
        
        for(it=0; it<=lastSlot; it++){
            iconLabel[it].setIcon(AssetsManager.getImageIcon("/unit/"+units[currentPage*slots+it]+"/0/idle_0","png"));
            iconLabel[it].setVisible(true);
            
            nameLabel[it].setText(Localizer.translate("unit."+units[currentPage*slots+it]));
            nameLabel[it].setVisible(true);
            
            priceLabel[it].setText(""+costs[currentPage*slots+it]);
            priceLabel[it].setVisible(true);
            
            if(it == currentSlot){
                unitPanel[it].setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            }
            else{
                unitPanel[it].setBorder(null);
            }
        }
        for(;it<slots; it++){
            iconLabel[it].setVisible(false);
            nameLabel[it].setVisible(false);
            priceLabel[it].setVisible(false);
            unitPanel[it].setBorder(null);
        }
        
        page.setText((currentPage+1)+"/"+(lastPage+1));

    }
    
    public void up(){
        currentSlot = currentSlot == 0 ? lastSlot : currentSlot-1;
        update();
    }
    
    public void left(){
        currentPage = currentPage == 0 ? lastPage : currentPage-1;
        lastSlot = currentPage < units.length/slots ? slots-1 : (units.length%slots)-1;
        if(currentSlot > lastSlot){
            currentSlot = lastSlot;
        }
        update();
    }
    
    public void right(){
        currentPage = (currentPage+1)%(lastPage+1);
        lastSlot = currentPage < units.length/slots ? slots-1 : (units.length%slots)-1;
        if(currentSlot > lastSlot){
            currentSlot = lastSlot;
        }
        update();
    }
    
    public void down(){
        currentSlot = (currentSlot+1)%(lastSlot+1);
        update();
    }
    
    public void setVisible(boolean b){
        if(b){
            currentSlot = 0;
            currentPage = 0;
            lastPage = (units.length-1)/slots;
            lastSlot = currentPage < units.length/slots ? slots-1 : (units.length%slots)-1;
            update();
        }
        super.setVisible(b);
    }
    
    public Unit select(){
        // Has enough gold
        if(true){
            switch(units[currentPage*slots+currentSlot]){
                case "footman":
                    return new Footman(Game.game.getTurn());
                case "archer":
                    return new Archer(Game.game.getTurn());
                default:
                    return null;
            }
        }
        else{
            return null;
        }
    }
    
    public void localize(){
        for(int it = 0; it<lastSlot; it++){
            nameLabel[it].setText(Localizer.translate("unit."+units[currentPage*slots+it]));
        }
    }
    
}
