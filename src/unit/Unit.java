
package unit;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import map.square.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public abstract class Unit implements Comparable<Unit>{
    
    public enum Stat {
        OWNER, CURRHP, MAXHP, ATTACK, DEFENSE, DMGTYPE, ARMTYPE, SIEGE, MOVEMENT, CLIMB, MORALE, MINRANGE, MAXRANGE,
        RANGED, SICK, PARALYZED, SICKNESSIMM, PARALYSISIMM, DAMAGEIMM
    }
    
    // Owner
    private int owner;
    
    private boolean used;
    
    // Stats
    private int currHP;
    private int maxHP;
    private int attack;
    private int defense;
    private EquipmentType.DamageType dmgType;
    private EquipmentType.ArmorType armType;
    private int siege;
    private int movement;
    private int climb;
    private int morale;
    private int minRange;
    private int maxRange;
    
    // Flags
    private boolean ranged;
    private boolean sick;
    private boolean paralyzed;
    
    private boolean sicknessImmunity;
    private boolean paralysisImmunity;
    private boolean damageImmunity;
    
    // Buffs
    private ArrayList <Buff> buffs;
    
    // Square
    private Square currentSquare;
    
    // Sprite
    private Integer x;
    private Integer y;
    private String currentAnimation;
    private int currentFrame;
    private int animCounter;
    int maxFrames;
    private int animDoneTimes;
    
    public Unit(int newOwner){
        owner = newOwner;
        
        used = false;
        
        currHP = getBaseHP();
        maxHP = getBaseHP();
        attack = getBaseAttack();
        defense = getBaseDefense();
        dmgType = getDamageType();
        armType = getArmorType();
        siege = getBaseSiege();
        movement = getBaseMovement();
        climb = getBaseClimb();
        morale = getBaseMorale();
        minRange = getBaseMinRange();
        maxRange = getBaseMaxRange();
        
        ranged = isRanged();
        sick = false;
        paralyzed = false;
        
        sicknessImmunity = false;
        paralysisImmunity = false;
        damageImmunity = false;
        
        buffs = new ArrayList <Buff>();
        
        x = 0;
        y = 0;
    }
    
    public Unit(int newOwner, Square square){
        this(newOwner);
        currentSquare = square;
        currentSquare.setUnit(this);
    }
    
    public Object getStat(Stat stat){
        switch (stat){
            case OWNER:
                return owner;
            case CURRHP:
                return currHP;
            case MAXHP:
                return maxHP;
            case ATTACK:
                return attack;
            case DEFENSE:
                return defense;
            case DMGTYPE:
                return dmgType;
            case ARMTYPE:
                return armType;
            case SIEGE:
                return siege;
            case MOVEMENT:
                return movement;
            case CLIMB:
                return climb;
            case MORALE:
                return morale;
            case MINRANGE:
                return minRange;
            case MAXRANGE:
                return maxRange;
            case RANGED:
                return ranged;
            case SICK:
                return sick;
            case PARALYZED:
                return paralyzed;
            case SICKNESSIMM:
                return sicknessImmunity;
            case PARALYSISIMM:
                return paralysisImmunity;
            case DAMAGEIMM:
                return damageImmunity;
            default:
                return null;
        }
    }
    
    public void setStat(Stat stat, Object newValue){
        switch (stat){
            case OWNER:
                owner = (int)newValue;
                break;
            case CURRHP:
                if((int)newValue > maxHP){
                    newValue = maxHP;
                }
                currHP = (int)newValue;
                break;
            case MAXHP:
                maxHP = (int)newValue;
                if(currHP > maxHP){
                    currHP = maxHP;
                }
                break;
            case ATTACK:
                attack = (int)newValue;
                break;
            case DEFENSE:
                defense = (int)newValue;
                break;
            case DMGTYPE:
                dmgType = (EquipmentType.DamageType)newValue;
                break;
            case ARMTYPE:
                armType = (EquipmentType.ArmorType)newValue;
                break;
            case SIEGE:
                siege = (int)newValue;
                break;
            case MOVEMENT:
                movement = (int)newValue;
                break;
            case CLIMB:
                climb = (int)newValue;
                break;
            case MORALE:
                morale = (int)newValue;
                break;
            case MINRANGE:
                minRange = (int)newValue;
                break;
            case MAXRANGE:
                maxRange = (int)newValue;
                break;
            case RANGED:
                ranged = (boolean)newValue;
                break;
            case SICK:
                if((boolean)newValue == false || sicknessImmunity == false){
                    sick = (boolean)newValue;
                }
                break;
            case PARALYZED:
                if((boolean)newValue == false || paralysisImmunity == false){
                    paralyzed = (boolean)newValue;
                }
                break;
            case SICKNESSIMM:
                sicknessImmunity = (boolean)newValue;
                break;
            case PARALYSISIMM:
                paralysisImmunity = (boolean)newValue;
                break;
            case DAMAGEIMM:
                damageImmunity = (boolean)newValue;
                break;
        }
    }
    
    public boolean isDead(){
        return currHP <= 0;
    }
    
    public boolean isUsed(){
        return used;
    }
    
    public void setUsed(boolean value){
        setAnimation(value ? "idleUsed" : "idle");
        used = value;
    }
    
    public Square getSquare(){
        return currentSquare;
    }
    
    public void setSquare(Square newSquare){
        if(currentSquare != null || newSquare == null){
            currentSquare.setUnit(null);
        }
        currentSquare = newSquare;
        if(currentSquare != null){
            currentSquare.setUnit(this);
        }
    }
    
    public ArrayList<Buff> getBuffs(){
        return buffs;
    }
    
    public boolean addBuff(Buff newBuff){
        if(newBuff.isSetter()){
            for(int i=0; i<buffs.size(); i++){
                if(buffs.get(i).getStat() == newBuff.getStat()){
                    removeBuff(i);
                    i--;
                }
            }
        }
        else{
            for(int i=0; i<buffs.size(); i++){
                if(buffs.get(i).getStat() == newBuff.getStat() && buffs.get(i).isSetter()){
                    return false;
                }
            }
        }
        
        newBuff.setOwner(this);
        newBuff.initBuff();
        buffs.add(newBuff);
        
        return true;
    }
    
    public void updateBuffs(){
        for(int i=0; i<buffs.size(); i++){
            if(buffs.get(i).updateBuff()){
                buffs.remove(i);
                i--;
            }
        }
    }
    
    public void removeBuff(int index){
        buffs.get(index).endBuff();
        buffs.remove(index);
    }
    
    public static int getBaseMorale(){
        return 40;
    }
    
    public void setXOffset(int offset){
        x = offset;
    }
    
    public int getXOffset(){
        return x.intValue();
    }
    
    public void setYOffset(int offset){
        y = offset;
    }
    
    public int getYOffset(){
        return y.intValue();
    }
    
    public String getAnimation(){
        return currentAnimation;
    }
    
    public void setAnimation(String name){
        if(!name.equals(currentAnimation)){
            currentAnimation = name;
            currentFrame = 0;
            animCounter = 0;
            animDoneTimes = 0;
            setMaxFrames();
        }
    }
    
    public void setCurrentFrame(int frame){
        if(frame < maxFrames){
            currentFrame = frame;
        }
    }
    
    public int getCurrentFrame(){
        return currentFrame;
    }
    
    public void update(){
        animCounter++;
        if(animCounter >= getAnimationSpeed()){
            currentFrame = (currentFrame+1)%maxFrames;
            if(currentFrame == 0){
                animDoneTimes++;
            }
            animCounter = 0;
        }
    }
    
    public int timesAnim(){
        return animDoneTimes;
    }
    
    public int compareTo(Unit compareUnit){
        int compareTo = (compareUnit.getSquare().getRow()*32)+compareUnit.getYOffset();
        
        return (getSquare().getRow()*32)+y-compareTo;
    }
    
    public abstract String getName();
    public abstract int getBaseHP();
    public abstract int getBaseAttack();
    public abstract int getBaseDefense();
    public abstract EquipmentType.DamageType getDamageType();
    public abstract EquipmentType.ArmorType getArmorType();
    public abstract int getBaseSiege();
    public abstract int getBaseMovement();
    public abstract int getBaseClimb();
    public abstract int getBaseMinRange();
    public abstract int getBaseMaxRange();
    public abstract boolean isRanged();
    public abstract int getAnimationSpeed();
    public abstract void setMaxFrames();
    public abstract BufferedImage getFrame();

}
