package me.felix.gamemodule.file;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Objects;

public class FileProvider {

    private File file;
    private File settingsFile;
    private FileConfiguration settingFileConfiguration;

    /**
     * Files werden erstellt
     */
    public FileProvider() {
        file = new File("plugins/GameModule/modules");
        settingsFile = new File("plugins/GameModule", "settings.yml");

        if(!file.exists()) {
            file.mkdirs();

            settingFileConfiguration = YamlConfiguration.loadConfiguration(settingsFile);
            CoreServerSettings coreServerSettings = new CoreServerSettings();

            saveClassToFile(coreServerSettings, settingFileConfiguration, settingsFile);
        } else {
            settingFileConfiguration = YamlConfiguration.loadConfiguration(settingsFile);

            loadClassFile(new CoreServerSettings(), settingFileConfiguration);
        }
    }

    /**
     * Speichert die Klasse in eine File
     * @param object (Klasse, von wo die Variabeln genommen werden)
     * @param fileConfiguration
     * @param file
     */
    @SneakyThrows
    private void saveClassToFile(Object object, FileConfiguration fileConfiguration, File file)  {
        Class clazz = object.getClass();

        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);

            Object value = declaredField.get(object);

            fileConfiguration.set(declaredField.getName(), value);
        }

        fileConfiguration.save(file);
    }

    /**
     * Lädt die Klasse aus der File
     * @param object (Klasse, wo die Daten aus der File eingefügt werden)
     * @param fileConfiguration
     */
    @SneakyThrows
    private void loadClassFile(Object object, FileConfiguration fileConfiguration) {
        Class clazz = object.getClass();

        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);

            declaredField.set(object, fileConfiguration.get(declaredField.getName()));
        }
    }

}
