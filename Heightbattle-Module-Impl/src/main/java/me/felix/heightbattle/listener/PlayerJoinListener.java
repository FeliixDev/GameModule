package me.felix.heightbattle.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage("<green>Es ist ein Module aktiv");

        event.joinMessage(MiniMessage.miniMessage().deserialize(
                "<gold>Willkommen, Bitch %s.".formatted(
                        player.getName()
                )
        ));
    }

}
