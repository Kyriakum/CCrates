package kyriakum.ccrates.ccrates.animations;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum AnimationType {

        STD_ANIMATION(StdStack()),
        MAGIC_ANIMATION(MagicStack());

        private ItemStack item;
        private String displayName;

        AnimationType(ItemStack item){
                this.item = item; this.displayName = item.getItemMeta().getDisplayName();
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
                meta.setDisplayName(ChatColor.GRAY.toString() + "Standard Animation");
                meta.setLore(Arrays.asList(ChatColor.GOLD + "This is the Standard Animation"));
                stack.setItemMeta(meta);
                return stack;
        }

        private static ItemStack MagicStack(){
                ItemStack stack = new ItemStack(Material.END_STONE);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Magic Animation");
                meta.setLore(Arrays.asList(ChatColor.GOLD + "This is the Magic Animation"));
                stack.setItemMeta(meta);
                return stack;
        }
}
