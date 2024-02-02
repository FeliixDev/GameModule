package me.felix.gamemodule.module;

import lombok.Getter;
import me.felix.gamemodule.GameModuleBootstrap;
import me.felix.gamemodule.exception.IllegalModuleDescriptionException;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleLoader {

    private final GameModuleBootstrap gameModule = GameModuleBootstrap.getInstance();

    @Getter
    private final File file;

    public ModuleLoader() {
        this.file = new File("plugins/GameModule/modules");
    }

    public void loadModule(String name, Consumer<Boolean> consumer) {
        Arrays.stream(file.listFiles())
                .filter(files -> files.getName().toLowerCase().contains(name.toLowerCase()))
                .findFirst()
                .ifPresentOrElse(file -> {

                    try {
                        JarFile jarFile = new JarFile(file.getPath());

                        Enumeration<JarEntry> entries = jarFile.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();

                            if (!entry.isDirectory() && entry.getName().equalsIgnoreCase("module.yml")) {
                                System.out.println(entry);

                                String classContent = readClassContent(jarFile, entry);

                                System.out.println("Inhalt:");
                                System.out.println(classContent);
                                System.out.println("------------");

                                //throw new IllegalModuleDescriptionException("Description is not correct. Please check.");
                            }
                        }

                        consumer.accept(true);
                        jarFile.close();
                    } catch (IOException /*| IllegalModuleDescriptionException*/ e) {
                        throw new RuntimeException(e);
                    }

                }, () -> consumer.accept(false));
    }

    public void unloadModule() {

    }

    public void listModules(CommandSender commandSender) {
        if (file.listFiles().length == 0) {
            commandSender.sendMessage(MiniMessage.miniMessage().deserialize(
                    "<red>Es sind keine Module vorhanden"
            ));
            return;
        }

        commandSender.sendMessage(MiniMessage.miniMessage().deserialize(
                "<green>Es gibt momentan <yellow>" + file.listFiles().length + " <green>Module."
        ));
        for (File listFile : file.listFiles()) {
            String name = listFile.getName().replace(".jar", "");

            commandSender.sendMessage(MiniMessage.miniMessage().deserialize(
                    "<gray>- <gold>" + name
            ));
        }

        commandSender.sendMessage(MiniMessage.miniMessage().deserialize(
                " \n" +
                        "<green>Es reicht beim laden nur einen Teil von den Name anzugeben. Als Beispiel: (Plugin: XYZ-1.0.Snapshot -> XYZ. Sollte es mehrere geben, genauer))"
        ));
    }

    private String readClassContent(JarFile jarFile, JarEntry entry) {
        try (InputStream inputStream = jarFile.getInputStream(entry)) {
            byte[] classBytes = inputStream.readAllBytes();
            return new String(classBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
