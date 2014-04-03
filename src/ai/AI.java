
package ai;

import java.util.ArrayList;
import map.*;
import unit.*;
import game.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class AI implements Runnable{
    // TO DO: The AI cannot determine the next unit's path before the last one ends its movement
    // Movements made by the AI must be simulated in a map copy in order to preserve consistency
    private Map map;
    private ArrayList <Unit> units;
    private ArrayList <GameAction> actions;
    
    private boolean finished;
    
    public AI(Map newMap, ArrayList <Unit> newUnits, ArrayList <GameAction> newActions){
        map = newMap;
        units = newUnits;
        actions = newActions;
        finished = true;
    }
    
    public boolean finished(){
        return finished;
    }
    
    public void run(){
        int counter = 0;
        ArrayList <ArrayList <Integer>> paths;
        GameAction action;
        
        finished = false;
        
        for(int i=0; i<units.size(); i++){
            if((int)units.get(i).getStat(Unit.Stat.OWNER) == Game.game.getTurn()){
                
                while(Game.game.getProcessCounter() < counter || Game.game.getEventQueue().size() > 0){
                    try {
                        Thread.sleep(300);
                    }
                    catch(InterruptedException ex){}
                }
                
                paths = MapUtils.getIndexAvailablePaths(units.get(i), (int)units.get(i).getStat(Unit.Stat.MOVEMENT), units.get(i).getSquare().getRow(), units.get(i).getSquare().getColumn(), map, null);
                if(paths.size() > 1){
                    int pathChosen = (int)(1.0+Math.random()*(paths.size()-1));
                    
                    action = new GameAction(GameAction.Action.MOVE,counter++);
                    action.setStart(units.get(i).getSquare().getRow(), units.get(i).getSquare().getColumn());
                    action.setEnd(paths.get(pathChosen).get(0),paths.get(pathChosen).get(1));
                    
                    synchronized(actions){
                        actions.add(action);
                    }
                    
                    action = new GameAction(GameAction.Action.WAIT,counter++);
                    action.setStart(units.get(i).getSquare().getRow(), units.get(i).getSquare().getColumn());
                    action.setEnd(paths.get(pathChosen).get(0),paths.get(pathChosen).get(1));
                    
                    synchronized(actions){
                        actions.add(action);
                    }
                }
            }
        }
        
        action = new GameAction(GameAction.Action.TURNEND,counter++);
        synchronized(actions){
            actions.add(action);
        }
        
        finished = true;
        
    }
    
}
