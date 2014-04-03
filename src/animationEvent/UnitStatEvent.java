
package animationEvent;

import game.Game;
import unit.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class UnitStatEvent extends AnimationEvent{
    
    private Unit.Stat stat;
    private Unit unit;
    private int finalValue;
    
    private int step;
    
    public UnitStatEvent(Unit newUnit, Unit.Stat newStat, int newFinalValue){
        unit = newUnit;
        stat = newStat;
        finalValue = newFinalValue;
    }
    
    public void set(){
        Game.game.getFrame().getMapPanel().setCursor(unit.getSquare().getRow(),unit.getSquare().getColumn(),true);
        step = finalValue < (int)unit.getStat(stat) ? -1 : 1;
    }
    
    public void update(){
        if(finalValue != (int)unit.getStat(stat)){
            unit.setStat(stat, (int)unit.getStat(stat)+step);
        }
        else{
            finish();
        }
    }
}
