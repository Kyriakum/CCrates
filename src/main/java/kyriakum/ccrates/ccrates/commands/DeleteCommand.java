package kyriakum.ccrates.ccrates.commands;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DeleteCommand implements SubCommand {

    private final String name = "delete";
    private final CCrates cCrates;
    public DeleteCommand(CCrates cCrates){
        this.cCrates = cCrates;
    }
    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length<2){
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "Wrong syntax: /ccrates delete (crate)");
            return;
        }

        StringBuilder builder = new StringBuilder();
        for(int i = 1; i<args.length;i++){
            builder.append(args[i]);
        }
        Crate crate = cCrates.getCrateManager().getCrate(builder.toString());
        if(crate==null){
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "This crate doesn't exist!");
        } else {
            cCrates.getConfigManager().deleteCrate(crate);
            cCrates.getLocationManager().removeAllCrateLocations(crate);
            player.sendMessage(cCrates.getPlaceHolder().replaceCrate(cCrates.getMessagesManager().getCrateRemoved(), builder.toString()));
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
