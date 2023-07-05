package org.softwareengine.utils.ui;

import javafx.application.Platform;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.stage.Window;
import org.softwareengine.config.Constants;
import org.softwareengine.utils.service.LocaleService;

import java.util.Timer;
import java.util.TimerTask;

public class FXDialogSuccess extends FXDialog {

    public FXDialogSuccess(Window window) {
        super(window);
        Label title = new Label();
        title.textProperty().bind(LocaleService.getKey("success"));
        title.setStyle("""
                -fx-font-size: 17;
                -fx-font-weight: bold;
                """);

        SVGPath svgPath = new SVGPath();
        svgPath.setScaleX(1);
        svgPath.setScaleY(1);
        svgPath.setContent(Constants.CHECK_ICON.getValue());
        svgPath.setFill(Paint.valueOf("#000000"));
        svgPath.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        root.getChildren().add(title);
        root.getChildren().add(svgPath);
        setSize(250, 200);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> close());
            }
        }, 1000);
    }
}
