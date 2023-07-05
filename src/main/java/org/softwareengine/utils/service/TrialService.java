package org.softwareengine.utils.service;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.softwareengine.utils.ui.FXInfoDialog;

import java.time.LocalDateTime;

public class TrialService {
    public static void start(Stage stage) {
        new Thread(() -> {
            while (true) {
                if (checkExpire()) {
                    Platform.runLater(() -> {
                        FXInfoDialog infoDialog = new FXInfoDialog();
                        infoDialog.setMessage("""
                                انتهت الفترة التجريبية لاستخدام النظام
                                للدعم الفني أرجو الاتصال بالأرقام التالية
                                                                        
                                The trial period for using the system has expired
                                For technical support, please call the following numbers
                                                                        
                                +218-92-374-1683
                                +218-94-438-6947
                                                                        
                                """);
                        infoDialog.okButton.setOnAction(event -> System.exit(1));
                        infoDialog.show();
                        stage.close();
                    });
                    break;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.exit(1);
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private static boolean checkExpire() {
        if (StorageService.getProperty("date") == null)
            System.exit(1);
        return LocalDateTime.parse(StorageService.getProperty("date")).isBefore(LocalDateTime.now());
    }
}