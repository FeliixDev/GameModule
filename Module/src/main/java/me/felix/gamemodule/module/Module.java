package me.felix.gamemodule.module;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;

public abstract class Module {

    @Getter@Setter
    private Plugin pluginInstance;

    @Getter
    private Listener[] listeners;
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
        this.listeners = listeners;

        PluginManager pluginManager = pluginInstance.getServer().getPluginManager();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, pluginInstance);
        }
    }

    public void disableModule() {

    }

}
