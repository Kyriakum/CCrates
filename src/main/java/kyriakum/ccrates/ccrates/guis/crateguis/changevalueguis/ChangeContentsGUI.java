package kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis;

import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.entities.contents.Content;
import kyriakum.ccrates.ccrates.guis.MultiInventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChangeContentsGUI extends MultiInventory implements ChangeValue {

    private ChangeValueType type;
    private Crate crate;

    public ChangeContentsGUI(Crate crate) {
        super(crate.getName() + " Contents");
        type = ChangeValueType.CONTENTS;
        this.crate = crate;
        setupInvs();
    }

    @Override
    public ChangeValueType getType() {
        return type;
    }

    @Override
    public Inventory getInventory() {
        return getInventory(1);
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
    protected List<ItemStack> getInvStacks() {
    return crate.getContents().stream().map(Content::getContent).collect(Collectors.toList());
    }

    @Override
    public Crate getCrate() {
        return crate;
    }
}
