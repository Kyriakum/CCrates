package kyriakum.ccrates.ccrates.entities;

import kyriakum.ccrates.ccrates.api.PlayerOpenCrateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CrateInstance {

    private Crate crate;
    private Location location;
    private CrateState state;
    private CrateRunnable running;
    private int id;

    public CrateInstance(Crate crate, Location location, int id){
        this.crate = crate;
        this.location = location;
        this.state = CrateState.ENABLED;
        this.id = id;
    }

    public void startCrate(Player player){
        running = new CrateRunnable(player, this);
        state = CrateState.IN_USE;
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);

        PlayerOpenCrateEvent e = new PlayerOpenCrateEvent(player, this);
        Bukkit.getPluginManager().callEvent(e);
    }

    public Location getLocation() {
        return location;
    }

    public Crate getCrate() {
        return crate;
    }

    public CrateState getState() {
        return state;
    }

    public void setState(CrateState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public boolean isRunning(){
        if(state==CrateState.IN_USE) return true;
        return false;
    }

    public void stopRunnable(){
        running.resetArea();
        setState(CrateState.ENABLED);
        running = null;
    }

    public CrateRunnable getCrateRunnable() {
        return running;
    }


}
