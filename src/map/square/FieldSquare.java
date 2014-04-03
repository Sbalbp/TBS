
package map.square;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class FieldSquare extends Square{
    
    public FieldSquare(){
        super();
    }
    
    public FieldSquare(int newElevation, City newCity, int allegiance, int isUsed, int gold){
        super(newElevation, newCity, allegiance, isUsed, gold);
    }
    
    public int getTextureNumber(){
        return 0;
    }
    
    public int getCost(){
        return 1;
    }
    
    public int getVisionCost(){
        return 1;
    }
    
    public int getDefense(){
        return 0;
    }
    
}
