package kyriakum.ccrates.ccrates.managers.filemanagers;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessagesManager extends FileManager{

    private String nopermission;

    public MessagesManager(CCrates cCrates) {
        super(cCrates);
        loadFile(new File(cCrates.getDataFolder(), "messages.yml"));
        loadConfig(YamlConfiguration.loadConfiguration(getFile()));
    }

    private void loadMessages(){
        nopermission = PlaceHolder.alternateColors(getConfig().getString("No_Permission"));
    }

    public String getNoPermission() {
        return nopermission;
    }
}
