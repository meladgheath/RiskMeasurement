package org.softwareengine.modules.settings.controller;

import javafx.geometry.NodeOrientation;
import javafx.stage.Window;
import org.softwareengine.modules.settings.model.General;
import org.softwareengine.modules.settings.repositories.GeneralRepository;
import org.softwareengine.modules.settings.view.GeneralView;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.service.SystemService;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class GeneralController extends GeneralView {

    private final Window window;

    public GeneralController(Window window) {
        this.window = window;
        GeneralRepository repository = new GeneralRepository();

        try {
            String language = repository.get(General.LANGUAGE);
            if (language == null) language = "ar";
            if (language.equals("ar")) toArabic();
            else if (language.equals("en")) toEnglish();

            String isDarkMode = repository.get(General.DARK_MODE);
            if (isDarkMode == null) isDarkMode = "0";
            if (isDarkMode.equals("0")) toLightMode();
            else toDarkMode();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        arabicToggleButton.setOnAction(event -> {
            try {
                repository.update(General.LANGUAGE, "ar");
                toArabic();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        englishToggleButton.setOnAction(event -> {
            try {
                repository.update(General.LANGUAGE, "en");
                toEnglish();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        darkModeSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                try {
                    repository.update(General.DARK_MODE, "1");
                    toDarkMode();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    repository.update(General.DARK_MODE, "0");
                    toLightMode();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        updateButton.setOnAction(event -> {
            SystemService.upgradeSystem();
        });
    }

    private void toArabic() {
        arabicToggleButton.setSelected(true);
        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        LocaleService.setResources(ResourceBundle.getBundle("locales/lang", locale));
        window.getScene().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    }

    private void toEnglish() {
        englishToggleButton.setSelected(true);
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        LocaleService.setResources(ResourceBundle.getBundle("locales/lang", locale));
        window.getScene().setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
    }

    private void toLightMode() {
        darkModeSwitch.setSelected(false);
        window.getScene().getStylesheets().clear();
        window.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/light.css")).toExternalForm());
    }

    private void toDarkMode() {
        darkModeSwitch.setSelected(true);
        window.getScene().getStylesheets().clear();
        window.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/dark.css")).toExternalForm());
    }
}
