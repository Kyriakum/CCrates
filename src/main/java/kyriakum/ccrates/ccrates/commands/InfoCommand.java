package kyriakum.ccrates.ccrates.commands;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.contents.Content;
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
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "Wrong syntax! /ccrates info (crate)");
            return;
        }

        Crate crate = cCrates.getCrateManager().getCrate(args[1]);
        if(crate==null){
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "This crate does not exist!");
        } else {
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + crate.getDisplayName());
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.GRAY + "Contents Size: " + ChatColor.GOLD + crate.getContents().size());
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.GRAY + "Block: " +ChatColor.GOLD + cCrates.getPlaceHolder().normalizeMaterial(crate.getBlock().name()));
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.GRAY + "Floor: " +ChatColor.GOLD + cCrates.getPlaceHolder().normalizeMaterial(crate.getFloor().name()));
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.GRAY + "Key: "+ChatColor.GOLD + crate.getKey().getItemMeta().getDisplayName());
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.GRAY + "Contents: ");
            for(Content content : crate.getContents()){
                player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.GRAY+ "Content "+ ChatColor.GOLD +content.getContentID() +ChatColor.GRAY+ ": " + ChatColor.GOLD + content.getContent().getItemMeta().getDisplayName());
            }
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
