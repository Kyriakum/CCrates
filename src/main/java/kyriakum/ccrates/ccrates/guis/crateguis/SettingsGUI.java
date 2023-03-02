package kyriakum.ccrates.ccrates.guis.crateguis;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis.*;
import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsGUI {

    private Crate crate;
    private CCrates cCrates;
    private Inventory settings;
    private final int SIZE = 9*3;
    private List<ChangeValue> changeValueGUIS;

    public SettingsGUI(CCrates cCrates, Crate crate){
        this.cCrates = cCrates;
        this.crate = crate;
        setupInv();
        setupValueGUIS();
    }

    public void setupInv(){
        settings = Bukkit.createInventory(null, SIZE, crate.getName() + " Settings");
        settings.setItem(9,blockStack());
        settings.setItem(11,floorStack());
        settings.setItem(13,keyStack());
        settings.setItem(15,animStack());
        settings.setItem(17,contentsStack());
    }


    public Inventory getInventory(){return settings;}

    public void setupValueGUIS(){
        changeValueGUIS = new ArrayList<>();
        changeValueGUIS.add(new ChangeValueGUI(cCrates, crate, ChangeValueType.BLOCK, new ItemStack(crate.getBlock())));
        changeValueGUIS.add(new ChangeValueGUI(cCrates, crate, ChangeValueType.FLOOR, new ItemStack(crate.getFloor())));
        changeValueGUIS.add(new ChangeValueGUI(cCrates, crate, ChangeValueType.KEY, crate.getKey()));
        changeValueGUIS.add(new ChangeAnimationGUI(crate));
        changeValueGUIS.add(new ChangeContentsGUI(crate));
    }

    public ItemStack blockStack(){
        ItemStack stack = new ItemStack(crate.getBlock());
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Current Block: " + PlaceHolder.normalizeMaterial(crate.getBlock().name()));
        meta.setLore(Arrays.asList("Click Here to Change!"));
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack floorStack(){
        ItemStack stack = new ItemStack(crate.getFloor());
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Current Floor: " + PlaceHolder.normalizeMaterial(crate.getFloor().name()));
        meta.setLore(Arrays.asList("Click Here to Change!"));
        stack.setItemMeta(meta);
        return stack;
    }
    public ItemStack keyStack(){
        ItemStack stack = crate.getKey().clone();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Current Key: " + crate.getKey().getItemMeta().getDisplayName());
        meta.setLore(Arrays.asList("Click Here to Change!"));
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack animStack(){
        ItemStack stack = crate.getAnimationType().getItem().clone();
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Current Animation: " + crate.getAnimationType().getDisplayName());
        meta.setLore(Arrays.asList("Click Here to Change!"));
        stack.setItemMeta(meta);
        return stack;
    }

    public ItemStack contentsStack(){
        ItemStack stack = new ItemStack(Material.SHULKER_BOX);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Contents: " + crate.getContents().size());
        meta.setLore(Arrays.asList("Click Here to Change!"));
        stack.setItemMeta(meta);
        return stack;
    }

    public ChangeValue getValueGUI(ChangeValueType type){
        for(ChangeValue changeValueGUI : changeValueGUIS){
            if(changeValueGUI.getType()==type) return changeValueGUI;
        }
        return null;
    }

    public List<ChangeValue> getValueGUIS() {
        return changeValueGUIS;
    }
}
