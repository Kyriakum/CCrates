package kyriakum.ccrates.ccrates.managers.filemanagers;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.api.contents.AddContentEvent;
import kyriakum.ccrates.ccrates.api.crates.*;
import kyriakum.ccrates.ccrates.api.contents.RemoveContentEvent;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.contents.CmdContent;
import kyriakum.ccrates.ccrates.entities.contents.Content;
import kyriakum.ccrates.ccrates.entities.contents.ItemContent;
import kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis.ChangeValueType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager extends FileManager {

    public ConfigManager(CCrates cCrates) {
        super(cCrates);
        loadFile(new File(cCrates.getDataFolder(), "config.yml"));
        loadConfig(YamlConfiguration.loadConfiguration(getFile()));
    }


    public List<Crate> loadCrates(){
        List<Crate> crates = new ArrayList<>();
        if(getConfig().getConfigurationSection("Crates")==null) return crates;
        getConfig().getConfigurationSection("Crates").getKeys(false).forEach(name -> {
            crates.add( loadCrate(name));
        }
        );
        return crates;
    }
    public Crate loadCrate(String name){
        if(getConfig().get("Crates." + name) == null) return null;
        String displayName = getCCrates().getPlaceHolder().alternateColors(getConfig().getString("Crates." + name + ".DisplayName"));
        Material block = Material.matchMaterial(getConfig().getString("Crates." + name + ".Block"));
        Material floor = Material.matchMaterial(getConfig().getString("Crates." + name + ".Floor"));
        boolean enabled = getConfig().getBoolean("Crates." + name + ".Enabled");
        AnimationType type = AnimationType.valueOf(getConfig().getString("Crates." + name + ".Animation"));
        ItemStack key = loadKeyItem("Crates."+name+".Key");
        List<Content> contents = loadContents("Crates."+name+".Items");
        return new Crate(getCCrates(),name, displayName, key, block, floor, contents, type, enabled);
    }

    private ItemStack loadKeyItem(String path){
        ItemStack key = new ItemStack(Material.matchMaterial(getConfig().getString(path+".Item")));
        ItemMeta meta = key.getItemMeta();
        meta.setDisplayName(getCCrates().getPlaceHolder().alternateColors(getConfig().getString(path+".Name")));
        List<String> lore = new ArrayList<>();
        if(getConfig().getList(path+".Lore")!=null) for(String string: (List<String>) getConfig().getList(path+".Lore")){
            lore.add(getCCrates().getPlaceHolder().alternateColors(string));
        }
        meta.setLore(lore);
        key.setItemMeta(meta);
        return key;
    }

    private List<Content> loadContents(String path){
        List<Content> contents = new ArrayList<>();
        if(getConfig().getConfigurationSection(path)==null) return contents;
        getConfig().getConfigurationSection(path).getKeys(false).forEach(item -> {
            Content content = null;
            int id = Integer.valueOf(item);
            String type = getConfig().getString(path+ "." + item + ".Type");
            switch(type){
                case "ITEM": {content = loadItemContent(path+"."+item, id); break; }
                case "CMD": {content = loadCmdContent(path+"."+item, id); break;}
                default:
                    break;
            }

          if(content!=null) contents.add(content);
        });

        return contents;
    }

    private ItemContent loadItemContent(String path, int id){
        ItemStack stack = loadKeyItem(path);
        int amount = getConfig().getInt(path + ".Amount");
        int perc = getConfig().getInt(path + ".Percentage");
        boolean announce = getConfig().getBoolean(path + ".Announce");
        return new ItemContent(getCCrates(), id, stack, perc, amount, announce);
    }

    private CmdContent loadCmdContent(String path, int id){
        ItemStack stack = loadKeyItem(path);
        List<String> cmds = new ArrayList<>();
        if(getConfig().getList(path+".Commands")!=null) cmds = (List<String>) getConfig().getList(path+".Lore");
        int perc = getConfig().getInt(path + ".Percentage");
        boolean announce = getConfig().getBoolean(path + ".Announce");
        return new CmdContent(getCCrates(), id, stack, perc, cmds, announce);
    }


    public void addItemContent(Crate crate, ItemStack itemStack){
        try {
            int id = 1;
            while(crate.getContent(id)!=null)id++;

            getConfig().set("Crates." + crate.getName() + ".Items." + String.valueOf(id) + ".Type", "ITEM");
            getConfig().set("Crates." + crate.getName() + ".Items." + String.valueOf(id) + ".Item", itemStack.getType().name());
            getConfig().set("Crates." + crate.getName() + ".Items." + String.valueOf(id) + ".Name", itemStack.getItemMeta().getDisplayName());
            getConfig().set("Crates." + crate.getName() + ".Items." + String.valueOf(id) + ".Amount", itemStack.getAmount());
            getConfig().set("Crates." + crate.getName() + ".Items." + String.valueOf(id) + ".Percentage", 20);
            getConfig().set("Crates." + crate.getName() + ".Items." + String.valueOf(id) + ".Announce", false);
            getConfig().set("Crates." + crate.getName() + ".Items." + String.valueOf(id) + ".Lore", itemStack.getItemMeta().getLore());
            getConfig().save(getFile());
            Content content = loadItemContent("Crates." + crate.getName() + ".Items." + String.valueOf(id), id);
            System.out.println(itemStack.getType());
            crate.addContent(content);

            AddContentEvent e= new AddContentEvent(crate, content);
            Bukkit.getPluginManager().callEvent(e);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void addCmdContent(Crate crate, ItemStack itemStack){
        try {
            int id = 1;
            while(crate.getContent(id)!=null)id++;

            getConfig().set("Crates." + crate.getName() + ".Items." + id + ".Type", "CMD");
            getConfig().set("Crates." + crate.getName() + ".Items." + id + ".Item", itemStack.getType().name());
            getConfig().set("Crates." + crate.getName() + ".Items." + id + ".Name", itemStack.getItemMeta().getDisplayName());
            getConfig().set("Crates." + crate.getName() + ".Items." + id + ".Percentage", 20);
            getConfig().set("Crates." + crate.getName() + ".Items." + String.valueOf(id) + ".Announce", false);
            getConfig().set("Crates." + crate.getName() + ".Items." + id + ".Lore", itemStack.getItemMeta().getLore());
            getConfig().set("Crates." + crate.getName() + ".Items." + id + ".Commands", "");
            getConfig().save(getFile());
            Content content = loadCmdContent("Crates." + crate.getName() + ".Items." + id, id);
            crate.addContent(content);

            AddContentEvent e= new AddContentEvent(crate, content);
            Bukkit.getPluginManager().callEvent(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean removeContent(Crate crate, int id){
            if(crate.getContent(id)!=null) {
                try {
                    getConfig().set("Crates." + crate.getName() + ".Items." + id, null);
                    getConfig().save(getFile());
                    RemoveContentEvent e = new RemoveContentEvent(crate, crate.getContent(id));
                    crate.removeContent(id);
                    Bukkit.getPluginManager().callEvent(e);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            }
            return false;
    }

    public void createCrate(String name){
        Crate crate = new Crate(getCCrates(), name, name, new ItemStack(Material.TRIPWIRE_HOOK), Material.CHEST, Material.STONE, new ArrayList<>(), AnimationType.STD_ANIMATION, false);

        try {
            getConfig().set("Crates." + name  + ".DisplayName", name);
            getConfig().set("Crates." + name  + ".Enabled", false);
            getConfig().set("Crates." + name  + ".Block", "CHEST");
            getConfig().set("Crates." + name  + ".Animation", "STD_ANIMATION");
            getConfig().set("Crates." + name  + ".Floor", "STONE");
            getConfig().set("Crates." + name  + ".Key.Item", "TRIPWIRE_HOOK");
            getConfig().set("Crates." + name  + ".Key.Name", "Key");
            getConfig().set("Crates." + name  + ".Key.Lore", new ArrayList<>());
            getConfig().save(getFile());
            getCCrates().getCrateManager().addCrate(crate);
            CreateCrateEvent e = new CreateCrateEvent(crate);
            Bukkit.getPluginManager().callEvent(e);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void changeEnabled(Crate crate){
        try {
            Event e;
        if(crate.isEnabled()){
            getConfig().set("Crates." + crate.getName() + ".Enabled", false);
            crate.disable();
            e = new CrateDisableEvent(crate);
        } else {
            getConfig().set("Crates." + crate.getName() + ".Enabled", true);
            crate.enable();
            e = new CrateEnableEvent(crate);
        }
        Bukkit.getPluginManager().callEvent(e);
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteCrate(Crate crate){
        if(crate==null) return false;
        try {
            getConfig().set("Crates." + crate.getName(), null);
            getConfig().save(getFile());
            DeleteCrateEvent e = new DeleteCrateEvent(crate);
            getCCrates().getCrateManager().deleteCrate(crate);
            Bukkit.getPluginManager().callEvent(e);
         } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void changeValue(Crate crate, ChangeValueType type, ItemStack stack){

         try {
             switch (type){
                 case BLOCK: { getConfig().set("Crates." + crate.getName() + ".Block", stack.getType().name());
                     CrateBlockChangeEvent e = new CrateBlockChangeEvent(crate, crate.getBlock(), stack.getType());
                     crate.setBlock(stack.getType());
                     getCCrates().getLocationManager().changeBlocks(crate);
                     Bukkit.getPluginManager().callEvent(e);
                     break;
                 }
                 case FLOOR: { getConfig().set("Crates." + crate.getName() + ".Floor", stack.getType().name());
                     CrateFloorChangeEvent e = new CrateFloorChangeEvent(crate, crate.getFloor(), stack.getType());
                     crate.setFloor(stack.getType());
                     Bukkit.getPluginManager().callEvent(e);
                     break;
                 }
                 case KEY: { getConfig().set("Crates." + crate.getName() + ".Key.Item", stack.getType().name());
                     getConfig().set("Crates." + crate.getName() + ".Key.Name", stack.getItemMeta().getDisplayName());
                     getConfig().set("Crates." + crate.getName() + ".Key.Lore", stack.getItemMeta().getLore());
                     CrateKeyChangeEvent e = new CrateKeyChangeEvent(crate, crate.getKey(), stack);
                     crate.setKey(stack);
                     Bukkit.getPluginManager().callEvent(e);
                     break;}
             }
             getConfig().save(getFile());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void changeAnimation(Crate crate, AnimationType type){
        try{
            getConfig().set("Crates." + crate.getName() + ".Animation", type.name());
            getConfig().save(getFile());
            CrateAnimationChangeEvent e = new CrateAnimationChangeEvent(crate, crate.getAnimationType(), type);
            crate.setAnimationType(type);
            Bukkit.getPluginManager().callEvent(e);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
