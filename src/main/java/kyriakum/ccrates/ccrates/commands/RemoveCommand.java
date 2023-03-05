package kyriakum.ccrates.ccrates.commands;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoveCommand implements SubCommand{

    private final String name = "remove";
    private final CCrates cCrates;
    public RemoveCommand(CCrates cCrates){
        this.cCrates = cCrates;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length<3){
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "Wrong syntax: /ccrates remove (crate) (content ID)");
            return;
        }
        Crate crate = cCrates.getCrateManager().getCrate(args[1]);
        if(crate==null){
            player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "This crate does not exist!");
        } else {
           int id = 0;
            try{
                id = Integer.valueOf(args[2]);
            }catch (NumberFormatException e){
                player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "Your input was not a number!");
                return;
            }
            if(crate.getContent(id)!=null){
                player.sendMessage(cCrates.getPlaceHolder().replaceItem(cCrates.getMessagesManager().getContentRemoved(), crate.getContent(id).getContent()));
                cCrates.getConfigManager().removeContent(crate,id);
            } else {
                player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "This content ID does not exist!");
            }
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
