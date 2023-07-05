package org.softwareengine.utils.ui;

import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ToggleSwitch extends StackPane {

    private final BooleanProperty selectedProperty;
    private final TranslateTransition translateAnimation;
    private final FillTransition fillAnimation;
    private final ParallelTransition animation;
    private double offsetX;
    private double offsetY;

    public ToggleSwitch() {
        selectedProperty = new SimpleBooleanProperty(false);
        translateAnimation = new TranslateTransition(Duration.seconds(0.25));
        translateAnimation.setInterpolator(Interpolator.EASE_OUT);
        fillAnimation = new FillTransition(Duration.seconds(0.25));
        fillAnimation.setInterpolator(Interpolator.EASE_OUT);

        animation = new ParallelTransition(translateAnimation, fillAnimation);

        Rectangle background = new Rectangle(40, 24);
        background.setArcWidth(background.getHeight());
        background.setArcHeight(background.getHeight());
        background.setFill(Color.WHITE);
        background.setStroke(Color.LIGHTGRAY);

        Circle trigger = new Circle(10);
        trigger.setCenterX(background.getWidth() / 2 - trigger.getRadius() + 2);
        trigger.setCenterY(background.getHeight() / 2);
        trigger.setFill(Color.WHITE);
        trigger.setStroke(Color.LIGHTGRAY);

        translateAnimation.setNode(trigger);
        fillAnimation.setShape(background);

        Pane pane = new Pane(background, trigger);
        pane.setMinSize(background.getWidth(), background.getHeight());
        pane.setMaxSize(background.getWidth(), background.getHeight());
        getChildren().addAll(pane);
        setAlignment(Pos.CENTER);
        setFocusTraversable(true);
        setPadding(new Insets(5));
        getStyleClass().add("toggle-switch");

        setOnMousePressed(event -> selectedProperty.set(!selectedProperty.get()));
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE)
                selectedProperty.set(!selectedProperty.get());
        });

        selectedProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                translateAnimation.setToX(background.getWidth() - trigger.getRadius() * 2 - 4);
                fillAnimation.setFromValue(Color.WHITE);
                fillAnimation.setToValue(Color.valueOf("#016efd"));
            } else {
                translateAnimation.setToX(-trigger.getCenterX() + trigger.getRadius() + 2);
                fillAnimation.setFromValue(Color.valueOf("#016efd"));
                fillAnimation.setToValue(Color.WHITE);
            }
            animation.play();
        });
    }

    public BooleanProperty selectedProperty() {
        return selectedProperty;
    }

    public void setSelected(boolean isSelected) {
        selectedProperty.set(isSelected);
    }

    public boolean isSelected() {
        return this.selectedProperty.get();
    }
}