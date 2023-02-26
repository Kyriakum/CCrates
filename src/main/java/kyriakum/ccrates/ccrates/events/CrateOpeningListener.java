package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.CrateEntity;
import kyriakum.ccrates.ccrates.entities.CrateInstance;
import kyriakum.ccrates.ccrates.entities.CrateRunning;
import kyriakum.ccrates.ccrates.entities.contents.Content;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrateOpeningListener implements Listener {

    private CrateRunning running;
    private CCrates cCrates;

    public CrateOpeningListener(CCrates cCrates, CrateRunning running){
        this.cCrates = cCrates;
        this.running = running;
    }

    @EventHandler
    public void OpeningListener(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if(!p.equals(running.getPlayer())) return;

        if(running.getCounter()<7) { e.setCancelled(true); return; }

        CrateEntity entity = running.getChestBlock(e.getClickedBlock().getLocation());

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock()!=null && entity!=null){
            p.sendMessage(ChatColor.GREEN + "Poof!");
            entity.makeMagicOpening();
            if(running.allOpened())
                Bukkit.getScheduler().runTaskLater(cCrates, () -> running.resetArea(), 3*running.SECONDS);
        }
        e.setCancelled(true);
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        if(e.getPlayer().equals(running.getPlayer())){
            running.resetArea();
        }
    }

    @EventHandler
    public void onCrateMove(PlayerMoveEvent e){
        if(e.getPlayer().equals(running.getPlayer())){
            if((e.getFrom().getBlockX()!=e.getTo().getBlockX() || e.getFrom().getBlockY()!=e.getTo().getBlockY() || e.getFrom().getBlockZ()!=e.getTo().getBlockZ())) {
                e.setCancelled(true);
            }
        }
    }

    private ItemStack drawRandomItem(){
        List<Content> contents = new ArrayList<>();
        for(Content content : running.getInstance().getCrate().getContents()){
            for(int i = 0; i<content.getPercentage();i++){
                contents.add(content);
            }
        }
        Collections.shuffle(contents);
        Content chosen = (Content) contents.toArray()[0];
        return chosen.getContent();
    }

    private void makeMagicOpening(Block block){
        ItemStack item = drawRandomItem();
        Location loc = block.getLocation();
        PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(new BlockPosition(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()), CraftMagicNumbers.getBlock(Material.CHEST),1 ,1);
        ((CraftPlayer) running.getPlayer()).getHandle().b.a(packet);
        Item dropped = running.getPlayer().getWorld().dropItem(new Location(loc.getWorld(), loc.getBlockX()+0.5, loc.getBlockY()+0.8, loc.getBlockZ()+0.5), item);
        dropped.setVelocity(new Vector(0,0.1,0));
        dropped.setCustomNameVisible(true);
        dropped.setCustomName(item.getItemMeta().getDisplayName());
        dropped.setPickupDelay(9999);
    }
}
