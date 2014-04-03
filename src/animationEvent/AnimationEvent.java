
package animationEvent;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public abstract class AnimationEvent {
    
    private boolean finished;
    
    public AnimationEvent(){
        finished = false;
    }
    
    public void finish(){
        finished = true;
    }
    
    public boolean isFinished(){
        return finished;
    }
    
    public abstract void set();
    public abstract void update();
    
}
