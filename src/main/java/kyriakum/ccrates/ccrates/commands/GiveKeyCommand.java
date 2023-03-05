package kyriakum.ccrates.ccrates.commands;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GiveKeyCommand implements SubCommand{

    private final String name = "give";
    private final CCrates cCrates;
    public GiveKeyCommand(CCrates cCrates){
        this.cCrates = cCrates;
    }
    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length < 3){
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "Wrong syntax! /ccrates give (player) (crate)");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[1]);
        if(target==null || !target.isOnline()){
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "This player is not online!");
        } else {
            Crate crate = cCrates.getCrateManager().getCrate(args[2]);
            if(crate==null){
                player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "This crate does not exist!");
            } else {
                target.getInventory().addItem(crate.getKey());
                player.sendMessage(cCrates.getPlaceHolder().replaceItem(cCrates.getPlaceHolder().replaceName(cCrates.getPlaceHolder().replaceCrate(cCrates.getMessagesManager().getGiveKey(), crate), target), crate.getKey()));
                target.sendMessage(cCrates.getPlaceHolder().replaceCrate(cCrates.getPlaceHolder().replaceItem(cCrates.getMessagesManager().getKeyGivenToPlayer(), crate.getKey()), crate));
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
