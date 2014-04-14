
package game.settings;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class KeySettings {
    
    private Properties keyBindings;
    
    public KeySettings(){
    }
    
    private int getKeyEquivalence(String code){
        switch(code){
            case "w":
                return KeyEvent.VK_W;
            case "a":
                return KeyEvent.VK_A;
            case "s":
                return KeyEvent.VK_S;
            case "d":
                return KeyEvent.VK_D;
            case "enter":
                return KeyEvent.VK_ENTER;
            case "backspace":
                return KeyEvent.VK_BACK_SPACE;
            default:
                return -1;
        }
    }
    
    public void loadSettings(){
        keyBindings = new Properties();
        try{
            FileInputStream in = new FileInputStream("config/control_settings.properties");
            keyBindings.load(in);
            in.close();
        }catch(IOException e){}
    }
    
    public void saveSettings(){
        try{
            FileOutputStream out = new FileOutputStream("config/control_settings.properties");
            keyBindings.store(out, null);
            out.close();
        }catch(IOException e){}
    }
    
    public int getKeyEvent(String action){
        return getKeyEquivalence(keyBindings.getProperty(action));
    }
    
    public void setKeyEvent(String key, String value){
        keyBindings.setProperty(key, value);
    }
}
