package com.u2apple.tool.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.u2apple.tool.model.KnockOffConfig;
import com.u2apple.tool.model.Partition;
import com.u2apple.tool.model.Partitions;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class FreemarkerUtils {
    public static void main(String[] args) throws Exception {

        /* ----------------------------------------------------------------------- */
        /* You should do this ONLY ONCE in the whole application life-cycle: */

        Configuration cfg = getConfiguration();

        /* ----------------------------------------------------------------------- */
        /* You usually do these for many times in the application life-cycle: */

        /* Create a data-model */
        KnockOffConfig config = new KnockOffConfig();
        Partitions partitions = new Partitions();
        Partition systemPartition = new Partition("system", 1055809536L, "mmcblk0p5");
        Partition cachePartition = new Partition("cache", 130039808L, "mmcblk0p7");
        Partition dataPartition = new Partition("data", 2310324224L, "mmcblk0p8");
        partitions.setSystemPartition(systemPartition);
        partitions.setCachePartition(cachePartition);
        partitions.setDataPartition(dataPartition);
        config.setPartitions(partitions);
        config.setResolution("720x1280");
        config.setProductId("huawei-123");
        config.setKnockOffProductId("huaweicc-123");
        /* Get the template */
        // Template temp = cfg.getTemplate("KnockOffConfig.xml");
        //
        // /* Merge data-model with template */
        // Writer out = new OutputStreamWriter(System.out);
        // temp.process(config, out);

        String result = generate(config, "KnockOffConfig.xml");
        System.out.println(result);
    }

    public static String generate(Object model, String templateFile) {
        Configuration cfg = getConfiguration();
        Writer out = new StringWriter();
        try {
            Template temp = cfg.getTemplate(templateFile);
            temp.process(model, out);
            out.flush();
            out.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    private static Configuration getConfiguration() {
        /* ----------------------------------------------------------------------- */
        /* You should do this ONLY ONCE in the whole application life-cycle: */

        /* Create and adjust the configuration */
        Configuration cfg = new Configuration();

        // cfg.setDirectoryForTemplateLoading(new File("com/u2apple/rt/templates"));
        cfg.setClassForTemplateLoading(FreemarkerUtils.class, "/com/u2apple/rt/templates");
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        return cfg;
    }
}
