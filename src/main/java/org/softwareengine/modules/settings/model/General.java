package org.softwareengine.modules.settings.model;

public enum General {
    LANGUAGE("language"),
    DARK_MODE("is_dark_mode")
    ;

    final String value;
    General(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
