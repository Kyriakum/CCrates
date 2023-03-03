package kyriakum.ccrates.ccrates.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public abstract class MultiInventory {

    private final int SIZE = 9*3;
    private Map<Integer, Inventory> inventories;
    private final String title;

    public MultiInventory(String title){
        this.title = ChatColor.DARK_PURPLE + title;
    }

    public Inventory getInventory(int page){
        return inventories.get(page);
    }

    public Integer getPage(Inventory inventory){
        for(Integer inte : inventories.keySet()){
            if(inventories.get(inte).equals(inventory)) return inte;
        }
        return -1;
    }

        public Inventory createInventory(int page, List<ItemStack> crates) {
            Inventory inv = Bukkit.createInventory(null, SIZE, title + " - Page " + page);
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

    protected abstract List<ItemStack> getInvStacks();

    protected void setupInvs() {
        int page = 1;
        inventories = new HashMap<>();
        List<ItemStack> stacks = getInvStacks();
        while(stacks.size()>SIZE){
            List<ItemStack> thisinv = new ArrayList<>();
            if(page==1){
                for (int i = 0; i < SIZE - 1; i++) {
                    thisinv.add(stacks.get(0));
                    stacks.remove(0);
                }
            } else {
                for (int i = 0; i < SIZE - 2; i++) {
                    thisinv.add(stacks.get(0));
                    stacks.remove(0);
                }
            }
            inventories.put(page, createInventory(page++, thisinv));
        }
        if(stacks.size()>=1 || page==1) inventories.put(page,createInventory(page, stacks));
    }

    public ItemStack nextPage() {
        ItemStack item = new ItemStack(Material.ACACIA_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Next Page");
        meta.setLore(Arrays.asList("Click to go to next page"));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack previousPage() {
        ItemStack item = new ItemStack(Material.ACACIA_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Previous Page");
        meta.setLore(Arrays.asList("Click to go to previous page"));
        item.setItemMeta(meta);
        return item;
    }

    public String getTitle() {
        return title;
    }

    public Map<Integer, Inventory> getInventories() {
        return inventories;
    }
}
