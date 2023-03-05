package kyriakum.ccrates.ccrates.entities.contents;

import kyriakum.ccrates.ccrates.CCrates;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Content {

    private ItemStack content;
    private int contentID;
    private int percentage;
    private boolean announce;
    private CCrates cCrates;

    public Content(CCrates cCrates, int contentID, ItemStack content, int percentage, boolean announce){
        this.contentID = contentID;
        this.content = content;
        this.percentage = percentage;
        this.announce = announce;
        this.cCrates = cCrates;
    }


    public int getContentID() {
        return contentID;
    }

    public ItemStack getContent() {
        return content;
    }

    public boolean isAnnounce() {
        return announce;
    }

    protected CCrates getcCrates(){
        return cCrates;
    }

    public int getPercentage() {
        return percentage;
    }

    public abstract void givePlayer(Player player);
}
