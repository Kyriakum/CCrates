package kyriakum.ccrates.ccrates.commands;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.utils.PlaceHolder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CCratesCommands implements CommandExecutor {

    private List<SubCommand> commands = new ArrayList<>();
    private final CCrates cCrates;

    public CCratesCommands(CCrates cCrates){
        this.cCrates = cCrates;
        setup();
    }

    private void setup(){
        commands.add(new SetCrateCommand(cCrates));
        commands.add(new GiveKeyCommand(cCrates));
        commands.add(new ListCommand(cCrates));
        commands.add(new InfoCommand(cCrates));
        commands.add(new AddCommand(cCrates));
        commands.add(new RemoveCommand(cCrates));
        commands.add(new CreateCommand(cCrates));
        commands.add(new DeleteCommand(cCrates));
        commands.add(new ReloadCommand(cCrates));
        cCrates.getCommand("ccrates").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if(sender instanceof Player){
          Player player = (Player) sender;

          if(!player.hasPermission("ccrates.admin")){
              player.sendMessage(cCrates.getMessagesManager().getNoPermission());
              return false;
          }
          if(args.length == 0){
            //OpenInventory
              player.openInventory(cCrates.getMainGUI().getInventory(1));
              return false;
          } else if(args.length >= 1 && args[0].equalsIgnoreCase("help")){
              invalidSubcommand(player);
              return false;
          }

          SubCommand target = this.get(args[0]);
          if(target==null) {
              invalidSubcommand(player);
              return false;
          }

          try{
                target.onCommand(player, args);
          }catch (Exception e){
              player.sendMessage(ChatColor.RED + "Error occured!");
              e.printStackTrace();
          }
      }

        return false;
    }

    private void invalidSubcommand(Player player){
        commands.forEach(t -> player.sendMessage(cCrates.getMessagesManager().getPrefix() + ChatColor.RED + "/ccrates " + t.getName()));
    }

    private SubCommand get(String name){
        Iterator<SubCommand> subcommands = commands.iterator();

        while(subcommands.hasNext()){
            SubCommand sc = subcommands.next();
            if(sc.getName().equalsIgnoreCase(name)) return sc;
            String[] aliases = sc.getAliases();
            int length = aliases.length;
            for(int i = 0; i<length; ++i){
                String alias = aliases[i];
                if(name.equalsIgnoreCase(alias)) return sc;
            }
        }
        return null;
    }
}
