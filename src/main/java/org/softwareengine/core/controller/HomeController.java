package org.softwareengine.core.controller;

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
import org.softwareengine.modules.warehouse.controller.PointHeadController;
import org.softwareengine.modules.warehouse.controller.ProductsController;
import org.softwareengine.modules.warehouse.controller.StoresController;
import org.softwareengine.modules.warehouse.controller.TransformsController;
import org.softwareengine.modules.warehouse.view.PointHead;

import java.sql.SQLException;

public class HomeController extends HomeView {
    public static SalePointController salePointController;
    public static CustomersController customersController;
    public static PurchasePointController purchasePointController;
    public static SuppliersController suppliersController;
    public static ProductsController productsController;
    public static PointHeadController pointHeadController ;
    public static StoresController storesController;
    public static TreasuryController treasuryController;
    public static DebtBookController debtBookController;
    public static GeneralController generalController;

    public static ReportsController reportsController ;
    public static StoreReportController storeReportController ;
    public static ExpensesController expensesController ;

    public static TransformsController transformsController ;

    public HomeController(Stage stage) {
        super(stage);
//        storesController = new StoresController();
//        transformsController = new TransformsController();
//        productsController = new ProductsController();
        pointHeadController = new PointHeadController();
//        suppliersController = new SuppliersController();
//        customersController = new CustomersController();
//        treasuryController = new TreasuryController();
//        debtBookController = new DebtBookController();
//        salePointController = new SalePointController();
//        purchasePointController = new PurchasePointController(stage);
//        generalController = new GeneralController(stage);
//        reportsController = new ReportsController();
//        expensesController = new ExpensesController(stage);
//        storeReportController = new StoreReportController();



        PointHeadButton.setOnAction(event -> changeScene(Home.POINT_HEAD));

    /*    salePointButton.setOnAction(event -> changeScene(Home.SALE_POINT));
        customersButton.setOnAction(event -> changeScene(Home.CUSTOMERS));
        purchasePointButton.setOnAction(event -> changeScene(Home.PURCHASE_POINT));
        suppliersButton.setOnAction(event -> changeScene(Home.SUPPLIERS));
        productsButton.setOnAction(event -> changeScene(Home.PRODUCTS));
        storesButton.setOnAction(event -> changeScene(Home.STORES));
        transformsButton.setOnAction(event -> changeScene(Home.TRANSFORMS));
        treasuryButton.setOnAction(event -> changeScene(Home.TREASURY));
        reportsButton.setOnAction(event -> changeScene(Home.REPORTS));
        debtBookButton.setOnAction(event -> changeScene(Home.DEBT_BOOK));
        expensesButton.setOnAction(event -> changeScene(Home.EXPENSES));
        generalButton.setOnAction(event -> changeScene(Home.GENERAL));
        storeReportButton.setOnAction(event -> changeScene(Home.STOREREPORT));
*/
//        changeScene(Home.POINT_HEAD);
    }

    private void changeScene(Home home) {
        switch (home) {
            /*case SALE_POINT -> {
                resetSelection();
                salePointButton.getStyleClass().add("selected-navigation-button");
                salePointIcon.setContent(Constants.SALE_POINT_SELECTED_ICON.getValue());
                root.setCenter(salePointController.root);
                salePointController.onSceneChanged();
            }
            case CUSTOMERS -> {
                resetSelection();
                customersButton.getStyleClass().add("selected-navigation-button");
                customersIcon.setContent(Constants.CUSTOMERS_SELECTED_ICON.getValue());
                root.setCenter(customersController.root);
            }
            case PURCHASE_POINT -> {
                resetSelection();
                purchasePointButton.getStyleClass().add("selected-navigation-button");
                purchasePointIcon.setContent(Constants.PURCHASE_POINT_SELECTED_ICON.getValue());
                root.setCenter(purchasePointController.root);
                purchasePointController.onSceneChanged();
            }
            case SUPPLIERS -> {
                resetSelection();
                suppliersButton.getStyleClass().add("selected-navigation-button");
                suppliersIcon.setContent(Constants.SUPPLIERS_SELECTED_ICON.getValue());
                root.setCenter(suppliersController.root);
            }
            case PRODUCTS -> {
                resetSelection();
                productsButton.getStyleClass().add("selected-navigation-button");
                productsIcon.setContent(Constants.PRODUCTS_SELECTED_ICON.getValue());
                root.setCenter(productsController.root);
            }
            case STORES -> {
                resetSelection();
                storesButton.getStyleClass().add("selected-navigation-button");
                storesIcon.setContent(Constants.STORES_SELECTED_ICON.getValue());
                root.setCenter(storesController.root);
            }
            case TRANSFORMS -> {
                resetSelection();
                transformsButton.getStyleClass().add("selected-navigation-button");
                transformsIcon.setContent(Constants.TRANSFORMS_SELECTED.getValue());
                root.setCenter(transformsController.root);
            }
            case TREASURY -> {
                resetSelection();
                treasuryButton.getStyleClass().add("selected-navigation-button");
                treasuryIcon.setContent(Constants.TREASURY_SELECTED_ICON.getValue());
                root.setCenter(treasuryController.root);
                treasuryController.onSceneChanged();
            }
            case DEBT_BOOK -> {
                resetSelection();
                debtBookButton.getStyleClass().add("selected-navigation-button");
                debtBookIcon.setContent(Constants.DEBT_BOOK_ICON_SELECTED.getValue());
                root.setCenter(debtBookController.root);
            }
            case GENERAL -> {
                resetSelection();
                generalButton.getStyleClass().add("selected-navigation-button");
                generalIcon.setContent(Constants.GENERAL_SELECTED_ICON.getValue());
                root.setCenter(generalController.root);

            }
            case REPORTS -> {
                resetSelection();
                reportsButton.getStyleClass().add("selected-navigation-button");
                reportsIcon.setContent(Constants.REPORTS_ICON_SELECTED.getValue());
                root.setCenter(reportsController.root);

                try {
                    reportsController.tableView.setItems(reportsController.repositories.getAll());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            case EXPENSES -> {
                resetSelection();
                expensesButton.getStyleClass().add("selected-navigation-button");
                expensesIcon.setContent(Constants.DEBT_BOOK_ICON_SELECTED.getValue());
                root.setCenter(expensesController.root);
            }
            case STOREREPORT -> {
                resetSelection();
                storeReportButton.getStyleClass().add("selected-navigation-button");
                storeReportIcon.setContent(Constants.REPORTS_ICON_SELECTED.getValue());
                root.setCenter(storeReportController.root);
            }*/
            case POINT_HEAD -> {
                resetSelection();
                PointHeadButton.getStyleClass().add("selected-navigation-button") ;
                PointHeadIcon.setContent(Constants.GENERAL_SELECTED_ICON.getValue());
                root.setCenter(pointHeadController.root);
            }

        }
    }

    private void resetSelection() {
       /* salePointButton.getStyleClass().remove("selected-navigation-button");
        salePointIcon.setContent(Constants.SALE_POINT_ICON.getValue());

        customersButton.getStyleClass().remove("selected-navigation-button");
        customersIcon.setContent(Constants.CUSTOMERS_ICON.getValue());

        purchasePointButton.getStyleClass().remove("selected-navigation-button");
        purchasePointIcon.setContent(Constants.PURCHASE_POINT_ICON.getValue());

        suppliersButton.getStyleClass().remove("selected-navigation-button");
        suppliersIcon.setContent(Constants.SUPPLIERS_ICON.getValue());

        productsButton.getStyleClass().remove("selected-navigation-button");
        productsIcon.setContent(Constants.PRODUCTS_ICON.getValue());

        storesButton.getStyleClass().remove("selected-navigation-button");
        storesIcon.setContent(Constants.STORES_ICON.getValue());

        transformsButton.getStyleClass().remove("selected-navigation-button");
        transformsIcon.setContent(Constants.TRANSFORMS.getValue());

        treasuryButton.getStyleClass().remove("selected-navigation-button");
        treasuryIcon.setContent(Constants.TREASURY_ICON.getValue());

        reportsButton.getStyleClass().remove("selected-navigation-button");
        reportsIcon.setContent(Constants.REPORTS_ICON.getValue());

        storeReportButton.getStyleClass().remove("selected-navigation-button");
        storeReportIcon.setContent(Constants.REPORTS_ICON.getValue());

        debtBookButton.getStyleClass().remove("selected-navigation-button");
        debtBookIcon.setContent(Constants.DEBT_BOOK_ICON.getValue());

        //fix  the fucking shit . . .
        expensesButton.getStyleClass().remove("selected-navigation-button");
        expensesIcon.setContent(Constants.DEBT_BOOK_ICON.getValue());

        generalButton.getStyleClass().remove("selected-navigation-button");
        generalIcon.setContent(Constants.GENERAL_ICON.getValue());*/

        PointHeadButton.getStyleClass().remove("selected-navigation-button");
        PointHeadIcon.setContent(Constants.GENERAL_ICON.getValue());
    }
}