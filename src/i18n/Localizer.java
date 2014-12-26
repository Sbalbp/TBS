
package i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Localizer {
    
    private static ResourceBundle bundle;
    
    public static void setLanguage(String language){
        bundle = ResourceBundle.getBundle("i18n.ClassBundle", new Locale(language));
    }
    
    public static String translate(String key){
        if(bundle.containsKey(key)){
            return (String)bundle.getObject(key);
        }
        else{
            return key;
        }
    }
    
}
