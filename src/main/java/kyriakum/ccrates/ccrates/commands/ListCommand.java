package kyriakum.ccrates.ccrates.commands;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ListCommand implements SubCommand{

    private final String name = "list";
    private final CCrates cCrates;

    public ListCommand(CCrates cCrates){
        this.cCrates = cCrates;
    }
    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.GREEN + "ALL CRATES ARE: ");
        for(Crate crate : cCrates.getCrateManager().getCrates()){
            player.sendMessage(ChatColor.GOLD + crate.getDisplayName());
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
