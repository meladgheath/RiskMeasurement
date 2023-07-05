package org.softwareengine.utils.service;

import org.softwareengine.config.Paths;
import org.softwareengine.modules.settings.model.General;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Properties;

public class StorageService {

    private final static Properties properties;

    static {
        properties = new Properties();
        try {
            if (Files.notExists(Path.of(Paths.CONFIG.getPath()))) {
                if (Files.notExists(Path.of(Paths.APP_DATA.getPath()))) {
                    Files.createDirectories(Path.of(Paths.APP_DATA.getPath()));
                }
                properties.setProperty("date", LocalDateTime.now().plusDays(15).toString());
                properties.setProperty(General.LANGUAGE.getValue(), "ar");
                properties.setProperty(General.DARK_MODE.getValue(), "0");
                properties.store(new FileOutputStream(Paths.CONFIG.getPath()), null);
            }
            StorageService.properties.load(new FileInputStream(Paths.CONFIG.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setProperty(String key, String value, String comments) {
        try {
            properties.setProperty(key, value);
            properties.store(new FileOutputStream(Paths.CONFIG.getPath()), comments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}