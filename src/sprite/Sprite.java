
package sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Sprite {
    
    private HashMap spriteSets;
    
    public Sprite(){
        spriteSets = new HashMap();
    }
    
    public void addAnimationSeparate(String name, String path, String format){
        int index = 0;
        ArrayList <BufferedImage> images = new ArrayList <BufferedImage>();
        
        while(new File(path+"_"+index+"."+format).isFile()){
            try{
                images.add(ImageIO.read(new File(path+"_"+index+"."+format)));
            }catch(java.io.IOException e){}
            index++;
        }
        
        if(images.size() > 0){
            spriteSets.put(name, images);
        }
    }
    
    public void addAnimationCopy(String name, String from){
        ArrayList <BufferedImage> images = new ArrayList <BufferedImage>();
        ArrayList <BufferedImage> originalSet;
        BufferedImage original, newImage;
        
        if(spriteSets.containsKey(from)){
            originalSet = (ArrayList <BufferedImage>)spriteSets.get(from);
            for(int i=0; i<originalSet.size(); i++){
                original = originalSet.get(i);
                BufferedImage b = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
                newImage = new BufferedImage(original.getWidth(),original.getHeight(),original.getType());
                Graphics2D g = newImage.createGraphics();
                g.drawImage(original,0,0,null);
                g.dispose();
                
                images.add(newImage);
            }
            
            if(images.size() > 0){
                spriteSets.put(name, images);
            }
        }
    }
    
    /**
     * 
     * Code from: http://sanjaal.com/java/tag/flip-bufferedimage-in-java/
     * @param name
     * @param from 
     */
    public void addAnimationFlip(String name, String from){
        int w,h;
        ArrayList <BufferedImage> images = new ArrayList <BufferedImage>();
        ArrayList <BufferedImage> imagesToFlip;
        BufferedImage imageToFlip, flippedImage;
        
        if(spriteSets.containsKey(from)){
            imagesToFlip = (ArrayList <BufferedImage>)spriteSets.get(from);
            for(int i=0; i<imagesToFlip.size(); i++){
                imageToFlip = imagesToFlip.get(i);
                w = imageToFlip.getWidth();
                h = imageToFlip.getHeight();
                flippedImage = new BufferedImage(w,h,imageToFlip.getType());
                Graphics2D g = flippedImage.createGraphics();
                g.drawImage(imageToFlip,0,0,w,h,w,0,0,h,null);
                g.dispose();
                
                images.add(flippedImage);
            }
            
            if(images.size() > 0){
                spriteSets.put(name, images);
            }
        }
        
    }
    
    public int numFrames(String animationName){
        return spriteSets.containsKey(animationName) ? ((ArrayList <BufferedImage>)spriteSets.get(animationName)).size() : 0;
    }
    
    public BufferedImage getFrame(String animationName, int frameNumber){
        return spriteSets.containsKey(animationName) ? ((ArrayList <BufferedImage>)spriteSets.get(animationName)).get(frameNumber) : null;
    }

}
