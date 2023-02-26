package kyriakum.ccrates.ccrates.entities.regions;

import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.CrateRunning;
import kyriakum.ccrates.ccrates.events.Region2DListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class Region2D {

    private Location pos1;
    private Location pos2;
    private Map<Location, Material> blocks;
    private CrateRunning running;
    private Region2DListener listener;

    public Region2D(Location pos1, Location pos2, CrateRunning running){
        this.pos1 = pos1;
        this.pos2 = pos2;
        blocks = new HashMap<>();
        this.running = running;
        listener = new Region2DListener(running, this);
        Bukkit.getPluginManager().registerEvents(listener, running.getInstance().getCrate().getCCrates());
    }

    public void fillRegion(Material mat){
        for(int x = pos1.getBlockX(); x<=pos2.getBlockX(); x++){
            for(int z = pos1.getBlockZ(); z<=pos2.getBlockZ(); z++){
                Location placeb = new Location(pos1.getWorld(),x,pos1.getBlockY(),z);
                placeb.getBlock().setType(mat);
                pos2.getWorld().spawnParticle(Particle.ITEM_CRACK, placeb, 2 ,0,0,0,new ItemStack(mat));
            }
        }
    }

    public void clipCorners(){

        pos1.getBlock().setType(blocks.get(pos1));
        pos2.getBlock().setType(blocks.get(pos2));

        Location corner3 = new Location(pos1.getWorld(), pos1.getX(), pos1.getY(), pos2.getZ());
        Location corner4 = new Location(pos2.getWorld(), pos2.getX(), pos1.getY(), pos1.getZ());

        corner3.getBlock().setType(blocks.get(corner3));
        corner4.getBlock().setType(blocks.get(corner4));

        System.out.println(blocks.get(pos1).name());
    }

    public void listAllBlocks(){
        for(int x = pos1.getBlockX(); x<=pos2.getBlockX(); x++) {
            for (int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++) {
                Location block = new Location(pos1.getWorld(),x,pos1.getBlockY(),z);
                Block bblock = block.getBlock();
                blocks.put(block, bblock.getType());
            }
        }
    }

    public boolean isInRegion(Location loc){
        for(int x = pos1.getBlockX(); x<=pos2.getBlockX(); x++){
            for(int z = pos1.getBlockZ(); z<=pos2.getBlockZ(); z++){
                if(loc.getBlockX()==x&&loc.getBlockZ()==z) return true;
            }
        }
        return false;
    }

    public Location getPos2() {
        return pos2;
    }

    public Location getPos1() {
        return pos1;
    }


    public void resetRegion(){
        for(Location loc : blocks.keySet()){
            loc.getBlock().setType(blocks.get(loc));
            loc.getWorld().spawnParticle(Particle.DRAGON_BREATH,loc,0,0,0,0);
        }
    }

    public Map<Location, Material> getBlocks() {
        return blocks;
    }

    public Region2D clone(){
        try{
            return (Region2D) super.clone();
        }catch (CloneNotSupportedException e){
            throw new Error(e);
        }
    }

    public void terminate(){
        resetRegion();
        HandlerList.unregisterAll(listener);
    }
}
