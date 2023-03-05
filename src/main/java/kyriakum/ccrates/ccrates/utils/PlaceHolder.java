package kyriakum.ccrates.ccrates.utils;

import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.CrateInstance;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlaceHolder {

    public String replaceName(String string, Player player){
       return string.replaceAll("%player%", player.getName());

    }
    public String replaceItem(String string, ItemStack item){
        return string.replaceAll("%item%", item.getItemMeta().getDisplayName());
    }
    public String replaceMaterial(String string, Material material){
        return string.replaceAll("%material%", normalizeMaterial(material.name()));

    }
    public String replaceInstance(String string, CrateInstance instance){
        return string.replaceAll("%crateinst%", String.valueOf(instance.getId()));

    }
    public String replaceCrate(String string, Crate crate){
        return string.replaceAll("%crate%", crate.getDisplayName());

    }
    public String replaceCrate(String string, String crateName){
        return string.replaceAll("%crate%", crateName);


    }
    public String replaceAnimation(String string, String animation){
        return string.replaceAll("%animation%", animation);


    }

    public String alternateColors(String string){
        return string.replaceAll("&","§");
    }

    public String normalizeMaterial(String string){
        StringBuilder build = new StringBuilder();
        String[] args = string.split("_");
        for(String str : args){
            str = str.toLowerCase();
            String res = str.substring(0, 1).toUpperCase() + str.substring(1);;
            str = res;
            build.append(str + " ");
        }
        return build.toString();
    }
}
