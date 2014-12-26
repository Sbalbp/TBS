
package utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class RegexpFilter implements FilenameFilter{
    
    public String regexp;
    
    public RegexpFilter(String regularExpression) {
        regexp = regularExpression;
    }
    
    public boolean accept(File dir, String name) {
        return name.matches(regexp);
    }
}
