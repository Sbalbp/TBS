
package animationEvent;

import game.Game;
import unit.Unit;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class UnitDeathEvent extends AnimationEvent{
    
    private Unit unit;
    
    public UnitDeathEvent(Unit newUnit){
        unit = newUnit;
    }
    
    public void set(){
        unit.setAnimation("death");
    }
    
    public void update(){
        if(unit.timesAnim() > 0){
            Game.game.killUnit(unit);
            finish();
        }
    }
    
}
