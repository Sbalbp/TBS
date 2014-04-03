
package unit;

import game.Game;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class Battle {
    
    private static int dmgDealtPerAttack = 15;
    private static int dmgBlockedPerDefense = 5;
    private static int dmgPerElevation = 5;
    
    private static double dmgDealt(Unit attacker, Unit defender){
        double baseAtk, baseDef, dmg;
        
        if((boolean)defender.getStat(Unit.Stat.DAMAGEIMM)){
            return 0;
        }
        
        baseAtk = (int)attacker.getStat(Unit.Stat.ATTACK)*dmgDealtPerAttack +
                (attacker.getSquare().getElevation()-defender.getSquare().getElevation())*dmgPerElevation;
        baseDef = (int)defender.getStat(Unit.Stat.DEFENSE)*dmgBlockedPerDefense +
                (defender.getSquare().getDefense()*dmgBlockedPerDefense);
        
        baseAtk = (boolean)attacker.getStat(Unit.Stat.SICK) ? baseAtk*0.8 : baseAtk;
        baseDef = (boolean)defender.getStat(Unit.Stat.SICK) ? baseDef*0.8 : baseDef;
        
        dmg = (baseAtk-baseDef);
        dmg *= EquipmentType.dmgMultiplier((EquipmentType.DamageType)attacker.getStat(Unit.Stat.DMGTYPE), (EquipmentType.ArmorType)defender.getStat(Unit.Stat.ARMTYPE));
        dmg *= (int)attacker.getStat(Unit.Stat.MORALE)/(double)Unit.getBaseMorale();
        dmg *= Math.pow(2,(int)attacker.getStat(Unit.Stat.CURRHP)/(double)(int)attacker.getStat(Unit.Stat.MAXHP))/2.0;
        
        return dmg;

    }
    
    public static void combat(Unit attacker, Unit defender){
        int dmgToAttacker = 0, dmgToDefender = 0;
        
        dmgToDefender = (int)dmgDealt(attacker, defender);
        if(!attacker.isRanged()){
            dmgToAttacker = (int)dmgDealt(defender, attacker);
        }
        
        Game.game.damageUnits(new Unit[]{attacker,defender}, new int[]{dmgToAttacker,dmgToDefender});
        System.out.println("attacker receives "+dmgToAttacker+" dmg");
        System.out.println("defender receives "+dmgToDefender+" dmg");
        
    }
    
}
