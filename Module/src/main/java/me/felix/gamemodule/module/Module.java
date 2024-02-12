package me.felix.gamemodule.module;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.felix.gamemodule.commands.ModuleCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;

public abstract class Module {

    @Getter
    @Setter
    private Plugin pluginInstance;

    @Getter
    private Listener[] listeners;
    @Getter
    private ModuleCommand[] moduleCommands;
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

    @SneakyThrows
    public void registerCommands(ModuleCommand... moduleCommands) {
        Field field = pluginInstance.getServer().getClass().getDeclaredField("commandMap");

        field.setAccessible(true);
        CommandMap commandMap = (CommandMap) field.get(pluginInstance.getServer());

        for (ModuleCommand moduleCommand : moduleCommands) {
            commandMap.register(moduleCommand.getName(), moduleCommand);
        }
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
