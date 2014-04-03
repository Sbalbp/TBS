
package map;

import game.Game;
import java.util.ArrayList;
import java.util.Arrays;
import unit.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public final class MapUtils {
    
    private MapUtils(){}
    
    private static int transitCost(int iRow, int iCol, int dRow, int dCol, Map map, Unit unit){
        
        return map.getSquare(dRow, dCol).getCost() +
                ((map.getSquare(dRow, dCol).getElevation() > map.getSquare(iRow, iCol).getElevation()+(int)unit.getStat(Unit.Stat.CLIMB) && !map.getSquare(dRow, dCol).isCity())
                ? map.getSquare(dRow, dCol).getElevation()-map.getSquare(iRow, iCol).getElevation()-(int)unit.getStat(Unit.Stat.CLIMB) : 0)+
                ((map.getSquare(dRow, dCol).getUsed() != -1 && Game.game.getTeam((int)unit.getStat(Unit.Stat.OWNER)) != Game.game.getTeam((int)map.getSquare(dRow, dCol).getUsed()))
                ? 100 : 0);
        
    }
    
    public static String[] getAvailableActions(int row, int col, Map map){
        
        ArrayList <String> actions = new ArrayList<String>();
        ArrayList <ArrayList <Integer>> targets = MapUtils.getIndexPossibleTargets(map.getSquare(row,col).getUnit(), map);
        for(int i=0; i<targets.size(); i++){
            if(map.getSquare(targets.get(i).get(0),targets.get(i).get(1)).getUsed() != -1
                && Game.game.getTeam((int)map.getSquare(targets.get(i).get(0),targets.get(i).get(1)).getUsed()) != Game.game.getTeam((int)map.getSquare(row,col).getUnit().getStat(Unit.Stat.OWNER))){
                
                actions.add("attack");
                break;
            }
        }
        if(map.getSquare(row,col).isCity() && (map.getSquare(row,col).getCityAllegiance() == -1 || Game.game.getTeam(map.getSquare(row,col).getCityAllegiance()) != Game.game.getTeam((int)map.getSquare(row,col).getUnit().getStat(Unit.Stat.OWNER)))){
            actions.add("capture");
        }
        if(map.getSquare(row, col).getGoldValue() > 0){
            actions.add("ransack");
        }
        actions.add("wait");
            
        String[] actionsArray = new String[actions.size()];
        actionsArray = actions.toArray(actionsArray);
        return actionsArray;
        
    }
    
    public static ArrayList <ArrayList <Integer>> getIndexPossibleTargets
            (int row, int column, int minRange, int maxRange, Map map){
        
        ArrayList <ArrayList <Integer>> indexes = new ArrayList <ArrayList<Integer>>();
        boolean[] added = new boolean[map.getRows()*map.getColumns()];
        Arrays.fill(added,false);
        
        for(int i=minRange; i<=maxRange; i++){
            for(int r=-i; r<=i; r++){
                for(int c=-i; c<=i; c++){
                    if(row+r > -1 && row+r < map.getRows() && column+c > -1 && column+c < map.getColumns()
                       && Math.abs(r)+Math.abs(c) == i && !added[column+c+(row+r)*map.getColumns()]){
                        indexes.add(new ArrayList <Integer> (Arrays.asList(row+r,column+c)));
                        added[column+c+(row+r)*map.getColumns()] = true;
                    }
                }
            }
        }
        
        return indexes;
        
    }
    
    public static ArrayList <ArrayList <Integer>> getIndexPossibleTargets
            (Unit unit, Map map){
        
        int row = unit.getSquare().getRow(), column = unit.getSquare().getColumn();
        int min = (int)unit.getStat(Unit.Stat.MINRANGE), max = (int)unit.getStat(Unit.Stat.MAXRANGE);
        
        return getIndexPossibleTargets(row,column,min,max,map);
        
    }
    
    public static ArrayList <ArrayList <Integer>> getIndexAvailablePaths
            (Unit unit, int pointsLeft, int row, int column, Map map, boolean[] added){

        ArrayList <ArrayList<Integer>> indexes = new ArrayList <ArrayList<Integer>>();
        int nextRow, nextColumn;
        
        if(added == null){
            added = new boolean[map.getRows()*map.getColumns()];
            Arrays.fill(added, false);
        }
        
        // This square was visited
        if(!added[column+row*map.getColumns()] && (map.getSquare(row, column).getUsed() == -1 || map.getSquare(row, column).getUnit() == unit)){
            indexes.add(new ArrayList <Integer> (Arrays.asList(row,column)));
            added[column+row*map.getColumns()] = true;
        }
        
        if(pointsLeft == 0){
            return indexes;
        }
        
        // Up
        nextRow = row-1;
        if(nextRow > -1 && transitCost(row,column,nextRow,column,map,unit) <= pointsLeft){
            indexes.addAll(getIndexAvailablePaths(unit, pointsLeft-transitCost(row,column,nextRow,column,map,unit),
                    nextRow, column, map, added));
        }
        
        // Left
        nextColumn = column-1;
        if(nextColumn > -1 && transitCost(row,column,row,nextColumn,map,unit) <= pointsLeft){
            indexes.addAll(getIndexAvailablePaths(unit, pointsLeft-transitCost(row,column,row,nextColumn,map,unit),
                    row, nextColumn, map, added));
        }
        
        // Right
        nextColumn = column+1;
        if(nextColumn < map.getColumns() && transitCost(row,column,row,nextColumn,map,unit) <= pointsLeft){
            indexes.addAll(getIndexAvailablePaths(unit, pointsLeft-transitCost(row,column,row,nextColumn,map,unit),
                    row, nextColumn, map, added));
        }
        
        // Down
        nextRow = row+1;
        if(nextRow < map.getRows() && transitCost(row,column,nextRow,column,map,unit) <= pointsLeft){
            indexes.addAll(getIndexAvailablePaths(unit, pointsLeft-transitCost(row,column,nextRow,column,map,unit),
                    nextRow, column, map, added));
        }
        
        return indexes;
        
    }
    
    private static ArrayList <ArrayList <Integer>> buildPath(int[]previous, int currRow, int currCol, int columns){
        ArrayList <ArrayList <Integer>> path;
        int index = currCol+currRow*columns;
        
        if(previous[index] != -1){
            path = buildPath(previous,previous[index]/columns,previous[index]%columns,columns);
        }
        else{
            path = new ArrayList <ArrayList <Integer>>();
        }
        path.add(new ArrayList <Integer>(Arrays.asList(currRow,currCol)));
        return path;
    }
    
    public static ArrayList <ArrayList <Integer>> getBestPath(Unit unit, int iRow, int iCol, int dRow, int dCol, Map map){
        
        int current = iCol+iRow*map.getColumns(), neighbour;
        ArrayList <Integer> closed = new ArrayList <Integer>();
        ArrayList <Integer> open = new ArrayList <Integer>(Arrays.asList(current));
        int previous[] = new int[map.getRows()*map.getColumns()];
        int g[] = new int[map.getRows()*map.getColumns()];
        int f[] = new int[map.getRows()*map.getColumns()];
        int neighbourhood[][] = {{-1,0},
                                 {0,-1},
                                 {0,1},
                                 {1,0}};
        int lowerF, openIndex, currRow, currCol, neighRow, neighCol, tentG;
        
        Arrays.fill(previous, -1);
        
        g[current] = 0;
        f[current] = g[current]+dRow-iRow+dCol-iCol;
        
        while(open.size() > 0){
            lowerF = f[open.get(0)];
            current = open.get(0);
            openIndex = 0;
            for(int i=1; i<open.size(); i++){
                if(f[open.get(i)] < lowerF){
                    lowerF = f[open.get(i)];
                    current = open.get(i);
                    openIndex = i;
                }
            }
            
            currRow = current/map.getColumns();
            currCol = current%map.getColumns();
            
            if(currRow == dRow && currCol == dCol){
                return buildPath(previous,dRow,dCol,map.getColumns());
            }
            
            open.remove(openIndex);
            closed.add(current);
            
            for(int i=0; i<4; i++){
                neighRow = currRow + neighbourhood[i][0];
                neighCol = currCol + neighbourhood[i][1];

                if(neighRow < 0 || neighRow >= map.getRows() || neighCol < 0 || neighCol >= map.getColumns()){
                    continue;
                }
                neighbour = neighCol+neighRow*map.getColumns();
                if(closed.indexOf(neighbour) > -1){
                    continue;
                }
                
                tentG = g[current]+transitCost(currRow,currCol,neighRow,neighCol,map,unit);
                
                if(open.indexOf(neighbour) < 0 || tentG < g[neighbour]){
                    previous[neighbour] = current;
                    g[neighbour] = tentG;
                    f[neighbour] = g[neighbour]+dRow-neighRow+dCol-neighCol;
                    if(open.indexOf(neighbour) < 0){
                        open.add(neighbour);
                    }
                }
            }
        }
        
        return null;
        
    }
}
