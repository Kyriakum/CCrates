package kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis;

import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface ChangeValue {

    ChangeValueType getType();

    Inventory getInventory();

    //Accept Stack is null if you want the inventory to apply changes when closing it
    boolean accept(ItemStack stack);

    ItemStack acceptStack();

    Crate getCrate();
}
