package kyriakum.ccrates.ccrates.managers.filemanagers;

import kyriakum.ccrates.ccrates.CCrates;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessagesManager extends FileManager{

    private String PREFIX;
    private String noPermission;
    private String disabledCrate;
    private String privateWinMessage;
    private String announcementWinMessage;
    private String crateInstDestroyed;
    private String crateInstPlaced;
    private String crateRemoved;
    private String crateCreated;
    private String blockChanged;
    private String floorChanged;
    private String animationChanged;
    private String keyChanged;
    private String contentAdded;
    private String contentRemoved;
    private String crateDisabled;
    private String crateEnabled;
    private String giveKey;
    private String keyGivenToPlayer;
    private String pluginReloaded;
    private String keyNeeded;

    public MessagesManager(CCrates cCrates) {
        super(cCrates);
        loadFile(new File(cCrates.getDataFolder(), "messages.yml"));
        loadConfig(YamlConfiguration.loadConfiguration(getFile()));
        loadMessages();
    }

    private void loadMessages(){
        loadPrefix();
        loadNoPermission(); loadDisabledCrate();
        loadPrivateWinMessage(); loadAnnouncementWinMessage();
        loadCrateInstDestroyed();loadCrateInstPlaced();
        loadAnimationChanged();
        loadCrateCreated();loadCrateRemoved();loadBlockChanged();
        loadFloorChanged();loadKeyChanged();loadContentAdded();
        loadContentRemoved();loadCrateDisabled();loadCrateEnabled();
       loadGiveKey();loadKeyGivenToPlayer();loadPluginReloaded();
       loadKeyNeeded();
    }

    private void loadPrefix(){
        this.PREFIX = getCCrates().getPlaceHolder().alternateColors(getConfig().getString("Prefix"));
    }
    private void loadNoPermission(){
        this.noPermission = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("NoPermission"));
    }
    private void loadDisabledCrate(){
        this.disabledCrate = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("DisabledCrate"));
    }
    private void loadPrivateWinMessage(){
        this.privateWinMessage = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("PrivateWinMessage"));
    }
    private void loadAnnouncementWinMessage(){
        this.announcementWinMessage = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("AnnouncementWinMessage"));
    }
    private void loadCrateInstDestroyed(){
        this.crateInstDestroyed = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("CrateInstDestroyed"));
    }
    private void loadCrateInstPlaced(){
        this.crateInstPlaced = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("CrateInstPlaced"));
    }
    private void loadCrateCreated(){
        this.crateCreated = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("CrateCreated"));
    }
    private void loadCrateRemoved(){
        this.crateRemoved = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("CrateRemoved"));
    }
    private void loadBlockChanged(){
        this.blockChanged =PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("BlockChanged"));
    }
    private void loadFloorChanged(){
        this.floorChanged =PREFIX+ getCCrates().getPlaceHolder().alternateColors(getConfig().getString("FloorChanged"));
    }
    private void loadKeyChanged(){
        this.keyChanged = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("KeyChanged"));
    }
    private void loadAnimationChanged(){
        this.animationChanged = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("AnimationChanged"));
    }
    private void loadContentAdded(){
        this.contentAdded= PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("ContentAdded"));
    }
    private void loadContentRemoved(){
        this.contentRemoved =PREFIX+ getCCrates().getPlaceHolder().alternateColors(getConfig().getString("ContentRemoved"));
    }
    private void loadCrateDisabled(){
        this.crateDisabled = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("CrateDisabled"));
    }
    private void loadCrateEnabled(){
        this.crateEnabled=PREFIX+ getCCrates().getPlaceHolder().alternateColors(getConfig().getString("CrateEnabled"));
    }
    private void loadGiveKey(){
        this.giveKey = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("GiveKey"));
    }
    private void loadKeyGivenToPlayer(){
        this.keyGivenToPlayer =PREFIX+ getCCrates().getPlaceHolder().alternateColors(getConfig().getString("KeyGivenToPlayer"));
    }
    private void loadPluginReloaded(){
        this.pluginReloaded = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("PluginReloaded"));
    }
    private void loadKeyNeeded(){
        this.keyNeeded = PREFIX+getCCrates().getPlaceHolder().alternateColors(getConfig().getString("KeyNeeded"));

    }
    public String getPrefix(){
        return PREFIX;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getDisabledCrate() {
        return disabledCrate;
    }

    public String getAnnouncementWinMessage() {
        return announcementWinMessage;
    }

    public String getPrivateWinMessage() {
        return privateWinMessage;
    }

    public String getCrateInstDestroyed() {
        return crateInstDestroyed;
    }

    public String getCrateInstPlaced() {
        return crateInstPlaced;
    }

    public String getCrateCreated() {
        return crateCreated;
    }

    public String getCrateRemoved() {
        return crateRemoved;
    }

    public String getBlockChanged() {
        return blockChanged;
    }

    public String getFloorChanged() {
        return floorChanged;
    }

    public String getAnimationChanged() {
        return animationChanged;
    }

    public String getKeyChanged() {
        return keyChanged;
    }

    public String getContentAdded() {
        return contentAdded;
    }

    public String getContentRemoved() {
        return contentRemoved;
    }

    public String getCrateDisabled() {
        return crateDisabled;
    }

    public String getCrateEnabled() {
        return crateEnabled;
    }

    public String getGiveKey() {
        return giveKey;
    }

    public String getKeyGivenToPlayer() {
        return keyGivenToPlayer;
    }

    public String getKeyNeeded() {
        return keyNeeded;
    }

    public String getPluginReloaded() {
        return pluginReloaded;
    }
}
