
package gui;

import game.Game;
import i18n.Localizer;
import java.awt.Color;
import java.awt.Panel;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import unit.Unit;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class UnitInfoPanel extends Panel{
    
    private int width = 250, height = 600;
    private Unit unit;
    private HashMap elements;
    
    public UnitInfoPanel(int x, int y){
        super();
        
        elements = new HashMap();
        
        this.setLayout(null);
        this.setBounds(x,y,width,height);
        
        this.setBackground(Color.red);
        
        initElements();
    }
    
    private void initElements(){
        String[] generalKeys = new String[]{"unit","player","team"};
        
        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(null);
        imgPanel.setBounds(122,10,128,128);
        imgPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        
        elements.put("image", imgPanel);
        this.add(imgPanel);
        
        for(int i=0; i<3; i++){
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBounds(0,10+10+i*12+i*28,122,28);
            panel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        
            JLabel label1 = new JLabel();
            label1.setFont(label1.getFont().deriveFont((float)10.0));
            label1.setText(Localizer.translate("gui.UnitInfoPanel."+generalKeys[i])+":");
            label1.setBounds(3,0,122,12);
            panel.add(label1);
        
            JLabel label2 = new JLabel();
            label2.setFont(label2.getFont().deriveFont((float)10.0));
            label2.setText("");
            label2.setBounds(3,16,122,12);
            panel.add(label2);
            
            elements.put(generalKeys[i],label2);
            this.add(panel);
        }
    }
    
    public void setUnit(Unit newUnit){
        unit = newUnit;
        
        ((JLabel)elements.get("unit")).setText(Localizer.translate("unit."+unit.getName()));
        ((JLabel)elements.get("player")).setText(""+(int)unit.getStat(Unit.Stat.OWNER));
        ((JLabel)elements.get("team")).setText(""+Game.game.getTeam((int)unit.getStat(Unit.Stat.OWNER)));
    }
    
}
