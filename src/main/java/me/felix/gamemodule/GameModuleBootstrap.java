package me.felix.gamemodule;

import lombok.Getter;
import lombok.SneakyThrows;
import me.felix.gamemodule.commands.GameModuleCommand;
import me.felix.gamemodule.exception.IllegalModuleDescriptionException;
import me.felix.gamemodule.file.CoreServerSettings;
import me.felix.gamemodule.file.FileProvider;
import me.felix.gamemodule.listener.PlayerJoinListener;
import me.felix.gamemodule.listener.PlayerQuitListener;
import me.felix.gamemodule.module.ModuleLoader;
import me.felix.gamemodule.module.ModuleProvider;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

    @Override
    public void onEnable() {
        instance = this;

        fileProvider = new FileProvider();
        moduleLoader = new ModuleLoader();
        moduleProvider = new ModuleProvider();

        initListener();

        initCommands();
    }

    private void initListener() {
        if(!CoreServerSettings.REGISTER_LISTENER) {
            return;
        }

        PluginManager pluginManager = getServer().getPluginManager();

        for(Listener listener : new Listener[]{
                new PlayerJoinListener(),
                new PlayerQuitListener()
        }) {
              pluginManager.registerEvents(listener, this);
        };
    }

    private void initCommands() {
        getCommand("gamemodule").setExecutor(new GameModuleCommand());
    }

    @Override
    public void onDisable() {

    }

}
