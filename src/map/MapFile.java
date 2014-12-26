
package map;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import map.square.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class MapFile implements Serializable{
    
    private String name;
    private int maxPlayers;
    
    private int rows, columns;
    private SquareFile[] squares;
    
    public MapFile(Map map){
        Square sq;
        
        name = map.getName();
        maxPlayers = map.getMaxPlayers();
        
        rows = map.getRows();
        columns = map.getColumns();
        
        squares = new SquareFile[rows*columns];
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                sq = map.getSquare(i,j);
                squares[i*columns+j] = new SquareFile(sq.getTextureNumber(),sq.getElevation(),sq.getGoldValue(),sq.isCity(),
                        sq.isCity() ? sq.getCity().getAllegiance() : -1,
                        sq.isCity() ? sq.getCity().getMaxHP() : -1,
                        sq.isCity() ? sq.getCity().getGoldPerTurn() : -1);
            }
        }
    }
    
    public MapFile(byte[] bytes){
        try{
            deserialize(bytes);
        }catch(Exception e){e.printStackTrace();}
    }
    
    public String getName(){
        return name;
    }
    
    public int getMaxPlayers(){
        return maxPlayers;
    }
    
    public int getRows(){
        return rows;
    }
    
    public int getColumns(){
        return columns;
    }
    
    public SquareFile getSquare(int row, int column){
        return squares[row*columns+column];
    }
    
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(bs);
        o.writeObject(this);
        return bs.toByteArray();
    }
    
    public void deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(bs);
        
        MapFile mf = (MapFile)o.readObject();
        this.name = mf.name;
        this.maxPlayers = mf.maxPlayers;
        this.rows = mf.rows;
        this.columns = mf.columns;
        this.squares = mf.squares;
    }
    
    public class SquareFile implements Serializable{
        private int type;
        
        private int elevation;
        private int goldValue;
        
        private boolean isCity;
        private int cityAllegiance;
        private int cityMaxHP;
        private int cityGoldPerTurn;
        
        public SquareFile(int newType, int newElevation, int newGoldValue, boolean city,
                int newAllegiance, int newHP, int newGPT){
            type = newType;
            elevation = newElevation;
            goldValue = newGoldValue;
            isCity = city;
            cityAllegiance = newAllegiance;
            cityMaxHP = newHP;
            cityGoldPerTurn = newGPT;
        }
        
        public int getType(){
            return type;
        }
        
        public int getElevation(){
            return elevation;
        }
        
        public int getGoldValue(){
            return goldValue;
        }
        
        public boolean isCity(){
            return isCity;
        }
        
        public int getAllegiance(){
            return cityAllegiance;
        }
        
        public int getMaxHP(){
            return cityMaxHP;
        }
        
        public int getGoldPerTurn(){
            return cityGoldPerTurn;
        }
    }
}
