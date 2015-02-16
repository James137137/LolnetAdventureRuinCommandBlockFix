/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nz.co.lolnet.james137137.lolnetadventureruincommandblockfix;


import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CommandBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author James
 */
public class LolnetAdventureRuinCommandBlockFix extends JavaPlugin implements Listener{

     @Override
    public void onEnable() {
        String version = Bukkit.getServer().getPluginManager().getPlugin(this.getName()).getDescription().getVersion();
        this.getLogger().log(Level.INFO, this.getName() + " : Version {0} enabled", version);
        getServer().getPluginManager().registerEvents(this, this);
    }
    
    @EventHandler
    public void onCommand(BlockBreakEvent event) {
        this.getLogger().info("LolnetAdventureRuinCommandBlockFix Debug");
        boolean a = event.getBlock().getState() instanceof CommandBlock;
        this.getLogger().info("" + a);
        if (event.getBlock().getState() instanceof CommandBlock)
        {
            this.getLogger().info("is a block");
            CommandBlock commandBlock = (CommandBlock) event.getBlock().getState();
            final Location location = commandBlock.getLocation();
            final Location location2 = location.clone();
            location2.setY(location.getY() + 1);
            final Material type = location2.getBlock().getType();
            
            String command = commandBlock.getCommand();
            if (command.contains("RUINSTRIGGER"))
            {
                this.getLogger().info("is a ruinstrigger");
                command = command.replaceFirst("RUINSTRIGGER ", "");
                commandBlock.setCommand(command);
                commandBlock.update();
                location2.getBlock().setType(Material.REDSTONE_BLOCK);
                Bukkit.getScheduler().runTaskLater(this, new Runnable() {

                    @Override
                    public void run() {
                        location.getBlock().setType(Material.AIR);
                        location2.getBlock().setType(type);
                    }
                }, 20*5l);
            }
            this.getLogger().info(commandBlock.getCommand());
            this.getLogger().info(commandBlock.getLocation().toString());
        }
        
        this.getLogger().info("LolnetAdventureRuinCommandBlockFix Debug end");
        event.setCancelled(true);
    }
    
}
