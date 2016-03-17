/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.service;

import com.u2apple.tool.service.IdentifyAnalyticsService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adam
 */
public class IdentifyAnalyticsServiceTest {
    
    public IdentifyAnalyticsServiceTest() {
    }

    @Test
    public void testAnalytics() throws Exception {
        IdentifyAnalyticsService service=new IdentifyAnalyticsService();
        service.listErrorIdentifiedDevicesForShuamePC();
    }
    
}
