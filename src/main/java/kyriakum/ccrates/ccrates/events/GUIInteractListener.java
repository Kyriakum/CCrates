package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.guis.crateguis.CrateInstancesGUI;
import kyriakum.ccrates.ccrates.guis.crateguis.CrateMenuGUI;
import kyriakum.ccrates.ccrates.guis.crateguis.SettingsGUI;
import kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis.ChangeAnimationGUI;
import kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis.ChangeContentsGUI;
import kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis.ChangeValue;
import kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis.ChangeValueType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GUIInteractListener implements Listener {

    private CCrates cCrates;
    public GUIInteractListener(CCrates cCrates){
        this.cCrates = cCrates;
    }


    @EventHandler
    public void MainGUIInteract(InventoryClickEvent e){
        if(e.getCurrentItem()==null) return;
        int integ = 0;
        for(Integer inte : cCrates.getMainGUI().getInventories().keySet()){
            if(e.getInventory().equals(cCrates.getMainGUI().getInventory(inte))){
                integ = inte;
            }
        }

        if(integ==0) return;


        Player player = (Player) e.getWhoClicked();



             if(e.getCurrentItem().equals(cCrates.getMainGUI().previousPage())){
                 player.openInventory(cCrates.getMainGUI().getInventory(integ-1));
             } else if(e.getCurrentItem().equals(cCrates.getMainGUI().nextPage())){
                 player.openInventory(cCrates.getMainGUI().getInventory(integ));
             } else {
                 for(Crate crate : cCrates.getCrateManager().getCrates()){
                     if(e.getCurrentItem().equals(cCrates.getMainGUI().loadItem(crate))) {
                         player.openInventory(crate.getMenuGUI().getMainGUI());
                         e.setCancelled(true);
                         return;
                     }
                 }
             }

             e.setCancelled(true);


    }

    @EventHandler
    public void CrateMenuGUIInteract(InventoryClickEvent e){

        if(e.getCurrentItem()==null) return;
            for(Crate crate : cCrates.getCrateManager().getCrates()){
                if(e.getInventory().equals(crate.getMenuGUI().getMainGUI())){
                    CrateMenuGUI maingui = crate.getMenuGUI();

                    Player player = (Player) e.getWhoClicked();

                    if(e.getCurrentItem().equals(maingui.crateInstances())){
                        player.openInventory(maingui.getCrateInstancesGUI().getInventory(1));
                    } else if(e.getCurrentItem().equals(maingui.settingsItem())){
                        player.openInventory(maingui.getSettingsGUI().getInventory());
                    }

                    e.setCancelled(true);
                    break;
                }
            }
    }

    @EventHandler
    public void CrateInstancesGUIInteract(InventoryClickEvent e) {
        CrateInstancesGUI crat = null;
        int integ = 0;
        if(e.getCurrentItem()==null) return;
        for (Crate crate : cCrates.getCrateManager().getCrates()) {
            for (Integer inte : crate.getMenuGUI().getCrateInstancesGUI().getInventories().keySet()) {
                if (crate.getMenuGUI().getCrateInstancesGUI().getInventories().get(inte).equals(e.getInventory())) {
                        crat = crate.getMenuGUI().getCrateInstancesGUI();
                        integ = inte;
                    break;
                }
            }
        }
            if (crat==null) return;
            Player player = (Player) e.getWhoClicked();
                CrateInstancesGUI gui = crat;
                if (e.getCurrentItem().equals(gui.previousPage())) {
                    player.openInventory(gui.getInventory(integ - 1));
                } else if (e.getCurrentItem().equals(gui.nextPage())) {
                    player.openInventory(gui.getInventory(integ + 1));
                } else {
                    //TODO
                }
                e.setCancelled(true);
        }


        @EventHandler
    public void SettingsGUIInteract(InventoryClickEvent e){



            if(e.getCurrentItem()==null) return;
            SettingsGUI settingsGUI = null;
            for(Crate crate : cCrates.getCrateManager().getCrates()) {
                if (e.getInventory().equals(crate.getMenuGUI().getSettingsGUI().getInventory())) {
                    settingsGUI = crate.getMenuGUI().getSettingsGUI();
                    break;
                }
            }
            if(settingsGUI==null) return;

            Player player = (Player) e.getWhoClicked();
            if(e.getCurrentItem().equals(settingsGUI.blockStack())){
                player.openInventory(settingsGUI.getValueGUI(ChangeValueType.BLOCK).getInventory());
            } else if(e.getCurrentItem().equals(settingsGUI.floorStack())){
                player.openInventory(settingsGUI.getValueGUI(ChangeValueType.FLOOR).getInventory());
            } else if(e.getCurrentItem().equals(settingsGUI.keyStack())){
                player.openInventory(settingsGUI.getValueGUI(ChangeValueType.KEY).getInventory());
            } else if(e.getCurrentItem().equals(settingsGUI.animStack())){
                player.openInventory(settingsGUI.getValueGUI(ChangeValueType.ANIMATION).getInventory());
            } else if(e.getCurrentItem().equals(settingsGUI.contentsStack())){
                player.openInventory(settingsGUI.getValueGUI(ChangeValueType.CONTENTS).getInventory());
            }

            e.setCancelled(true);
        }

        @EventHandler
    public void ChangeValueGUIInteract(InventoryClickEvent e){

            ChangeValue value = null;
            if(e.getCurrentItem()==null) return;
            for(Crate crate1 : cCrates.getCrateManager().getCrates()){
                for(ChangeValue changeValue : crate1.getMenuGUI().getSettingsGUI().getValueGUIS()){
                        if(e.getInventory().equals(changeValue.getInventory())) {
                            value = changeValue;
                            break;
                        }
                }
            }
            if(value==null) return;

            if(value.getType()==ChangeValueType.BLOCK||value.getType()==ChangeValueType.FLOOR||value.getType()==ChangeValueType.KEY)
                if(e.getClickedInventory().getHolder() instanceof Player || e.getRawSlot()==22) return;

                if(value.acceptStack()!=null && e.getCurrentItem().equals(value.acceptStack())){
                if(value.accept(e.getInventory().getItem(22))){
                    e.getWhoClicked().closeInventory();
                } else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Place correct Items!");
                }
                }

                if(value.getType()==ChangeValueType.ANIMATION){
                    for(AnimationType type : AnimationType.values()){
                        if(type.getItem().equals(e.getCurrentItem())) cCrates.getConfigManager().changeAnimation(value.getCrate(), type);
                    }
                    if(value.accept(e.getCurrentItem()))
                        e.getWhoClicked().closeInventory();

                }
                if(value instanceof ChangeContentsGUI){
                    ChangeContentsGUI gui = (ChangeContentsGUI) value;
                   if(e.getCurrentItem().equals(gui.nextPage())){
                    e.getWhoClicked().openInventory(gui.getInventory(gui.getPage(e.getInventory())+1));
                   } else if(e.getCurrentItem().equals(gui.previousPage())){
                       e.getWhoClicked().openInventory(gui.getInventory(gui.getPage(e.getInventory())-1));
                   }
                }
            e.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        ChangeValue value = null;
        for(Crate crate1 : cCrates.getCrateManager().getCrates()){
            for(ChangeValue changeValue : crate1.getMenuGUI().getSettingsGUI().getValueGUIS()){
                if(e.getInventory().equals(changeValue.getInventory())) {
                    value = changeValue;
                    break;
                }
            }
        }
        if(value==null) return;

        if(value.getType()==ChangeValueType.CONTENTS) value.accept(null);
    }
}
