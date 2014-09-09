
package gui;

import game.settings.Settings;
import i18n.Localizer;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class ServerSelectPanel extends JLayeredPane implements KeyInteractive{
    private int width = 400, height = 200;
    private JTextField serverTextField;
    private JButton confirmButton;
    
    public ServerSelectPanel(ActionListener listener){
        super();
        
        this.setLayout(null);
        this.setBounds(0,0,width,height);
        this.setBorder(javax.swing.BorderFactory.createMatteBorder(height, 0, 0, 0, new ImageIcon(Settings.get("assets.image.route")+"/tiles/tile1.gif")));
        
        serverTextField = new JTextField();
        serverTextField.setHorizontalAlignment(SwingConstants.CENTER);
        serverTextField.setFont(serverTextField.getFont().deriveFont((float)14.0));
        serverTextField.setBounds((width-350)/2,20,350,40);
        this.add(serverTextField);
        
        confirmButton = new JButton();
        confirmButton.setBorderPainted(false);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setRolloverIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/ok_0_rollover.gif"));
        confirmButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/ok_0.gif"));
        confirmButton.setBounds((width-60)/2,height-80,60,60);
        confirmButton.setActionCommand("joinGamePanel.setServer");
        confirmButton.addActionListener(listener);
        this.add(confirmButton, new Integer(0),-1);
    }
    
    public String getServerName(){
        return serverTextField.getText();
    }
    
    public void setServerName(String str){
        if(str != null){
            serverTextField.setText(str);
        }
        else{
            serverTextField.setText(Localizer.translate("gui.HostGamePanel.input"));
        }
    }
    
    public void focusOnText(){
        serverTextField.requestFocus();
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
        
    }
    
    public void back(){
        
    }
    
    public void switchTab(){
        
    }

}
