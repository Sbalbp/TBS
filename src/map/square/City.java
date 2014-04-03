
package map.square;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class City {
    
    private int allegiance;
    private int currHP;
    private int maxHP;
    private int goldPerTurn;
    private boolean underSiege;
    
    private Square square;
    
    public City(){
        allegiance = -1;
        maxHP = 100;
        currHP = 100;
        goldPerTurn = 100;
        underSiege = false;
    }
    
    public City(Square newSquare){
        this();
        setSquare(newSquare);
    }
    
    public void setAllegiance(int newAllegiance){
        allegiance = newAllegiance;
    }
    
    public int getAllegiance(){
        return allegiance;
    }
    
    public void setHP(int newHP){
        currHP = newHP;
    }
    
    public int getHP(){
        return currHP;
    }
    
    public void setMaxHP(int newMaxHP){
        maxHP = newMaxHP;
    }
    
    public int getMaxHP(){
        return maxHP;
    }
    
    public void setGoldPerTurn(int newGPT){
        goldPerTurn = newGPT;
    }
    
    public int getGoldPerTurn(){
        return goldPerTurn;
    }
    
    public void siege(boolean newValue){
        underSiege = newValue;
    }
    
    public boolean isSieged(){
        return underSiege;
    }
    
    public void setSquare(Square newSquare){
        square = newSquare;
    }
    
    public Square getSquare(){
        return square;
    }
    
}
