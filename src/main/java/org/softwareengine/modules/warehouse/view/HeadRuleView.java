package org.softwareengine.modules.warehouse.view;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXSurface;

public class HeadRuleView {


    public TextField t1 ;
    public TextField t2 ;
    public TextField t3 ;
    public TextField t4 ;
    public TextField t5 ;
    public Label total ;

    public Label total2 ;
    public TextField tf1 ;
    public TextField tf2 ;

    public Label totalMoney ;

    public Label totalMoney2 ;
    public TextField tf3 ;

    public Label headRuleTotal ;

    public VBox root ;

    public HeadRuleView(){
        FXSurface HeadMoneysurface = new FXSurface();
        HeadMoneysurface.addTitle(LocaleService.getKey("total-head"));
        t1 = HeadMoneysurface.addRowWithTextField(LocaleService.getKey("headT1")) ;
        t2 = HeadMoneysurface.addRowWithTextField(LocaleService.getKey("headT2")) ;
        t3 = HeadMoneysurface.addRowWithTextField(LocaleService.getKey("headT3")) ;
        t4 = HeadMoneysurface.addRowWithTextField(LocaleService.getKey("headT4")) ;
        t5 = HeadMoneysurface.addRowWithTextField(LocaleService.getKey("headT5")) ;
        HeadMoneysurface.addSeparator();
        total = HeadMoneysurface.addRowWithLabel(LocaleService.getKey("totals")) ;

        FXSurface outSurface = new FXSurface();
        outSurface.addTitle(LocaleService.getKey("out"));
        total2 = outSurface.addRowWithLabel(LocaleService.getKey("total-head")) ;
        outSurface.addSeparator();
        tf1 = outSurface.addRowWithTextField(LocaleService.getKey("tf1"));
        tf2 = outSurface.addRowWithTextField(LocaleService.getKey("tf2"));
        outSurface.addSeparator();
        totalMoney = outSurface.addRowWithLabel(LocaleService.getKey("totals"));


        FXSurface headRuleSurface = new FXSurface() ;
        headRuleSurface.addTitle(LocaleService.getKey("total-head-rule"));
        totalMoney2 = headRuleSurface.addRowWithLabel(LocaleService.getKey("totalMoney")) ;
        headRuleSurface.addSeparator();
        tf3 = headRuleSurface.addRowWithTextField(LocaleService.getKey("tf3")) ;
        headRuleSurface.addSeparator();
        headRuleTotal = headRuleSurface.addRowWithLabel(LocaleService.getKey("total-head-rule")) ;


        HBox hBox = new HBox(HeadMoneysurface,outSurface,headRuleSurface);
        hBox.setSpacing(25);


        root = new VBox( hBox);
        VBox.setMargin(hBox, new Insets(25));
        root.getStyleClass().add("home-center");

        root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        root.setAlignment(Pos.CENTER);

    }
}
