package org.softwareengine.utils.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class DeviceIDService {

    private static String sn = null;

    public static String getSerialNumber() {
        if (sn != null) return sn;

        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            if (System.getProperty("os.name").toLowerCase().contains("mac"))
                process = runtime.exec(new String[]{"system_profiler SPHardwareDataType | awk '/UUID/ { print $3; }'"});
            if (System.getProperty("os.name").toLowerCase().contains("win"))
                process = runtime.exec(new String[]{"wmic", "csproduct", "get", "id"});
            if (System.getProperty("os.name").toLowerCase().contains("nux"))
                process = runtime.exec(new String[]{"cat /sys/class/dmi/id/product_uuid"});

            OutputStream os = process.getOutputStream();
            InputStream is = process.getInputStream();
            os.close();
            Scanner sc = new Scanner(is);
            while (sc.hasNext()) {
                String next = sc.next();
                if ("UUID".equals(next)) {
                    sn = sc.next().trim();
                    break;
                }
            }
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sn;
    }
}