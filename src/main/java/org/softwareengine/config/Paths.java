package org.softwareengine.config;

import static org.softwareengine.config.Constants.OS;

public enum Paths {
    APP_ICON("/icons/app.png"),
    SPLASH_IMAGE("/images/splash.jpg"),
    PLACEHOLDER_IMAGE("/images/placeholder.png"),
    MAC_APP_DATA(System.getProperty("user.home") + "/Library/Application Support/se/casher"),
    WIN_APP_DATA(System.getenv("LOCALAPPDATA") + "/se/casher"),
    APP_DATA(OS.getValue().contains("win") ? WIN_APP_DATA.path : MAC_APP_DATA.path),
    DATABASE(APP_DATA.path + "/casher-engine.db"),
    CONFIG(APP_DATA.path + "/config.properties"),
    ;
    private final String path;

    Paths(String p) {
        path = p;
    }

    public String getPath() {
        return path;
    }
}