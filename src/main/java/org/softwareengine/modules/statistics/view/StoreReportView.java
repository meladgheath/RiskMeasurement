package org.softwareengine.modules.statistics.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXSurface;

public class StoreReportView {

    public VBox root  ;
    public ComboBox<Store> storeComboBox ;
    public TableView tableView ;
    public StoreReportView() {
        root = new VBox();
        storeComboBox = new ComboBox<>();
        tableView = new TableView();

        FXSurface surface = new FXSurface();
        surface.addRowWithComboBox(storeComboBox, LocaleService.getKey("store"));
        surface.addRow(tableView);

        root.getChildren().add(surface);
        root.getChildren().add(tableView);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.CENTER);
    }
}
