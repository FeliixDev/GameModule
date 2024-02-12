package me.felix.heightbattle;

import me.felix.gamemodule.GameModuleBootstrap;
import me.felix.gamemodule.module.Module;
import me.felix.heightbattle.commands.HeightbattleCommand;
import me.felix.heightbattle.listener.PlayerJoinListener;
import me.felix.heightbattle.listener.SimulatePlayerJoinListener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

public class Heightbattle extends Module {

    @Override
    public void enableModule() {
        Bukkit.broadcast(
                MiniMessage.miniMessage().deserialize(GameModuleBootstrap.getInstance().getPrefix() + "Module wurde geladen.")
        );

        System.out.println("Jojo");

        registerListener(
                new PlayerJoinListener(),
                new SimulatePlayerJoinListener()
        );

        registerCommands(new HeightbattleCommand("heightbattle"));
    }
}