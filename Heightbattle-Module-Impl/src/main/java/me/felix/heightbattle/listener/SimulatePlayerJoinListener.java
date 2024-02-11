package me.felix.heightbattle.listener;

import me.felix.gamemodule.listener.custom.SimulatePlayerJoinEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SimulatePlayerJoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(SimulatePlayerJoinEvent event) {
        Player player = event.player();

        player.sendMessage(MiniMessage.miniMessage().deserialize(
                "<green>Du bist während ein Module gestartet ist, auf dem Server. Diese Nachricht zeigt nur eine billige Join Nachricht."
        ));
    }

}
