package org.softwareengine.utils.ui;

import javafx.application.Platform;
import javafx.collections.WeakListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.Objects;

public abstract class FXDialog extends Stage {

    public final VBox root;
    public final HBox buttons;
    private final Window window;


    public FXDialog(Window window) {
        this.window = window;

        root = new VBox();
        VBox.setVgrow(root, Priority.ALWAYS);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(25);

        buttons = new HBox();
        buttons.setSpacing(10);

        VBox vBox = new VBox(root, buttons);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(50));
        vBox.getStyleClass().add("fx-dialog");

        Scene scene = new Scene(vBox);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(window.getScene().getStylesheets().get(0));
        scene.nodeOrientationProperty().bind(window.getScene().getRoot().getChildrenUnmodifiable().get(0).nodeOrientationProperty());

        setScene(scene);
        initOwner(window);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        setSize(400, 300);
        setOnShowing(event -> Platform.runLater(() -> {
            window.getScene().getRoot().getChildrenUnmodifiable().get(0).setEffect(new BoxBlur(10, 10, 10));
            window.getScene().getRoot().getChildrenUnmodifiable().get(1).setVisible(true);
        }));
        setOnHiding(event -> {
            window.getScene().getRoot().getChildrenUnmodifiable().get(0).setEffect(null);
            window.getScene().getRoot().getChildrenUnmodifiable().get(1).setVisible(false);
        });
    }

    public void setSize(double width, double height) {
        setWidth(width);
        setHeight(height);
        setX(window.getX() + (window.getWidth() / 2) - (getWidth() / 2));
        setY(window.getY() + (window.getHeight() / 2) - (getHeight() / 2));
    }
}