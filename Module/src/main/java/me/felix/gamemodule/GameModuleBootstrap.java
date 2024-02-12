package me.felix.gamemodule;

import lombok.Getter;
import lombok.SneakyThrows;
import me.felix.gamemodule.listener.world.BlockBreakListener;
import me.felix.gamemodule.listener.world.WeatherChangeListener;
import me.felix.gamemodule.module.ModuleLoader;
import me.felix.gamemodule.module.ModuleProvider;
import me.felix.gamemodule.commands.GameModuleCommand;
import me.felix.gamemodule.commands.completer.GameModuleCommandTabCompleter;
import me.felix.gamemodule.file.CoreServerSettings;
import me.felix.gamemodule.file.FileProvider;
import me.felix.gamemodule.listener.player.PlayerJoinListener;
import me.felix.gamemodule.listener.player.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class GameModuleBootstrap extends JavaPlugin {

    @Getter
    private static GameModuleBootstrap instance;

    @Getter
    private final String prefix = "<dark_gray>┃</dark_gray> <gradient:#ff1749:#ff0000>GameModule</gradient> <dark_gray>» <white>";

    @Getter
    private FileProvider fileProvider;
    @Getter
    private ModuleLoader moduleLoader;
    @Getter
    private ModuleProvider moduleProvider;
    @Getter
    private Listener[] listeners;

    @Override
    public void onEnable() {
        instance = this;

        fileProvider = new FileProvider();
        moduleLoader = new ModuleLoader();
        moduleProvider = new ModuleProvider();

        initListener();

        initCommands();
    }

    public void initListener() {
        if (!CoreServerSettings.REGISTER_LISTENER) {
            return;
        }

        PluginManager pluginManager = getServer().getPluginManager();

        if(listeners == null) {
            this.listeners = new Listener[]{
                    new PlayerJoinListener(),
                    new PlayerQuitListener(),
                    new BlockBreakListener(),
                    new WeatherChangeListener()
            };
        }

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    @SneakyThrows
    private void initCommands() {
        getCommand("gamemodule").setExecutor(new GameModuleCommand());

        getCommand("gamemodule").setTabCompleter(new GameModuleCommandTabCompleter());
    }

    @Override
    public void onDisable() {

    }

}
