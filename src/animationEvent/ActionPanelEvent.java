
package animationEvent;

import gui.ActionsPanel;
import map.Map;
import map.MapUtils;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class ActionPanelEvent extends AnimationEvent{
    
    private ActionsPanel actionsPanel;
    private int row;
    private int col;
    private Map map;
    
    public ActionPanelEvent(ActionsPanel newPanel, int newRow, int newCol, Map newMap){
        actionsPanel = newPanel;
        row = newRow;
        col = newCol;
        map = newMap;
    }
    
    public void set(){
    }
    
    public void update(){
        actionsPanel.setActions(MapUtils.getAvailableActions(row, col, map));
        actionsPanel.setOnSquare(row,col,map);
        actionsPanel.setVisible(true);
        finish();
    }
    
}
