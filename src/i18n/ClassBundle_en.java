
package i18n;

import java.util.ListResourceBundle;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class ClassBundle_en extends ListResourceBundle{
    
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            { "game.frameName"   , "Turn Based Strategy Game" },
            { "unit.footman"   , "Footman" },
            { "unit.archer"   , "Archer" },
            { "gui.ActionsPanel.move"   , "Move" },
            { "gui.ActionsPanel.attack"   , "Attack" },
            { "gui.ActionsPanel.wait"   , "Wait" },
            { "gui.ActionsPanel.ransack"   , "Ransack" },
            { "gui.ActionsPanel.capture"   , "Capture" },
            { "gui.UnitInfoPanel.unit"   , "Unit" },
            { "gui.UnitInfoPanel.player"   , "Player" },
            { "gui.UnitInfoPanel.team"   , "Team" }
    };
    
}
