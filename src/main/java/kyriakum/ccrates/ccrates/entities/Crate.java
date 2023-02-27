package kyriakum.ccrates.ccrates.entities;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.animations.Animation;
import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.animations.StdAnimation;
import kyriakum.ccrates.ccrates.entities.contents.Content;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Crate {

    private String name;
    private String hologramName;
    private ItemStack key;
    private Material block;
    private Material floor;
    private List<Content> contents;
    private AnimationType type;
    private final CCrates cCrates;

    public Crate(CCrates cCrates, String name, String hologramName, ItemStack key, Material block, Material floor, List<Content> contents){
        this.cCrates = cCrates;
        this.name = name;
        this.hologramName = hologramName;
        this.key = key;
        type = AnimationType.STD_ANIMATION;
        this.block = block;
        this.floor = floor;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public String getHologramName() {
        return hologramName;
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

}
