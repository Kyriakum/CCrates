package kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ChangeValueGUI implements ChangeValue {


    private Crate crate;
    private CCrates cCrates;
    private ChangeValueType type;
    private Inventory inventory;
    private ItemStack itemStack;
    private final int SIZE = 9*5;

    public ChangeValueGUI(CCrates cCrates, Crate crate, ChangeValueType type, ItemStack stack){
        this.cCrates = cCrates;
        this.crate = crate;
        this.type = type;
        itemStack = stack;
        setupInv();
    }


    public void accept(){
       ItemStack accept = inventory.getItem(22);
       cCrates.getConfigManager().changeValue(crate,type,accept);
    }

    public void setupInv(){
        inventory = Bukkit.createInventory(null, SIZE, "Change Value");
        for(int i = 0;i<SIZE; i++){
            inventory.setItem(i, emptySpace());
        }
        inventory.setItem(13, itemStack);
        inventory.setItem(22, new ItemStack(Material.AIR));
        inventory.setItem(31, acceptStack());
    }

    public ItemStack emptySpace(){
        ItemStack stack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("PLACE AN ITEM");
        meta.setLore(Arrays.asList("Place an Item in the middle", "And Click Accept!"));
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack acceptStack(){
        ItemStack stack = new ItemStack(Material.GREEN_WOOL);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("ACCEPT");
        meta.setLore(Arrays.asList("Click to Change " + PlaceHolder.normalizeMaterial(type.name())));
        stack.setItemMeta(meta);
        return stack;
    }

    public ChangeValueType getType() {
        return type;
    }

    public Inventory getInventory() {
        return inventory;
    }
}