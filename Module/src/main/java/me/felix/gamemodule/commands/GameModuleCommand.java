package me.felix.gamemodule.commands;

import me.felix.gamemodule.GameModuleBootstrap;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GameModuleCommand implements CommandExecutor {

    private GameModuleBootstrap gameModule = GameModuleBootstrap.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!commandSender.hasPermission("gamemodulecore.command.gamemodule")) {
            commandSender.sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>Du hast nicht genügend Rechte für diesen Command!")
            );
        }

        switch (args.length) {
            case 1:
                switch (args[0].toLowerCase()) {
                    case "list":
                        gameModule.getModuleLoader().listModules(commandSender);
                        break;

                    case "unload":
                        gameModule.getModuleLoader().unloadModule(commandSender);
                        break;
                }
                break;

            case 2:
                if (args[0].equalsIgnoreCase("load")) {
                    String module = args[1];

                    gameModule.getModuleLoader().loadModule(module, success -> {
                        if (success) {
                            commandSender.sendMessage(MiniMessage.miniMessage().deserialize(
                                    "<green>Module wurde gefunden."
                            ));
                        } else {
                            commandSender.sendMessage(MiniMessage.miniMessage().deserialize(
                                    "<red>Module konnte nicht gefunden werden."
                            ));
                        }
                    });
                }
                break;
            default:
                commandSender.sendMessage(
                        MiniMessage.miniMessage().deserialize(
                                "<red>Du kannst folgende Commands verwenden: \n" +
                                        "<red>- /gamemodule list -> Zeigt die alle Module, die du laden kannst\n" +
                                        "\n" +
                                        "<red>- /gamemodule load <Module> -> Lädt dein Module\n" +
                                        "\n" +
                                        "<red>- /gamemodule unload -> Entlädt das aktuelle Module."
                        )
                );
                break;
        }
        return true;
    }
}
