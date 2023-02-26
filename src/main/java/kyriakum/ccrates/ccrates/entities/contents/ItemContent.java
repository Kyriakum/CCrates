package kyriakum.ccrates.ccrates.entities.contents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemContent extends Content{

    private int amount;

    public ItemContent(int contentID, ItemStack content, int percentage, int amount) {
        super(contentID, content, percentage);
        this.amount = amount;
    }

    @Override
    public void givePlayer(Player player) {
        ItemStack content = getContent().clone();
        if(amount>1)
        content.setAmount(amount);
        player.getInventory().addItem(content);
    }

    public int getAmount() {
        return amount;
    }
}
