/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.util;

import com.u2apple.tool.model.Partition;
import com.u2apple.tool.model.Partitions;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Adam
 */
public final class PartitionUtils {

    private PartitionUtils() {

    }

    public static Partitions parsePartition(String partitionString) {
        Partitions partitions = new Partitions();
        if (StringUtils.isNotBlank(partitionString)) {
            String[] partitionArray = partitionString.split(";");
            for (String partitionStr : partitionArray) {
                String[] elements = partitionStr.split("\\|");
                String name = StringUtils.trim(elements[0]);
                Long size = Long.parseLong(StringUtils.trim(elements[1]));
                String path = StringUtils.trim(elements[2]);
                Partition partition = new Partition(name, size, path);
                if ("system".equalsIgnoreCase(name)) {
                    partitions.setSystemPartition(partition);
                } else if ("cache".equalsIgnoreCase(name)) {
                    partitions.setCachePartition(partition);
                } else if ("data".equalsIgnoreCase(name)) {
                    partitions.setDataPartition(partition);
                }
            }
        }
        return partitions;
    }
}
