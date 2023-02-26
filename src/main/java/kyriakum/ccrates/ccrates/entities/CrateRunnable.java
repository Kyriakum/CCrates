package kyriakum.ccrates.ccrates.entities;

import kyriakum.ccrates.ccrates.entities.regions.Region2D;
import kyriakum.ccrates.ccrates.events.CrateOpeningListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CrateRunnable extends BukkitRunnable {

    private Player player;
    private CrateInstance instance;
    public final int SECONDS = 20;
    private int counter = 0;
    private final BlockFace mainChestFace;
    private List<CrateEntity> entities;
    private Region2D region2D;
    private Location playerLocation;

    private CrateOpeningListener listener;


    public CrateRunnable(Player player, CrateInstance instance){
        this.player = player;
        playerLocation = player.getLocation();
        this.instance = instance;
        Directional directional = (Directional) instance.getLocation().getBlock().getBlockData();
        mainChestFace = directional.getFacing();
        entities = new ArrayList<>();
        setChestEntities();
        region2D = new Region2D(instance.getLocation().clone().add(-3,-1,-3), instance.getLocation().clone().add(3,-1,3), this);
        region2D.noPassing();
        listener = new CrateOpeningListener(instance.getCrate().getCCrates(), this);
        Bukkit.getPluginManager().registerEvents(listener, instance.getCrate().getCCrates());
        start();
    }
    @Override
    public void run() {
        if(counter<2) {
            new Region2D(region2D.getPos1().clone().add(2-counter,0,2-counter),
                    region2D.getPos2().clone().subtract(2-counter,0,2-counter), this).fillRegion(instance.getCrate().getFloor());
         } else if (counter == 2){
            region2D.fillRegion(instance.getCrate().getFloor());
            region2D.clipCorners();
        } else if(counter<=6){
            entities.get(counter%4).spawn();
        } else if(counter==7) {
            cancel();
        }
        counter++;
    }

     public void start(){
        region2D.listAllBlocks();
        instance.getLocation().getBlock().setType(Material.AIR);
        player.teleport(instance.getLocation().clone().add(.5,0,.5));
        runTaskTimer(instance.getCrate().getCCrates(), SECONDS, SECONDS);
    }


    public Player getPlayer() {
        return player;
    }

    public CrateEntity getChestBlock(Location loc){
        for(CrateEntity entity : entities){
            if(loc.equals(entity.getLocation())) return entity;
        }
        return null;
    }

    private void setChestEntities(){
        entities.add(new CrateEntity(instance.getLocation().clone().subtract(3,0,0).getBlock().getLocation(), BlockFace.EAST, this));
        entities.add(new CrateEntity(instance.getLocation().clone().subtract(0,0,3).getBlock().getLocation(), BlockFace.SOUTH, this));
        entities.add(new CrateEntity(instance.getLocation().clone().add(3,0,0).getBlock().getLocation(), BlockFace.WEST, this));
        entities.add(new CrateEntity(instance.getLocation().clone().add(0,0,3).getBlock().getLocation(), BlockFace.NORTH, this));
    }

    public CrateInstance getInstance() {
        return instance;
    }

    public int getCounter() {
        return counter;
    }


    public void resetArea(){
        if(player.isOnline()) player.teleport(playerLocation);

       entities.forEach(CrateEntity::despawn);

        setMainBlock();

        region2D.terminate();

        HandlerList.unregisterAll(listener);
    }

    private void setMainBlock(){
        instance.getLocation().getBlock().setType(instance.getCrate().getBlock());
        Directional dir = (Directional) instance.getLocation().getBlock().getBlockData();
        dir.setFacing(mainChestFace);
        instance.getLocation().getBlock().setBlockData(dir);
    }

    public boolean allOpened(){
        for(CrateEntity entity : entities){
            if(!entity.isOpened()) return false;
        }
        return true;
    }
}
