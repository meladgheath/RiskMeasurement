package org.softwareengine.modules.warehouse.controller;

import org.softwareengine.core.controller.HomeController;
import org.softwareengine.modules.warehouse.view.OmolatView;

import java.text.DecimalFormat;

public class OmolatController extends OmolatView {

    public OmolatController() {
        save.setOnAction(event -> save());
    }

    public void save() {
        Long usd = Long.parseLong(USD.getText()) ;
        Long eur = Long.parseLong(EUR.getText()) ;
        Long gbp = Long.parseLong(GBP.getText()) ;
        Long tnd = Long.parseLong(TND.getText()) ;
        Long chf = Long.parseLong(CHF.getText()) ;
        Long mad = Long.parseLong(MAD.getText()) ;
        Long cny = Long.parseLong(CNY.getText()) ;
        Long sek = Long.parseLong(SEK.getText()) ;

        Long result = (usd+eur+gbp+tnd+chf+mad+cny+sek) ;
        DecimalFormat format = new DecimalFormat("#.##") ;
        format.setGroupingUsed(true);
        format.setGroupingSize(3);

        totals.setText(format.format(result));
        HomeController.riskMarketController.total.setText(format.format(result));
    }

}
