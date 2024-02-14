package me.felix.skywarsffa.listener.player;

import me.felix.skywarsffa.SkyWarsFFA;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {

    private final SkyWarsFFA skyWarsFFA = SkyWarsFFA.getInstance();

    @EventHandler
    public void handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(MiniMessage.miniMessage().deserialize(
                "\n" +
                        skyWarsFFA.getPrefix() + "<red>Teaming ist verboten und wird bestraft.\n" +
                        ""
        ));

        event.joinMessage(null); // -> message to player in spawn area.

        //Teleport Spawnlocation/Set tiems
        setupPlayer(player);
    }

    private void setupPlayer(Player player) {

    }

    @EventHandler
    public void handleLogin(PlayerLoginEvent event) {
        //load player
    }

    @EventHandler
    public void handleAsyncPlayerJoin(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST) {
            event.kickMessage(MiniMessage.miniMessage().deserialize(
                    skyWarsFFA.getPrefix() + "<red>Momentan befindet sich dieses Module in Wartung.\n" +
                            "\n" +
                            "Versuche es später nochmal..."
            ));
        }
    }

}
