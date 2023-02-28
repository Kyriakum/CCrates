package kyriakum.ccrates.ccrates.events;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;
import kyriakum.ccrates.ccrates.guis.crateguis.CrateInstancesGUI;
import kyriakum.ccrates.ccrates.guis.crateguis.CrateMenuGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GUIInteractListener implements Listener {

    private CCrates cCrates;
    public GUIInteractListener(CCrates cCrates){
        this.cCrates = cCrates;
    }


    @EventHandler
    public void MainGUIInteract(InventoryClickEvent e){
        AtomicBoolean isInv = new AtomicBoolean(false);
        AtomicInteger integ = new AtomicInteger(0);
        cCrates.getMainGUI().getGUI().forEach((integer, inventory) -> {
            if(e.getInventory().equals(inventory)) { isInv.set(true); integ.set(integer); }
        });

        if(!isInv.get()) return;


        Player player = (Player) e.getWhoClicked();
         if(e.getCurrentItem()!=null){

             if(e.getCurrentItem().equals(cCrates.getMainGUI().previousPage())){
                 player.openInventory(cCrates.getMainGUI().getInventory(integ.get()-1));
             } else if(e.getCurrentItem().equals(cCrates.getMainGUI().nextPage())){
                 player.openInventory(cCrates.getMainGUI().getInventory(integ.get()));
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
    }

    @EventHandler
    public void CrateMenuGUIInteract(InventoryClickEvent e){
            for(Crate crate : cCrates.getCrateManager().getCrates()){
                if(e.getInventory().equals(crate.getMenuGUI().getMainGUI())){
                    CrateMenuGUI maingui = crate.getMenuGUI();
                    if(e.getCurrentItem()==null) return;
                    Player player = (Player) e.getWhoClicked();

                    if(e.getCurrentItem().equals(maingui.crateInstances())){
                        player.openInventory(maingui.getCrateInstancesGUI().getInventory(1));
                    } else if(e.getCurrentItem().equals(maingui.settingsItem())){
                        player.openInventory(maingui.getSettingsGUI().getInventory());
                    }

                    e.setCancelled(true);
                }
            }
    }

    @EventHandler
    public void CrateInstancesGUIInteract(InventoryClickEvent e) {
        AtomicBoolean isInv = new AtomicBoolean(false);
        AtomicInteger integ = new AtomicInteger(0);

        for (Crate crate : cCrates.getCrateManager().getCrates()) {
            crate.getMenuGUI().getCrateInstancesGUI().getInstancesInvs().forEach((integer, inventory) -> {
                if (e.getInventory().equals(inventory)) {
                    isInv.set(true);
                    integ.set(integer);

                }
            });


            if (!isInv.get()) return;


            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() != null) {
                CrateInstancesGUI gui = crate.getMenuGUI().getCrateInstancesGUI();
                if (e.getCurrentItem().equals(gui.previousPage())) {
                    player.openInventory(gui.getInventory(integ.get() - 1));
                } else if (e.getCurrentItem().equals(gui.nextPage())) {
                    player.openInventory(gui.getInventory(integ.get() + 1));
                } else {
                    //TODO
                }
                System.out.println(integ);

                e.setCancelled(true);
                return;
            }
        }
        }

}
