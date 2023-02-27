package kyriakum.ccrates.ccrates.api;

import kyriakum.ccrates.ccrates.entities.CrateEntity;
import kyriakum.ccrates.ccrates.entities.CrateInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerOpenCrateEntityEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player player;
    private CrateEntity entity;

    public PlayerOpenCrateEntityEvent(Player player, CrateEntity entity){
        this.player = player;
        this.entity = entity;
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

    public CrateEntity getCrateEntity() {
        return entity;
    }
}
