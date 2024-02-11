package me.felix.gamemodule.module;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.felix.gamemodule.GameModuleBootstrap;
import me.felix.gamemodule.exception.IllegalModuleDescriptionException;
import me.felix.gamemodule.file.CoreServerSettings;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleLoader {

    private final GameModuleBootstrap gameModule = GameModuleBootstrap.getInstance();

    @Getter
    private final File file;

    @Getter@Setter
    private Module module;

    public ModuleLoader() {
        this.file = new File("plugins/gamemodule-file/modules");
    }

    public void loadModule(String name, Consumer<Boolean> consumer) {
        Arrays.stream(file.listFiles())
                .filter(files -> files.getName().toLowerCase().contains(name.toLowerCase()))
                .findFirst()
                .ifPresentOrElse(file -> {

                    try {
                        JarFile jarFile = new JarFile(file.getPath());

                        Enumeration<JarEntry> entries = jarFile.entries();
                        entries.asIterator().forEachRemaining(jarEntry -> {
                            System.out.println(jarEntry.getName());

                            if (!jarEntry.isDirectory() && jarEntry.getName().equalsIgnoreCase("module.yml")) {

                                String classContent = readClassContent(jarFile, jarEntry);

                                findAndLoadClass(classContent, jarFile, file);
                            }
                        });
                        consumer.accept(true);
                        jarFile.close();
                    } catch (IOException /*| IllegalModuleDescriptionException*/ e) {
                        throw new RuntimeException(e);
                    }

                }, () -> consumer.accept(false));
    }

    @SneakyThrows
    private void findAndLoadClass(String description, JarFile jarFile, File file) {
        if(!description.contains("main:")) {
            throw new IllegalModuleDescriptionException("Description is not correct. Please check.");
        }

        String className = description
                .replace("main:", "").trim().replace(".", "/") + ".class";

        JarEntry jarEntry = jarFile.getJarEntry(className);
        if(jarEntry == null) {
            throw new IllegalModuleDescriptionException(className + " is null. Please check your description");
        }

        File spigot = new File(CoreServerSettings.SERVER_SOFTWARE_PATH);

        URLClassLoader classLoader = new URLClassLoader(new URL[]
                {
                        new URL("file:" + spigot.getPath()),
                        new URL("file:" + file.getPath())
                },
                gameModule.getClass().getClassLoader()
        );

        // Klasse laden
        String classLoadName = description
                .replace("main:", "")
                .replace("/", ".").trim();
        System.out.println(classLoadName + " Name");

        Class<?> loadedClass = classLoader.loadClass(classLoadName);

        Object instance = loadedClass.getDeclaredConstructor().newInstance();

        if(instance instanceof Module) {
            Module module = (Module) instance;

            setModule(module);

            module.setPluginInstance(gameModule);
            module.enableModule();
        }

        classLoader.close();
    }

    public void unloadModule(CommandSender commandSender) {
        if(getModule() == null) {
            commandSender.sendMessage(MiniMessage.miniMessage().deserialize(
                    gameModule.getPrefix() + "<red>Momentan ist kein Module geladen. Du kannst mit /gamemodule load <Module> eines laden."
            ));
            return;
        }

        module.disableModule();

        if(module.getListeners() != null) {
            for (Listener listener : module.getListeners()) {
                HandlerList.unregisterAll(listener);
            }
        }
        Bukkit.getScheduler().cancelTasks(gameModule);
        setModule(null);

        commandSender.sendMessage(MiniMessage.miniMessage().deserialize(
                gameModule.getPrefix() + "<green>Module wurde entladen."
        ));
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
