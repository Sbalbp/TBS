
package gui;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class View {
    
    private int cursorRow;
    private int cursorCol;
    private int displayRow;
    private int displayCol;
    private int maxRows;
    private int maxCols;
    
    public View(int cR, int cC, int dR, int dC, int mR, int mC){
        cursorRow = cR;
        cursorCol = cC;
        displayRow = dR;
        displayCol = dC;
        maxRows = mR;
        maxCols = mC;
    }
    
    public void setCursorRow(int newValue){
        cursorRow = newValue;
    }
    
    public void setCursorColumn(int newValue){
        cursorCol = newValue;
    }
    
    public void setDisplayRow(int newValue){
        displayRow = newValue;
    }
    
    public void setDisplayColumn(int newValue){
        displayCol = newValue;
    }
    
    public void setMaxRows(int newValue){
        maxRows = newValue;
    }
    
    public void setMaxColumns(int newValue){
        maxCols = newValue;
    }
    
    public int getCursorRow(){
        return cursorRow;
    }
    
    public int getCursorColumn(){
        return cursorCol;
    }
    
    public int getDisplayRow(){
        return displayRow;
    }
    
    public int getDisplayColumn(){
        return displayCol;
    }
    
    public int getMaxRows(){
        return maxRows;
    }
    
    public int getMaxColumns(){
        return maxCols;
    }
    
    public void centerAroundCursor(int mapRows, int mapCols){
        int newRow = cursorRow-maxRows/2, newCol = cursorCol-maxCols/2;
        
        if(mapRows > maxRows){
            displayRow = newRow < 0 ? 0 : newRow+maxRows >= mapRows ? mapRows-maxRows : newRow;
        }
        if(mapCols > maxCols){
            displayCol = newCol < 0 ? 0 : newCol+maxCols >= mapCols ? mapCols-maxCols : newCol;
        }
    }
    
}
