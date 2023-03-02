package kyriakum.ccrates.ccrates.api.crates;

import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CrateBlockChangeEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Crate crate;
    private Material oldBlock, newBlock;
    public CrateBlockChangeEvent(Crate crate, Material oldBlock, Material newBlock){
        this.crate = crate;
        this.oldBlock = oldBlock;
        this.newBlock = newBlock;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS_LIST;
    }


    public Material getNewBlock() {
        return newBlock;
    }

    public Material getOldBlock() {
        return oldBlock;
    }

    public Crate getCrate() {
        return crate;
    }

}
