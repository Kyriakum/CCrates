package kyriakum.ccrates.ccrates.managers;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;

import java.util.List;

public class CrateManager {

    private final CCrates cCrates;

    private List<Crate> crates;

    public CrateManager(CCrates cCrates){
        this.cCrates = cCrates;
        crates = cCrates.getConfigManager().loadCrates();
    }

    public List<Crate> getCrates() {
        return crates;
    }

    public Crate getCrate(String name){
        for(Crate crate : crates){
            if(name.equalsIgnoreCase(crate.getName())) return crate;
        }
        return null;
    }


}
