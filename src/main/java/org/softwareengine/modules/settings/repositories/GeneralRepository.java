package org.softwareengine.modules.settings.repositories;

import org.softwareengine.modules.settings.model.General;
import org.softwareengine.utils.service.StorageService;

import java.sql.SQLException;

public class GeneralRepository {
    public void update(General general, String value) throws SQLException {
        StorageService.setProperty(general.getValue(), value, null);
    }

    public String get(General general) throws SQLException {
        return StorageService.getProperty(general.getValue());
    }
}
