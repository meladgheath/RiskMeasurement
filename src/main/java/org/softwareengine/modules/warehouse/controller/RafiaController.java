package org.softwareengine.modules.warehouse.controller;

import org.softwareengine.modules.warehouse.view.rafiaView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RafiaController extends rafiaView {


    public RafiaController() {
        save.setOnAction(event -> save());
    }

    private void save () {

        Long one = Long.parseLong(t1.getText());
        Long two = Long.parseLong(t2.getText());

        totalPerCent.setText(
                BigDecimal.valueOf(( ( (double) one/two) *100)).setScale(1, RoundingMode.HALF_DOWN).doubleValue()
                +"");

    }
}
