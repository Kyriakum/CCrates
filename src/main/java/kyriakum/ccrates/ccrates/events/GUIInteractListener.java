package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.animations.AnimationType;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.guis.crateguis.CrateInstancesGUI;
import kyriakum.ccrates.ccrates.guis.crateguis.CrateMenuGUI;
import kyriakum.ccrates.ccrates.guis.crateguis.SettingsGUI;
import kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis.ChangeContentsGUI;
import kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis.ChangeValue;
import kyriakum.ccrates.ccrates.guis.crateguis.changevalueguis.ChangeValueType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

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
            Crate crate1 = null;
            for(Crate crate : cCrates.getCrateManager().getCrates()) {
                if (e.getInventory().equals(crate.getMenuGUI().getSettingsGUI().getInventory())) {
                    settingsGUI = crate.getMenuGUI().getSettingsGUI();
                    crate1= crate;
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
            }else if(e.getCurrentItem().equals(settingsGUI.enabledStack())){
                for(Player player1 : Bukkit.getOnlinePlayers()){
                    if(player1.getOpenInventory().getTopInventory().equals(settingsGUI.getInventory())) player1.closeInventory();
                }
                cCrates.getConfigManager().changeEnabled(crate1);
               if(crate1.isEnabled()) player.sendMessage(cCrates.getPlaceHolder().replaceCrate(cCrates.getMessagesManager().getCrateEnabled(), crate1));
               else player.sendMessage(cCrates.getPlaceHolder().replaceCrate(cCrates.getMessagesManager().getCrateDisabled(), crate1));


            }

            e.setCancelled(true);
        }

        @EventHandler
    public void ChangeValueGUIInteract(InventoryClickEvent e){

            ChangeValue value = null;
            Crate crate = null;
            if(e.getCurrentItem()==null) return;
            for(Crate crate1 : cCrates.getCrateManager().getCrates()){
                for(ChangeValue changeValue : crate1.getMenuGUI().getSettingsGUI().getValueGUIS()){
                        if(e.getInventory().equals(changeValue.getInventory())) {
                            value = changeValue;
                            crate = crate1;
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
                    for(Player player : Bukkit.getOnlinePlayers()){
                        if(player.getOpenInventory().equals(e.getInventory())) player.closeInventory();
                    }

                    switch (value.getType()){
                        case BLOCK: {
                            e.getWhoClicked().sendMessage(cCrates.getPlaceHolder().replaceMaterial(cCrates.getPlaceHolder().replaceCrate(cCrates.getMessagesManager().getBlockChanged(), crate), e.getInventory().getItem(22).getType()));
                            break;
                        }
                        case FLOOR: {
                            e.getWhoClicked().sendMessage(cCrates.getPlaceHolder().replaceMaterial(cCrates.getPlaceHolder().replaceCrate(cCrates.getMessagesManager().getFloorChanged(), crate), e.getInventory().getItem(22).getType()));
                            break;
                        }
                        case KEY: {
                            e.getWhoClicked().sendMessage(cCrates.getPlaceHolder().replaceItem(cCrates.getPlaceHolder().replaceCrate(cCrates.getMessagesManager().getKeyChanged(), crate), e.getInventory().getItem(22)));
                            break;
                        }
                    }

                } else {
                    e.getWhoClicked().sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "Place correct Items!");
                }
                }

                if(value.getType()==ChangeValueType.ANIMATION){
                    for(AnimationType type : AnimationType.values()) {
                        if (type.getItem().equals(e.getCurrentItem())) {
                            cCrates.getConfigManager().changeAnimation(value.getCrate(), type);
                            e.getWhoClicked().sendMessage(cCrates.getPlaceHolder().replaceAnimation(cCrates.getPlaceHolder().replaceCrate(cCrates.getMessagesManager().getAnimationChanged(), crate), type.getDisplayName()));
                        }
                    }
                    if(value.accept(e.getCurrentItem()))
                        e.getWhoClicked().closeInventory();
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (player.getOpenInventory().equals(e.getInventory())) player.closeInventory();
                        }
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
