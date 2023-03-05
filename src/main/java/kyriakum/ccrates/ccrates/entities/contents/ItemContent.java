package kyriakum.ccrates.ccrates.entities.contents;

import kyriakum.ccrates.ccrates.CCrates;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemContent extends Content{

    private int amount;

    public ItemContent(CCrates cCrates, int contentID, ItemStack content, int percentage, int amount, boolean announce) {
        super(cCrates, contentID, content, percentage, announce);
        this.amount = amount;
    }

    @Override
    public void givePlayer(Player player) {
        ItemStack content = getContent().clone();
        if(amount>1)
        content.setAmount(amount);
        player.getInventory().addItem(content);
        player.sendMessage(getcCrates().getPlaceHolder().replaceItem(getcCrates().getMessagesManager().getPrivateWinMessage(), getContent()));
        if(isAnnounce()){
            Bukkit.broadcastMessage(getcCrates().getPlaceHolder().replaceItem(getcCrates().getPlaceHolder().replaceName(getcCrates().getMessagesManager().getAnnouncementWinMessage(), player),getContent()));
            for(Player players: Bukkit.getOnlinePlayers()){
                players.playSound(player, Sound.ENTITY_WITHER_DEATH,1 ,3);
            }
        } else {
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING,1 ,5);

        }
    }

    public int getAmount() {
        return amount;
    }
}
