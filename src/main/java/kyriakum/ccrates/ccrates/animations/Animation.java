package kyriakum.ccrates.ccrates.animations;
import kyriakum.ccrates.ccrates.api.PlayerOpenCrateEntityEvent;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.CrateEntity;
import org.bukkit.Bukkit;

public abstract class Animation {

    private CrateEntity entity;

    public Animation(CrateEntity entity){
        this.entity = entity;
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
                case STD_ANIMATION: return new StdAnimation(entity);
                default: break;
            }
            return null;
    }
}
