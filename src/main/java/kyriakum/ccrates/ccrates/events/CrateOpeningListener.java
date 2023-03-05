package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.CrateEntity;
import kyriakum.ccrates.ccrates.entities.CrateRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CrateOpeningListener implements Listener {

    private CrateRunnable running;
    private CCrates cCrates;

    public CrateOpeningListener(CCrates cCrates, CrateRunnable running){
        this.cCrates = cCrates;
        this.running = running;
    }

    @EventHandler
    public void OpeningListener(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if(!p.equals(running.getPlayer())) return;

        if(running.getCounter()<7) { e.setCancelled(true); return; }

        CrateEntity entity = running.getChestBlock(e.getClickedBlock().getLocation());

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock()!=null && entity!=null && !entity.isOpened()){
            entity.getAnimation().start();
            if(running.allOpened())
                Bukkit.getScheduler().runTaskLater(cCrates, () -> running.getInstance().stopRunnable(), entity.getAnimation().getDelay()*running.SECONDS);
        }
        e.setCancelled(true);
        return;
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        if(e.getPlayer().equals(running.getPlayer())){
            running.getInstance().stopRunnable();
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

}
