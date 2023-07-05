package org.softwareengine.utils.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import org.softwareengine.utils.service.LocaleService;

public class FXDialogContent extends FXDialog {
    public final Button primaryButton;
    public final Button secondaryButton;

    public FXDialogContent(Window window) {
        super(window);
        addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) close();
        });
        secondaryButton = new Button();
        secondaryButton.setPrefWidth(150);
        secondaryButton.textProperty().bind(LocaleService.getKey("dismiss"));
        secondaryButton.getStyleClass().add("secondary-button");
        secondaryButton.addEventHandler(ActionEvent.ACTION, event -> close());
        buttons.getChildren().add(secondaryButton);

        primaryButton = new Button();
        primaryButton.setPrefWidth(150);
        primaryButton.textProperty().bind(LocaleService.getKey("add"));
        primaryButton.getStyleClass().add("primary-button");
        buttons.getChildren().add(primaryButton);
    }

    public void setContent(Pane content) {
        this.root.getChildren().add(content);
    }
}
