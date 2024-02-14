package me.felix.skywarsffa.listener.player;

import me.felix.gamemodule.listener.custom.SimulatePlayerJoinEvent;
import me.felix.skywarsffa.SkyWarsFFA;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SimulatePlayerJoinListener implements Listener {

    private final SkyWarsFFA skyWarsFFA = SkyWarsFFA.getInstance();

    @EventHandler
    public void handleJoin(SimulatePlayerJoinEvent event) {
        Player player = event.player();

        //teleport and setup

        player.sendMessage(MiniMessage.miniMessage().deserialize(
                "\n" +
                        skyWarsFFA.getPrefix() + "<red>Teaming ist verboten und wird bestraft.\n" +
                        ""
        ));

        //PlayerJoinEvent trigger?
    }

}
