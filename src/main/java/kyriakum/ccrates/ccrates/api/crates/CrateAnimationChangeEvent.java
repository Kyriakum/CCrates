package kyriakum.ccrates.ccrates.api.crates;

import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CrateAnimationChangeEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Crate crate;
    private AnimationType oldAnimation, newAnimation;
    public CrateAnimationChangeEvent(Crate crate, AnimationType oldAnimation, AnimationType newAnimation){
        this.crate = crate;
        this.oldAnimation = oldAnimation;
        this.newAnimation = newAnimation;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS_LIST;
    }


    public AnimationType getOldAnimation() {
        return oldAnimation;
    }

    public AnimationType getNewAnimation() {
        return newAnimation;
    }

    public Crate getCrate() {
        return crate;
    }

}
