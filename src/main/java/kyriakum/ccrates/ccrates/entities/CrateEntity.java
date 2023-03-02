package kyriakum.ccrates.ccrates.entities;

import kyriakum.ccrates.ccrates.animations.Animation;
import kyriakum.ccrates.ccrates.entities.contents.Content;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrateEntity {

    private Content content;
    private Location location;
    private BlockFace face;
    private CrateRunnable running;
    private Animation animation;
    private boolean isOpened;

    public CrateEntity(Location location, BlockFace face, CrateRunnable running){
        this.location = location;
        this.face = face;
        this.running = running;
        animation = Animation.getCrateAnimation(this);
        content = drawRandomItem();
        isOpened = false;
    }

    private Content drawRandomItem(){
        List<Content> contents = new ArrayList<>();
        for(Content content : running.getInstance().getCrate().getContents()){
            for(int i = 0; i<content.getPercentage();i++){
                contents.add(content);
            }
        }
        Collections.shuffle(contents);
        Content chosen = (Content) contents.toArray()[0];
        return chosen;
    }

    public void spawn(){
        Block block = getLocation().getBlock();
        block.setType(running.getInstance().getCrate().getBlock());
        if(face!=null) {
            Directional directional = (Directional) block.getBlockData();
            directional.setFacing(face);
            block.setBlockData(directional);
        }
        getLocation().getWorld().spawnParticle(Particle.ITEM_CRACK, getLocation().clone().add(.5,.5,.5), 0,0,0,0,new ItemStack(Material.CHEST));
        getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_WOOD_PLACE, 1, 1);

    }

    public void despawn(){
        getLocation().getBlock().setType(Material.AIR);
        animation.despawn();
        getLocation().getWorld().spawnParticle(Particle.ITEM_CRACK, getLocation().clone().add(.5,.5,.5), 0,0,0,0,new ItemStack(Material.CHEST));
        getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);

    }

    public Location getLocation() {
        return location;
    }

    public void setOpened(){
        isOpened = true;
    }

    public boolean isOpened(){
        return isOpened;
    }

    public Player getPlayer() { return running.getPlayer(); }

    public Content getContent() {return content;}

    public CrateRunnable getRunning() {return running;}

    public Animation getAnimation() {return animation;}
}
