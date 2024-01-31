package me.felix.gamemodule.module;

import lombok.Getter;
import me.felix.gamemodule.GameModuleBootstrap;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public abstract class Module {

    @Getter
    public String name;
    @Getter
    public String author;
    @Getter
    public String version;

    /**
     * Erste Methode, die beim Start aufgerufen wird
     */
    public void enableModule() {
        //read Resources File -> Module.yml - name and more
    }

    public void registerCommands(CommandExecutor... commandExecutors) {

    }

    public void registerListener(Listener... listeners) {
        PluginManager pluginManager = getPluginInstance().getServer().getPluginManager();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, getPluginInstance());
        }
    }

    public void disableModule() {

    }

    public GameModuleBootstrap getPluginInstance() {
        return GameModuleBootstrap.getInstance();
    }

}
