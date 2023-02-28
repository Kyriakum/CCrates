package kyriakum.ccrates.ccrates.managers.filemanagers;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.api.NewInstanceEvent;
import kyriakum.ccrates.ccrates.api.RemoveInstanceEvent;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.CrateInstance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationManager extends FileManager {

    private List<CrateInstance> cratesInstances;
    public LocationManager(CCrates cCrates) {
        super(cCrates);
        loadFile(new File(cCrates.getDataFolder(), "locations.yml"));
        loadConfig(YamlConfiguration.loadConfiguration(getFile()));
        cratesInstances = loadLocations(cCrates.getCrateManager().getCrates());
    }

    public List<CrateInstance> getCrateInstances(Crate crate) {
        List<CrateInstance> locs = new ArrayList<>();
        for(CrateInstance crateInstance : cratesInstances){
            if(crateInstance.getCrate().equals(crate)) locs.add(crateInstance);
        }
        return locs;
    }

    public List<CrateInstance> loadLocations(List<Crate> crates) {
            List<CrateInstance> instances = new ArrayList<>();
        for (Crate crate : crates) {
            if(getConfig().getConfigurationSection("Crates." + crate.getName() + ".Locations") == null) return instances;
            getConfig().getConfigurationSection("Crates." + crate.getName() + ".Locations").getKeys(false).forEach(location -> {
                Location loc = new Location(Bukkit.getWorld(getConfig().getString("Crates." + crate.getName() + ".Locations." + location + ".world")), getConfig().getDouble("Crates." + crate.getName() + ".Locations." + location + ".x"), getConfig().getDouble("Crates." + crate.getName() + ".Locations." + location + ".y"), getConfig().getDouble("Crates." + crate.getName() + ".Locations." + location + ".z"));
                instances.add(new CrateInstance(crate, loc, Integer.valueOf(location)));
            });
        }
        return instances;
    }

    public boolean containsCrate(Location location) {
            for (CrateInstance crateInstance : cratesInstances) {
                if(location.equals(crateInstance.getLocation())) return true;
        }
        return false;
    }

    public CrateInstance getCrateInstanceByLocation(Location location){
            for (CrateInstance crateInstance : cratesInstances) {
                if(location.equals(crateInstance.getLocation())) return crateInstance;

        }
        return null;
    }



    public CrateInstance getInstance(Crate crate, int id){
        for(CrateInstance instance : cratesInstances){
            if(instance.getCrate().equals(crate)&&instance.getId()==id) return instance;
        }
        return null;
    }

    public void addCrateLocation(Crate crate, Location loc){
        try {
            int id = 1;
            while(getInstance(crate, id)!=null) id++;

            getConfig().set("Crates." + crate.getName() + ".Locations." + id + ".world",loc.getWorld().getName());
            getConfig().set("Crates." + crate.getName() + ".Locations." + id + ".x", loc.getBlockX());
            getConfig().set("Crates." + crate.getName() + ".Locations." + id + ".y", loc.getBlockY());
            getConfig().set("Crates." + crate.getName() + ".Locations." + id + ".z", loc.getBlockZ());
            getConfig().save(getFile());
            CrateInstance instance = new CrateInstance(crate, loc, id);
            cratesInstances.add(instance);
            NewInstanceEvent e = new NewInstanceEvent(instance);
            Bukkit.getPluginManager().callEvent(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeAllCrateLocations(Crate crate){
        for(CrateInstance inst : getCrateInstances(crate)) removeCrateLocation(inst);
    }

    public void removeCrateLocation(CrateInstance inst){
        try {
            getConfig().set("Crates."+inst.getCrate().getName()+".Locations." + String.valueOf(inst.getId()), null);
            getConfig().save(getFile());

            RemoveInstanceEvent e = new RemoveInstanceEvent(inst);
            cratesInstances.remove(inst);
            Bukkit.getPluginManager().callEvent(e);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeCrateLocation(Location loc){
        CrateInstance inst = getCrateInstanceByLocation(loc);
        try {
            getConfig().set("Crates."+inst.getCrate().getName()+".Locations." + String.valueOf(inst.getId()), null);
            getConfig().save(getFile());

            RemoveInstanceEvent e = new RemoveInstanceEvent(inst);
            cratesInstances.remove(inst);
            Bukkit.getPluginManager().callEvent(e);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public int getLocSize(Crate crate){
        int i = 0;
        for(CrateInstance inst : cratesInstances){
            if(inst.getCrate().equals(crate)) i++;
        }
        return i;
    }

    public List<CrateInstance> getCratesInstances() {
        return cratesInstances;
    }
}