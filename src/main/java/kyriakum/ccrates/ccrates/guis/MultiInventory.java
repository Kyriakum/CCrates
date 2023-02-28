package kyriakum.ccrates.ccrates.guis;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface MultiInventory {

    Inventory getInventory(int page);

    Integer getPage(Inventory inventory);

    Inventory createInventory(int page, List<ItemStack> items);

    void setupInvs();

    ItemStack nextPage();

    ItemStack previousPage();

}
