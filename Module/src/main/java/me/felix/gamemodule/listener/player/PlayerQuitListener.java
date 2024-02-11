package me.felix.gamemodule.listener.player;

import me.felix.gamemodule.GameModuleBootstrap;
import me.felix.gamemodule.file.CoreServerSettings;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private GameModuleBootstrap gameModule = GameModuleBootstrap.getInstance();

    @EventHandler
    public void handleQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(CoreServerSettings.QUIT_MESSAGE) {
            event.quitMessage(MiniMessage.miniMessage().deserialize(
                    gameModule.getPrefix() + player.getName() + " <white>hat den Server <red>verlassen"
            ));
        }
    }

}
