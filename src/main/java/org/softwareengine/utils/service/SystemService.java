package org.softwareengine.utils.service;

import javafx.application.Platform;
import org.softwareengine.utils.ui.FXInfoDialog;

import java.sql.SQLException;
import java.sql.Statement;

public class SystemService {
    private final static FXInfoDialog infoDialog;

    static {
        infoDialog = new FXInfoDialog();
        infoDialog.setWidth(400);
        infoDialog.setHeight(300);
        infoDialog.okButton.setOnAction(event -> infoDialog.close());
    }

    public static void upgradeSystem() {
        infoDialog.setMessage("""
                النظام محدث بالفعل
                شكرا لاستخدامك نظامنا (:
                                                                                        
                """);
        Platform.runLater(infoDialog::show);
    }

    public static void upgradeDatabase() {
        try {
            DatabaseService.openConnection();
            Statement statement = DatabaseService.connection.createStatement();
            statement.addBatch("""
                    CREATE TABLE IF NOT EXISTS customer (
                        uuid       varchar     PRIMARY KEY
                                            UNIQUE
                                            NOT NULL,
                        name       varchar     UNIQUE
                                            NOT NULL,
                        phone      varchar     UNIQUE,
                        created_at timestamp DEFAULT (current_timestamp),
                        updated_at timestamp DEFAULT (current_timestamp)
                    )
                    """);
            statement.executeBatch();

        } catch (SQLException e) {
            showError();
            throw new RuntimeException(e);
        }
    }

    private static void showSuccess() {
        infoDialog.setMessage("""
                تم تحديث النظام بنجاح (:
                قم باعادة تشغيل النظام
                                                                                        
                """);
        Platform.runLater(infoDialog::show);
    }

    private static void showError() {
        infoDialog.setMessage("""
                حدثت مشكلة أثناء تحديث النظام ):
                                                
                                                                                        
                """);
        Platform.runLater(infoDialog::show);
    }
}