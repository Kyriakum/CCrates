package kyriakum.ccrates.ccrates.animations;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum AnimationType {

        STD_ANIMATION(StdStack());

        private ItemStack item;

        AnimationType(ItemStack item){
                this.item = item;
        }

        public ItemStack getItem() {
                return item;
        }

        private static ItemStack StdStack(){
                ItemStack stack = new ItemStack(Material.DIAMOND);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName("Standard Animation");
                meta.setLore(Arrays.asList("This is the Standard Animation"));
                stack.setItemMeta(meta);
                return stack;
        }
}
