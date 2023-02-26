package kyriakum.ccrates.ccrates.managers.filemanagers;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.contents.CmdContent;
import kyriakum.ccrates.ccrates.entities.contents.Content;
import kyriakum.ccrates.ccrates.entities.contents.ItemContent;
import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
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
        String hologramName = PlaceHolder.alternateColors(getConfig().getString("Crates." + name + ".HologramName"));
        Material block = Material.matchMaterial(getConfig().getString("Crates." + name + ".Block"));
        Material floor = Material.matchMaterial(getConfig().getString("Crates." + name + ".Floor"));
        ItemStack key = loadKeyItem("Crates."+name+".Key");
        List<Content> contents = loadContents("Crates."+name+".Items");
        return new Crate(getCCrates(),name, hologramName, key, block, floor, contents);
    }

    private ItemStack loadKeyItem(String path){
        ItemStack key = new ItemStack(Material.matchMaterial(getConfig().getString(path+".Item")));
        ItemMeta meta = key.getItemMeta();
        meta.setDisplayName(PlaceHolder.alternateColors(getConfig().getString(path+".Name")));
        List<String> lore = new ArrayList<>();
        if(getConfig().getList(path+".Lore")!=null) for(String string: (List<String>) getConfig().getList(path+".Lore")){
            lore.add(PlaceHolder.alternateColors(string));
        }
        meta.setLore(lore);
        key.setItemMeta(meta);
        return key;
    }

    private List<Content> loadContents(String path){
        List<Content> contents = new ArrayList<>();
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

        return new ItemContent(id, stack, perc, amount);
    }

    private CmdContent loadCmdContent(String path, int id){
        ItemStack stack = loadKeyItem(path);
        List<String> cmds = new ArrayList<>();
        if(getConfig().getList(path+".Commands")!=null) cmds = (List<String>) getConfig().getList(path+".Lore");
        int perc = getConfig().getInt(path + ".Percentage");

        return new CmdContent(id, stack, perc, cmds);
    }


}
