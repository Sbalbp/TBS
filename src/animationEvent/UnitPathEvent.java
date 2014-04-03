
package animationEvent;

import game.Game;
import java.util.ArrayList;
import map.Map;
import unit.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class UnitPathEvent extends AnimationEvent{
    
    private Unit unit;
    private ArrayList <ArrayList <Integer>> path;
    private Map map;
    private int index;
    private int currentIncrease;
    private int stepX, stepY;
    
    public UnitPathEvent(Unit newUnit, ArrayList <ArrayList <Integer>> newPath, Map newMap){
        unit = newUnit;
        path = newPath;
        map = newMap;
        index = 0;
    }
    
    private void setStep(){
        if(path.size() > index+1){
            currentIncrease = 0;
            
            stepX = path.get(index+1).get(1)-path.get(index).get(1);
            stepY = path.get(index+1).get(0)-path.get(index).get(0);
            
            unit.setAnimation(stepX==0 ? stepY > 0 ? "down" : "up" : stepX > 0 ? "right" : "left");
        }
        else{
            unit.setSquare(map.getSquare(path.get(index).get(0),path.get(index).get(1)));
            unit.setXOffset(0);
            unit.setYOffset(0);
            finish();
            Game.game.getFrame().getMapPanel().setCursor(unit.getSquare().getRow(),unit.getSquare().getColumn(),false);
        }
    }
    
    public void set(){
        Game.game.getFrame().getMapPanel().setCursor(unit.getSquare().getRow(),unit.getSquare().getColumn(),true);
        setStep();
    }
    
    public void update(){
        
        if(currentIncrease == 32){
            index++;
            setStep();
        }
        else{
            currentIncrease+=2;
            unit.setXOffset(unit.getXOffset()+stepX*2);
            unit.setYOffset(unit.getYOffset()+stepY*2);
        }
    }
    
}
