/*
 * Copyright 2015 CptWin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nz.co.lolnet.james137137.lolnetadventureruincommandblockfix;

import static nz.co.lolnet.james137137.lolnetadventureruincommandblockfix.LolnetAdventureRuinCommandBlockFix.logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;

/**
 *
 * @author CptWin
 */
public class ThreadedSearcher implements Runnable {
    
    BlockState[] blocks;

    public ThreadedSearcher(BlockState[] blocks) {
        this.blocks = blocks;
        startUp();
    }
    
    /**
     * Takes a command block and then checks if it is a RUINSTRIGGER command block.
     * 
     * @param commandBlock 
     * @author James137137, CptWin
     */
    public void commandBlock(CommandBlock commandBlock) {
        logger.info("is a block");
        final Location location = commandBlock.getLocation();
        final Location location2 = location.clone();
        location2.setY(location.getY() + 1);
        final Material type = location2.getBlock().getType();

        String command = commandBlock.getCommand();
        if (command.contains("RUINSTRIGGER")) {
            logger.info("is a ruinstrigger");
            command = command.replaceFirst("RUINSTRIGGER ", "");
            commandBlock.setCommand(command);
            commandBlock.update();
            location2.getBlock().setType(Material.REDSTONE_BLOCK);
            Bukkit.getScheduler().runTaskLater(LolnetAdventureRuinCommandBlockFix.plugin, new Runnable() {

                @Override
                public void run() {
                    location.getBlock().setType(Material.AIR);
                    location2.getBlock().setType(type);
                }
            }, 20 * 5l);
        }
        logger.info(commandBlock.getCommand());
        logger.info(commandBlock.getLocation().toString());
    }
    
    /**
     *  Starts the thread
     */
    private void startUp()
    {
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Searches through our array of blocks in the chunk.
     * If it finds a command block it will call the appropriate method.
     */
    @Override
    public void run() {
        for(BlockState blockState : blocks)
        {
            if(blockState.getBlock().getState() instanceof CommandBlock)
            {
                commandBlock((CommandBlock) blockState.getBlock().getState());
            }
        }
    }
    
}
