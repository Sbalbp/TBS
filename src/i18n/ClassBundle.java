
package i18n;

import java.util.ListResourceBundle;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class ClassBundle extends ListResourceBundle{
    
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            { "game.frameName"   , "Turn Based Strategy Game" }
    };
    
}
