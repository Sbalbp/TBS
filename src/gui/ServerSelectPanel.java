
package gui;

import i18n.Localizer;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import utils.AssetsManager;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class ServerSelectPanel extends JLayeredPane implements KeyInteractive{
    private int width = 400, height = 200;
    private JTextField serverTextField;
    private JButton confirmButton, backButton;
    private JLabel connectionLabel;
    
    public ServerSelectPanel(ActionListener listener){
        super();
        
        this.setLayout(null);
        this.setBounds(0,0,width,height);
        this.setBorder(javax.swing.BorderFactory.createMatteBorder(height, 0, 0, 0, AssetsManager.getImageIcon("/tiles/tile1", "gif")));
        
        serverTextField = new JTextField();
        serverTextField.setHorizontalAlignment(SwingConstants.CENTER);
        serverTextField.setFont(serverTextField.getFont().deriveFont((float)14.0));
        serverTextField.setBounds((width-350)/2,20,350,40);
        this.add(serverTextField);
        
        confirmButton = new JButton();
        confirmButton.setBorderPainted(false);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setIcon(AssetsManager.getImageIcon("/icons/ok_0", "gif"));
        confirmButton.setRolloverIcon(AssetsManager.getImageIcon("/icons/ok_0_rollover", "gif"));
        confirmButton.setBounds((width-60)/2,height-80,60,60);
        confirmButton.setActionCommand("joinGamePanel.setServer");
        confirmButton.addActionListener(listener);
        this.add(confirmButton, new Integer(0),-1);
        
        backButton = new JButton();
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setIcon(AssetsManager.getImageIcon("/icons/back_0", "gif"));
        backButton.setRolloverIcon(AssetsManager.getImageIcon("/icons/back_0_rollover", "gif"));
        backButton.setBounds(width-100,height-60,100,60);
        backButton.setActionCommand("joinGamePanel.back");
        backButton.addActionListener(listener);
        this.add(backButton, new Integer(0),-1);
        
        connectionLabel = new JLabel();
        connectionLabel.setText(Localizer.translate("gui.HostGamePanel.connecting"));
        connectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        connectionLabel.setFont(connectionLabel.getFont().deriveFont((float)20.0));
        connectionLabel.setForeground(Color.WHITE);
        connectionLabel.setVisible(false);
        connectionLabel.setBounds(0,(int)(height/3),width,(int)(height/3));
        this.add(connectionLabel, new Integer(0),-1);
    }
    
    public String getButtonCommand(){
        return confirmButton.getActionCommand();
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
    
    public void localize(){
        connectionLabel.setText(Localizer.translate("gui.HostGamePanel.connecting"));
    }
    
    public void showConnectForm(){
        serverTextField.setVisible(true);
        backButton.setVisible(true);
        confirmButton.setVisible(true);
        confirmButton.setActionCommand("joinGamePanel.setServer");
        connectionLabel.setBounds(0,(int)(height/3),width,(int)(height/3));
        connectionLabel.setVisible(false);
    }
    
    public void showConnecting(){
        serverTextField.setVisible(false);
        backButton.setVisible(false);
        confirmButton.setVisible(false);
        connectionLabel.setVisible(true);
        connectionLabel.setText(Localizer.translate("gui.HostGamePanel.connecting"));
    }
    
    public void showConnectionFail(){
        connectionLabel.setText(Localizer.translate("gui.HostGamePanel.failedconnection"));
        confirmButton.setActionCommand("joinGamePanel.failedOk");
        connectionLabel.setBounds(0,(int)(height/6),width,(int)(height/3));
        backButton.setVisible(true);
        confirmButton.setVisible(true);
        connectionLabel.setVisible(true);
        serverTextField.setVisible(false);
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
