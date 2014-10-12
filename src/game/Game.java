
package game;

import ai.AI;
import animationEvent.*;
import connection.ClientThread;
import god.Spell;
import gui.MainFrame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import map.Map;
import map.MapUtils;
import map.square.City;
import unit.Unit;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Game {
    
    public static Game game = new Game();
    
    private MainFrame frame;
    
    private int playerTurn;
    private int currentTurn = 0;
    private int nPlayers;
    private HashMap teams;
    private HashMap colors;
    private ArrayList <ArrayList<Integer>> lastCursor;
    
    private ClientThread clientThread;
    
    private Map map;
    
    private ArrayList <Unit> units;
    private ArrayList <City> cities;
    
    private AI ai;
    
    private ArrayList <ArrayList <Integer>> destinations;
    private HashMap paths;
    
    private AnimationEventQueue eventQueue;
    
    private int actionCounter;
    private ArrayList <GameAction> actions;
    private ArrayList <GameAction> actionsToProcess;
    
    private int processCounter;
    
    public void setFrame(MainFrame newFrame){
        frame = newFrame;
    }
    
    public MainFrame getFrame(){
        return frame;
    }
    
    public void setPlayerTurn(int turn){
        playerTurn = turn;
    }
    
    public int getPlayerTurn(){
        return playerTurn;
    }
    
    public void setTurn(int turn){
        currentTurn = turn;
    }
    
    public int getTurn(){
        return currentTurn;
    }
    
    public void setNPlayers(int n){
        nPlayers = n;
    }
    
    public void setTeams(int[][] teamsArray){
        nPlayers = 0;
        teams = new HashMap();
        lastCursor = new ArrayList <ArrayList<Integer>>();
        
        for(int i=0; i<teamsArray.length; i++){
            for(int j=0; j<teamsArray[i].length; j++){
                teams.put(teamsArray[i][j],i);
                nPlayers++;
                lastCursor.add(new ArrayList<Integer>(Arrays.asList(0,0)));
            }
        }
        
        setColors(new String[]{"red","blue"});
    }
    
    public int getTeam(int player){
        return (int)teams.get(player);
    }
    
    public void setColors(String[] colorsArray){
        colors = new HashMap();
        
        for(int i=0; i<colorsArray.length; i++){
            switch(colorsArray[i]){
                case "red":
                    colors.put(i, Color.RED);
                    break;
                case "blue":
                    colors.put(i, Color.BLUE);
                    break;
            }
        }
        colors.put(-1, Color.BLACK);
    }
    
    public Color getColor(int player){
        return (Color)colors.get(player);
    }
    
    private void nextTurn(){
        currentTurn = (currentTurn+1)%nPlayers;
    }
    
    private void setMap(Map newMap){
        map = newMap;
    }
    
    public Map getMap(){
        return map;
    }
    
    public ArrayList<Unit> getUnits(){
        return units;
    }
    
    public ArrayList<City> getCities(){
        return cities;
    }
    
    public ArrayList <ArrayList<Integer>> getDestinations(){
        return destinations;
    }
    
    public HashMap getPaths(){
        return paths;
    }
    
    public AnimationEventQueue getEventQueue(){
        return eventQueue;
    }
    
    public int getActionCounter(){
        return actionCounter;
    }
    
    public void increaseActionCounter(){
        actionCounter++;
    }
    
    public ArrayList<GameAction> getActions(){
        return actions;
    }
    
    public ArrayList<GameAction> getActionsToProcess(){
        return actionsToProcess;
    }
    
    public void setProcessCounter(int value){
        processCounter = 0;
    }
    
    public int getProcessCounter(){
        return processCounter;
    }
    
    public void increaseProcessCounter(){
        processCounter++;
    }
    
    public ClientThread.CurrentState getClientState(){
        return clientThread.getCurrentState();
    }
    
    public void runClient(String address){
        clientThread = new ClientThread();
        clientThread.setName("ClientThread");
        clientThread.setServerAddress(address);
        clientThread.start();
    }
    
    public void newGame(Map map){
        setMap(map);
        
        units = new ArrayList <Unit>();
        cities = new ArrayList <City>();
        
        eventQueue = new AnimationEventQueue();
        
        actionCounter = 0;
        actions = new ArrayList<GameAction>();
        actionsToProcess = new ArrayList<GameAction>();
        
        ai = new AI(map,units,actions);   
    }
    
    public void createUnit(Unit unitToCreate){
        units.add(unitToCreate);
    }
    
    public void killUnit(Unit unitToKill){
        unitToKill.setSquare(null);
        units.remove(unitToKill);
    }
    
    public void endTurn(){
        lastCursor.get(currentTurn).set(0, frame.getMapPanel().getView().getCursorRow());
        lastCursor.get(currentTurn).set(1, frame.getMapPanel().getView().getCursorColumn());
        
        nextTurn();
        
        frame.getMapPanel().setCursor(lastCursor.get(currentTurn).get(0),lastCursor.get(currentTurn).get(1),true);
        
        for(int i=0; i<units.size(); i++){
            if(units.get(i).isUsed()){
                units.get(i).setUsed(false);
            }
            
            if((int)units.get(i).getStat(Unit.Stat.OWNER) == currentTurn){
                units.get(i).updateBuffs();
            }
        }
        
        frame.setUnitInfo(map.getSquare(frame.getMapPanel().getView().getCursorRow(), frame.getMapPanel().getView().getCursorColumn()).getUnit());//.getUnitInfoPanel().update();

        if(currentTurn != playerTurn && ai.finished()){
            actionCounter = 0;
            processCounter = 0;
            Thread aiThread = new Thread(ai);
            aiThread.setName("AIThread");
            aiThread.start();
        }
    }
    
    public void setDestinationsAndPaths(Unit unit, int startRow, int startCol){
        destinations = MapUtils.getIndexAvailablePaths(unit, (int)unit.getStat(Unit.Stat.MOVEMENT), startRow, startCol, map, null);
        paths = new HashMap();
        for(int i=0; i<destinations.size(); i++){
            paths.put(destinations.get(i).get(0)*map.getColumns()+destinations.get(i).get(1), MapUtils.getBestPath(unit, startRow, startCol, destinations.get(i).get(0), destinations.get(i).get(1), map));
        }
    }
    
    public void moveUnit(Unit unit, int destinyRow, int destinyCol){ 
        eventQueue.add(new AnimationEvent[]{
            new UnitPathEvent(unit,(ArrayList <ArrayList <Integer>>)paths.get(destinyRow*map.getColumns()+destinyCol),map)
        });
        if(currentTurn == playerTurn){
            eventQueue.add(new AnimationEvent[]{
                new ActionPanelEvent(frame.getMapPanel().getActionsPanel(),destinyRow,destinyCol,map)
            });
        }
    }
    
    public void damageUnits(Unit[] unitsToDamage, int[] dmgDealt){
        int remainingHP;
        AnimationEvent[] HPEvArray, DeathEvArray;
        ArrayList <AnimationEvent> HPEvents = new ArrayList <AnimationEvent>();
        ArrayList <AnimationEvent> deathEvents = new ArrayList <AnimationEvent>();
        
        for(int i=0; i<unitsToDamage.length; i++){
            if(dmgDealt[i] != 0){
                remainingHP = (int)unitsToDamage[i].getStat(Unit.Stat.CURRHP)-dmgDealt[i];
                remainingHP = remainingHP < 0 ? 0 : remainingHP > (int)unitsToDamage[i].getStat(Unit.Stat.MAXHP) ? (int)unitsToDamage[i].getStat(Unit.Stat.MAXHP) : remainingHP;
                HPEvents.add(new UnitStatEvent(unitsToDamage[i],Unit.Stat.CURRHP,remainingHP));
                if(remainingHP == 0){
                    deathEvents.add(new UnitDeathEvent(unitsToDamage[i]));
                }
            }
        }
        
        if(HPEvents.size() > 0){
            HPEvArray = new AnimationEvent[HPEvents.size()];
            HPEvArray = HPEvents.toArray(HPEvArray);
            eventQueue.add(HPEvArray);
        
            if(deathEvents.size() > 0){
                DeathEvArray = new AnimationEvent[deathEvents.size()];
                DeathEvArray = deathEvents.toArray(DeathEvArray);
                eventQueue.add(DeathEvArray);
            }
        }
    }
    
    private void executeAction(GameAction action){
        Unit unit;

        switch(action.getType()){
            case MOVE:
                unit = map.getSquare(action.getStartRow(), action.getStartCol()).getUnit();
                setDestinationsAndPaths(unit,action.getStartRow(), action.getStartCol());
                moveUnit(unit,action.getEndRow(),action.getEndCol());
                break;
            case WAIT:
                unit = map.getSquare(action.getStartRow(), action.getStartCol()).getUnit() == null ? map.getSquare(action.getEndRow(), action.getEndCol()).getUnit() : map.getSquare(action.getStartRow(), action.getStartCol()).getUnit();
                eventQueue.add(new AnimationEvent[]{
                    new UnitEndActionEvent(unit)
                });
                break;
            case CAPTURE:
                unit = map.getSquare(action.getEndRow(), action.getEndCol()).getUnit();
                getEventQueue().add(new AnimationEvent[]{
                    new CityHPEvent(unit.getSquare().getCity(),(int)unit.getStat(Unit.Stat.SIEGE))
                });
                getEventQueue().add(new AnimationEvent[]{
                    new UnitEndActionEvent(unit)
                });
                break;
            case SPELL:
                Spell spell = new Spell();
                frame.getMapPanel().setSpell(spell);
                spell.getAffectedSquares(action.getStartRow(),action.getStartCol(),map);
                spell.setDrawingCoordinates(frame.getMapPanel().getView());
                spell.getAffectedUnits(currentTurn,map);
                spell.apply(currentTurn);
                break;
            case TURNEND:
                eventQueue.add(new AnimationEvent[]{
                    new TurnEndEvent()
                });
                break;
        }
        
    }
    
    public void processActions(){
        
        synchronized(actions){
            for(int i=0; i<actions.size(); i++){
                if(actions.get(i).getOrder() == actionCounter){
                    actionCounter++;
                    actionsToProcess.add(actions.get(i));
                    actions.remove(i);
                    i = -1;
                }
            }
        }
        
        for(int i=0; i<actionsToProcess.size(); i++){
            if(eventQueue.size() == 0){
                executeAction(actionsToProcess.get(i));
                processCounter++;
                actionsToProcess.remove(i);
                i--;
            }
        }
    }

}
