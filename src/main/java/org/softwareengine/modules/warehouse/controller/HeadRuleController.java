package org.softwareengine.modules.warehouse.controller;

import org.softwareengine.modules.warehouse.view.HeadRuleView;

public class HeadRuleController extends HeadRuleView {


    public HeadRuleController() {
        t1.setOnKeyTyped(event -> sum());
        t2.setOnKeyTyped(event -> sum());
        t3.setOnKeyTyped(event -> sum());
        t4.setOnKeyTyped(event -> sum());
        t5.setOnKeyTyped(event -> sum());

        tf1.setOnKeyTyped(event -> div());
        tf2.setOnKeyTyped(event -> div());

        tf3.setOnKeyTyped(event -> plus() );


    }

    public void sum () {
        Long one   = (t1.getText().equals("")) ? 0 : Long.parseLong(t1.getText()) ;
        Long two   = (t2.getText().equals("")) ? 0 : Long.parseLong(t2.getText()) ;
        Long three = (t3.getText().equals("")) ? 0 : Long.parseLong(t3.getText()) ;
        Long four  = (t4.getText().equals("")) ? 0 : Long.parseLong(t4.getText()) ;
        Long five  = (t5.getText().equals("")) ? 0 : Long.parseLong(t5.getText()) ;

        Long result =  one + two + three + four + five ;

        total.setText(result+"");
        total2.setText(result+"");
    }

    public void div() {
        Long one = (total2.getText().equals("")) ? 0 : Long.parseLong(total2.getText()) ;
        Long two = (tf1.getText().equals("")) ? 0 : Long.parseLong(tf1.getText()) ;
        Long three = (tf2.getText().equals("")) ? 0 : Long.parseLong(tf2.getText()) ;

        Long result = one - two - three ;

        totalMoney.setText(result+"");
        totalMoney2.setText(result+"");
    }

    public void plus() {
        Long one = (totalMoney2.getText().equals("")) ? 0 : Long.parseLong(totalMoney2.getText()) ;
        Long two = (tf3.getText().equals("")) ? 0 : Long.parseLong(tf3.getText()) ;

        Long result = one + two ;

        headRuleTotal.setText(result+"");
    }

}
