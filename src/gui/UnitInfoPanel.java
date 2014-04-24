
package gui;

import game.Game;
import game.settings.Settings;
import i18n.Localizer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import unit.EquipmentType;
import unit.Unit;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class UnitInfoPanel extends JPanel{
    
    private int width = 250, height = 600;
    private int currentTab = 1, nextTab = 0, totalTabs=2;
    private Unit unit;
    private HashMap elements;
    private UnitInfoPanelButtonPressed buttonListener = new UnitInfoPanelButtonPressed();
    
    public UnitInfoPanel(int x, int y){
        super();
        
        elements = new HashMap();
        
        this.setLayout(null);
        this.setBounds(x,y,width,height);
        
        this.setBorder(javax.swing.BorderFactory.createMatteBorder(height, 0, 0, 0, new ImageIcon(Settings.get("assets.image.route")+"/tiles/tile0.gif")));
        
        initElements();
    }
    
    private void initElements(){
        String[] generalKeys = new String[]{"unit","player","team"},
                 icons = new String[]{"","","range","attack","defense","movement","climb","siege","morale"};
      
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
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(10,150,width-20,200);
        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        
        JLabel label = new JLabel();
        label.setFont(label.getFont().deriveFont((float)13.0));
        label.setBounds(5,5,width-25,15);
        
        elements.put("tabTitle", label);
        panel.add(label);
        
        JPanel stats = new JPanel();
        stats.setLayout(null);
        stats.setBounds(10,20,width-40,170);
        stats.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        elements.put("statsPanel", stats);
        panel.add(stats,new Integer(0),0);
        
        JPanel status = new JPanel();
        status.setLayout(null);
        status.setVisible(false);
        status.setBounds(10,20,width-40,170);
        status.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        elements.put("statusPanel", status);
        panel.add(status,new Integer(0),0);
        
        JButton button = new JButton();
        button.setBounds(80,170,30,15);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_left_1.gif"));
        button.setRolloverIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_left_1_rollover.gif"));
        button.setRolloverEnabled(true);
        button.setActionCommand("left");
        button.addActionListener(buttonListener);
        panel.add(button,new Integer(1),0);
        
        button = new JButton();
        button.setBounds(120,170,30,15);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_right_1.gif"));
        button.setRolloverIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_right_1_rollover.gif"));
        button.setRolloverEnabled(true);
        button.setActionCommand("left");
        button.addActionListener(buttonListener);
        panel.add(button,new Integer(1),0);
        
        label = new JLabel();
        label.setBounds(15,10,20,20);
        elements.put("damageIcon",label);
        stats.add(label);
        
        label = new JLabel();
        label.setFont(label.getFont().deriveFont((float)10.0));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        label.setBounds(40,13,95,16);
        elements.put("damageText",label);
        stats.add(label);
        
        label = new JLabel();
        label.setBounds(15,40,20,20);
        elements.put("armorIcon",label);
        stats.add(label);
        
        label = new JLabel();
        label.setFont(label.getFont().deriveFont((float)10.0));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        label.setBounds(40,43,95,16);
        elements.put("armorText",label);
        stats.add(label);
        
        for(int i=2; i<9; i++){
            int row = i/3, col = i%3;
            
            label = new JLabel();
            label.setBounds(15+65*col,29+46*row,20,20);
            label.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/"+icons[i]+".gif"));
            stats.add(label);
            
            label = new JLabel();
            label.setFont(label.getFont().deriveFont((float)10.0));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            label.setBounds(35+65*col,33+46*row,30,14);
            elements.put(icons[i],label);
            stats.add(label);
        }
        
        this.add(panel);
        
        updateTabs();
    }
    
    public void setUnit(Unit newUnit){
        unit = newUnit;
        
        if(unit != null){
            ((JLabel)elements.get("unit")).setText(Localizer.translate("unit."+unit.getName()));
            ((JLabel)elements.get("player")).setText(""+(int)unit.getStat(Unit.Stat.OWNER));
            ((JLabel)elements.get("team")).setText(""+Game.game.getTeam((int)unit.getStat(Unit.Stat.OWNER)));
            
            ((JLabel)elements.get("damageIcon")).setIcon(new ImageIcon(
                    Settings.get("assets.image.route")+"/icons/damage_"+((EquipmentType.DamageType)unit.getStat(Unit.Stat.DMGTYPE)).toString()+".gif"));
            ((JLabel)elements.get("damageText")).setText(
                    Localizer.translate("unit.damageType."+((EquipmentType.DamageType)unit.getStat(Unit.Stat.DMGTYPE)).toString().toLowerCase()));
            ((JLabel)elements.get("armorIcon")).setIcon(new ImageIcon(
                    Settings.get("assets.image.route")+"/icons/armor_"+((EquipmentType.ArmorType)unit.getStat(Unit.Stat.ARMTYPE)).toString()+".gif"));
            ((JLabel)elements.get("armorText")).setText(
                    Localizer.translate("unit.armorType."+((EquipmentType.ArmorType)unit.getStat(Unit.Stat.ARMTYPE)).toString().toLowerCase()));
            
            ((JLabel)elements.get("range")).setText((int)unit.getStat(Unit.Stat.MAXRANGE) == 1 ?
                    "-" : (int)unit.getStat(Unit.Stat.MINRANGE)+"-"+(int)unit.getStat(Unit.Stat.MAXRANGE));
            ((JLabel)elements.get("attack")).setText(""+(int)unit.getStat(Unit.Stat.ATTACK));
            ((JLabel)elements.get("defense")).setText(""+(int)unit.getStat(Unit.Stat.DEFENSE));
            ((JLabel)elements.get("movement")).setText(""+(int)unit.getStat(Unit.Stat.MOVEMENT));
            ((JLabel)elements.get("climb")).setText(""+(int)unit.getStat(Unit.Stat.CLIMB));
            ((JLabel)elements.get("siege")).setText(""+(int)unit.getStat(Unit.Stat.SIEGE));
            ((JLabel)elements.get("morale")).setText(""+(int)unit.getStat(Unit.Stat.MORALE));
        }
        else{
            ((JLabel)elements.get("unit")).setText("");
            ((JLabel)elements.get("player")).setText("");
            ((JLabel)elements.get("team")).setText("");
            
            ((JLabel)elements.get("damageIcon")).setIcon(null);
            ((JLabel)elements.get("damageText")).setText("");
            ((JLabel)elements.get("armorIcon")).setIcon(null);
            ((JLabel)elements.get("armorText")).setText("");
            
            ((JLabel)elements.get("range")).setText("");
            ((JLabel)elements.get("attack")).setText("");
            ((JLabel)elements.get("defense")).setText("");
            ((JLabel)elements.get("movement")).setText("");
            ((JLabel)elements.get("climb")).setText("");
            ((JLabel)elements.get("siege")).setText("");
            ((JLabel)elements.get("morale")).setText("");
        }
    }
    
    private void updateTabs(){
        String[] tabKeys = new String[]{"stats","status"};
        
        ((JPanel)elements.get(tabKeys[currentTab]+"Panel")).setVisible(false);
        currentTab = nextTab;
        ((JLabel)elements.get("tabTitle")).setText(Localizer.translate("gui.UnitInfoPanel."+tabKeys[currentTab]));
        ((JPanel)elements.get(tabKeys[currentTab]+"Panel")).setVisible(true);
    }
    
    public void nextTab(){
        nextTab = (currentTab+1)%totalTabs;
        updateTabs();
    }
    
    public void previousTab(){
        nextTab = currentTab-1 < 0 ? totalTabs-1 : currentTab-1;
        updateTabs();
    }
    
    private class UnitInfoPanelButtonPressed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ("left".equals(e.getActionCommand())) {
                previousTab();
            }
            if ("right".equals(e.getActionCommand())) {
                nextTab();
            }
        }
    }

}
