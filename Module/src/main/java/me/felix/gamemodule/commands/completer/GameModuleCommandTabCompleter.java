package me.felix.gamemodule.commands.completer;

import me.felix.gamemodule.GameModuleBootstrap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameModuleCommandTabCompleter implements TabCompleter {

    private final GameModuleBootstrap gameModule = GameModuleBootstrap.getInstance();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        switch (args.length) {
            case 1 -> {
                List<String> options = Arrays.asList("list", "load", "unload", "info");

                return StringUtil.copyPartialMatches(args[0], options, new ArrayList<>());
            }

            case 2 -> {
                if (!args[0].equalsIgnoreCase("load")) {
                    return new ArrayList<>();
                }

                File[] files = gameModule.getModuleLoader().getFile().listFiles();

                if (files == null || files.length == 0) {
                    return new ArrayList<>();
                }

                List<String> options = new ArrayList<>();
                for (File file : files) {
                    options.add(file.getName().replace(".jar", ""));
                }

                return StringUtil.copyPartialMatches(args[1], options, new ArrayList<>());
            }
        }
        return new ArrayList<>();
    }
}
