package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.HashMap;

public class SetCrateListener implements Listener {

    public static HashMap<Player, Crate> setCrate = new HashMap<>();
    private CCrates cCrates;

    public SetCrateListener(CCrates cCrates){
        this.cCrates = cCrates;
    }

    @EventHandler
    public void onSetCrate(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(setCrate.containsKey(player) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock()!=null && e.getClickedBlock().getType()!= Material.AIR){
            Block block = e.getClickedBlock();
            if(cCrates.getLocationManager().containsCrate(block.getLocation())){
                player.sendMessage(ChatColor.RED + "This block is already a Crate!");
            } else {


                cCrates.getLocationManager().addCrateLocation(setCrate.get(player), block.getLocation());
                player.sendMessage(ChatColor.GREEN + "Crate " + setCrate.get(player).getHologramName() + " was set!");
                block.setType(setCrate.get(player).getBlock());
                Directional ddata = (Directional) block.getBlockData();
                ddata.setFacing(player.getFacing().getOppositeFace());
                block.setBlockData(ddata);
            }
            setCrate.remove(player);
        }

    }
}
