package me.felix.gamemodule.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handleQuit(PlayerQuitEvent event) {
        event.quitMessage(null);
    }

}
