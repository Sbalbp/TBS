
package animationEvent;

import game.Game;
import map.square.City;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class CityHPEvent extends AnimationEvent{
    
    private City siegedCity;
    private int change;
    private int finalValue;
    private int step;
    private boolean evenIteration;
    
    public CityHPEvent(City newCity, int HPChange){
        siegedCity = newCity;
        change = HPChange;
        evenIteration = true;
    }
    
    public void set(){
        Game.game.getFrame().getMapPanel().setCursor(siegedCity.getSquare().getRow(),siegedCity.getSquare().getColumn(),true);
        
        finalValue = siegedCity.getHP()-change;
        finalValue = finalValue > siegedCity.getMaxHP() ? siegedCity.getMaxHP() : finalValue < 0 ? 0 : finalValue;
        step = (int)Math.signum(-change);

        siegedCity.siege(true);
    }
    
    public void update(){
        if(siegedCity.getHP() == finalValue){
            if(finalValue == 0){
                siegedCity.setAllegiance(siegedCity.getSquare().getUsed());
                siegedCity.setHP(siegedCity.getMaxHP());
            }
            siegedCity.siege(false);
            finish();
        }
        else{
            if(evenIteration){
                siegedCity.setHP(siegedCity.getHP()+step);
            }
            evenIteration = !evenIteration;
        }
    }
    
}
