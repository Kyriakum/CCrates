package kyriakum.ccrates.ccrates.guis.crateguis;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class SettingsGUI {

    private Crate crate;
    private CCrates cCrates;
    private Inventory settings;
    private final int SIZE = 9*3;

    public SettingsGUI(CCrates cCrates, Crate crate){
        this.cCrates = cCrates;
        this.crate = crate;
        setupInv();
    }

    public void setupInv(){
        settings = Bukkit.createInventory(null, SIZE, crate.getName() + " Settings");
    }


    public Inventory getInventory(){return settings;}

}
