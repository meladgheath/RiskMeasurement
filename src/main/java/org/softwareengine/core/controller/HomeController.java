package org.softwareengine.core.controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.softwareengine.config.Constants;
import org.softwareengine.core.model.Home;
import org.softwareengine.core.view.HomeView;
import org.softwareengine.modules.finance.controller.DebtBookController;
import org.softwareengine.modules.finance.controller.TreasuryController;
import org.softwareengine.modules.finance.controller.ExpensesController;
import org.softwareengine.modules.purchases.controller.PurchasePointController;
import org.softwareengine.modules.purchases.controller.SuppliersController;
import org.softwareengine.modules.sales.controller.CustomersController;
import org.softwareengine.modules.sales.controller.SalePointController;
import org.softwareengine.modules.settings.controller.GeneralController;
import org.softwareengine.modules.statistics.controller.ReportsController;
import org.softwareengine.modules.statistics.controller.StoreReportController;
import org.softwareengine.modules.warehouse.controller.*;
import org.softwareengine.modules.warehouse.view.PointHead;

import java.sql.SQLException;

public class HomeController extends HomeView {

    public static PointHeadController pointHeadController ;
    public static RafiaController rafiaController ;
    public static HeadRuleController headRuleController;
    public static OmolatController omolatController ;
    public static RiskMarketController riskMarketController ;

    public static CustomersController customersController ;
    public static TreasuryController treasuryController ;
    public static StoresController storesController ;
    public static SuppliersController suppliersController ;
    public static ProductsController productsController ;
    public static DebtBookController debtBookController ;

    public HomeController(Stage stage) {
        super(stage);

        pointHeadController  = new PointHeadController();
        rafiaController      = new RafiaController() ;
        headRuleController   = new HeadRuleController();
        omolatController     = new OmolatController();
        riskMarketController = new RiskMarketController();

        PointHeadButton .setOnAction(event -> changeScene(Home.POINT_HEAD));
        rafiaButton     .setOnAction(event -> changeScene(Home.RAFIA));
        headRuleButton  .setOnAction(event -> changeScene(Home.HEAD_RULE));
        OmolatButton    .setOnAction(event -> changeScene(Home.OMOLAT));
        RiskMarketButton.setOnAction(event -> changeScene(Home.RISK_MARKET));



//        changeScene(Home.POINT_HEAD);
    }

    private void changeScene(Home home) {
        switch (home) {

            case POINT_HEAD -> {
                resetSelection();
                PointHeadButton.getStyleClass().add("selected-navigation-button") ;
                PointHeadIcon.setContent(Constants.GENERAL_SELECTED_ICON.getValue());
                root.setCenter(pointHeadController.root);
            }
            case RAFIA -> {
                resetSelection();
                rafiaButton.getStyleClass().add("selected-navigation-button");
                rafiaIcon.setContent(Constants.GENERAL_SELECTED_ICON.getValue());
                root.setCenter(rafiaController.root);
            }
            case HEAD_RULE ->{
                resetSelection();
                headRuleButton.getStyleClass().add("selected-navigation-button");
                headRuleIcon.setContent(Constants.GENERAL_ICON.getValue());
                root.setCenter(headRuleController.root);
            }
            case OMOLAT -> {
                resetSelection();
                OmolatButton.getStyleClass().add("selected-navigation-button");
                OmolatIcon  .setContent(Constants.GENERAL_ICON.getValue());
                root.setCenter(omolatController.root);

            }
            case RISK_MARKET -> {
                resetSelection();
                RiskMarketButton.getStyleClass().add("selected-navigation-button");
                RiskMarketIcon  .setContent(Constants.GENERAL_ICON.getValue());
                root.setCenter(riskMarketController.root);
            }


        }
    }

    private void resetSelection() {

        PointHeadButton.getStyleClass().remove("selected-navigation-button");
        PointHeadIcon.setContent(Constants.POINTHEAD_ICON.getValue());

        rafiaButton.getStyleClass().remove("selected-navigation-button");
        rafiaIcon.setContent(Constants.POINTHEAD_ICON.getValue());

        headRuleButton.getStyleClass().remove("selected-navigation-button");
        headRuleIcon.setContent(Constants.POINTHEAD_ICON.getValue());

        OmolatButton.getStyleClass().remove("selected-navigation-button");
        OmolatIcon.setContent(Constants.POINTHEAD_ICON.getValue());

        RiskMarketButton.getStyleClass().remove("selected-navigation-button");
        RiskMarketIcon.setContent(Constants.POINTHEAD_ICON.getValue());
    }
}