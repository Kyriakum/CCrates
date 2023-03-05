package kyriakum.ccrates.ccrates.guis.crateguis;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.CrateInstance;
import kyriakum.ccrates.ccrates.guis.MultiInventory;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CrateInstancesGUI extends MultiInventory {

    private Crate crate;
    private CCrates cCrates;

    public CrateInstancesGUI(CCrates cCrates, Crate crate){
        super(ChatColor.DARK_PURPLE + "Crate Instances");
        this.crate = crate;
        this.cCrates = cCrates;
        setupInvs();
    }

    @Override
    protected List<ItemStack> getInvStacks() {
        List<ItemStack> stacks = new ArrayList<>();
        for(CrateInstance crateInstance : cCrates.getLocationManager().getCrateInstances(crate)){
            stacks.add(loadInstanceItem(crateInstance));
        }
        return stacks;
    }


    public ItemStack loadInstanceItem(CrateInstance crate){
        ItemStack item = new ItemStack(crate.getCrate().getBlock());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Instance " + ChatColor.GOLD + crate.getId());
        meta.setLore(Arrays.asList(
                ChatColor.GOLD + "Location: (" + crate.getLocation().getBlockX() +", " + crate.getLocation().getBlockY() + ", " + crate.getLocation().getBlockZ() + ")"));
        item.setItemMeta(meta);

        return item;
    }
}
