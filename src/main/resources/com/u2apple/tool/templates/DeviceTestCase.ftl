 @Test
    public void ${methodName}() {
        //When VID is ${vid}.
       <#assign paramName ="paramOfVid${vid}">
        DeviceInitParam ${paramName} = new DeviceInitParam();
        ${paramName} .setVID("${vid}");
        ${paramName} .setRoProductModel("${roProductModel}");
        ${paramName} .normalize();
        Assert.assertEquals("${productId}", DeviceStore.findProductId(${paramName} ));

        //When VID is 18D1.
        DeviceInitParam paramOfRecovery = new DeviceInitParam();
        paramOfRecovery.setVID("18D1");
        paramOfRecovery.setRoProductModel("${roProductModel}");
        paramOfRecovery.normalize();
        Assert.assertEquals("${productId}", DeviceStore.findProductId(paramOfRecovery));
    }