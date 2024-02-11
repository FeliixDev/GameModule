package me.felix.gamemodule.listener.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SimulatePlayerJoinEvent extends Event {

    public static HandlerList handlerList = new HandlerList();

    private Player player;

    public SimulatePlayerJoinEvent(Player player) {
        this.player = player;
    }

    public Player player() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return SimulatePlayerJoinEvent.handlerList;
    }

    public static HandlerList getHandlerList() {
        return SimulatePlayerJoinEvent.handlerList;
    }
}
