package org.softwareengine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.softwareengine.core.controller.HomeController;
import org.softwareengine.core.view.SplashView;

import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.service.SystemService;
import org.softwareengine.utils.service.TrialService;

import java.math.BigDecimal;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        super.init();
        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        LocaleService.setResources(ResourceBundle.getBundle("locales/lang", locale));
    }

    @Override
    public void start(Stage primaryStage) {
//        Preferences.systemRoot().put("test1", "hello");
//        SystemService.upgradeDatabase();
        Long a2 = 8354305962L;

        Long a1 = 1735126059L ;

        double me = ((double) a1 / a2 );


        System.out.println(me);
        System.out.println(Math.floor(me*100)/100);
        System.out.println(Math.round(me * 100) / 100);

//        BigDecimal b = new BigDecimal(me).setScale(2);

        System.out.println(me*100);
        me = BigDecimal.valueOf(me*100).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();

        System.out.println(me);
        new HomeController(primaryStage);
        TrialService.start(primaryStage);
        SplashView.close();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
    }

    public static void main(String[] args) throws SQLException {
        Platform.runLater(SplashView::show);
        launch(args);
    }
}