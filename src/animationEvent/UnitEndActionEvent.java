
package animationEvent;

import unit.Unit;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class UnitEndActionEvent extends AnimationEvent{
    
    private Unit unit;
    
    public UnitEndActionEvent(Unit newUnit){
        unit = newUnit;
    }
    
    public void set(){
    }
    
    public void update(){
        if(!unit.isDead()){
            unit.setUsed(true);
        }
        finish();
    }
    
}
