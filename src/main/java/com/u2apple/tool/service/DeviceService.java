package com.u2apple.tool.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


import com.jcraft.jsch.JSchException;
import com.u2apple.tool.persistence.Pool;
import com.u2apple.tool.dao.AttDeviceDao;
import com.u2apple.tool.model.AndroidDeviceRanking;
import com.u2apple.tool.util.AndroidDeviceUtils;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceService {

    private static final String PRODUCT_ID_PATTERN = "^\\w+-\\w+(-a\\d{2})?$";
    private static final String PRODUCT_ID_PATTERN_OLD = "^\\w+(-\\w+){1,2}$";
    private static final String OUTPUT_FILE = "C:\\Users\\Adam\\Documents\\result4.csv";

    public static void main(String[] args) {
        String file = "C:\\Users\\Adam\\Documents\\supported-device-list.csv";
        DeviceService deviceService = new DeviceService();
        try {
            deviceService.generate(file);
        } catch (ClassNotFoundException | JSchException | SQLException | IOException | PropertyVetoException ex) {
            Logger.getLogger(DeviceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isProductId(String productId) {
        return Pattern.matches(PRODUCT_ID_PATTERN_OLD, productId);
    }

    /**
     * Parse the input the product list CSV file to get the pure product id
     * list.
     *
     * @param csv
     * @return
     * @throws IOException
     */
    private List<String> getProductIdsFromCSV(String fileString) throws IOException {
        List<String> productIds = new ArrayList<>();
        if (fileString != null) {
            File file = new File(fileString);
            if (file.exists()) {
                BufferedReader in = new BufferedReader(new FileReader(file));
                String line;
                while ((line = in.readLine()) != null) {
                    String[] items = line.split(",");
                    if (items.length > 0) {
                        String productId = items[0].trim();
                        if (isProductId(productId)) {
                            productIds.add(productId);
                        }
                    }
                }
            }
        }
        return productIds;
    }

    public void generate(String file) throws ClassNotFoundException, JSchException, SQLException, IOException, PropertyVetoException {
        List<String> productIds = getProductIdsFromCSV(file);
        // List<String> productIds = new ArrayList<>();
        // productIds.add("bbk-vivos7t");
        if (!productIds.isEmpty()) {
            AttDeviceDao deviceDao = new AttDeviceDao();
            Map<String, List<AndroidDeviceRanking>> devices = new HashMap<>();
            for (String productId : productIds) {
                String brand = AndroidDeviceUtils.getBrandByProductId(productId);
                // List<AndroidDeviceRanking> deviceList = brandFilter(deviceDao.queryByProductId(productId), brand);
                devices.put(productId, deviceDao.queryByProductId(productId, brand));
            }
            // Close SSH connection.
            Pool.close();
            output(devices);
        }
    }

//    private String getBrandByProductId(String productId) {
//        String brand = null;
//        if (StringUtils.isNotBlank(productId)) {
//            brand = StringUtils.substringBefore(productId, "-");
//        }
//        return brand;
//    }
//    private List<AndroidDeviceRanking> brandFilter(List<AndroidDeviceRanking> devices, String brand) {
//        List<AndroidDeviceRanking> filteredDevices = new ArrayList<AndroidDeviceRanking>();
//        for (AndroidDeviceRanking device : devices) {
//            if (StringUtils.endsWithIgnoreCase(brand, device.getRoProductBrand())) {
//                filteredDevices.add(device);
//            }
//        }
//        return filteredDevices;
//    }
    /**
     * Output an CSV file.
     *
     * @param devices
     */
    private void output(Map<String, List<AndroidDeviceRanking>> devices) {
        StringBuilder csv = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        csv.append("product_id,ro.product.brand,ro.product.model,ro.product.device,count").append(lineSeparator);
        for (String productId : devices.keySet()) {
            for (AndroidDeviceRanking ranking : devices.get(productId)) {
                csv.append(ranking.getProductId());
                csv.append(",");
                csv.append(ranking.getRoProductBrand());
                csv.append(",");
                csv.append(ranking.getRoProductModel());
                csv.append(",");
                csv.append(ranking.getRoProductDevice());
                csv.append(",");
                csv.append(ranking.getCount());
                csv.append(lineSeparator);
            }
        }
        try {
            output(csv.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void output(String text) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE)));
        out.print(text);
        out.flush();
        out.close();
    }

}
