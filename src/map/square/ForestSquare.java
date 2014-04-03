
package map.square;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class ForestSquare extends Square{
    
    public ForestSquare(){
        super();
    }
    
    public ForestSquare(int newElevation, City newCity, int allegiance,int isUsed, int gold){
        super(newElevation, newCity, allegiance, isUsed, gold);
    }
    
    public int getTextureNumber(){
        return 1;
    }
    
    public int getCost(){
        return 2;
    }
    
    public int getVisionCost(){
        return 2;
    }
    
    public int getDefense(){
        return 1;
    }

}
