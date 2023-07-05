package org.softwareengine.utils.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {

    public static Connection connection;

    public static void openConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/casher");
//        try {
//            Class.forName("org.sqlite.JDBC");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        SQLiteConfig sqLiteConfig = new SQLiteConfig();
//        sqLiteConfig.enforceForeignKeys(true);
//        sqLiteConfig.setEncoding(SQLiteConfig.Encoding.UTF8);

        /*
         * For Development
         */
//        connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\casher-engine.db") ;
//        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/casher-engine.db");
//        try {
//            connection = DriverManager.getConnection("jdbc:sqlite:" + Objects.requireNonNull(
//                    DatabaseService.class.getResource("/database/casher-engine.db")).toURI(), sqLiteConfig.toProperties()
//            );
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }

        /*
         * For Production
         */
//        if (Files.notExists(Path.of(Paths.APP_DATA.getPath()))) {
//            try {
//                Files.createDirectories(Path.of(Paths.APP_DATA.getPath()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        if (!Files.isDirectory(Path.of(Paths.DATABASE.getPath())) && Files.notExists(Path.of(Paths.DATABASE.getPath()))) {
//            try {
//                Files.copy(Path.of(Objects.requireNonNull(DatabaseService.class.getResource("/database/casher-engine.db")).toURI()), Path.of(Paths.DATABASE.getPath()));
//            } catch (IOException | URISyntaxException e) {
//                throw new RuntimeException(e);
//            }
//        }
////        connection = DriverManager.getConnection("jdbc:sqlite:" + Path.of(Paths.DATABASE.getPath()), sqLiteConfig.toProperties());
//        connection = DriverManager.getConnection("jdbc:sqlite:/Users/adelbograyn/Library/Application Support/se/casher/casher-engine.db", sqLiteConfig.toProperties());
    }

    public static void closeConnection() throws SQLException {
        if (!connection.isClosed()) connection.close();
    }
}