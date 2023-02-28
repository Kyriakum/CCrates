package kyriakum.ccrates.ccrates.guis.crateguis;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.CrateInstance;
import kyriakum.ccrates.ccrates.guis.MultiInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CrateInstancesGUI implements MultiInventory {

    private Crate crate;
    private Map<Integer, Inventory> instancesInvs;
    private final int SIZE = 9*3;
    private CCrates cCrates;

    public CrateInstancesGUI(CCrates cCrates, Crate crate){
        this.crate = crate;
        this.cCrates = cCrates;
        instancesInvs = new HashMap<>();
        setupInvs();
    }

    @Override
    public Inventory getInventory(int page) {
        return instancesInvs.get(page);
    }

    @Override
    public Integer getPage(Inventory inventory) {
        for(Integer inte : instancesInvs.keySet()){
            if(instancesInvs.get(inte).equals(inventory)) return inte;
        }
        return -1;
    }

    @Override
    public Inventory createInventory(int page, List<ItemStack> crates) {
        Inventory inv = Bukkit.createInventory(null, SIZE, "Crates GUI - Page " + page);
        int itemcounter=0;
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
                inv.setItem(i, crates.get(itemcounter++));
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

    @Override
    public void setupInvs() {
        List<ItemStack> crates = new ArrayList<>();
        for(CrateInstance crate : cCrates.getLocationManager().getCrateInstances(this.crate)) crates.add(loadInstanceItem(crate));
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
            instancesInvs.put(page, createInventory(page++, thisinv));
        }
        if(crates.size()>=1 || page==1) instancesInvs.put(page,createInventory(page, crates));
    }

    public ItemStack loadInstanceItem(CrateInstance crate){
        ItemStack item = new ItemStack(crate.getCrate().getBlock());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Instance " + crate.getId());
        meta.setLore(Arrays.asList(
                "Click to edit Instance" + crate.getId(),
                "Location: " + crate.getLocation().getBlockX() +", " + crate.getLocation().getBlockY() + ", " + crate.getLocation().getBlockZ()));
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public ItemStack nextPage() {
        ItemStack item = new ItemStack(Material.ACACIA_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Next Page");
        meta.setLore(Arrays.asList("Click to go to next page"));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack previousPage() {
        ItemStack item = new ItemStack(Material.ACACIA_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Previous Page");
        meta.setLore(Arrays.asList("Click to go to previous page"));
        item.setItemMeta(meta);
        return item;
    }

    public Map<Integer, Inventory> getInstancesInvs() {
        return instancesInvs;
    }
}
