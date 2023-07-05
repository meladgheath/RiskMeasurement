package org.softwareengine.core.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.softwareengine.Main;
import org.softwareengine.config.Paths;

import java.util.Objects;

public abstract class SplashView {
    private static Stage stage;

    public static void show() {
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Paths.SPLASH_IMAGE.getPath()))));
        imageView.setFitWidth(600);
        imageView.setFitHeight(300);
        VBox root = new VBox(imageView);

        stage = new Stage();
        stage.setScene(new Scene(root, 600, 300));
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream(Paths.APP_ICON.getPath()))));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void close() {
        stage.close();
    }
}