package kyriakum.ccrates.ccrates;

import kyriakum.ccrates.ccrates.commands.CCratesCommands;
import kyriakum.ccrates.ccrates.entities.CrateInstance;
import kyriakum.ccrates.ccrates.events.CCratesEventsListener;
import kyriakum.ccrates.ccrates.events.GUIInteractListener;
import kyriakum.ccrates.ccrates.events.PlayerInteract;
import kyriakum.ccrates.ccrates.events.SetCrateListener;
import kyriakum.ccrates.ccrates.guis.MainGUI;
import kyriakum.ccrates.ccrates.managers.filemanagers.ConfigManager;
import kyriakum.ccrates.ccrates.managers.CrateManager;
import kyriakum.ccrates.ccrates.managers.filemanagers.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CCrates extends JavaPlugin {

    private ConfigManager configManager;
    private LocationManager locationManager;
    private CrateManager crateManager;
    private MainGUI mainGUI;

    @Override
    public void onEnable() {
        //Setup Managers
        configManager = new ConfigManager(this);
        crateManager = new CrateManager(this);
        locationManager = new LocationManager(this);
         //Setup Commands
        new CCratesCommands(this);

        mainGUI = new MainGUI(this);
        crateManager.loadGUIS();
        //Setup Listeners
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(this), this);
        Bukkit.getPluginManager().registerEvents(new SetCrateListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GUIInteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CCratesEventsListener(this), this);
    }

    @Override
    public void onDisable() {
        safeRemove();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public CrateManager getCrateManager() {
        return crateManager;
    }

    public MainGUI getMainGUI() {return mainGUI;}

    private void safeRemove(){
        for(CrateInstance crateInstance : locationManager.getCratesInstances()){
            if(crateInstance.isRunning()){
                crateInstance.stopRunnable();
            }
        }
    }

    public void reloadGUI() { mainGUI = new MainGUI(this);}
}
