package kyriakum.ccrates.ccrates.animations;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.CrateEntity;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockAction;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;


public class StdAnimation extends Animation {

    private Item item;

    public StdAnimation(CCrates cCrates, CrateEntity entity) {
        super(cCrates, entity, 3);
    }


    @Override
    public void start() {
        if(getCrateEntity().isOpened()) return;
        super.start();
        PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(new BlockPosition(getCrateEntity().getLocation().getBlockX(),getCrateEntity().getLocation().getBlockY(),getCrateEntity().getLocation().getBlockZ()), CraftMagicNumbers.getBlock(getCrateEntity().getRunning().getInstance().getCrate().getBlock()),1 ,1);
        Bukkit.getOnlinePlayers().forEach(player -> ((CraftPlayer) player).getHandle().b.a(packet));
        spawnItem();
        getCrateEntity().setOpened();
        setDone();
    }

    @Override
    public void despawn() {
        if(item!=null)
            item.remove();
    }

    private void spawnItem(){
        CrateEntity entity = getCrateEntity();
        item = entity.getPlayer().getWorld().dropItem(entity.getLocation().clone().add(.5,.8,.5), entity.getContent().getContent());
        item.setVelocity(new Vector(0,0.1,0));
        item.setCustomNameVisible(true);
        item.setCustomName(entity.getContent().getContent().getItemMeta().getDisplayName());
        item.setPickupDelay(9999);
    }
}
