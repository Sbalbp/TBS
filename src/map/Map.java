
package map;

import map.square.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Map {
    
    private int rows;
    private int columns;
    
    private Square [] squares;
    
    public int getRows(){
        return rows;
    }
    
    public int getColumns(){
        return columns;
    }
    
    public Square getSquare(int row, int column){
        return squares[row*columns+column];
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
    
    public void print(){
        
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                System.out.print(squares[i*columns+j].getTextureNumber());
            }
            System.out.println();
        }

    }
}
