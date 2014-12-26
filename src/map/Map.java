
package map;

import game.settings.Settings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import map.square.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Map {
    
    private String name;
    private int maxPlayers;
    
    private int rows;
    private int columns;
    
    private Square [] squares;
    
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
    
    public Square getSquare(int row, int column){
        return squares[row*columns+column];
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    public void randomize(int maxRows, int maxCols){
        int total;
             
        rows = maxRows;
        columns = maxCols;
        
        total = rows*columns;
        squares = new Square[total];
        
        java.util.Random random = new java.util.Random();
        for(int i=0; i<total; i++){
            switch(random.nextInt(2)){
                case 0:
                    squares[i] = new FieldSquare();
                    break;
                case 1:
                    squares[i] = new ForestSquare();
                    break;
            }
            squares[i].setRow(i/columns);
            squares[i].setColumn(i%columns);
            
            squares[i].setCity(Math.random() < 0.02 ? new City() : null);
        }
        
    }
    
    public void create(int r,int c,int[][] overlay){
        rows = r;
        columns = c;
        
        int total = rows*columns;
        squares = new Square[total];
        
        for(int i=0; i<rows;i++){
            for(int j=0;j<columns;j++){
                switch(overlay[i][j]){
                    case 0:
                    squares[j+columns*i] = new FieldSquare();
                    break;
                case 1:
                    squares[j+columns*i] = new ForestSquare();
                    break;
                }
                squares[j+columns*i].setRow(i);
                squares[j+columns*i].setColumn(j);
            }
        }
    }
    
    private void createFromMapFile(MapFile mapFile){
        int total;
        
        name = mapFile.getName();
        maxPlayers = mapFile.getMaxPlayers();
        
        rows = mapFile.getRows();
        columns = mapFile.getColumns();
        
        total = rows*columns;
        squares = new Square[total];

        for(int i=0; i<rows;i++){
            for(int j=0; j<columns;j++){
                MapFile.SquareFile sf = mapFile.getSquare(i, j);
                switch(sf.getType()){
                    case 0:
                    squares[j+columns*i] = new FieldSquare();
                    break;
                case 1:
                    squares[j+columns*i] = new ForestSquare();
                    break;
                }
                squares[j+columns*i].setRow(i);
                squares[j+columns*i].setColumn(j);
                
                squares[j+columns*i].setGoldValue(sf.getGoldValue());
                squares[j+columns*i].setElevation(sf.getElevation());
                if(sf.isCity()){
                    City city = new City();
                    city.setAllegiance(sf.getAllegiance());
                    city.setGoldPerTurn(sf.getGoldPerTurn());
                    city.setMaxHP(sf.getMaxHP());
                    city.setHP(sf.getMaxHP());
                    squares[j+columns*i].setCity(city);
                }
            }
        }
    }
    
    public void print(){
        
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                System.out.print(squares[i*columns+j].getTextureNumber());
            }
            System.out.println();
        }

    }
    
    public void read(String filename){
        byte[] data;
        
        try{
            File file = new File(Settings.get("assets.maps.route")+"\\"+filename);
            FileInputStream in = new FileInputStream(file);
            data = new byte[(int)file.length()];
            in.read(data);
            in.close();
        }catch(Exception e){System.out.println(e);return;}
        
        createFromMapFile(new MapFile(data));
    }
    
    public void write(String filename){
        MapFile mf = new MapFile(this);
        
        try{
            byte[] data = mf.serialize();
            
            FileOutputStream out = new FileOutputStream(Settings.get("assets.maps.route")+"\\"+filename);
            out.write(data);
            out.close();
        }catch(Exception e){System.out.println(e);return;}
    }
}
