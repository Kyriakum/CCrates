package kyriakum.ccrates.ccrates.animations;
import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.api.PlayerOpenCrateEntityEvent;
import kyriakum.ccrates.ccrates.entities.CrateEntity;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public abstract class Animation {

    private CrateEntity entity;
    private CCrates cCrates;
    private boolean done = false;
    private int delay;

    public Animation(CCrates cCrates, CrateEntity entity, int delay){
        this.cCrates = cCrates;
        this.entity = entity;
        this.delay = delay;
    }

    public void setDone() { done = true; getCrateEntity().getContent().givePlayer(getCrateEntity().getPlayer());}

    public boolean isDone() {return done; }

    public int getDelay() {
        return delay;
    }

    public CrateEntity getCrateEntity() {
        return entity;
    }

    public void start(){
        entity.setOpened();
        PlayerOpenCrateEntityEvent e = new PlayerOpenCrateEntityEvent(getCrateEntity().getPlayer(), entity);
        Bukkit.getPluginManager().callEvent(e);
    }

    public abstract void despawn();

    public static Animation getCrateAnimation(CrateEntity entity){
            AnimationType type = entity.getRunning().getInstance().getCrate().getAnimationType();
            switch (type){
                case STD_ANIMATION: return new StdAnimation(CCrates.getCCrates(), entity);
                case MAGIC_ANIMATION: return new MagicAnimation(CCrates.getCCrates(), entity);
                default: break;
            }
            return null;
    }

    public CCrates getcCrates() {
        return cCrates;
    }
}
