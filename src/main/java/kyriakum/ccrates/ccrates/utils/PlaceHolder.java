package kyriakum.ccrates.ccrates.utils;

import org.bukkit.entity.Player;

public class PlaceHolder {

    public static String replaceName(String string, Player player){
        string.replaceAll("%player%", player.getName());
        return string;
    }

    public static String alternateColors(String string){
        return string.replaceAll("&","§");
    }

    public static String normalizeMaterial(String string){
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
