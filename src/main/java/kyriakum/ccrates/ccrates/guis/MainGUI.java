package kyriakum.ccrates.ccrates.guis;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MainGUI extends MultiInventory {

    private final int SIZE = 9*3;
    private final CCrates cCrates;

    public MainGUI(CCrates cCrates){
        super(ChatColor.DARK_PURPLE + "Crates Menu");
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
        meta.setDisplayName(ChatColor.GOLD + crate.getName());
        meta.setLore(Arrays.asList(
                ChatColor.GOLD + "Click to change " +ChatColor.GOLD +  crate.getName(),
                ChatColor.GRAY + "Contents: " +ChatColor.GOLD +  crate.getContents().size(),
                ChatColor.GRAY + "Floor: " +ChatColor.GOLD +  PlaceHolder.normalizeMaterial(crate.getFloor().name()),
                ChatColor.GRAY + "Key: " + ChatColor.GOLD + crate.getKey().getItemMeta().getDisplayName(),
                ChatColor.GRAY + "Live Instances: " + ChatColor.GOLD + cCrates.getLocationManager().getCrateInstances(crate).size()));
        item.setItemMeta(meta);
        return item;
    }

}
