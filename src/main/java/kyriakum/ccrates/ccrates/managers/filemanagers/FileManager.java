package kyriakum.ccrates.ccrates.managers.filemanagers;

import kyriakum.ccrates.ccrates.CCrates;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class FileManager {

    private CCrates cCrates;
    private YamlConfiguration config;
    private File file;

    public FileManager(CCrates cCrates){
        this.cCrates = cCrates;
    }

    public void loadFile(File file){
        this.file = file;
        if(!file.exists()){
            cCrates.saveResource(file.getName(), false);
        }
    }
    public void loadConfig(YamlConfiguration config){
        this.config = config;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    protected CCrates getCCrates(){
        return cCrates;
    }
}
