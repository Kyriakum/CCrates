package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.CrateInstance;
import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

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
        if(cCrates.getLocationManager().containsCrate(e.getClickedBlock().getLocation()) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getHand().equals(EquipmentSlot.HAND)) {
            CrateInstance crateInstance = cCrates.getLocationManager().getCrateInstanceByLocation(e.getClickedBlock().getLocation());

            if(player.getInventory().getItemInMainHand().isSimilar(crateInstance.getCrate().getKey())){
                if(!crateInstance.startCrate(player)){
                    player.sendMessage(cCrates.getPlaceHolder().replaceCrate(cCrates.getMessagesManager().getDisabledCrate(), crateInstance.getCrate()));
                }
            } else player.sendMessage(cCrates.getPlaceHolder().replaceItem(cCrates.getPlaceHolder().replaceCrate(cCrates.getMessagesManager().getKeyNeeded(), crateInstance.getCrate()), crateInstance.getCrate().getKey()));


            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e){
        Player player = e.getPlayer();

        if(cCrates.getLocationManager().containsCrate(e.getBlock().getLocation())) {
            if(!player.hasPermission("ccrates.admin")){
                player.sendMessage(cCrates.getMessagesManager().getNoPermission());
                e.setCancelled(true);
                return;
            }

            CrateInstance instance = cCrates.getLocationManager().getCrateInstanceByLocation(e.getBlock().getLocation());
            player.sendMessage(cCrates.getPlaceHolder().replaceCrate(cCrates.getPlaceHolder().replaceInstance(cCrates.getMessagesManager().getCrateInstDestroyed(), instance), instance.getCrate()));
            cCrates.getLocationManager().removeCrateLocation(e.getBlock().getLocation());
        }
    }


}
