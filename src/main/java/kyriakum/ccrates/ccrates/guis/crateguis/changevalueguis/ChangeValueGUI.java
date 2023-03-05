package kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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


    public boolean accept(ItemStack accept){
       if(accept==null) return false;
       if((type==ChangeValueType.FLOOR || type==ChangeValueType.BLOCK) && !accept.getType().isBlock()) return false;
       cCrates.getConfigManager().changeValue(crate,type,accept);
        return true;
    }

    public void setupInv(){
        inventory = Bukkit.createInventory(null, SIZE, ChatColor.DARK_PURPLE +  crate.getName() + " - Change " + cCrates.getPlaceHolder().normalizeMaterial(type.name()));
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
        meta.setDisplayName(ChatColor.GOLD + "PLACE AN ITEM");
        meta.setLore(Arrays.asList(ChatColor.GOLD + "Place an Item in the middle",ChatColor.GOLD +  "And Click Accept!"));
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack acceptStack(){
        ItemStack stack = new ItemStack(Material.GREEN_WOOL);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "ACCEPT");
        meta.setLore(Arrays.asList(ChatColor.GOLD + "Click to Change " + cCrates.getPlaceHolder().normalizeMaterial(type.name())));
        stack.setItemMeta(meta);
        return stack;
    }

    public ChangeValueType getType() {
        return type;
    }

    public Inventory getInventory() {
        return inventory;
    }
    @Override
    public Crate getCrate() {
        return crate;
    }
}
