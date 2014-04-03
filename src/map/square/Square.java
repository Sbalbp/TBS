
package map.square;

import unit.Unit;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public abstract class Square {
    
    private int row;
    private int column;
    
    private Unit unit;
    
    private int elevation;
    
    private City city;
    
    private int used;
    private int goldValue;
    
    public Square(){
        row = -1;
        column = -1;
        
        unit = null;
        
        elevation = 0;
        
        city = null;
        used = -1;
        goldValue = 0;
    }
    
    public Square(int newElevation, City newCity, int allegiance, int isUsed, int gold){
        unit = null;
        
        elevation = newElevation;
        
        city = null;
        
        used = isUsed;
        goldValue = gold;
    }

    public int getRow(){
        return row;
    }
    
    public void setRow(int newRow){
        row = newRow;
    }
    
    public int getColumn(){
        return column;
    }
    
    public void setColumn(int newColumn){
        column = newColumn;
    }
    
    public int getElevation(){
        return elevation;
    }
    
    public void setElevation(int newElevation){
        elevation = newElevation;
    }
    
    public boolean isCity(){
        return city != null;
    }
    
    public void setCity(City newCity){
        if(city != null){
            city.setSquare(null);
        }
        if(newCity != null){
            newCity.setSquare(this);
        }
        city = newCity;
    }
    
    public City getCity(){
        return city;
    }
    
    public int getCityAllegiance(){
        if(city != null){
            return city.getAllegiance();
        }
        else{
            return -2;
        }
    }
    
    public void setAllegiance(int newAllegiance){
        if(city != null){
            city.setAllegiance(newAllegiance);
        }
    }
    
    public int getUsed(){
        return used;
    }
    
    public void setUsed(int isUsed){
        used = isUsed;
    }
    
    public int getGoldValue(){
        return goldValue;
    }
    
    public void setGoldValue(int gold){
        goldValue = gold;
    }
    
    public Unit getUnit(){
        return unit;
    }
    
    public void setUnit(Unit newUnit){
        used = newUnit != null ? (int)newUnit.getStat(Unit.Stat.OWNER) : -1;
        unit = newUnit;
    }

    public abstract int getTextureNumber();
    public abstract int getCost();
    public abstract int getVisionCost();
    public abstract int getDefense();
    
}
