package org.softwareengine.utils.ui;

import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.SVGPath;
import javafx.stage.Window;
import org.softwareengine.config.Constants;
import org.softwareengine.utils.service.LocaleService;

public class FXDialogDelete extends FXDialog{
    public final Button deleteButton;
    public final Button secondaryButton;
    public final Label message;

    public FXDialogDelete(Window window) {
        super(window);
        addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) close();
        });

        SVGPath svgPath = new SVGPath();
        svgPath.setContent(Constants.DELETE_ICON.getValue());
        root.getChildren().add(svgPath);

        message = new Label();
        message.getStyleClass().add("dialog-message");
        root.getChildren().add(message);

        secondaryButton = new Button();
        secondaryButton.setPrefWidth(150);
        secondaryButton.textProperty().bind(LocaleService.getKey("dismiss"));
        secondaryButton.getStyleClass().add("secondary-button");
        secondaryButton.addEventHandler(ActionEvent.ACTION, event -> close());
        buttons.getChildren().add(secondaryButton);

        deleteButton = new Button();
        deleteButton.setPrefWidth(150);
        deleteButton.textProperty().bind(LocaleService.getKey("delete"));
        deleteButton.getStyleClass().add("delete-button");
        buttons.getChildren().add(deleteButton);
    }

    public void setMessage(StringBinding message) {
        this.message.textProperty().bind(message);
    }
}
