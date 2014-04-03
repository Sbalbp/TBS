
package animationEvent;

import game.Game;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class TurnEndEvent extends AnimationEvent{
    
    public TurnEndEvent(){
    }
    
    public void set(){
    }
    
    public void update(){
        Game.game.endTurn();
        finish();
    }
    
}
