package org.softwareengine.modules.statistics.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ReportsView {

    public Button printButton ;
    public  TableView tableView ;
    public final VBox root ;

    public ReportsView() {


        tableView = new TableView();
        printButton = new Button();


        root = new VBox();
        root.setPadding(new Insets(8,0,0,0));
        root.setSpacing(20);

        root.getChildren().add(printButton);
        root.getChildren().add(tableView);
        root.getStyleClass().add("home-center");
        root.setAlignment(Pos.TOP_CENTER);

    }

}
