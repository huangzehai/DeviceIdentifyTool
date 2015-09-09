/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.u2apple.tool.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.shuame.wandoujia.bean.Device;
import com.shuame.wandoujia.bean.Modal;
import com.shuame.wandoujia.bean.ProductId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Adam
 */
public class DeviceLogDao {

    public void addDeviceLog(Device device) {
        getDeviceLog().insertOne(toDocument(device));
    }

    private MongoCollection<Document> getDeviceLog() {
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase db = mongoClient.getDatabase("recognition");
        return db.getCollection("log");
    }

    public void updateDeviceLog(String productId, String[] vids, Modal model) {
        MongoCollection<Document> deviceLog = getDeviceLog();
        Document filter = new Document("product-id", productId);
        Document doc = getDeviceLog().find(filter).first();
        if (doc != null && model != null) {
            List<Document> models = doc.get("models", List.class);
            Document m = convertModel(vids, model);
            if (models != null) {
                models.add(m);
            } else {
                models = new ArrayList<>();
                models.add(m);
            }
            doc.append("models", models);
            deviceLog.replaceOne(filter, doc);
        } else {
            Device device = new Device();
            device.setProductId(productId);
            doc = toDocument(device);
            List<Document> models = new ArrayList<>();
            models.add(convertModel(vids, model));
            doc.append("models", models);
            deviceLog.insertOne(doc);
        }
    }

    private Document toDocument(Device device) {
        Document doc = new Document();
        doc.append("product-id", device.getProductId());
        doc.append("brand", device.getBrand());
        doc.append("product", device.getProduct());
        doc.append("alias", device.getAlias());
        doc.append("type", device.getType());
        doc.append("created", new Date());
        return doc;
    }

    private Document convertModel(String[] vids, Modal model) {
        Document doc = new Document();
        doc.append("vids", Arrays.asList(vids));
        doc.append("values", model.getValues());
        doc.append("productIds", convertProductIds(model.getProductId()));
        doc.append("created", new Date());
        return doc;
    }

    private List<Document> convertProductIds(List<ProductId> productIds) {
        List<Document> documents = new LinkedList<>();
        productIds.stream().forEach((productId) -> {
            documents.add(convertProductId(productId));
        });
        return documents;
    }

    private Document convertProductId(ProductId productId) {
        Document doc = new Document();
        doc.append("values", productId.getValue());
        if (productId.getCondition() != null && !productId.getCondition().isEmpty()) {
            doc.append("conditions", productId.getCondition());
        }
        return doc;
    }

}
