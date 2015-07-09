 @Test
    public void ${methodName}() {
        // System mode.
        DeviceInitParam paramOfSystem = new DeviceInitParam();
        paramOfSystem.setVID("${vid}");
        paramOfSystem.setRoProductModel("${roProductModel}");
        paramOfSystem.setRoProductBrand("${roProductBrand}");
        paramOfSystem.normalize();
        Assert.assertEquals("${productId}", DeviceStore.findProductId(paramOfSystem));

        // Recovery mode.
        DeviceInitParam paramOfRecovery = new DeviceInitParam();
        paramOfRecovery.setVID("18D1");
        paramOfRecovery.setRoProductModel("${roProductModel}");
        paramOfRecovery.setRoProductBrand("${roProductBrand}");
        paramOfRecovery.normalize();
        Assert.assertEquals("${productId}", DeviceStore.findProductId(paramOfRecovery));
    }