package kyriakum.ccrates.ccrates.entities.contents;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CmdContent extends Content{

    private List<String> commands;

    public CmdContent(int contentID, ItemStack content, int percentage, List<String> commands) {
        super(contentID, content, percentage);
        this.commands = commands;
    }
}
