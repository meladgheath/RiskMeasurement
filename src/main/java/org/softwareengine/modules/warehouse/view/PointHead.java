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

public class PointHead {

    public TextField t1 ;
    public TextField t1_1 ;
    public TextField t1_2 ;
    public TextField t2 ;
    public TextField t3 ;
    public TextField t4 ;

    public TextField t5 ;
    public Button save ;

    public Label totalPerCent ;
    public VBox root ;
    public PointHead() {
        FXSurface pointSurface = new FXSurface();
        pointSurface.addTitle(LocaleService.getKey("point"));

         t1 = pointSurface.addRowWithTextField(LocaleService.getKey("t1")) ;
         t1_1 = pointSurface.addRowWithTextField(LocaleService.getKey("t1_1")) ;
         t1_2 = pointSurface.addRowWithTextField(LocaleService.getKey("t1_2")) ;
         pointSurface.addSeparator();
         t2 = pointSurface.addRowWithTextField(LocaleService.getKey("t2"));
         t3 = pointSurface.addRowWithTextField(LocaleService.getKey("t3")) ;
         t4 = pointSurface.addRowWithTextField(LocaleService.getKey("t4")) ;
         t5 = pointSurface.addRowWithTextField(LocaleService.getKey("t5"));
         save = pointSurface.addButton(LocaleService.getKey("save")) ;


        FXSurface perCentSurface = new FXSurface();
        perCentSurface.addTitle(LocaleService.getKey("per"));
        totalPerCent = perCentSurface.addRowWithLabel(LocaleService.getKey("percent")) ;


        HBox hBox = new HBox(pointSurface,perCentSurface);
        hBox.setSpacing(25);


        root = new VBox( hBox);
        VBox.setMargin(hBox, new Insets(25));
        root.getStyleClass().add("home-center");

        root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        root.setAlignment(Pos.CENTER);
    }
}
