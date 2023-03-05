package kyriakum.ccrates.ccrates.api.crates;

import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CrateDisableEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Crate crate;

    public CrateDisableEvent(Crate crate){
        this.crate = crate;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS_LIST;
    }

    public Crate getCrate() {
        return crate;
    }

}
