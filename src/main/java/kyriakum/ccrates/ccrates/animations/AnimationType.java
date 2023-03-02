package kyriakum.ccrates.ccrates.animations;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum AnimationType {

        STD_ANIMATION(StdStack(), "Standard Animation"),
        MAGIC_ANIMATION(MagicStack(), "Magic Animation");

        private ItemStack item;
        private String displayName;

        AnimationType(ItemStack item, String displayName){
                this.item = item; this.displayName = displayName;
        }

        public ItemStack getItem() {
                return item;
        }

        public String getDisplayName() {
                return displayName;
        }

        private static ItemStack StdStack(){
                ItemStack stack = new ItemStack(Material.DIAMOND);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName("Standard Animation");
                meta.setLore(Arrays.asList("This is the Standard Animation"));
                stack.setItemMeta(meta);
                return stack;
        }

        private static ItemStack MagicStack(){
                ItemStack stack = new ItemStack(Material.END_STONE);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName("Magic Animation");
                meta.setLore(Arrays.asList("This is the Magic Animation"));
                stack.setItemMeta(meta);
                return stack;
        }
}
