
package animationEvent;

import java.util.ArrayList;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class AnimationEventQueue {

    private ArrayList <ArrayList <AnimationEvent>> queue;
    
    public AnimationEventQueue(){
        queue = new ArrayList <ArrayList <AnimationEvent>>();
    }
    
    public int size(){
        return queue.size();
    }
    
    public void add(AnimationEvent[] simultaneousEvents){
        ArrayList <AnimationEvent> newElement = new ArrayList <AnimationEvent>();
        
        for(int i=0; i<simultaneousEvents.length; i++){
            newElement.add(simultaneousEvents[i]);
        }
        
        queue.add(newElement);
        
        if(queue.size() == 1){
            for(int i=0; i<queue.get(0).size(); i++){
                queue.get(0).get(i).set();
            }
        }
    }
    
    public void update(){
        ArrayList <AnimationEvent> currentAnimations;
        boolean allFinished;
        
        if(queue.size() > 0){
            allFinished = true;
            currentAnimations = queue.get(0);
            for(int i=0; i<currentAnimations.size(); i++){
                if(!currentAnimations.get(i).isFinished()){
                    allFinished = false;
                    currentAnimations.get(i).update();
                }
            }
            
            if(allFinished){
                queue.remove(0);
                if(queue.size() > 0){
                    for(int i=0; i<queue.get(0).size(); i++){
                        queue.get(0).get(i).set();
                    }
                }
            }
        }
    }
    
}
