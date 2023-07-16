package org.softwareengine.modules.warehouse.controller;

import org.softwareengine.core.controller.HomeController;
import org.softwareengine.core.view.HomeView;
import org.softwareengine.modules.warehouse.view.RiskMarketview;

import java.text.DecimalFormat;

public class RiskMarketController extends RiskMarketview {

    public RiskMarketController() {
        save.setOnAction(event -> save());
    }

    public void save(){

       Long omolatTotal = Long.parseLong(HomeController.omolatController.totals.getText().replaceAll("[,]",""));
       Long headTotal   = Long.parseLong(HomeController.headRuleController.headRuleTotal.getText().replaceAll("[,]",""));



        Double result = 0.0 ; //= omolatTotal * 0.08;


       if ( (omolatTotal)-0.02*(headTotal) > 0)
           result = omolatTotal * 0.08 ;

        DecimalFormat format = new DecimalFormat("#.##") ;
        format.setGroupingUsed(true);
        format.setGroupingSize(3);


        totalHeadRequire.setText(format.format(result));
        AssiedTotal_for_riskMarket.setText(format.format(result*12.5));
    }
}
