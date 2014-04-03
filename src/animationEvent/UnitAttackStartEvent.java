
package animationEvent;

import game.Game;
import unit.Unit;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class UnitAttackStartEvent extends AnimationEvent{
    
    private Unit attacker;
    private Unit defender;
    private int objX;
    private int objY;
    
    public UnitAttackStartEvent(Unit newAttacker, Unit newDefender){
        attacker = newAttacker;
        defender = newDefender;  
    }
    
    public void set(){
        int rowDif, colDif;
        
        Game.game.getFrame().getMapPanel().setCursor(attacker.getSquare().getRow(),attacker.getSquare().getColumn(),true);
        
        rowDif = defender.getSquare().getRow()-attacker.getSquare().getRow();
        colDif = defender.getSquare().getColumn()-attacker.getSquare().getColumn();
        
        switch(attacker.isRanged() ? 1 : 0){
            case 1:
                attacker.setAnimation(colDif >= 0 ? "attackRight" : "attackLeft");
                break;
            case 0:
                attacker.setAnimation(colDif == 0 ? rowDif < 0 ? "up" : "down" : colDif > 0 ? "right" : "left");
                objX = 32*((int)Math.signum(colDif))*(Math.abs(colDif)-1)+22*((int)Math.signum(colDif));
                objY = 32*((int)Math.signum(rowDif))*(Math.abs(rowDif)-1)+22*((int)Math.signum(rowDif));
                break;
        }
    }
    
    public void update(){
        switch(attacker.isRanged() ? 1 : 0){
            case 1:
                if(attacker.timesAnim() > 0){
                    attacker.setAnimation("idle");
                    finish();
                }
                break;
            case 0:
                if(attacker.getXOffset() == objX){
                    if(attacker.getYOffset() == objY){
                        finish();
                    }
                    else{
                        attacker.setYOffset(attacker.getYOffset()+(objY > 0 ? 1 : -1));
                    }
                }
                else{
                    attacker.setXOffset(attacker.getXOffset()+(objX > 0 ? 1 : -1));
                    if(attacker.getYOffset() != objY){
                        attacker.setYOffset(attacker.getYOffset()+(objY > 0 ? 1 : -1));
                    }
                }
                break;
        }
    }
    
}
