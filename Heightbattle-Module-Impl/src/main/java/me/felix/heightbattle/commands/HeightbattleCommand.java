package me.felix.heightbattle.commands;

import me.felix.gamemodule.commands.ModuleCommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HeightbattleCommand extends ModuleCommand {
    public HeightbattleCommand(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String commandLabel, @NotNull String[] arguments) {
        commandSender.sendMessage(MiniMessage.miniMessage().deserialize(
                "Jojo"
        ));
        return super.execute(commandSender, commandLabel, arguments);
    }
}
