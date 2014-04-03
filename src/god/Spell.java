
package god;

import animationEvent.*;
import game.Game;
import game.Settings;
import gui.View;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import map.*;
import sprite.Sprite;
import unit.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Spell {
    
    public enum AOE {
        SINGLE, AREA, HORIZONTAL, VERTICAL, ALL, NONE
    }
    
    public enum Target {
        SELF, ALLY, ENEMY, ALL, RANDOM, NONE
    }
    
    private AOE aoe;
    private Target targets;
    
    private int size;
    
    private Sprite sprite;
    private int currentFrame;
    private int speed;
    
    private boolean bypassImmunity;
    private int dmgDealtAlly;
    private boolean isPercentageAlly;
    private int dmgDealtEnemy;
    private boolean isPercentageEnemy;
    
    private boolean visible;
    private int drawRow;
    private int drawCol;
    private int xOffset;
    private int yOffset;
    
    private ArrayList <Integer> buffsAlly;
    private ArrayList <Integer> buffsEnemy;
    
    private ArrayList <ArrayList <Integer>> squaresAffected;
    private ArrayList <Unit> unitsAffected;
    
    // THIS IS A PLACEHOLDER CONSTRUCTOR FOR NOW
    public Spell(){
        aoe = AOE.VERTICAL;
        targets = Target.ALL;
        
        size = 3;
        
        sprite = new Sprite();
        sprite.addAnimationSeparate("play",Settings.getImgRoute()+"/animation/anim0","png");
        currentFrame = 0;
        speed = 6;
        
        bypassImmunity = false;
        dmgDealtAlly = 20;
        isPercentageAlly = false;
        dmgDealtEnemy = 40;
        isPercentageEnemy = false;
        
        visible = false;
        
        xOffset = -16;
        yOffset = -16;
        
        buffsAlly = new ArrayList<Integer>();
        buffsEnemy = new ArrayList<Integer>();
    }
    
    public int getDrawRow(){
        return drawRow;
    }
    
    public int getDrawColumn(){
        return drawCol;
    }
    
    public void setCurrentFrame(int newFrame){
        currentFrame = newFrame;
    }
    
    public void setVisible(boolean value){
        visible = value;
    }
    
    public boolean isVisible(){
        return visible;
    }
    
    public int getXOffset(){
        return xOffset;
    }
    
    public int getYOffset(){
        return yOffset;
    }
    
    public BufferedImage getFrame(){
        return sprite.getFrame("play", currentFrame);
    }
    
    public void setDrawingCoordinates(View view){
        switch(aoe){
            case SINGLE:
                drawRow = view.getCursorRow();
                drawCol = view.getCursorColumn();
                break;
            case AREA:
                drawRow = view.getCursorRow();
                drawCol = view.getCursorColumn();
                break;
            case HORIZONTAL:
                drawRow = view.getCursorRow();
                drawCol = 0;
                break;
            case VERTICAL:
                drawRow = 0;
                drawCol = view.getCursorColumn();
                break;
            case ALL:
                drawRow = view.getDisplayRow()+view.getMaxRows()/2;
                drawCol = view.getDisplayColumn()+view.getMaxColumns()/2;
                break;
            case NONE:
                drawRow = view.getDisplayRow()+view.getMaxRows()/2;
                drawCol = view.getDisplayColumn()+view.getMaxColumns()/2;
                break;
        }
    }
    
    public ArrayList <ArrayList <Integer>> getAffectedSquares(int row, int column, Map map){
        squaresAffected = new ArrayList <ArrayList <Integer>>();
        
        switch(aoe){
            case SINGLE:
                squaresAffected.add(new ArrayList <Integer> (Arrays.asList(row,column)));
                break;
            case AREA:
                squaresAffected = MapUtils.getIndexPossibleTargets(row, column, 0, size, map);
                break;
            case HORIZONTAL:
                for(int i=row; i<row+size && i<map.getRows(); i++){
                    for(int j=0; j<map.getColumns(); j++){
                        squaresAffected.add(new ArrayList <Integer> (Arrays.asList(i,j)));
                    }
                }
                break;
            case VERTICAL:
                for(int i=0; i<map.getRows(); i++){
                    for(int j=column; j<column+size && j<map.getColumns(); j++){
                        squaresAffected.add(new ArrayList <Integer> (Arrays.asList(i,j)));
                    }
                }
                break;
            case ALL:
                for(int i=0; i<map.getRows(); i++){
                    for(int j=0; j<map.getColumns(); j++){
                        squaresAffected.add(new ArrayList <Integer> (Arrays.asList(i,j)));
                    }
                }
                break;
        }
        
        return squaresAffected;
    }
    
    private boolean isAffected(Unit unit, int player){
        if(unit == null){
            return false;
        }
        
        switch(targets){
            case SELF:
                return (int)unit.getStat(Unit.Stat.OWNER) == player;
            case ALLY:
                return Game.game.getTeam((int)unit.getStat(Unit.Stat.OWNER)) == Game.game.getTeam(player);
            case ENEMY:
                return Game.game.getTeam((int)unit.getStat(Unit.Stat.OWNER)) != Game.game.getTeam(player);
            case ALL:
                return true;
            default:
                return false;
        }
    }
    
    public ArrayList <Unit> getAffectedUnits(int player, Map map){
        Unit currentUnit;
        
        if(squaresAffected != null){
            unitsAffected = new ArrayList <Unit>();
            
            for(int i=0; i<squaresAffected.size(); i++){
                currentUnit = map.getSquare(squaresAffected.get(i).get(0), squaresAffected.get(i).get(1)).getUnit();
                
                if(isAffected(currentUnit,player)){
                    unitsAffected.add(currentUnit);
                }
            }
        }

        return unitsAffected;

    }
    
    private int dmgToUnit(Unit unit, int player){
        int dmgToReturn;
        
        if(Game.game.getTeam((int)unit.getStat(Unit.Stat.OWNER)) == Game.game.getTeam(player)){
            dmgToReturn = isPercentageAlly ? (int)((int)unit.getStat(Unit.Stat.MAXHP) * Math.signum(dmgDealtAlly) *(0.01*Math.abs(dmgDealtAlly))) : dmgDealtAlly;
        }
        else{
            dmgToReturn = isPercentageEnemy ? (int)((int)unit.getStat(Unit.Stat.MAXHP) * Math.signum(dmgDealtEnemy) *(0.01*Math.abs(dmgDealtEnemy))) : dmgDealtEnemy;
        }
        
        if((boolean)unit.getStat(Unit.Stat.DAMAGEIMM) && !bypassImmunity){
            dmgToReturn = 0;
        }
        
        return dmgToReturn;  
    }
    
    private void addBuffs(Unit unit, int player){
        Buff buff;
        ArrayList<Integer> buffs = Game.game.getTeam((int)unit.getStat(Unit.Stat.OWNER)) == Game.game.getTeam(player) ? buffsAlly : buffsEnemy;
        
        for(int i=0; i<buffs.size(); i++){
            buff = new Buff();
            unit.addBuff(buff);
        }
    }
    
    public void apply(int player){
        int dmgValue;
        int[] dmgDealtArray;
        Unit[] unitsArray;
        ArrayList<Integer> dmg = new ArrayList<Integer>();
        
        Game.game.getEventQueue().add(new AnimationEvent[]{
            new SpellAnimationEvent(this,speed)
        });
        
        if(unitsAffected != null){
            for(int i=0; i<unitsAffected.size(); i++){
                dmgValue = dmgToUnit(unitsAffected.get(i),player);
                dmg.add(dmgValue);
                addBuffs(unitsAffected.get(i),player);
            }
            
            dmgDealtArray = new int[dmg.size()];
            for(int i=0; i<dmg.size(); i++){
                dmgDealtArray[i] = dmg.get(i).intValue();
            }
            unitsArray = new Unit[unitsAffected.size()];
            unitsArray = unitsAffected.toArray(unitsArray);

            Game.game.damageUnits(unitsArray,dmgDealtArray);

        }
        
    }
    
    public boolean update(){
        if(currentFrame == sprite.numFrames("play")){
            visible = false;
            currentFrame = 0;
            return true;
        }
        else{
            currentFrame++;
            return false;
        }
    }
    
}
