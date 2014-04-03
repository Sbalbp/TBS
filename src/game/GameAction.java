
package game;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class GameAction {
    
    public enum Action {
        MOVE, ATTACK, CAPTURE, WAIT, TURNEND, SPELL
    }
    
    private Action action;
    
    private int order;
    
    private int id;
    
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;
    
    public GameAction(Action newAction, int newOrder){
        action = newAction;
        order = newOrder;
    }
    
    public Action getType(){
        return action;
    }
    
    public int getOrder(){
        return order;
    }
    
    public int getID(){
        return id;
    }
    
    public int getStartRow(){
        return startRow;
    }
    
    public int getStartCol(){
        return startCol;
    }
    
    public void setID(int newID){
        id = newID;
    }
    
    public void setStart(int newRow, int newCol){
        startRow = newRow;
        startCol = newCol;
    }
    
    public int getEndRow(){
        return endRow;
    }
    
    public int getEndCol(){
        return endCol;
    }
    
    public void setEnd(int newRow, int newCol){
        endRow = newRow;
        endCol = newCol;
    }
    
}
