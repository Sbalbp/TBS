
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
            { "gui.ActionsPanel.move"   , "Mover" },
            { "gui.ActionsPanel.attack"   , "Atacar" },
            { "gui.ActionsPanel.wait"   , "Esperar" },
            { "gui.ActionsPanel.ransack"   , "Saquear" },
            { "gui.ActionsPanel.capture"   , "Capturar" },
            { "gui.UnitInfoPanel.unit"   , "Unidad" },
            { "gui.UnitInfoPanel.player"   , "Jugador" },
            { "gui.UnitInfoPanel.team"   , "Equipo" }
    };
    
}
