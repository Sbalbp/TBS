
package animationEvent;

import god.Spell;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class SpellAnimationEvent extends AnimationEvent{
    
    private Spell spell;
    private int speed;
    private int counter;
    
    public SpellAnimationEvent(Spell newSpell, int newSpeed){
        spell = newSpell;
        speed = newSpeed;
    }
    
    public void set(){
        spell.setCurrentFrame(0);
        spell.setVisible(true);
        counter = 0;
    }
    
    public void update(){
        if(counter >= speed){
            counter = 0;
            if(spell.update()){
                finish();
            }
        }
        else{
            counter++;
        }
    }
    
}
