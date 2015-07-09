 @Test
    public void ${methodName}() {
        // System mode.
        DeviceInitParam paramOfSystem = new DeviceInitParam();
        paramOfSystem.setVID("${vid}");
        paramOfSystem.setRoProductModel("${roProductModel}");
        paramOfSystem.setResolution("${resolution}");
        List<DevicePartition> partitions = new ArrayList<DevicePartition>();
        DevicePartition dataPartition = new DevicePartition();
        dataPartition.setName("data");
        dataPartition.setPath("${partitions.dataPartition.path}");
        dataPartition.setSize(${partitions.dataPartition.size?c});
        partitions.add(dataPartition);

        DevicePartition cachePartition = new DevicePartition();
        cachePartition.setName("cache");
        cachePartition.setPath("${partitions.cachePartition.path}");
        cachePartition.setSize(${partitions.cachePartition.size?c});
        partitions.add(cachePartition);

        DevicePartition systemPartition = new DevicePartition();
        systemPartition.setName("system");
        systemPartition.setPath("${partitions.systemPartition.path}");
        systemPartition.setSize(${partitions.systemPartition.size?c});
        partitions.add(systemPartition);
        paramOfSystem.setPartitionsList(partitions);
        paramOfSystem.setType("m");
        paramOfSystem.normalize();
        Assert.assertEquals("${productId}", DeviceStore.findProductId(paramOfSystem));

        // Recovery mode.
        DeviceInitParam paramOfRecovery = new DeviceInitParam();
        paramOfRecovery.setVID("18D1");
        paramOfRecovery.setRoProductModel("${roProductModel}");
        paramOfRecovery.setResolution("${resolution}");
        paramOfRecovery.setPartitionsList(partitions);
        paramOfRecovery.setType("m");
        paramOfRecovery.normalize();
        Assert.assertEquals("${productId}", DeviceStore.findProductId(paramOfRecovery));
    }