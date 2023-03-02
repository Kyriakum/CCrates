package kyriakum.ccrates.ccrates.animations;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.CrateEntity;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockAction;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class MagicAnimation extends Animation{

    private Item item;

    private double degrees = 0;
    private float pitch = 0.1f;
    private double yaxis = 0.1;
    private double radius = 0.9;
    private int counter = 0;

    public MagicAnimation(CCrates cCrates, CrateEntity entity) {
        super(cCrates, entity, 7);
    }

    @Override
    public void start(){
        if(getCrateEntity().isOpened()) return;
        super.start();
        Location loc = getCrateEntity().getLocation();
        Player player = getCrateEntity().getPlayer();
        Bukkit.getScheduler().runTaskTimer(getcCrates(), task -> {
            Location loc1 = new Location(loc.getWorld(),loc.getBlockX()+0.5+radius*Math.sin(degrees), loc.getBlockY()+yaxis, loc.getBlockZ()+0.5+radius*Math.cos(degrees));
            if(counter>=45&&counter<70) {
                counter+=1;

            }
            else if(counter==70){
                PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(new BlockPosition(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()), CraftMagicNumbers.getBlock(getCrateEntity().getRunning().getInstance().getCrate().getBlock()),1 ,1);
                for(Player player1 : Bukkit.getOnlinePlayers())
                    ((CraftPlayer) player1).getHandle().b.a(packet);
                player.getWorld().spawnParticle(Particle.DRAGON_BREATH, loc1,10,0.3,0,0.3);
                player.getWorld().playSound(loc1, Sound.ENTITY_GENERIC_EXPLODE, 5, 1);
                spawnItem();
                task.cancel();
                setDone();
                return;
            } else {
                player.getWorld().spawnParticle(Particle.DRAGON_BREATH, loc1,0,0,0,0);
                if(counter%4==0) {
                    player.getWorld().playSound(loc1, Sound.BLOCK_NOTE_BLOCK_FLUTE, 5, pitch);
                    pitch += 0.15;
                }
                degrees += 2;

                if (radius > 0) {
                    radius -= 0.025;
                    yaxis += 0.05;
                }
                counter += 1;
            }
        },1 ,1);
     }

    @Override
    public void despawn() {
        if(item!=null)
            item.remove();
    }
    private void spawnItem(){
        CrateEntity entity = getCrateEntity();
        item = entity.getPlayer().getWorld().dropItem(entity.getLocation().clone().add(.5,entity.getRunning().getInstance().getCrate().getBlock().name().contains("CHEST") ? 0.8 : 1.1,.5), entity.getContent().getContent());
        item.setVelocity(new Vector(0,0.1,0));
        item.setCustomNameVisible(true);
        item.setCustomName(entity.getContent().getContent().getItemMeta().getDisplayName());
        item.setPickupDelay(9999);
    }
}
