package kyriakum.ccrates.ccrates.commands;

import org.bukkit.entity.Player;

public interface SubCommand {

    void onCommand(Player player, String[] args);
    String getName();
    String[] getAliases();
}
