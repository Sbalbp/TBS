
package gui;

import game.settings.Settings;
import i18n.Localizer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import unit.Buff;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class BuffPanel extends JPanel{
    
    private int width = 126, height = 50;
    private Buff buff;
    private JLabel nameText;
    private JLabel remainingText;
    private JLabel icon;
    
    public BuffPanel(){
        this(0,0,126,50);
    }
    
    public BuffPanel(int x, int y, int setWidth, int setHeight){
        super();
        
        width = setWidth;
        height = setHeight;
        
        this.setLayout(null);
        this.setBounds(x,y,width,height);
        
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        
        nameText = new JLabel();
        nameText.setFont(nameText.getFont().deriveFont((float)10.0));
        nameText.setHorizontalAlignment(JLabel.CENTER);
        nameText.setBounds(30,0,width-32,16);
        this.add(nameText);
        
        remainingText = new JLabel();
        remainingText.setFont(remainingText.getFont().deriveFont((float)10.0));
        remainingText.setHorizontalAlignment(JLabel.CENTER);
        remainingText.setBounds(30,16,width-32,16);
        this.add(remainingText);
        
        icon = new JLabel();
        icon.setBounds(10,6,20,20);
        this.add(icon);
        
        /*descriptionText = new javax.swing.JTextArea();
        descriptionText.setFont(descriptionText.getFont().deriveFont((float)10.0));
        descriptionText.setEditable(false);
        descriptionText.setOpaque(false);
        descriptionText.setBounds(32,2,width-34,height-4);
        this.add(descriptionText);*/
    }
    
    public void setBuff(Buff newBuff){
        buff = newBuff;
        update();
    }
    
    public void update(){
        nameText.setText(Localizer.translate("unit.buff.name."+buff.getName()));
        remainingText.setText(Localizer.translate("gui.BuffPanel.remaining")+": "+(buff.getTotalTurns() > 0 ? buff.getTotalTurns()-buff.getTurns() : "-"));
        icon.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/"+buff.getDescription()+".gif"));
        //descriptionText.setText(Localizer.translate("unit.buff.description."+buff.getDescription()));
    }
    
    public void localize(){
        nameText.setText(Localizer.translate("unit.buff.name."+buff.getName()));
        remainingText.setText(Localizer.translate("gui.BuffPanel.remaining")+": "+(buff.getTotalTurns() > 0 ? buff.getTotalTurns()-buff.getTurns() : "-"));
    }
    
}
