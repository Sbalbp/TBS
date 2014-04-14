
package gui.animatedpanel;

import animationEvent.*;
import game.Game;
import game.settings.Settings;
import god.Spell;
import gui.ActionsPanel;
import gui.UnitCreatePanel;
import gui.View;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javax.imageio.ImageIO;
import map.*;
import map.square.*;
import unit.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class MapPanel extends AnimatedPanel{
    
    public enum SelectionPhase {
        NOSELECTION, UNITSELECTED, ACTIONSELECT, CREATIONSELECT, ATTACKTARGETSELECT, SPELLTARGETSELECT
    }
    
    private ActionsPanel actionsPanel;
    private UnitCreatePanel unitCreatePanel;
    
    private Map map;
    private View view;

    private Color[] colours = new Color[]{Color.GRAY,Color.GREEN};
    
    private boolean showHPBars;
    
    private SelectionPhase selectionPhase;
    private Unit unitSelected, target;
    private City citySelected;
    private Square lastSquare;
    
    private HashMap citySprites;
    
    private ArrayList <ArrayList <Integer>> targetSquares;
    private int targetUnitIndex;
    private ArrayList <Unit> targetUnits;
    
    private ArrayList <Unit> unitsToRender;
    
    private int[][] transparencyColor;
    private int colorToDraw;
    
    private BufferedImage[][] pathArrow;
    private int[][] squaresArrow;
    private int arrowToDraw;
    
    private BufferedImage cursor;
    private BufferedImage blueTransparency;
    private BufferedImage redTransparency;
    
    private Spell spell;
    
    public MapPanel(){
        super();
        this.setLayout(null);
        
        end = false;
        
        showHPBars = true;
        
        view = new View(0,0,-6,-6,15,15);
        
        selectionPhase = SelectionPhase.NOSELECTION;
        unitSelected = null;
        
        citySprites = new HashMap();
        
        try{
            String imgRoute = Settings.get("assets.image.route");
            
            cursor = ImageIO.read(new File(imgRoute+"/map/cursor.png"));
            
            for(int i=-1; i<1; i++){
                citySprites.put(i,ImageIO.read(new File(imgRoute+"/map/city"+i+".png")));
            }
            
            blueTransparency = ImageIO.read(new File(imgRoute+"/map/squareBlueTransparent.png"));
            redTransparency = ImageIO.read(new File(imgRoute+"/map/squareRedTransparent.png"));
            
            pathArrow = new BufferedImage[4][4];
            for(int i=0; i<4; i++){
                for(int j=0; j<4; j++){
                    pathArrow[i][j] = ImageIO.read(new File(imgRoute+"/map/pathArrow/pathArrow_"+i+"_"+j+".png"));
                }
            }
        }catch(java.io.IOException e){
            System.out.println("Exception: "+e);
        }
    }
    
    public MapPanel(Map newMap){
        this();
        setMap(newMap);
    }
    
    public void initElements(){
        actionsPanel = new ActionsPanel(view);
        actionsPanel.setVisible(false);
        this.add(actionsPanel);
        
        unitCreatePanel = new UnitCreatePanel(this.getWidth(),this.getHeight());
        this.add(unitCreatePanel);
    }
    
    public View getView(){
        return view;
    }
    
    public void setMap(Map newMap){
        map = newMap;
        
        for(int r=0; r<map.getRows(); r++){
            for(int c=0; c<map.getColumns(); c++){
                if(map.getSquare(r,c).getUnit() != null){
                    Game.game.getUnits().add(map.getSquare(r,c).getUnit());
                }
                if(map.getSquare(r,c).isCity()){
                    Game.game.getCities().add(map.getSquare(r,c).getCity());
                }
            }
        }
        
        view.centerAroundCursor(map.getRows(),map.getColumns());
        
        transparencyColor = new int[map.getRows()][map.getColumns()];
        unsetTransparencySquares();
        squaresArrow = new int[map.getRows()][map.getColumns()];
        unsetArrowSquares();
    }
    
    public ActionsPanel getActionsPanel(){
        return actionsPanel;
    }
    
    private void setTransparencySquares(ArrayList <ArrayList <Integer>> squares, int color){
        
        unsetTransparencySquares();
        
        if(Game.game.getTurn() == Game.game.getPlayerTurn()){
            for(int i=0; i<squares.size(); i++){
                transparencyColor[squares.get(i).get(0)][squares.get(i).get(1)] = color;
            }
        }
        
    }
    
    private void unsetTransparencySquares(){
        
        for(int i=0; i<transparencyColor.length; i++){
            Arrays.fill(transparencyColor[i], 0);
        }
        
    }
    
    private void setArrowSquares(ArrayList <ArrayList <Integer>> path){
        int rowDif, colDif, from, to;
        
        unsetArrowSquares();
        
        for(int i=1; i<path.size(); i++){
            rowDif = path.get(i).get(0) - path.get(i-1).get(0);
            colDif = path.get(i).get(1) - path.get(i-1).get(1);
            from = rowDif == 0 ? colDif > 0 ? 2 : 1 : rowDif > 0 ? 3 : 0;
            if(i == path.size()-1){
                to = from;
            }
            else{
                rowDif = path.get(i+1).get(0) - path.get(i).get(0);
                colDif = path.get(i+1).get(1) - path.get(i).get(1);
                to = rowDif == 0 ? colDif > 0 ? 1 : 2 : rowDif > 0 ? 0 : 3;
            }

            squaresArrow[path.get(i).get(0)][path.get(i).get(1)] = from*4+to;
        }
        
    }
    
    private void unsetArrowSquares(){
        
        for(int i=0; i<squaresArrow.length; i++){
            Arrays.fill(squaresArrow[i], -1);
        }
        
    }
    
    public void select(){
        Unit newUnit;
        
        if(true){
            switch(selectionPhase){
                case NOSELECTION:
                    unitSelected = map.getSquare(view.getCursorRow(), view.getCursorColumn()).getUnit();
                    if(unitSelected != null && !unitSelected.isUsed() && unitSelected.getStat(Unit.Stat.OWNER) == Game.game.getTurn()){
                        lastSquare = unitSelected.getSquare();
                        Game.game.setDestinationsAndPaths(unitSelected,view.getCursorRow(), view.getCursorColumn());
                        setTransparencySquares(Game.game.getDestinations(),1);
                        unitSelected.setAnimation("right");
                        selectionPhase = SelectionPhase.UNITSELECTED;
                    }
                    else{
                        citySelected = map.getSquare(view.getCursorRow(), view.getCursorColumn()).getCity();
                        
                        if(unitSelected == null && citySelected != null && citySelected.getAllegiance() == Game.game.getPlayerTurn()){
                            unitCreatePanel.setVisible(true);
                            selectionPhase = SelectionPhase.CREATIONSELECT;
                        }
                        else{
                            citySelected = null;
                        }
                        unitSelected = null;
                    }
                    break;
                case UNITSELECTED:
                    if(transparencyColor[view.getCursorRow()][view.getCursorColumn()]>0){
                        unsetTransparencySquares();
                        unsetArrowSquares();
                        Game.game.moveUnit(unitSelected, view.getCursorRow(), view.getCursorColumn());
                        selectionPhase = SelectionPhase.ACTIONSELECT;
                    }
                    break;
                case ACTIONSELECT:
                    actionsPanel.setVisible(false);
                    switch(actionsPanel.select()){
                        case "wait":
                            Game.game.getEventQueue().add(new AnimationEvent[]{
                                new UnitEndActionEvent(unitSelected)
                            });
                            selectionPhase = SelectionPhase.NOSELECTION;
                            break;
                        case "attack":
                            targetSquares = MapUtils.getIndexPossibleTargets(unitSelected, map);
                            targetUnits = new ArrayList <Unit>();
                            for(int i=0; i<targetSquares.size(); i++){
                                if(map.getSquare(targetSquares.get(i).get(0), targetSquares.get(i).get(1)).getUnit() != null 
                                   && Game.game.getTeam((int)map.getSquare(targetSquares.get(i).get(0), targetSquares.get(i).get(1)).getUnit().getStat(Unit.Stat.OWNER)) != Game.game.getTeam((int)unitSelected.getStat(Unit.Stat.OWNER))){
                                    targetUnits.add(map.getSquare(targetSquares.get(i).get(0), targetSquares.get(i).get(1)).getUnit());
                                    targetUnitIndex = 0;
                                    setCursor(targetUnits.get(targetUnitIndex).getSquare().getRow(),targetUnits.get(targetUnitIndex).getSquare().getColumn(),true);
                                    selectionPhase = SelectionPhase.ATTACKTARGETSELECT;
                                }
                            }
                            break;
                        case "capture":
                            Game.game.getEventQueue().add(new AnimationEvent[]{
                                    new CityHPEvent(unitSelected.getSquare().getCity(),(int)unitSelected.getStat(Unit.Stat.SIEGE))
                            });
                            Game.game.getEventQueue().add(new AnimationEvent[]{
                                    new UnitEndActionEvent(unitSelected)
                            });
                            selectionPhase = SelectionPhase.NOSELECTION;
                            break;
                    }
                    break;
                case ATTACKTARGETSELECT:
                    target = map.getSquare(view.getCursorRow(),view.getCursorColumn()).getUnit();
                    Game.game.getEventQueue().add(new AnimationEvent[]{
                        new UnitAttackStartEvent(unitSelected, target)
                    });
                    Battle.combat(unitSelected, target);
                    Game.game.getEventQueue().add(new AnimationEvent[]{
                        new UnitAttackEndEvent(unitSelected, target)
                    });
                    Game.game.getEventQueue().add(new AnimationEvent[]{
                        new UnitEndActionEvent(unitSelected)
                    });
                    selectionPhase = SelectionPhase.NOSELECTION;
                    break;
                case CREATIONSELECT:
                    newUnit = unitCreatePanel.select();
                    if(newUnit != null){
                        newUnit.setSquare(citySelected.getSquare());
                        newUnit.setUsed(true);
                        Game.game.createUnit(newUnit);
                        unitCreatePanel.setVisible(false);
                        selectionPhase = SelectionPhase.NOSELECTION;
                    }
                    break;
                case SPELLTARGETSELECT:
                    spell.setDrawingCoordinates(view);
                    spell.getAffectedUnits(Game.game.getTurn(),map);
                    spell.apply(Game.game.getTurn());
                    unsetSpell();
                    break;
            }
        }
        
    }
    
    public void back(){
        
        if(true){
            switch(selectionPhase){
                case UNITSELECTED:
                    unitSelected.setAnimation("idle");
                    unsetTransparencySquares();
                    unsetArrowSquares();
                    setCursor(unitSelected.getSquare().getRow(),unitSelected.getSquare().getColumn(),false);
                    selectionPhase = SelectionPhase.NOSELECTION;
                    break;
                case ACTIONSELECT:
                    actionsPanel.setVisible(false);
                    setTransparencySquares(Game.game.getDestinations(),1);
                    unitSelected.setAnimation("right");
                    unitSelected.setSquare(lastSquare);
                    setCursor(unitSelected.getSquare().getRow(),unitSelected.getSquare().getColumn(),false);
                    selectionPhase = SelectionPhase.UNITSELECTED;
                    break;
                case ATTACKTARGETSELECT:
                    actionsPanel.setVisible(true);
                    setCursor(unitSelected.getSquare().getRow(),unitSelected.getSquare().getColumn(),false);
                    selectionPhase = SelectionPhase.ACTIONSELECT;
                    break;
                case CREATIONSELECT:
                    unitCreatePanel.setVisible(false);
                    selectionPhase = SelectionPhase.NOSELECTION;
                    break;
                case SPELLTARGETSELECT:
                    unsetSpell();
                    break;
            }
        }

    }
    
    public void setCursor(int row, int col, boolean center){
        view.setCursorRow(row);
        view.setCursorColumn(col);
        
        if(center){
            view.centerAroundCursor(map.getRows(),map.getColumns());
        }
    }
    
    public void cursorMove(int dir){
        
        if(true){
            if(!actionsPanel.isVisible()){
                if(!unitCreatePanel.isVisible()){
                    switch(dir){
                        case 0:
                            cursorUp();
                            break;
                        case 1:
                            cursorLeft();
                            break;
                        case 2:
                            cursorRight();
                            break;
                        case 3:
                            cursorDown();
                            break;
                    }

                    if(selectionPhase == SelectionPhase.SPELLTARGETSELECT){
                        setSpell(spell);
                    }
                }
                else{
                    switch(dir){
                        case 0:
                            unitCreatePanel.up();
                            break;
                        case 1:
                            unitCreatePanel.left();
                            break;
                        case 2:
                            unitCreatePanel.right();
                            break;
                        case 3:
                            unitCreatePanel.down();
                            break;
                    }
                }
            }
            else{
                switch(dir){
                    case 0:
                        actionsPanel.up();
                        break;
                    case 3:
                        actionsPanel.down();
                        break;
                }
            }

            if(selectionPhase == SelectionPhase.UNITSELECTED){
                if(Game.game.getPaths().containsKey(view.getCursorRow()*map.getColumns()+view.getCursorColumn())){
                    setArrowSquares((ArrayList <ArrayList <Integer>>)Game.game.getPaths().get(view.getCursorRow()*map.getColumns()+view.getCursorColumn()));
                }
                else{
                    unsetArrowSquares();
                }
            }
        }

    }
    
    private void cursorUp(){
        if(selectionPhase != SelectionPhase.ATTACKTARGETSELECT){
            if(view.getCursorRow() > 0){
                if(view.getCursorRow()-1 < view.getDisplayRow()){
                    view.setDisplayRow(view.getDisplayRow()-1);
                }
                setCursor(view.getCursorRow()-1,view.getCursorColumn(),false);
            }
        }
        else{
            targetUnitIndex = targetUnitIndex-1 < 0 ? targetUnits.size()-1 : targetUnitIndex-1;
            setCursor(targetUnits.get(targetUnitIndex).getSquare().getRow(),targetUnits.get(targetUnitIndex).getSquare().getColumn(),true);
        }
    }
    
    private void cursorLeft(){
        if(selectionPhase != SelectionPhase.ATTACKTARGETSELECT){
            if(view.getCursorColumn() > 0){
                if(view.getCursorColumn()-1 < view.getDisplayColumn()){
                    view.setDisplayColumn(view.getDisplayColumn()-1);
                }
                setCursor(view.getCursorRow(),view.getCursorColumn()-1,false);
            }
        }
        else{
            targetUnitIndex = targetUnitIndex-1 < 0 ? targetUnits.size()-1 : targetUnitIndex-1;
            setCursor(targetUnits.get(targetUnitIndex).getSquare().getRow(),targetUnits.get(targetUnitIndex).getSquare().getColumn(),true);
        }
    }
    
    private void cursorRight(){
        if(selectionPhase != SelectionPhase.ATTACKTARGETSELECT){
            if(view.getCursorColumn()+1 < map.getColumns()){
                if(view.getCursorColumn()+1-view.getDisplayColumn() >= view.getMaxColumns()){
                    view.setDisplayColumn(view.getDisplayColumn()+1);
                }
                setCursor(view.getCursorRow(),view.getCursorColumn()+1,false);
            }
        }
        else{
            targetUnitIndex = (targetUnitIndex+1)%targetUnits.size();
            setCursor(targetUnits.get(targetUnitIndex).getSquare().getRow(),targetUnits.get(targetUnitIndex).getSquare().getColumn(),true);
        }
    }
    
    private void cursorDown(){
        if(selectionPhase != SelectionPhase.ATTACKTARGETSELECT){
            if(view.getCursorRow()+1 < map.getRows()){
                if(view.getCursorRow()+1-view.getDisplayRow() >= view.getMaxRows()){
                    view.setDisplayRow(view.getDisplayRow()+1);
                }
                setCursor(view.getCursorRow()+1,view.getCursorColumn(),false);
            }
        }
        else{
            targetUnitIndex = (targetUnitIndex+1)%targetUnits.size();
            setCursor(targetUnits.get(targetUnitIndex).getSquare().getRow(),targetUnits.get(targetUnitIndex).getSquare().getColumn(),true);
        }
    }
    
    private void drawHPBar(int row, int col){
        Unit unit = map.getSquare(row,col).getUnit();
        int xI = (col-view.getDisplayColumn())*32+5, yI = (row-view.getDisplayRow())*32+22;
        int hp = (int)unit.getStat(Unit.Stat.CURRHP);
        double redValue = hp/300.0 > 1 ? 0 : 1.0-(hp/300.0);
        double blueValue = hp/300.0 > 1 ? 1 : hp/300.0;
        double percentage = (100*(double)hp/(double)(int)unit.getStat(Unit.Stat.MAXHP));
        int barWidth = 20*(int)percentage/100;

        if(percentage > 0 && barWidth < 1){
            barWidth = 1;
        }
        
        g2D.setColor(Color.white);
        g2D.fillRect(xI+unit.getXOffset(),yI+unit.getYOffset(),22,6);
        g2D.setColor(Color.black);
        g2D.fillRect(xI+1+unit.getXOffset(),yI+1+unit.getYOffset(),20,4);
        g2D.setColor(new Color((int)(redValue*255),0,(int)(blueValue*255)));
        g2D.fillRect(xI+1+unit.getXOffset(),yI+1+unit.getYOffset(),barWidth,4);
        
    }
    
    private void drawAllegianceBar(int row, int col){
        City city = map.getSquare(row, col).getCity();
        int xI = (col-view.getDisplayColumn())*32, yI = (row-view.getDisplayRow())*32+11;
        int hp = city.getMaxHP()-city.getHP();
        double percentage = (100*(double)hp/(double)city.getMaxHP());
        int barWidth = 30*(int)percentage/100;
        
        if(percentage > 0 && barWidth < 1){
            barWidth = 1;
        }

        g2D.setColor(Color.white);
        g2D.fillRect(xI,yI,32,10);
        g2D.setColor(Game.game.getColor(city.getAllegiance()));
        g2D.fillRect(xI+1,yI+1,30,8);
        g2D.setColor(Game.game.getColor(city.getSquare().getUsed()));
        g2D.fillRect(xI+1,yI+1,barWidth,8);
    }
    
    public void setSpell(Spell newSpell){
        if(spell != newSpell){
            spell = newSpell;
        }
        selectionPhase = SelectionPhase.SPELLTARGETSELECT;
        setTransparencySquares(spell.getAffectedSquares(view.getCursorRow(), view.getCursorColumn(), map),1);
    }
    
    public void unsetSpell(){
        selectionPhase = SelectionPhase.NOSELECTION;
        unsetTransparencySquares();
    }
    
    protected void update(){
        
        Game.game.processActions();
        
        Game.game.getEventQueue().update();

        unitsToRender = (ArrayList <Unit>)Game.game.getUnits().clone();
        Collections.sort(unitsToRender);
        for(int i=0; i<Game.game.getUnits().size(); i++){
            Game.game.getUnits().get(i).update();
        }

    }
    
    protected void render(){
        boolean isInMap;
        ArrayList <ArrayList <Integer>> allegianceBars = new ArrayList <ArrayList<Integer>>();
        Unit unit;
        Square square;
        
        try {
            
            copyOfImage =  new BufferedImage(480, 480, BufferedImage.TYPE_INT_RGB);
            g2D = copyOfImage.createGraphics();
            
            for(int i=view.getDisplayRow(), r=0; i<view.getDisplayRow()+view.getMaxRows(); i++, r++){
                for(int j=view.getDisplayColumn(), c=0; j<view.getDisplayColumn()+view.getMaxColumns(); j++, c++){
                    isInMap = true;
                    colorToDraw = 0;
                    arrowToDraw = -1;
                    
                    if(i<0 || i>=map.getRows() || j<0 || j>=map.getColumns()){
                        isInMap = false;
                        g2D.setColor(Color.black);
                    }
                    else{
                        g2D.setColor(colours[map.getSquare(i, j).getTextureNumber()]);
                        
                        colorToDraw = transparencyColor[i][j];
                        arrowToDraw = squaresArrow[i][j];
                    }
                    
                    g2D.fillRect(c*32,r*32,32,32);
                    if(isInMap && map.getSquare(i,j).isCity()){
                        g2D.drawImage((BufferedImage)citySprites.get(map.getSquare(i,j).getCityAllegiance()), c*32, r*32, 32, 32, this);
                        if(map.getSquare(i,j).getCity().isSieged()){
                            allegianceBars.add(new ArrayList<Integer>(Arrays.asList(i,j)));
                        }
                    }
                    
                    switch(colorToDraw){
                        case 1:
                            g2D.drawImage(blueTransparency,c*32,r*32,blueTransparency.getWidth(),blueTransparency.getHeight(),null);
                            break;
                    }
                    
                    if(arrowToDraw > -1){
                        int from = arrowToDraw/4, to = arrowToDraw%4;
                        g2D.drawImage(pathArrow[from][to],c*32,r*32,pathArrow[from][to].getWidth(),pathArrow[from][to].getHeight(),null);
                    }
                    
                }
            }
            
            for(int i=0; i<unitsToRender.size(); i++){
                unit = unitsToRender.get(i);
                square = unit.getSquare();

                if(square.getRow()+(unit.getYOffset()/32) >= view.getDisplayRow() && square.getRow()+(unit.getYOffset()/32) < view.getDisplayRow()+view.getMaxRows()
                   && square.getColumn()+(unit.getXOffset()/32) >= view.getDisplayColumn() && square.getColumn()+(unit.getXOffset()/32) < view.getDisplayColumn()+view.getMaxColumns()){
                    
                    g2D.drawImage(unit.getFrame(),(square.getColumn()-view.getDisplayColumn())*32+unit.getXOffset(),(square.getRow()-view.getDisplayRow())*32+unit.getYOffset(),unit.getFrame().getWidth(),unit.getFrame().getHeight(),null);
                    if(showHPBars){
                        drawHPBar(square.getRow(), square.getColumn());
                    }
                }
            }
            
            for(int i=0; i<allegianceBars.size(); i++){
                drawAllegianceBar(allegianceBars.get(i).get(0),allegianceBars.get(i).get(1));
            }
            
            g2D.drawImage(cursor,(view.getCursorColumn()-view.getDisplayColumn())*32,(view.getCursorRow()-view.getDisplayRow())*32,cursor.getWidth(),cursor.getHeight(),null);
            
            if(spell != null && spell.isVisible()){
                g2D.drawImage(spell.getFrame(),(spell.getDrawColumn()-view.getDisplayColumn())*32+spell.getXOffset(),(spell.getDrawRow()-view.getDisplayRow())*32+spell.getYOffset(),spell.getFrame().getWidth(),spell.getFrame().getHeight(),null);
            }
            
        }catch(Exception e){}
    }
    
}
