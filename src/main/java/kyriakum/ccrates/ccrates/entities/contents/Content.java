package kyriakum.ccrates.ccrates.entities.contents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Content {

    private ItemStack content;
    private int contentID;
    private int percentage;

    public Content(int contentID, ItemStack content, int percentage){
        this.contentID = contentID;
        this.content = content;
        this.percentage = percentage;
    }


    public int getContentID() {
        return contentID;
    }

    public ItemStack getContent() {
        return content;
    }

    public int getPercentage() {
        return percentage;
    }

    public abstract void givePlayer(Player player);
}
