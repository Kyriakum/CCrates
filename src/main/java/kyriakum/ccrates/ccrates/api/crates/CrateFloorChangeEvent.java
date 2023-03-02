package kyriakum.ccrates.ccrates.api.crates;

import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CrateFloorChangeEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Crate crate;
    private Material oldFloor, newFloor;
    public CrateFloorChangeEvent(Crate crate, Material oldFloor, Material newFloor){
        this.crate = crate;
        this.oldFloor = oldFloor;
        this.newFloor = newFloor;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS_LIST;
    }

    public Material getNewFloor() {
        return newFloor;
    }

    public Material getOldFloor() {
        return oldFloor;
    }

    public Crate getCrate() {
        return crate;
    }

}
