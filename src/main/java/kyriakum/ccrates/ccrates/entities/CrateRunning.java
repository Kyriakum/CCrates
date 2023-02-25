package kyriakum.ccrates.ccrates.entities;

import kyriakum.ccrates.ccrates.events.CrateOpeningListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrateRunning extends BukkitRunnable {

    private Player player;
    private CrateInstance instance;
    private Map<Location, Material> blocks;
    private final int SECONDS = 20;
    private int counter = 0;
    private final BlockFace mainChestFace;
    private Location pos1;
    private Location pos2;

    private CrateOpeningListener listener;


    public CrateRunning(Player player, CrateInstance instance){
        this.player = player;
        this.instance = instance;
        Directional directional = (Directional) instance.getLocation().getBlock().getBlockData();
        mainChestFace = directional.getFacing();
        blocks = new HashMap<>();
        pos1 = instance.getLocation().clone().add(-3,-1,-3);
        pos2 = instance.getLocation().clone().add(3,-1,3);
        listener = new CrateOpeningListener(instance.getCrate().getCCrates(), this);
        Bukkit.getPluginManager().registerEvents(listener, instance.getCrate().getCCrates());
        start();
    }
    @Override
    public void run() {
        if(counter<2) {
            fillRegion(pos1.clone().add(2-counter,0,2-counter),
            pos2.clone().subtract(2-counter,0,2-counter), instance.getCrate().getFloor());
        } else if (counter == 2){
            fillRegion(pos1,pos2,instance.getCrate().getFloor());
            clipCorners(pos1,pos2);
        } else if(counter<=6){
            spawnChest(counter);
        } else if(counter==7) {
            counter++;
            cancel();
            return;
        } else {
            resetArea();
            return;
        }
        counter++;
    }

    public void start(){
        listAllBlocks(pos1,pos2);
        instance.getLocation().getBlock().setType(Material.AIR);
        player.teleport(instance.getLocation().add(.5,0,.5));
        runTaskTimer(instance.getCrate().getCCrates(), SECONDS, SECONDS);
    }

    private void listAllBlocks(Location pos1, Location pos2){
        for(int x = pos1.getBlockX(); x<=pos2.getBlockX(); x++) {
            for (int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++) {
            Location block = new Location(pos1.getWorld(),x,pos1.getBlockY(),z);
            Block bblock = block.getBlock();
            blocks.put(block, bblock.getType());
            }
        }
    }
    private void fillRegion(Location pos1, Location pos2, Material mat){
        for(int x = pos1.getBlockX(); x<=pos2.getBlockX(); x++){
            for(int z = pos1.getBlockZ(); z<=pos2.getBlockZ(); z++){
                Location placeb = new Location(pos1.getWorld(),x,pos1.getBlockY(),z);
                placeb.getBlock().setType(mat);
                pos2.getWorld().spawnParticle(Particle.ITEM_CRACK, placeb, 2 ,0,0,0,new ItemStack(mat));
            }
        }
    }

    private void clipCorners(Location pos1, Location pos2){

        pos1.getBlock().setType(blocks.get(pos1));
        pos2.getBlock().setType(blocks.get(pos2));

        Location corner3 = new Location(pos1.getWorld(), pos1.getX(), pos1.getY(), pos2.getZ());
        Location corner4 = new Location(pos2.getWorld(), pos2.getX(), pos1.getY(), pos1.getZ());

        corner3.getBlock().setType(blocks.get(corner3));
        corner4.getBlock().setType(blocks.get(corner4));

        System.out.println(blocks.get(pos1).name());
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isChestBlock(Location loc){
        for(Location location : chestLocations()){
            System.out.println(loc + "\n" + location);
            if(loc.equals(new Location(location.getWorld(),location.getBlockX(),location.getBlockY(),location.getBlockZ()))) return true;
        }
        return false;
    }

    private List<Location> chestLocations(){
        List<Location> locs = new ArrayList<>();
        locs.add(instance.getLocation().clone().subtract(3,0,0));
        locs.add(instance.getLocation().clone().subtract(0,0,3));
        locs.add(instance.getLocation().clone().add(3,0,0));
        locs.add(instance.getLocation().clone().add(0,0,3));
    return locs;
    }

    public CrateInstance getInstance() {
        return instance;
    }

    public int getCounter() {
        return counter;
    }

    public void spawnChest(int mod){
        int value = mod%4;
        Block block = chestLocations().get(value).getBlock();
        block.setType(instance.getCrate().getBlock());
        Directional direction = (Directional) block.getBlockData();
        BlockFace face;
        switch (value){
            case 0: {
                face = BlockFace.EAST;
                break;
            }
            case 1: {
                face = BlockFace.SOUTH;
                break;
            }
            case 2: {
                face = BlockFace.WEST;
            break;
            }
            default: face = BlockFace.NORTH;
            break;
        }
        direction.setFacing(face);
        block.setBlockData(direction);
    }

    public void resetArea(){
        for(Location loc : blocks.keySet()){
            loc.getBlock().setType(blocks.get(loc));
            loc.getWorld().spawnParticle(Particle.DRAGON_BREATH,loc,0,0,0,0);
        }

        for(Location loc : chestLocations()){
            loc.getBlock().setType(Material.AIR);
            loc.getWorld().spawnParticle(Particle.ITEM_CRACK,loc,0,0,0,0, new ItemStack(instance.getCrate().getBlock()));
        }

        instance.getLocation().getBlock().setType(instance.getCrate().getBlock());
        Directional dir = (Directional) instance.getLocation().getBlock().getBlockData();
        dir.setFacing(mainChestFace);
        instance.getLocation().getBlock().setBlockData(dir);
        instance.setState(CrateState.ENABLED);

        HandlerList.unregisterAll(listener);
        cancel();
    }
}
