package kyriakum.ccrates.ccrates.api;

import kyriakum.ccrates.ccrates.entities.CrateInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerOpenCrateEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player player;
    private CrateInstance instance;

    public PlayerOpenCrateEvent(Player player, CrateInstance instance){
        this.player = player;
        this.instance = instance;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public CrateInstance getCrateInstance() {
        return instance;
    }
}
