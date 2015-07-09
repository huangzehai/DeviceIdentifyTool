/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.cache;

import com.u2apple.tool.cache.DeviceCache;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.xml.bind.JAXBException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adam
 */
public class DeviceCacheTest {

    public DeviceCacheTest() {
    }

    @Test
    public void testGetProductIds() {
        Set<String> productIds = DeviceCache.getProductIds();
        for (String productId : productIds) {
            System.out.println(productId);
        }
    }

    @Test
    public void testGetModels() {
        Map<String, Set<String>> models = DeviceCache.getModels();
        for (Entry<String, Set<String>> entry : models.entrySet()) {
            System.out.println(entry);
        }
    }

}
