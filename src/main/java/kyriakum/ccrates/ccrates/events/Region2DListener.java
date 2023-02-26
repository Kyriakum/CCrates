package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.entities.CrateRunnable;
import kyriakum.ccrates.ccrates.entities.regions.Region2D;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Region2DListener implements Listener {

    private CrateRunnable running;
    private Region2D region;

    public Region2DListener(CrateRunnable running, Region2D region){
        this.running = running;
        this.region = region;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(!e.getPlayer().equals(running.getPlayer())){
            if(region.isInRegion(e.getPlayer().getLocation())){
                e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(-1).setY(0.05));
            }
        }
    }

}
