package org.softwareengine.utils.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.softwareengine.config.Constants;
import org.softwareengine.utils.service.LocaleService;

import java.util.Objects;

public class FXInfoDialog extends Stage {
    public final VBox root;
    public final HBox buttons;
    public final Button okButton;
    public final Label message;

    public FXInfoDialog() {
        root = new VBox();
        VBox.setVgrow(root, Priority.ALWAYS);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(25);

        buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        VBox vBox = new VBox(root, buttons);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(50));
        vBox.getStyleClass().add("fx-dialog");

        Scene scene = new Scene(vBox);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/light.css")).toExternalForm());

        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        setWidth(500);
        setHeight(400);

        SVGPath svgPath = new SVGPath();
        svgPath.setContent(Constants.INFO_ICON.getValue());
        root.getChildren().add(svgPath);

        message = new Label();
        message.setTextAlignment(TextAlignment.CENTER);
        message.getStyleClass().add("dialog-message");
        root.getChildren().add(message);

        okButton = new Button();
        okButton.setPrefWidth(150);
        okButton.textProperty().bind(LocaleService.getKey("ok"));
        okButton.getStyleClass().add("primary-button");
        buttons.getChildren().add(okButton);
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }
}
