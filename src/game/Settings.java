
package game;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Settings {
    
    public static String language = "en";
    public static String country = "EN";
    
    private static String assetsRoute = "assets";
    
    private static String getAssetsRoute(){
        return assetsRoute;
    }
    
    public static String getImgRoute(){
        return getAssetsRoute()+"/img";
    }
    
    public static String getMapsRoute(){
        return getAssetsRoute()+"/maps";
    }
}
