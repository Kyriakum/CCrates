package kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis;

import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.entities.Crate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChangeAnimationGUI implements ChangeValue {

    //TODO NEXT SESH


    private Inventory inventory;
    private ChangeValueType type;
    private Crate crate;
    private final int SIZE = 9*3;

    public ChangeAnimationGUI(Crate crate){
        this.crate = crate;
    type = ChangeValueType.ANIMATION;
    setupInv();
    }


    @Override
    public ChangeValueType getType() {
        return type;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean accept(ItemStack stack) {

        return true;
    }

    @Override
    public ItemStack acceptStack() {
        return null;
    }

    @Override
    public Crate getCrate() {
        return crate;
    }


    private void setupInv(){
        inventory = Bukkit.createInventory(null, SIZE, ChatColor.DARK_PURPLE + "Choose Animation for " + crate.getName());
        for(ItemStack stack : getAnimationStacks()){
            inventory.addItem(stack);
        }
    }

    public List<ItemStack> getAnimationStacks(){
        List<ItemStack> stacks =Arrays.stream(AnimationType.values()).map(AnimationType::getItem).collect(Collectors.toList());
        stacks.remove(crate.getAnimationType().getItem());
        return stacks;
    }
}
