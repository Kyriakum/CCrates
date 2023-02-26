package kyriakum.ccrates.ccrates.commands;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class InfoCommand implements SubCommand{

    private final String name = "info";
    private final CCrates cCrates;
    public InfoCommand(CCrates cCrates){
        this.cCrates = cCrates;
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length<2){
            player.sendMessage(ChatColor.RED + "Wrong syntax! /ccrates info (crate)");
            return;
        }

        Crate crate = cCrates.getCrateManager().getCrate(args[1]);
        if(crate==null){
            player.sendMessage(ChatColor.RED + "This crate does not exist!");
        } else {
            player.sendMessage(crate.getHologramName());
            player.sendMessage(ChatColor.GREEN + "Contents Size: " + crate.getContents().size());
            player.sendMessage(ChatColor.GREEN + "Block: " + PlaceHolder.normalizeMaterial(crate.getBlock().name()));
            player.sendMessage(ChatColor.GREEN + "Floor: " + PlaceHolder.normalizeMaterial(crate.getFloor().name()));
            player.sendMessage(ChatColor.GREEN + "Key: "+crate.getKey().getItemMeta().getDisplayName());
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
