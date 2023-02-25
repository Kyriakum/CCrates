package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.CrateInstance;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    private CCrates cCrates;

    public PlayerInteract(CCrates cCrates){
        this.cCrates = cCrates;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getClickedBlock()==null || e.getClickedBlock().getType()== Material.AIR)
        return;

        Player player = e.getPlayer();

        if(cCrates.getLocationManager().containsCrate(e.getClickedBlock().getLocation()) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            CrateInstance crateInstance = cCrates.getLocationManager().getCrateInstanceByLocation(e.getClickedBlock().getLocation());
            if(player.getInventory().getItemInMainHand().equals(crateInstance.getCrate().getKey())){
                player.sendMessage(ChatColor.GREEN + "WOW!");
                crateInstance.startCrate(player);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if(cCrates.getLocationManager().containsCrate(e.getBlock().getLocation())) {
            cCrates.getLocationManager().removeCrateLocation(e.getBlock().getLocation());
            player.sendMessage(ChatColor.GREEN + "This Crate was Destroyed!");
        }
    }

}
