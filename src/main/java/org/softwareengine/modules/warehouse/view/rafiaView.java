package org.softwareengine.modules.warehouse.view;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXSurface;

public class rafiaView {

    public TextField t1 ;
    public TextField t2 ;

    public Label totalPerCent ;
    public Button save ;
    public VBox root ;
    public rafiaView() {

        FXSurface surface = new FXSurface();

        t1 = surface.addRowWithTextField(LocaleService.getKey("Raft1")) ;
        t2 = surface.addRowWithTextField(LocaleService.getKey("Raft2")) ;
        save = surface.addButton(LocaleService.getKey("save"));


        FXSurface perCentSurface = new FXSurface();
        perCentSurface.addTitle(LocaleService.getKey("per"));
        totalPerCent = perCentSurface.addRowWithLabel(LocaleService.getKey("rafia")) ;

        HBox hBox = new HBox(surface,perCentSurface);
        hBox.setSpacing(25);




        root = new VBox( hBox);
        VBox.setMargin(hBox, new Insets(25));
        root.getStyleClass().add("home-center");

        root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        root.setAlignment(Pos.CENTER);


    }
}
