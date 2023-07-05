module casher {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    requires java.prefs;

    requires jasperreports;
    requires org.postgresql.jdbc;

    exports org.softwareengine;
    exports org.softwareengine.config;
    exports org.softwareengine.utils.service;
    exports org.softwareengine.utils.ui;

    exports org.softwareengine.core.model;
    exports org.softwareengine.core.view;
    exports org.softwareengine.core.controller;

    exports org.softwareengine.modules.warehouse.model;
    exports org.softwareengine.modules.warehouse.view;
    exports org.softwareengine.modules.warehouse.controller;
    exports org.softwareengine.modules.warehouse.repositories;

    exports org.softwareengine.modules.sales.model;
    exports org.softwareengine.modules.sales.view;
    exports org.softwareengine.modules.sales.controller;
    exports org.softwareengine.modules.sales.repositories;

    exports org.softwareengine.modules.purchases.model;
    exports org.softwareengine.modules.purchases.view;
    exports org.softwareengine.modules.purchases.controller;
    exports org.softwareengine.modules.purchases.repositories;

    exports org.softwareengine.modules.finance.model;
    exports org.softwareengine.modules.finance.view;
    exports org.softwareengine.modules.finance.controller;
    exports org.softwareengine.modules.finance.repositories;

    exports org.softwareengine.modules.settings.model;
    exports org.softwareengine.modules.settings.view;
    exports org.softwareengine.modules.settings.controller;
    exports org.softwareengine.modules.settings.repositories;
    exports org.softwareengine.modules.statistics.repositories;
    exports org.softwareengine.modules.statistics.model;
}