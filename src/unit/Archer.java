
package unit;

import game.Settings;
import java.awt.image.BufferedImage;
import map.square.*;
import sprite.Sprite;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Archer extends Unit{
    
    private static int unitCode = 1;
    private static String unitString = "archer";
    private static Sprite sprite[] = {null,null,null,null,null,null,null,null};
    private static boolean initialized[] = {false,false,false,false,false,false,false,false};
    
    public static void initialize(int owner){
        sprite[owner] = new Sprite();
        sprite[owner].addAnimationSeparate("idle",Settings.getImgRoute()+"/unit/"+unitString+"/"+owner+"/idle","png");
        sprite[owner].addAnimationSeparate("right",Settings.getImgRoute()+"/unit/"+unitString+"/"+owner+"/right","png");
        sprite[owner].addAnimationFlip("left","right");
        sprite[owner].addAnimationSeparate("up",Settings.getImgRoute()+"/unit/"+unitString+"/"+owner+"/up","png");
        sprite[owner].addAnimationSeparate("down",Settings.getImgRoute()+"/unit/"+unitString+"/"+owner+"/down","png");
        sprite[owner].addAnimationSeparate("attackRight",Settings.getImgRoute()+"/unit/"+unitString+"/"+owner+"/attack","png");
        sprite[owner].addAnimationFlip("attackLeft","attackRight");
        sprite[owner].addAnimationSeparate("idleUsed",Settings.getImgRoute()+"/unit/"+unitString+"/"+owner+"/idleUsed","png");
        sprite[owner].addAnimationSeparate("death",Settings.getImgRoute()+"/unit/"+unitString+"/"+owner+"/death","png");
        
        initialized[owner] = true;
    }
    
    private static Sprite getSprite(int owner){
        return sprite[owner];
    }
    
    public Archer(int owner){
        super(owner);
        setSprite();
    }
    
    public Archer(int owner, Square square){
        super(owner, square);
        setSprite();
    }
    
    private void setSprite(){
        if(!Archer.initialized[(int)getStat(Unit.Stat.OWNER)]){
            Archer.initialize((int)getStat(Unit.Stat.OWNER));
        }
        setAnimation("idle");
    }
    
    public String getName(){
        return "archer";
    }
    
    public int getBaseHP(){
        return 40;
    }
    
    public int getBaseAttack(){
        return 3;
    }
    
    public EquipmentType.DamageType getDamageType(){
        return EquipmentType.DamageType.PIERCING;
    }
    
    public EquipmentType.ArmorType getArmorType(){
        return EquipmentType.ArmorType.LIGHT;
    }
    
    public int getBaseDefense(){
        return 2;
    }
    
    public int getBaseSiege(){
        return 25;
    }
    
    public int getBaseMovement(){
        return 3;
    }
    
    public int getBaseClimb(){
        return 1;
    }
    
    public int getBaseMinRange(){
        return 2;
    }
    
    public int getBaseMaxRange(){
        return 3;
    }
    
    public boolean isRanged(){
        return true;
    }
    
    public int getAnimationSpeed(){
        return 8;
    }
    
    public void setMaxFrames(){
        maxFrames = Archer.getSprite((int)getStat(Unit.Stat.OWNER)).numFrames(getAnimation());
    }
    
    public BufferedImage getFrame(){
        return Archer.getSprite((int)getStat(Unit.Stat.OWNER)).getFrame(getAnimation(), getCurrentFrame());
    }
    
}
