package kyriakum.ccrates.ccrates.entities;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.entities.contents.Content;
import kyriakum.ccrates.ccrates.guis.crateguis.CrateMenuGUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Crate {

    private String name;
    private String displayName;
    private ItemStack key;
    private Material block;
    private Material floor;
    private List<Content> contents;
    private AnimationType type;
    private boolean enabled;
    private CrateMenuGUI menuGUI;
    private final CCrates cCrates;

    public Crate(CCrates cCrates, String name, String displayName, ItemStack key, Material block, Material floor, List<Content> contents, AnimationType type, boolean enabled){
        this.cCrates = cCrates;
        this.name = name;
        this.displayName = displayName;
        this.key = key;
        this.type = type;
        this.block = block;
        this.floor = floor;
        this.contents = contents;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ItemStack getKey() {
        return key;
    }

    public Material getBlock() {
        return block;
    }

    public Material getFloor() {
        return floor;
    }

    public List<Content> getContents() {
        return contents;
    }

    public CCrates getCCrates(){
        return cCrates;
    }

    public void addContent(Content content){
        contents.add(content);
    }

    public Content getContent(int id){
        for(Content content : contents){
            if(content.getContentID()==id) return content;
        }
        return null;
    }

    public AnimationType getAnimationType() {
        return type;
    }

    public void removeContent(int id){
        contents.remove(getContent(id));
    }

    public void loadGUIs(){
        menuGUI = new CrateMenuGUI(cCrates,this);
    }

    public CrateMenuGUI getMenuGUI() {return menuGUI;}

    public void setBlock(Material block) {
        this.block = block;
    }

    public void setFloor(Material floor) {
        this.floor = floor;
    }

    public void setKey(ItemStack key) {
        this.key = key;
    }

    public void setAnimationType(AnimationType type) {
        this.type = type;
    }

    public boolean isChest(){
        if(getBlock().name().contains("CHEST")) return true;
        return false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean enable(){
        if(enabled) return false;
        return enabled = true;
    }

    public boolean disable(){
        if(!enabled) return false;
        return !(enabled = false);
    }
}
