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

public class MainGUI implements MultiInventory {

    private Map<Integer, Inventory> maingui;
    private final int SIZE = 9*3;
    private final CCrates cCrates;

    public MainGUI(CCrates cCrates){
        this.cCrates = cCrates;
        setupInvs();
    }


    public Inventory getInventory(int page) {
        return maingui.get(page);
    }

    public Integer getPage(Inventory inventory){
        for(Integer inte : maingui.keySet()){
            if(maingui.get(inte).equals(inventory)) return inte;
        }
    return -1;
    }

    public void setupInvs(){
        maingui = new HashMap<>();
        List<ItemStack> crates = new ArrayList<>();
        for(Crate crate : cCrates.getCrateManager().getCrates()) crates.add(loadItem(crate));
        int page = 1;
        while(crates.size()>SIZE){
            List<ItemStack> thisinv = new ArrayList<>();
            if(page==1){
                for (int i = 0; i < SIZE - 1; i++) {
                    thisinv.add(crates.get(0));
                    crates.remove(0);
                }
            } else {
                for (int i = 0; i < SIZE - 2; i++) {
                    thisinv.add(crates.get(0));
                    crates.remove(0);
                }
            }
            maingui.put(page, createInventory(page++, thisinv));
        }
        if(crates.size()>=1|| page==1) maingui.put(page, createInventory(page, crates));

    }

    public Inventory createInventory(int page, List<ItemStack> crates){
        Inventory inv = Bukkit.createInventory(null, SIZE, "Crates GUI - Page " + page);
        int itemcounter = 0;
        if(crates.size()==SIZE-1) {
            inv.setItem(26, nextPage());
            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) != null) continue;
                inv.setItem(i, crates.get(itemcounter++));
            }
        }
        else if(crates.size()==SIZE-2){
            inv.setItem(18, previousPage());
            inv.setItem(26, nextPage());
            for(int i = 0; i<inv.getSize();i++){
                if(inv.getItem(i)!=null) continue;
                else inv.setItem(i, crates.get(itemcounter++));
            }
        } else if(page>1){
            inv.setItem(18, previousPage());
            for(int i = 0; i<crates.size();i++){
                if(inv.getItem(i)!=null) continue;
                inv.setItem(i, crates.get(itemcounter++));
            }
        } else {
            for(int i = 0; i<crates.size();i++){
                inv.setItem(i, crates.get(i));
            }
        }
        return inv;
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

    public ItemStack nextPage(){
        ItemStack item = new ItemStack(Material.ACACIA_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Next Page");
        meta.setLore(Arrays.asList("Click to go to next page"));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack previousPage(){
        ItemStack item = new ItemStack(Material.ACACIA_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Previous Page");
        meta.setLore(Arrays.asList("Click to go to previous page"));
        item.setItemMeta(meta);
        return item;
    }

    public Map<Integer, Inventory> getGUI() {
        return maingui;
    }
}
