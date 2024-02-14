package me.felix.skywarsffa.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void handleFoodLevel(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
