package kyriakum.ccrates.ccrates.api.contents;

import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.contents.Content;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RemoveContentEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Crate crate;
    private Content content;

    public RemoveContentEvent(Crate crate, Content content){
        this.crate = crate;
        this.content = content;
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

    public Content getContent() {
        return content;
    }
}
