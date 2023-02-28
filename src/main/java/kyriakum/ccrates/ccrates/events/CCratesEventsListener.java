package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.api.NewInstanceEvent;
import kyriakum.ccrates.ccrates.api.RemoveInstanceEvent;
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

    //TODO ALL EVENTS OF CHANGES
}
