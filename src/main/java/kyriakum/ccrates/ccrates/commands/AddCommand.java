package kyriakum.ccrates.ccrates.commands;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AddCommand implements SubCommand {

    private final String name = "add";
    private final CCrates cCrates;

    public AddCommand(CCrates cCrates){
        this.cCrates = cCrates;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length < 3){
            player.sendMessage(cCrates.getMessagesManager().getPrefix() +ChatColor.RED + "Wrong syntax! /ccrates add (crate) (item/cmd)");
            return;
        }

        Crate crate = cCrates.getCrateManager().getCrate(args[1]);
        if(crate==null){
            player.sendMessage(cCrates.getMessagesManager().getPrefix() +ChatColor.RED + "This crate does not exist!");
        } else {
            String type = args[2];
            if(type.equalsIgnoreCase("item")){

                cCrates.getConfigManager().addItemContent(crate, player.getInventory().getItemInMainHand());
                player.sendMessage(cCrates.getPlaceHolder().replaceItem(cCrates.getMessagesManager().getContentAdded(),player.getInventory().getItemInMainHand()));

            } else if(type.equalsIgnoreCase("cmd")){
                cCrates.getConfigManager().addCmdContent(crate, player.getInventory().getItemInMainHand());
                player.sendMessage(cCrates.getPlaceHolder().replaceItem(cCrates.getMessagesManager().getContentAdded(),player.getInventory().getItemInMainHand()));

            } else {

                player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "This type does not exist! (item/cmd)");
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
