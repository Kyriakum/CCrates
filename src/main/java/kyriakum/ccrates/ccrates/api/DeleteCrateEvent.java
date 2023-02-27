package kyriakum.ccrates.ccrates.api;

import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeleteCrateEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Crate crate;

    public DeleteCrateEvent(Crate crate){
        this.crate = crate;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlersList(){
        return HANDLERS_LIST;
    }



    public Crate getCrate() {
        return crate;
    }

}
