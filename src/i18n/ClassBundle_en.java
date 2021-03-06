
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
            { "unit.damageType.slashing"   , "Slashing" },
            { "unit.damageType.piercing"   , "Piercing" },
            { "unit.damageType.cleaving"   , "Cleaving" },
            { "unit.damageType.demolishing"   , "Demolishing" },
            { "unit.damageType.divine"   , "Divine" },
            { "unit.armorType.light"   , "Light" },
            { "unit.armorType.medium"   , "Medium" },
            { "unit.armorType.heavy"   , "Heavy" },
            { "unit.armorType.armored"   , "Armored" },
            { "unit.armorType.divine"   , "Divine" },
            { "unit.buff.name.blessedweapon"   , "Blessed weapons" },
            { "gui.ActionsPanel.move"   , "Move" },
            { "gui.ActionsPanel.attack"   , "Attack" },
            { "gui.ActionsPanel.wait"   , "Wait" },
            { "gui.ActionsPanel.ransack"   , "Ransack" },
            { "gui.ActionsPanel.capture"   , "Capture" },
            { "gui.BuffPanel.remaining"   , "Remaining turns" },
            { "gui.UnitInfoPanel.unit"   , "Unit" },
            { "gui.UnitInfoPanel.player"   , "Player" },
            { "gui.UnitInfoPanel.team"   , "Team" },
            { "gui.UnitInfoPanel.stats"   , "Stats" },
            { "gui.UnitInfoPanel.status"   , "Status" },
            { "gui.MenuButtonsPanel.mainmenu"   , "Main menu" },
            { "gui.MenuButtonsPanel.singleplayer"   , "Singleplayer" },
            { "gui.MenuButtonsPanel.multiplayer"   , "Multiplayer" },
            { "gui.MenuButtonsPanel.multijoin"   , "Join a game" },
            { "gui.MenuButtonsPanel.multihost"   , "Host a game" },
            { "gui.MenuButtonsPanel.settings"   , "Settings" },
            { "gui.MenuButtonsPanel.language"   , "Language" },
            { "gui.MenuButtonsPanel.quit"   , "Quit" },
            { "gui.MenuButtonsPanel.back"   , "Back" },
            { "gui.MapSelectPanel.players"   , "players" },
            { "gui.HostGamePanel.server"   , "Server" },
            { "gui.HostGamePanel.noserver"   , "No server selected" },
            { "gui.HostGamePanel.input"   , "Enter here the server address" },
            { "gui.HostGamePanel.connecting"   , "Connecting ..." },
            { "gui.HostGamePanel.failedconnection"   , "Couldn't reach the server" }
    };
    
}
