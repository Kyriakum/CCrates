package kyriakum.ccrates.ccrates.api.crates;

import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CrateKeyChangeEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Crate crate;
    private ItemStack oldKey, newKey;
    public CrateKeyChangeEvent(Crate crate, ItemStack oldKey, ItemStack newKey){
        this.crate = crate;
        this.oldKey = oldKey;
        this.newKey = newKey;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS_LIST;
    }

    public ItemStack getNewKey() {
        return newKey;
    }

    public ItemStack getOldKey() {
        return oldKey;
    }

    public Crate getCrate() {
        return crate;
    }

}
