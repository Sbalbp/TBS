
package gui.animatedpanel;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public abstract class AnimatedPanel extends JPanel implements Runnable{
    
    protected Graphics g2D;
    protected BufferedImage copyOfImage = new BufferedImage(480, 480, BufferedImage.TYPE_INT_RGB);
    protected boolean end;
    
    public void start(){
        end = false;
    }
    
    public void stop(){
        end = true;
    }
      
    private void paintScreen(){ 
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (copyOfImage != null)){
                g.drawImage(copyOfImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit( ).sync( );
            g.dispose( );
        }
        catch (Exception e){
            System.out.println("Graphics context error: " + e);
        }
    }
    
    public void run(){
          
         while (!end){
             if(this.isVisible()){
                update();
                render(); 
                paintScreen();
                
                try {
                    Thread.sleep(20);
                }
                catch(InterruptedException ex){}
             }
             else{
                try {
                    Thread.sleep(100);
                }
                catch(InterruptedException ex){}
             } 
         }
    }
    
    protected abstract void update();
    protected abstract void render();
    
}
