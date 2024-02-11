package me.felix.gamemodule.listener.world;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void handleBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

}
