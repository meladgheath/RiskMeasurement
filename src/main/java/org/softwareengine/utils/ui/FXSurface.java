package org.softwareengine.utils.ui;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FXSurface extends VBox {
    private final Label title;
    public final VBox root;

    public FXSurface() {
        title = new Label();
        title.getStyleClass().add("surface-title");
        HBox titleContainer = new HBox(title);
        titleContainer.setPadding(new Insets(5));

        root = new VBox();
        root.getStyleClass().add("surface");
        root.setMaxHeight(Region.USE_PREF_SIZE);

        Text description = new Text();

        getChildren().add(titleContainer);
        getChildren().add(root);
        getChildren().add(description);
        setAlignment(Pos.TOP_CENTER);
    }

    public void addRow(Region region) {
        HBox hBox = new HBox(region);
        hBox.getStyleClass().add("surface-row");
        root.getChildren().add(hBox);
    }

    public Label addRowWithLabel(ObservableValue<String> observableValue) {
        Label label0 = new Label();
        label0.textProperty().bind(observableValue);

        Label label1 = new Label();
        label1.setStyle("-fx-font-size: 15");

            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);

        HBox hBox = new HBox(label0, region, label1);
        hBox.getStyleClass().add("surface-row");

        root.getChildren().add(hBox);
        return label1;
    }

    public void addRow(Region region, StringBinding stringBinding) {
        Label label = new Label();
        label.textProperty().bind(stringBinding);

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        HBox hBox = new HBox(label, region1, region);
        hBox.getStyleClass().add("surface-row");
        root.getChildren().add(hBox);
    }

    public void addRowWithComboBox(ComboBox<?> comboBox, StringBinding stringBinding) {
        Label label = new Label();
        label.textProperty().bind(stringBinding);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        HBox hBox = new HBox(label, region, comboBox);
        hBox.getStyleClass().add("surface-row");
        root.getChildren().add(hBox);
    }


    public void addRowWithChoiceBox(ChoiceBox<?> choiceBox, StringBinding stringBinding) {
        Label label = new Label();
        label.textProperty().bind(stringBinding);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        HBox hBox = new HBox(label, region, choiceBox);
        hBox.getStyleClass().add("surface-row");
        root.getChildren().add(hBox);
    }


    public TextField addRowWithTextField(ObservableValue<String> observableValue) {
        Label label0 = new Label();
        label0.textProperty().bind(observableValue);

        TextField textField = new TextField();

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        HBox hBox = new HBox(label0, region, textField);

        hBox.getStyleClass().add("surface-row");

        root.getChildren().add(hBox);
        return textField;
    }

    public HBox addRowWith2TextField(ObservableValue<String> observableValue) {
        Label label0 = new Label();
        label0.textProperty().bind(observableValue);

        TextField textField1 = new TextField();
        TextField textField2 = new TextField();

        textField1.setPromptText("q");
        textField2.setPromptText("pa");

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        CheckBox c = new CheckBox() ;

        HBox hBox = new HBox(label0, region, textField1,textField2,c);
        hBox.getStyleClass().add("surface-row");
        hBox.setSpacing(5);

        root.getChildren().add(hBox);

        return hBox;
    }

    public Button addButton(ObservableValue<String> observableValue) {
        Button button = new Button();
        button.textProperty().bind(observableValue);
        button.setPrefWidth(160);
        button.getStyleClass().add("primary-button");

        Region region = new Region();
        region.setPrefWidth(100);

        HBox hBox = new HBox(region, button);
        hBox.getStyleClass().add("surface-row");

        root.getChildren().add(hBox);
        return button;
    }

    public void addTitle(StringBinding title) {
        this.title.textProperty().bind(title);
    }

    public Label getTitle() {
        return this.title;
    }

    public void addSeparator() {
        Separator separator = new Separator();
        separator.setPadding(new Insets(5));
        root.getChildren().add(separator);
    }
}
