package com.u2apple.tool.core;

import org.apache.commons.lang3.StringUtils;

import com.u2apple.tool.model.KnockOffConfig;
import com.u2apple.tool.model.Partitions;
import com.u2apple.tool.util.FreemarkerUtils;
import com.u2apple.tool.util.PartitionUtils;

public class KnockOffTool {

    public static String getKnockOffConfig(String productId, String resolution, String partitionString) {
        Partitions partitions = PartitionUtils.parsePartition(partitionString);
        KnockOffConfig config = new KnockOffConfig();
        config.setProductId(productId);
        config.setPartitions(partitions);
        config.setResolution(resolution);
        config.setKnockOffProductId(getKnockOffProductId(productId));
        return FreemarkerUtils.generate(config, "KnockOffConfig.xml");
    }

    private static String getKnockOffProductId(String productId) {
        String knockOffProductId;
        if (StringUtils.isNotBlank(productId)) {
            knockOffProductId = productId.replace("-", "cc-");
        } else {
            knockOffProductId = null;
        }
        return knockOffProductId;
    }

    
}
