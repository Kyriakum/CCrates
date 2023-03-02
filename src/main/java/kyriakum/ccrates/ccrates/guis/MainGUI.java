package kyriakum.ccrates.ccrates.guis;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MainGUI extends MultiInventory {

    private Map<Integer, Inventory> maingui;
    private final int SIZE = 9*3;
    private final CCrates cCrates;

    public MainGUI(CCrates cCrates){
        super("Crates GUI");
        this.cCrates = cCrates;
        setupInvs();

    }

    @Override
    protected List<ItemStack> getInvStacks() {
        List<ItemStack> crates = new ArrayList<>();
        for(Crate crate : cCrates.getCrateManager().getCrates()) crates.add(loadItem(crate));
        return crates;
    }

    public ItemStack loadItem(Crate crate){
        ItemStack item = new ItemStack(crate.getBlock());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(crate.getName());
        meta.setLore(Arrays.asList(
                "Click to change " + crate.getName(),
                "Contents: " + crate.getContents().size(),
                "Floor: " + PlaceHolder.normalizeMaterial(crate.getFloor().name()),
                "Key: " + crate.getKey().getItemMeta().getDisplayName(),
                "Live Instances: " + cCrates.getLocationManager().getCrateInstances(crate).size()));
        item.setItemMeta(meta);
        return item;
    }

}
