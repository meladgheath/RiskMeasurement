package org.softwareengine.utils.service;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.security.Key;
import java.util.ResourceBundle;

public class LocaleService {
    private static final ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

    public static ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }

    public static ResourceBundle getResources() {
        return resourcesProperty().get();
    }

    public static void setResources(ResourceBundle resources) {
        resourcesProperty().set(resources);
    }

    public static StringBinding getKey(String key) {
        return new StringBinding() {
            {
                bind(resourcesProperty());
            }

            @Override
            public String computeValue() {

                System.out.println(" here the  key value = "+key);
                return getResources().getString(key);


            }
        };
    }
}
