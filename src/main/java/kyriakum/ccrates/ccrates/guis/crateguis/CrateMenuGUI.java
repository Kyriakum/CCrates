package kyriakum.ccrates.ccrates.guis.crateguis;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CrateMenuGUI {

    private Crate crate;
    private CCrates cCrates;
    private Inventory crateMenuGUI;
    private CrateInstancesGUI crateInstancesGUI;
    private SettingsGUI settingsGUI;
    private final int SIZE = 9*3;

    public CrateMenuGUI(CCrates cCrates, Crate crate){
        this.cCrates = cCrates;
        this.crate = crate;
        setupCratePickInv();
        crateInstancesGUI = new CrateInstancesGUI(cCrates, crate);
        settingsGUI = new SettingsGUI(cCrates, crate);
    }

    public ItemStack crateInstances(){
        ItemStack stack = new ItemStack(crate.getBlock());
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Crate Instances");
        meta.setLore(Arrays.asList(
                "Running Instances: " + cCrates.getLocationManager().getCrateInstances(crate).size(),
                "Click here"));
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack settingsItem(){
        ItemStack stack = new ItemStack(Material.COMPASS);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Crate Settings");
        meta.setLore(Arrays.asList("Click Here to change Settings"));
        stack.setItemMeta(meta);
        return stack;
    }

    private void setupCratePickInv(){
        crateMenuGUI = Bukkit.createInventory(null, SIZE, "Crate " + crate.getName());
        crateMenuGUI.setItem(10, crateInstances());
        crateMenuGUI.setItem(16, settingsItem());
    }

    public Inventory getMainGUI() {
        return crateMenuGUI;
    }


    public CrateInstancesGUI getCrateInstancesGUI() {
        return crateInstancesGUI;
    }

    public SettingsGUI getSettingsGUI() {
        return settingsGUI;
    }
}
