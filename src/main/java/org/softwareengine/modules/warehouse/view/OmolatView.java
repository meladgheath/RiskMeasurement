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

public class OmolatView {


    public TextField USD ;
    public TextField EUR ;
    public TextField GBP ;
    public TextField TND ;
    public TextField CHF ;
    public TextField MAD ;
    public TextField CNY ;
    public TextField SEK ;

    public VBox root ;
    public Button save ;
    public Label totals ;
    public OmolatView() {

        FXSurface oMolat = new FXSurface();
        FXSurface totalSurface = new FXSurface();

        USD = oMolat.addRowWithTextField(LocaleService.getKey("usd")) ;
        EUR = oMolat.addRowWithTextField(LocaleService.getKey("eur")) ;
        GBP = oMolat.addRowWithTextField(LocaleService.getKey("gbp")) ;
        TND = oMolat.addRowWithTextField(LocaleService.getKey("tnd")) ;
        CHF = oMolat.addRowWithTextField(LocaleService.getKey("chf")) ;
        MAD = oMolat.addRowWithTextField(LocaleService.getKey("mad")) ;
        CNY = oMolat.addRowWithTextField(LocaleService.getKey("cny")) ;
        SEK = oMolat.addRowWithTextField(LocaleService.getKey("sek")) ;
        oMolat.addSeparator();
        save = oMolat.addButton(LocaleService.getKey("save"));


        totals = totalSurface.addRowWithLabel(LocaleService.getKey("omolatTotal"));

        HBox hBox = new HBox(oMolat,totalSurface);
        hBox.setSpacing(25);


        root = new VBox( hBox);
        VBox.setMargin(hBox, new Insets(25));
        root.getStyleClass().add("home-center");

        root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        root.setAlignment(Pos.CENTER);


    }
}
