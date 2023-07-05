package org.softwareengine.core.view;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.softwareengine.config.Constants;
import org.softwareengine.config.Paths;
import org.softwareengine.modules.warehouse.view.PointHead;
import org.softwareengine.utils.service.LocaleService;

import java.util.Objects;

public abstract class HomeView {

    public final BorderPane root;
    /*public final Button salePointButton;
    public final SVGPath salePointIcon;
    public final Button customersButton;
    public final SVGPath customersIcon;

    public final Button purchasePointButton;
    public final SVGPath purchasePointIcon;
    public final Button suppliersButton;
    public final SVGPath suppliersIcon;

    public final Button productsButton;
    public final SVGPath productsIcon;
    public final Button storesButton;
    public final SVGPath storesIcon;

    public final Button transformsButton ;
    public final SVGPath transformsIcon ;

    public final Button treasuryButton;
    public final SVGPath treasuryIcon;

    public final Button reportsButton;
    public final SVGPath reportsIcon;

    public final Button storeReportButton ;
    public final SVGPath storeReportIcon ;

    public final Button expensesButton ;
    public final SVGPath expensesIcon  ;
    public final Button debtBookButton;
    public final SVGPath debtBookIcon;

    public final Button generalButton;
    public final SVGPath generalIcon;
*/

    public final Button PointHeadButton ;
    public final SVGPath PointHeadIcon ;

    public HomeView(Stage stage) {
        Label salesLabel = new Label();
        setupTitle(salesLabel, LocaleService.getKey("risk-meansure"));

       /* salePointButton = new Button();
        salePointIcon = new SVGPath();
        setupButton(salePointButton, LocaleService.getKey("sale-point"), salePointIcon, Constants.SALE_POINT_ICON);

        customersButton = new Button();
        customersIcon = new SVGPath();
        setupButton(customersButton, LocaleService.getKey("customers"), customersIcon, Constants.CUSTOMERS_ICON);

        Label purchasesLabel = new Label();
        setupTitle(purchasesLabel, LocaleService.getKey("purchases"));

        purchasePointButton = new Button();
        purchasePointIcon = new SVGPath();
        setupButton(purchasePointButton, LocaleService.getKey("purchase-point"), purchasePointIcon, Constants.PURCHASE_POINT_ICON);

        suppliersButton = new Button();
        suppliersIcon = new SVGPath();
        setupButton(suppliersButton, LocaleService.getKey("suppliers"), suppliersIcon, Constants.SUPPLIERS_ICON);

        Label warehouseLabel = new Label();
        setupTitle(warehouseLabel, LocaleService.getKey("warehouse"));

        productsButton = new Button();
        productsIcon = new SVGPath();
        setupButton(productsButton, LocaleService.getKey("products"), productsIcon, Constants.PRODUCTS_ICON);

        storesButton = new Button();
        storesIcon = new SVGPath();
        setupButton(storesButton, LocaleService.getKey("stores"), storesIcon, Constants.STORES_ICON);

        transformsButton = new Button  ();
        transformsIcon   = new SVGPath ();
        setupButton(transformsButton,LocaleService.getKey("transforms"),transformsIcon,Constants.TRANSFORMS);

        Label financeLabel = new Label();
        setupTitle(financeLabel, LocaleService.getKey("finance"));

        treasuryButton = new Button();
        treasuryIcon = new SVGPath();
        setupButton(treasuryButton, LocaleService.getKey("treasury"), treasuryIcon, Constants.TREASURY_ICON);

        reportsButton = new Button();
        reportsIcon = new SVGPath();
        setupButton(reportsButton,LocaleService.getKey("reports"),reportsIcon,Constants.REPORTS_ICON);

        storeReportButton = new Button();
        storeReportIcon   = new SVGPath();
        setupButton(storeReportButton,LocaleService.getKey("store"),storeReportIcon,Constants.REPORTS_ICON);

        // todo
        expensesButton = new Button() ;
        expensesIcon   = new SVGPath();
        setupButton(expensesButton,LocaleService.getKey("expenses"),expensesIcon,Constants.DEBT_BOOK_ICON);

        debtBookButton = new Button();
        debtBookIcon = new SVGPath();
        setupButton(debtBookButton, LocaleService.getKey("debt-book"), debtBookIcon, Constants.DEBT_BOOK_ICON);

        Label settingsLabel = new Label();
        setupTitle(settingsLabel, LocaleService.getKey("settings"));


        Label statistics = new Label();
        setupTitle(statistics, LocaleService.getKey("statistics"));


        generalButton = new Button();
        generalIcon = new SVGPath();
        setupButton(generalButton, LocaleService.getKey("general"), generalIcon, Constants.GENERAL_ICON);
*/
        PointHeadButton = new Button();
        PointHeadIcon = new SVGPath();
        setupButton(PointHeadButton,LocaleService.getKey("point"),PointHeadIcon,Constants.POINTHEAD_ICON);

        VBox vBox = new VBox();
        vBox.getStyleClass().add("home-left");

        vBox.getChildren().add(salesLabel);
        VBox.setMargin(salesLabel, new Insets(25, 0, 0, 0));
        vBox.getChildren().add(PointHeadButton);
        /*vBox.getChildren().add(salePointButton);
        vBox.getChildren().add(customersButton);

        vBox.getChildren().add(purchasesLabel);
        VBox.setMargin(purchasesLabel, new Insets(25, 0, 0, 0));
        vBox.getChildren().add(purchasePointButton);
        vBox.getChildren().add(suppliersButton);

        vBox.getChildren().add(warehouseLabel);
        VBox.setMargin(warehouseLabel, new Insets(25, 0, 0, 0));
        vBox.getChildren().add(productsButton);
        vBox.getChildren().add(storesButton);
        vBox.getChildren().add(transformsButton);


        vBox.getChildren().add(financeLabel);
        VBox.setMargin(financeLabel, new Insets(25, 0, 0, 0));
        vBox.getChildren().add(treasuryButton);
        vBox.getChildren().add(debtBookButton);
        //fixing the shit
        vBox.getChildren().add(expensesButton);



        vBox.getChildren().add(settingsLabel);
        VBox.setMargin(settingsLabel, new Insets(25, 0, 0, 0));
        vBox.getChildren().add(generalButton);

        vBox.getChildren().add(statistics);
        VBox.setMargin(statistics, new Insets(25, 0, 0, 0));
        vBox.getChildren().add(reportsButton);
        vBox.getChildren().add(storeReportButton);

        ScrollBar bar = new ScrollBar();

        vBox.getChildren().add(bar);

*/
        root = new BorderPane();
        root.getStyleClass().add("home-root");
        root.setLeft(vBox);


        StackPane container = new StackPane();
        container.getStyleClass().add("home-container");
        container.setVisible(false);


        StackPane stack = new StackPane(root, container);



        Scene scene = new Scene(stack, 1300, 600, Color.TRANSPARENT);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/light.css")).toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.APP_ICON.getPath()))));
        stage.setMinWidth(1200);
        stage.setMinHeight(600);
    }

    private void setupTitle(Label label, StringBinding stringBinding) {
        label.textProperty().bind(stringBinding);
        label.getStyleClass().add("navigation-title");
    }

    private void setupButton(Button button, StringBinding text, SVGPath svgPath, Constants svgContent) {
        button.textProperty().bind(text);
        button.getStyleClass().add("navigation-button");
        svgPath.setContent(svgContent.getValue());
        svgPath.setScaleX(.5);
        svgPath.setScaleY(.5);
        svgPath.getStyleClass().add("unselected-icon");
        button.setGraphic(svgPath);
        button.setAlignment(Pos.CENTER_LEFT);
    }
}