package kyriakum.ccrates.ccrates.entities.contents;

import kyriakum.ccrates.ccrates.CCrates;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CmdContent extends Content{

    private List<String> commands;

    public CmdContent(CCrates cCrates, int contentID, ItemStack content, int percentage, List<String> commands, boolean announce) {
        super(cCrates, contentID, content, percentage, announce);
        this.commands = commands;
    }

    @Override
    public void givePlayer(Player player) {
        for(String string : commands){
            String replaced = getcCrates().getPlaceHolder().replaceName(string, player);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaced);
        }
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
}
