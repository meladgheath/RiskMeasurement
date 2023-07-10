package org.softwareengine.core.view;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.softwareengine.config.Constants;
import org.softwareengine.config.Paths;
import org.softwareengine.modules.warehouse.view.PointHead;
import org.softwareengine.utils.service.LocaleService;

import java.util.Objects;

public abstract class HomeView {

    public final BorderPane root;

    public final Button PointHeadButton ;
    public final SVGPath PointHeadIcon ;

    public final Button rafiaButton ;
    public final SVGPath rafiaIcon ;

    public final Button headRuleButton ;
    public final SVGPath headRuleIcon ;

    public HomeView(Stage stage) {
        Label salesLabel = new Label();
        setupTitle(salesLabel, LocaleService.getKey("risk-meansure"));


        PointHeadButton = new Button();
        PointHeadIcon = new SVGPath();
        setupButton(PointHeadButton,LocaleService.getKey("point"),PointHeadIcon,Constants.POINTHEAD_ICON);

        rafiaButton = new Button();
        rafiaIcon   = new SVGPath();
        setupButton(rafiaButton,LocaleService.getKey("rafia"),rafiaIcon, Constants.POINTHEAD_ICON);

        headRuleButton = new Button();
        headRuleIcon   = new SVGPath();
        setupButton(headRuleButton,LocaleService.getKey("head-rule"),headRuleIcon,Constants.POINTHEAD_ICON);

        VBox vBox = new VBox();
        vBox.getStyleClass().add("home-left");

        vBox.getChildren().add(salesLabel);
        VBox.setMargin(salesLabel, new Insets(25, 0, 0, 0));
        vBox.getChildren().add(PointHeadButton);
        vBox.getChildren().add(rafiaButton);
        vBox.getChildren().add(headRuleButton);


        root = new BorderPane();
        root.getStyleClass().add("home-root");
        root.setLeft(vBox);


        StackPane container = new StackPane();
        container.getStyleClass().add("home-container");
        container.setVisible(false);


        StackPane stack = new StackPane(root, container);



        Scene scene = new Scene(stack, 1300, 600, Color.TRANSPARENT);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/light.css")).toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.APP_ICON.getPath()))));
        stage.setMinWidth(1200);
        stage.setMinHeight(600);
    }

    private void setupTitle(Label label, StringBinding stringBinding) {
        label.textProperty().bind(stringBinding);
        label.getStyleClass().add("navigation-title");
    }

    private void setupButton(Button button, StringBinding text, SVGPath svgPath, Constants svgContent) {
        button.textProperty().bind(text);
        button.getStyleClass().add("navigation-button");
        svgPath.setContent(svgContent.getValue());
        svgPath.setScaleX(.5);
        svgPath.setScaleY(.5);
        svgPath.getStyleClass().add("unselected-icon");
        button.setGraphic(svgPath);
        button.setAlignment(Pos.CENTER_LEFT);
    }
}