
package unit;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class EquipmentType {
    
    public enum DamageType {
        SLASHING, PIERCING, CLEAVING, DEMOLISHING, DIVINE
    }
    
    public enum ArmorType {
        LIGHT, MEDIUM, HEAVY, ARMORED, DIVINE
    }
    
    private EquipmentType(){}
    
    public static double dmgMultiplier(DamageType dmg, ArmorType arm){
        
        switch(dmg){
            case SLASHING:
                switch(arm){
                    case LIGHT:
                        return 1.25;
                    case MEDIUM:
                        return 1;
                    case HEAVY:
                        return 0.75;
                    case ARMORED:
                        return 0.75;
                    case DIVINE:
                        return 0.5;
                }
                break;
            case PIERCING:
                switch(arm){
                    case LIGHT:
                        return 1.5;
                    case MEDIUM:
                        return 1.25;
                    case HEAVY:
                        return 0.75;
                    case ARMORED:
                        return 0.75;
                    case DIVINE:
                        return 0.5;
                }
                break;
            case CLEAVING:
                switch(arm){
                    case LIGHT:
                        return 1;
                    case MEDIUM:
                        return 1;
                    case HEAVY:
                        return 1.25;
                    case ARMORED:
                        return 1;
                    case DIVINE:
                        return 0.5;
                }
                break;
            case DEMOLISHING:
                switch(arm){
                    case LIGHT:
                        return 0.5;
                    case MEDIUM:
                        return 0.75;
                    case HEAVY:
                        return 1;
                    case ARMORED:
                        return 1.25;
                    case DIVINE:
                        return 0.5;
                }
                break;
            case DIVINE:
                switch(arm){
                    case LIGHT:
                        return 1.5;
                    case MEDIUM:
                        return 1.5;
                    case HEAVY:
                        return 1.5;
                    case ARMORED:
                        return 1.5;
                    case DIVINE:
                        return 1;
                }
                break;
        }
        return 1;
        
    }
    
}
