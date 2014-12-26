
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import map.Map;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class MapPreviewPanel extends JPanel{
    
    private int height, width, squareHeight, squareWidth, offsetHeight, offsetWidth;
    private Color[] colours = new Color[]{Color.GRAY,Color.GREEN};
    private Map map;
    
    public MapPreviewPanel(int x, int y, int nWidth, int nHeight){
        super();
        setBounds(x,y,nWidth,nHeight);
        width = nWidth;
        height = nHeight;
    }
    
    public void setMap(Map newMap){
        map = newMap;
        squareHeight = height/map.getRows();
        squareWidth = width/map.getColumns();
        offsetHeight = (height-squareHeight*map.getRows())/2;
        offsetWidth = (width-squareWidth*map.getColumns())/2;
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.white);
        g2d.fillRect(0,0,width,height);
        
        if(map != null){
            for(int i=0; i<map.getRows(); i++){
                for(int j=0; j<map.getColumns(); j++){
                    g2d.setColor(colours[map.getSquare(i, j).getTextureNumber()]);
                    g2d.fillRect(offsetWidth+squareWidth*j,offsetHeight+squareHeight*i,squareWidth,squareHeight);
                }
            }
        }
        else{
            g2d.setColor(Color.white);
            g2d.fillRect(0,0,width,height);
        }
    }
}
