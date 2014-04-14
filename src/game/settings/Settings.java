
package game.settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Settings {
    
    private static Properties properties;
    
    private static KeySettings keySettings = new KeySettings();
    
    public static void loadSettings(){
        keySettings.loadSettings();
        
        properties = new Properties();
        try{
            FileInputStream in = new FileInputStream("config/settings.properties");
            properties.load(in);
            in.close();
        }catch(IOException e){}
    }
    
    public static void saveSettings(){
        keySettings.saveSettings();
        
        try{
            FileOutputStream out = new FileOutputStream("config/settings.properties");
            properties.store(out, null);
            out.close();
        }catch(IOException e){}
    }
    
    public static String get(String key){
        return properties.getProperty(key);
    }
    
    public static void set(String key, String value){
        properties.setProperty(key, value);
    }
    
    public static int getKey(String action){
        return keySettings.getKeyEvent(action);
    }
    
    public static void setKey(String action, String key){
        keySettings.setKeyEvent(action,key);
    }
}
