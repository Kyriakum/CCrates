package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.api.crates.CrateAnimationChangeEvent;
import kyriakum.ccrates.ccrates.api.crates.CrateBlockChangeEvent;
import kyriakum.ccrates.ccrates.api.crates.CrateFloorChangeEvent;
import kyriakum.ccrates.ccrates.api.crates.CrateKeyChangeEvent;
import kyriakum.ccrates.ccrates.api.instances.NewInstanceEvent;
import kyriakum.ccrates.ccrates.api.instances.RemoveInstanceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CCratesEventsListener implements Listener {

    private CCrates cCrates;
    public CCratesEventsListener(CCrates c){
        this.cCrates = c;
    }

    @EventHandler
    public void onInstanceAdd(NewInstanceEvent e){
        e.getCrateInstance().getCrate().loadGUIs();
        cCrates.reloadGUI();
    }


    @EventHandler
    public void onInstanceRemove(RemoveInstanceEvent e){
        e.getCrateInstance().getCrate().loadGUIs();
        cCrates.reloadGUI();
    }

    @EventHandler
    public void onBlockChange(CrateBlockChangeEvent e){
        e.getCrate().loadGUIs();
        cCrates.reloadGUI();
    }
    @EventHandler
    public void onFloorChange(CrateFloorChangeEvent e){
        e.getCrate().loadGUIs();
        cCrates.reloadGUI();
    }
    @EventHandler
    public void onKeyChange(CrateKeyChangeEvent e){
        e.getCrate().loadGUIs();
        cCrates.reloadGUI();
    }
    @EventHandler
    public void onAnimationChange(CrateAnimationChangeEvent e){
        e.getCrate().loadGUIs();
        cCrates.reloadGUI();
    }
}
