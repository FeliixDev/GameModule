package me.felix.gamemodule.listener;

import me.felix.gamemodule.GameModuleBootstrap;
import me.felix.gamemodule.file.CoreServerSettings;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {

    private final GameModuleBootstrap gameModule = GameModuleBootstrap.getInstance();

    @EventHandler
    public void handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //state check
        if (CoreServerSettings.JOIN_MESSAGE) {
            event.joinMessage(MiniMessage.miniMessage().deserialize(
                    gameModule.getPrefix() + player.getName() + " <white>hat den Server betreten"
            ));

            player.sendMessage(MiniMessage.miniMessage().deserialize(
                    "<red>Du kannst folgende Commands verwenden:\n" +
                            "<red>/gamemodule load <game>\n" +
                            "<red>/gamemodule list"
            ));
        }
    }

    @EventHandler
    public void handleLogin(PlayerLoginEvent event) {
        if (CoreServerSettings.MAINTENANCE) {
            if (event.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
                event.setKickMessage(
                        "§cWartung" +
                                "\n " +
                                "\n§cHier wird momentan etwas kleines gebaut..."
                );
            }
        }
    }

}
