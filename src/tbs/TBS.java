
package tbs;

import game.*;
import gui.MainFrame;
import i18n.Localizer;
import java.util.ArrayList;
import map.*;
import map.square.*;
import unit.*;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class TBS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Game.game.setTeams(new int[][]{{0},{1}});
        Game.game.setTurn(0);
        Game.game.setPlayerTurn(0);
        
        Map m1 = new Map();
        m1.randomize(3,3);
        m1.getSquare(1,1).setCity(new City());
        m1.getSquare(1,1).setAllegiance(0);
        
        Game.game.newGame(m1);

        Footman f = new Footman(0,m1.getSquare(0, 0));
        Footman f2 = new Footman(1,m1.getSquare(2, 2));
        
        if(Settings.language == null){
            Settings.language = "en";
        }
        Localizer.setLanguage(Settings.language);
        MainFrame frame = new MainFrame();
        frame.setMapPanel(Game.game.getMap());
        frame.runMapPanel();
        
    }
    
}
