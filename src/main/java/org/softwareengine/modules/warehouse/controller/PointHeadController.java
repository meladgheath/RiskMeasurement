package org.softwareengine.modules.warehouse.controller;

import org.softwareengine.modules.warehouse.view.PointHead;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PointHeadController extends PointHead {


    public PointHeadController() {

        save.setOnAction(event -> saveEvent());
        t1_1.setOnKeyPressed(event -> textEvent());
        t1_2.setOnKeyPressed(event -> textEvent());
    }

    public void saveEvent() {
      Long one   = Long.parseLong(t1.getText()) ;
      Long two   = Long.parseLong(t2.getText()) ;
      Long three = Long.parseLong(t3.getText()) ;
      Long four  = Long.parseLong(t4.getText()) ;
      Long five = Long.parseLong(t5.getText())  ;

      double per = ((double) one / (two+three+four+five)) * 100 ;
      per = BigDecimal.valueOf(per).setScale(1, RoundingMode.CEILING.ordinal()).doubleValue() ;

      totalPerCent.setText(per+" % ");
    }
    public void textEvent () {
        Long one = (t1_1.getText().equals("")) ? 0 : Long.parseLong(t1_1.getText()) ;
        Long two = (t1_2.getText().equals("")) ? 0 : Long.parseLong(t1_2.getText()) ;

        t1.setText((one+two)+"");
    }
}

//1735126059

//        8354305962
