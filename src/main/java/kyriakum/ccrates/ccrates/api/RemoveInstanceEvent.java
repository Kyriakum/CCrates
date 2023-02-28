package kyriakum.ccrates.ccrates.api;

import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.CrateInstance;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RemoveInstanceEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private CrateInstance instance;

    public RemoveInstanceEvent(CrateInstance instance){
        this.instance = instance;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS_LIST;
    }

    public CrateInstance getCrateInstance() {
        return instance;
    }
}
