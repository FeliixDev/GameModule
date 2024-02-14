package me.felix.gamemodule.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.felix.gamemodule.GameModuleBootstrap;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public abstract class ModuleCommand extends Command implements PluginIdentifiableCommand {

    private final GameModuleBootstrap gameModuleBootstrap = GameModuleBootstrap.getInstance();

    protected ModuleCommand(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String commandLabel, @NotNull String[] arguments) {
        return true;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return gameModuleBootstrap;
    }

}
