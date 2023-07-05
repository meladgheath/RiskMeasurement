package org.softwareengine.modules.finance.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.softwareengine.modules.finance.model.Treasury;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXSurface;

public class expensesView {

    public    ComboBox<Treasury>  expensesTreasury ;
    public   TextField expensesDesc ;
    public   TextField expensesValue ;
    public    Button save ;

    public StackPane root ;
    public expensesView() {
        FXSurface surface = new FXSurface();
        surface.addTitle(LocaleService.getKey("expenses-list"));
        expensesTreasury = new ComboBox();
        surface.addRowWithComboBox(expensesTreasury,LocaleService.getKey("treasury"));
        surface.addSeparator();
        expensesDesc     = surface.addRowWithTextField(LocaleService.getKey("expenses")) ;
        surface.addSeparator();
        expensesValue    = surface.addRowWithTextField(LocaleService.getKey("amount")) ;

        save = surface.addButton(LocaleService.getKey("save")) ;

        root = new StackPane();
        root.getChildren().add(surface);
        root.setPadding(new Insets(25,25,25,25));
        root.setAlignment(Pos.CENTER);

        root.setMaxSize(350,300);
        root.setMinSize(350,300);

    }
}
