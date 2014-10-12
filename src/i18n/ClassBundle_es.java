
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
            { "gui.UnitInfoPanel.status"   , "Estado" },
            { "gui.MenuButtonsPanel.mainmenu"   , "Menú" },
            { "gui.MenuButtonsPanel.singleplayer"   , "Un jugador" },
            { "gui.MenuButtonsPanel.multiplayer"   , "Multijugador" },
            { "gui.MenuButtonsPanel.multijoin"   , "Unirse a partida" },
            { "gui.MenuButtonsPanel.multihost"   , "Crear partida" },
            { "gui.MenuButtonsPanel.settings"   , "Opciones" },
            { "gui.MenuButtonsPanel.language"   , "Idioma" },
            { "gui.MenuButtonsPanel.quit"   , "Salir" },
            { "gui.MenuButtonsPanel.back"   , "Atrás" },
            { "gui.HostGamePanel.server"   , "Servidor" },
            { "gui.HostGamePanel.noserver"   , "No se ha seleccionado servidor" },
            { "gui.HostGamePanel.input"   , "Introduzca aquí la dirección del servidor" },
            { "gui.HostGamePanel.connecting"   , "Conectando ..." },
            { "gui.HostGamePanel.failedconnection"   , "No se pudo conectar con el servidor" }
    };
    
}
