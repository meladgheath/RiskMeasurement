package org.softwareengine.modules.warehouse.view;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXSurface;

public class RiskMarketview {

    public Label total ;
    public Label totalHead ;
    public Label totalHeadRequire ;
    public Label AssiedTotal_for_riskMarket ;
    public Button save ;
    public VBox root ;
    public RiskMarketview() {

        FXSurface surface = new FXSurface() ;

        total = surface.addRowWithLabel(LocaleService.getKey("omolatTotal")) ;
        totalHead = surface.addRowWithLabel(LocaleService.getKey("total-head-rule"));
        surface.addSeparator();
        save = surface.addButton(LocaleService.getKey("save"));
//        surface.addSeparator();


        FXSurface requireSurface = new FXSurface();
        requireSurface.addTitle(LocaleService.getKey("total-of-head-require"));
        totalHeadRequire = requireSurface.addRowWithLabel(LocaleService.getKey("all-totals"));
        AssiedTotal_for_riskMarket =requireSurface.addRowWithLabel(LocaleService.getKey("AssiedTotal"));

        HBox hBox = new HBox(surface,requireSurface);
        hBox.setSpacing(25);


        root = new VBox( hBox);
        VBox.setMargin(hBox, new Insets(25));
        root.getStyleClass().add("home-center");

        root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        root.setAlignment(Pos.CENTER);

    }

}
