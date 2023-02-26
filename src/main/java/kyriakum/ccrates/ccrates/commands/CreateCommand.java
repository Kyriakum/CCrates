package kyriakum.ccrates.ccrates.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CreateCommand implements SubCommand {

    private final String name = "create";
    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length<2){
            player.sendMessage(ChatColor.RED + "Wrong syntax: /ccrates create (crate)");
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
