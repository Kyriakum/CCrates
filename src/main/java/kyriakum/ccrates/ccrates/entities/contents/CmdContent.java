package kyriakum.ccrates.ccrates.entities.contents;

import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CmdContent extends Content{

    private List<String> commands;

    public CmdContent(int contentID, ItemStack content, int percentage, List<String> commands) {
        super(contentID, content, percentage);
        this.commands = commands;
    }

    @Override
    public void givePlayer(Player player) {
        for(String string : commands){
            String replaced = PlaceHolder.replaceName(string, player);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaced);
        }
    }
}
