
package unit;

import game.Game;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Buff {
    
    private Unit owner;
    
    private String name;
    private String description;
    
    private Unit.Stat stat;
    
    private boolean returns;
    private boolean setter;
    private boolean progressive;
    private boolean debuff;
    
    private int step;
    private int change;
    private int currentChange;
    
    private Object setValue;
    private Object previousValue;
    
    private int turns;
    private int totalTurns;
    
    // THIS IS A PLACEHOLDER CONSTRUCTOR FOR NOW
    public Buff(){
        owner = null;
        
        stat = Unit.Stat.ATTACK;
        returns = true;
        setter = false;
        progressive = false;
        debuff = false;
        
        step = 0;
        change = 1;
        currentChange = 0;
        
        turns = 0;
        totalTurns = 3;
    }
    
    public Unit getOwner(){
        return owner;
    }
    
    public void setOwner(Unit newOwner){
        owner = newOwner;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public Unit.Stat getStat(){
        return stat;
    }
    
    public boolean isSetter(){
        return setter;
    }
    
    public boolean isDebuff(){
        return debuff;
    }
    
    public void initBuff(){
        if(owner != null){
            turns = 0;
            if(setter){
                previousValue = owner.getStat(stat);
                owner.setStat(stat,setValue);
            }
            else{
                currentChange = change;
                if(stat == Unit.Stat.CURRHP){
                    Game.game.damageUnits(new Unit[]{owner},new int[]{-change});
                }
                else{
                    owner.setStat(stat,(int)owner.getStat(stat)+change);
                }
            }
        }
    }
    
    public void endBuff(){
        if(owner != null){
            if(returns){
                if(setter){
                    owner.setStat(stat,previousValue);
                }
                else{
                    owner.setStat(stat, (int)owner.getStat(stat)-currentChange);
                }
            }
            owner = null;
        }
    }
    
    public boolean updateBuff(){
        
        if(owner != null){
             
            turns++;
            if(turns == totalTurns){
                // BUFF ENDS HERE
                endBuff();
                return true;
            }
            else{
                if(progressive){
                    int increase = (change*(1-step))+(change*turns*step);
                    currentChange += increase;
                    
                    if(stat == Unit.Stat.CURRHP){
                        Game.game.damageUnits(new Unit[]{owner},new int[]{-increase});
                    }
                    else{
                        owner.setStat(stat, (int)owner.getStat(stat)+increase);
                    }
                }
                return false;
            }
            
        }
        
        return true;
    }
    
}
