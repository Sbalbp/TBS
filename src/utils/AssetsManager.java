
package utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import game.Settings;
import javax.swing.ImageIcon;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class AssetsManager {
    
    private static String createFullPath(String path, String format){
        return Settings.getImgRoute()+path+"."+format;
    }
    
    public static BufferedImage getBufferedImage(String imagePath, String format){
        try{
            return ImageIO.read(new File(createFullPath(imagePath, format)));
        }catch(Exception e){
            return null;
        }
    }
    
    public static ImageIcon getImageIcon(String imagePath, String format){
        try{
            return new ImageIcon(createFullPath(imagePath, format));
        }catch(Exception e){
            return null;
        }
    }
    
}
