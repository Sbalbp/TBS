
package animationEvent;

import game.Game;
import map.square.*;
import unit.Unit;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class UnitAttackEndEvent extends AnimationEvent{
    
    private Unit attacker;
    private Square attSquare;
    private Square defSquare;
    private int objX;
    private int objY;
    
    public UnitAttackEndEvent (Unit newAttacker, Unit newDefender){
        attacker = newAttacker;
        attSquare = attacker.getSquare();
        defSquare = newDefender.getSquare();
    }
    
    public void set(){
        int rowDif, colDif;
        
        if(!attacker.isDead()){

            rowDif = attSquare.getRow()-defSquare.getRow();
            colDif = attSquare.getColumn()-defSquare.getColumn();
        
            switch(attacker.isRanged() ? 1 : 0){
                case 1:
                    Game.game.getFrame().getMapPanel().setCursor(attSquare.getRow(),attSquare.getColumn(),true);
                    finish();
                    break;
                case 0:
                    attacker.setAnimation(colDif == 0 ? rowDif < 0 ? "up" : "down" : colDif > 0 ? "right" : "left");
                    objX = 0;
                    objY = 0;
                    break;
            }
            
        }
        else{
            Game.game.getFrame().getMapPanel().setCursor(attSquare.getRow(),attSquare.getColumn(),true);
            finish();
        }
    }
    
    public void update(){
        switch(attacker.isRanged() ? 1 : 0){
            case 1:
                Game.game.getFrame().getMapPanel().setCursor(attSquare.getRow(),attSquare.getColumn(),true);
                finish();
                break;
            case 0:
                if(attacker.getXOffset() == objX){
                    if(attacker.getYOffset() == objY){
                        attacker.setAnimation("idle");
                        Game.game.getFrame().getMapPanel().setCursor(attSquare.getRow(),attSquare.getColumn(),true);
                        finish();
                    }
                    else{
                        attacker.setYOffset(attacker.getYOffset()+(attacker.getYOffset() > objY ? -1 : 1));
                    }
                }
                else{
                    attacker.setXOffset(attacker.getXOffset()+(attacker.getXOffset() > objX ? -1 : 1));
                    if(attacker.getYOffset() != objY){
                        attacker.setYOffset(attacker.getYOffset()+(attacker.getYOffset() > objY ? -1 : 1));
                    }
                }
                break;
        }
    }
    
}
