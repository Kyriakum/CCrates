package kyriakum.ccrates.ccrates.commands;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.events.SetCrateListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetCrateCommand implements SubCommand{

    private final String name = "set";
    private final CCrates cCrates;

    public SetCrateCommand(CCrates cCrates){
        this.cCrates = cCrates;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Wrong syntax: " + "/ccrates set (crate)");
            return;
        }
        if (SetCrateListener.setCrate.containsKey(player)){
            player.sendMessage(ChatColor.RED + "You are already setting a crate!");
            return;
        }

            String crateName = args[1];
            Crate crate = cCrates.getCrateManager().getCrate(crateName);

        if(crate == null){
            player.sendMessage(ChatColor.RED + "This crate does not exist!");
            return;
        }

        if(crate.getContents().size()==0) {
            player.sendMessage(ChatColor.RED + "This crate has no contents!");
            return;
        }

        SetCrateListener.setCrate.put(player, crate);
        player.sendMessage(ChatColor.GREEN + "Right Click a block to set it as a " + crate.getHologramName());
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
