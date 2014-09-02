
package i18n;

import java.util.ListResourceBundle;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class ClassBundle_es extends ListResourceBundle{
    
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            { "game.frameName"   , "Juego de Estrategia por Turnos" },
            { "unit.footman"   , "Infantería" },
            { "unit.archer"   , "Arquero" },
            { "unit.damageType.slashing"   , "Cortante" },
            { "unit.damageType.piercing"   , "Penetrante" },
            { "unit.damageType.cleaving"   , "Despedazante" },
            { "unit.damageType.demolishing"   , "Demoledor" },
            { "unit.damageType.divine"   , "Divino" },
            { "unit.armorType.light"   , "Ligera" },
            { "unit.armorType.medium"   , "Media" },
            { "unit.armorType.heavy"   , "Armadura" },
            { "unit.armorType.armored"   , "Acorazada" },
            { "unit.armorType.divine"   , "Divina" },
            { "unit.buff.name.blessedweapon"   , "Armas bendecidas" },
            { "gui.ActionsPanel.move"   , "Mover" },
            { "gui.ActionsPanel.attack"   , "Atacar" },
            { "gui.ActionsPanel.wait"   , "Esperar" },
            { "gui.ActionsPanel.ransack"   , "Saquear" },
            { "gui.ActionsPanel.capture"   , "Capturar" },
            { "gui.BuffPanel.remaining"   , "Turnos restantes" },
            { "gui.UnitInfoPanel.unit"   , "Unidad" },
            { "gui.UnitInfoPanel.player"   , "Jugador" },
            { "gui.UnitInfoPanel.team"   , "Equipo" },
            { "gui.UnitInfoPanel.stats"   , "Características" },
            { "gui.UnitInfoPanel.status"   , "Estado" }
    };
    
}
