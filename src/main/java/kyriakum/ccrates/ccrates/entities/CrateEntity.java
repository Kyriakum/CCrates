package kyriakum.ccrates.ccrates.entities;

import kyriakum.ccrates.ccrates.entities.contents.Content;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockAction;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrateEntity {

    private Content content;
    private Location location;
    private Item item;
    private BlockFace face;
    private CrateRunnable running;
    private boolean isOpened;

    public CrateEntity(Location location, BlockFace face, CrateRunnable running){
        this.location = location;
        this.face = face;
        this.running = running;
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

    private void setupItem(){
        item = running.getPlayer().getWorld().dropItem(location.clone().add(.5,.8,.5), content.getContent());
        item.setVelocity(new Vector(0,0.1,0));
        item.setCustomNameVisible(true);
        item.setCustomName(content.getContent().getItemMeta().getDisplayName());
        item.setPickupDelay(9999);
    }


    public void makeMagicOpening(){
        if(isOpened) return;
        PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(new BlockPosition(location.getBlockX(),location.getBlockY(),location.getBlockZ()), CraftMagicNumbers.getBlock(running.getInstance().getCrate().getBlock()),1 ,1);
        Bukkit.getOnlinePlayers().forEach(player -> ((CraftPlayer) player).getHandle().b.a(packet));
        content.givePlayer(running.getPlayer());
        setupItem();
        setOpened();
    }

    public void spawn(){
        Block block = getLocation().getBlock();
        block.setType(running.getInstance().getCrate().getBlock());
        Directional directional = (Directional) block.getBlockData();
        directional.setFacing(face);
        block.setBlockData(directional);
        getLocation().getWorld().spawnParticle(Particle.ITEM_CRACK, getLocation().clone().add(.5,.5,.5), 0,0,0,0,new ItemStack(Material.CHEST));
        getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_WOOD_PLACE, 1, 1);

    }

    public void despawn(){
        getLocation().getBlock().setType(Material.AIR);
        if(item!=null)
        item.remove();
        getLocation().getWorld().spawnParticle(Particle.ITEM_CRACK, getLocation().clone().add(.5,.5,.5), 0,0,0,0,new ItemStack(Material.CHEST));
        getLocation().getWorld().playSound(getLocation(), Sound.BLOCK_WOOD_BREAK, 1, 1);

    }

    public Location getLocation() {
        return location;
    }

    public void setFace(BlockFace face) {
        this.face = face;
    }

    public void setOpened(){
        isOpened = true;
    }

    public boolean isOpened(){
        return isOpened;
    }
}
