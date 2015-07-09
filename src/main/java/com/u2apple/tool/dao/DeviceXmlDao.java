package com.u2apple.tool.dao;

import com.u2apple.tool.constant.Configuration;
import com.u2apple.tool.model.Device;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.u2apple.tool.model.Model;
import com.u2apple.tool.model.ProductId;
import com.u2apple.tool.util.StandaloneWriter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceXmlDao {

    private String devicesFile;

    private String vidFileFormat = Configuration.getProperty("vidFileFormat");

    public String getDevicesFile() {
        return devicesFile;
    }

    public void setDevicesFile(String devicesFile) {
        this.devicesFile = devicesFile;
    }

    public DeviceXmlDao(String devicesFile) {
        this.devicesFile = devicesFile;
    }

    private String getVidFileByVid(String vid) {
        return String.format(vidFileFormat, vid);
    }

    @SuppressWarnings("unchecked")
    public void addDevice(Device device) throws DocumentException, IOException {
        if (StringUtils.isNotBlank(getDevicesFile())) {
            File file = new File(getDevicesFile());
            if (file.exists() && file.canWrite()) {
                Document document = parse(file);
                // Insert a new device element.
                Element deviceElement = createDeviceElement(device);
                Node devicesNode = document.selectSingleNode("//Devices");
                Element devicesElement = (Element) devicesNode;
                devicesElement.elements().add(deviceElement);
                // Sort elements.
                List<Node> deviceNodes = document.selectNodes("//Device", "ProductId");
                // Remove old elements, and then add the ordered elements.
                devicesElement.clearContent();
                for (Node node : deviceNodes) {
                    devicesElement.add(node);
                }
                // Out put updated document.
                output(document, getDevicesFile());
            } else {
                // logger.info("xml file is not exit or can't write.");
            }
        } else {
            // logger.info("xmlFile is blank.");
        }
    }

    /**
     * Add an empy VID node.
     *
     * @param document
     * @param vid
     * @throws org.dom4j.DocumentException
     * @throws java.io.IOException
     */
    private Document newVidDocument(String vid) throws DocumentException, IOException {
        Document document = DocumentHelper.createDocument();
        Element vidElement = createVidElement(vid);
        document.add(vidElement);
        return document;
    }

    public int getDeviceCount() throws DocumentException {
        int count = 0;
        if (StringUtils.isNotBlank(getDevicesFile())) {
            File file = new File(getDevicesFile());
            if (file.exists() && file.canWrite()) {
                Document document = parse(file);
                // Sort elements.
                List<Node> deviceNodes = document.selectNodes("//Device");
                if (deviceNodes != null) {
                    count = deviceNodes.size();
                }
            } else {
                // logger.info("xml file is not exit or can't write.");
            }
        } else {
            // logger.info("xmlFile is blank.");
        }
        return count;
    }

    @Deprecated
    public boolean isTextExist(String vid, String text) throws DocumentException {
        String vidFile = getVidFileByVid(vid);
        File file = new File(vidFile);
        boolean found=false;
        if (file.exists()) {
            Document document = parse(file);
            String xPath = String.format("//VID[@Value='%s']", vid);
            Node vidNode = document.selectSingleNode(xPath);

            if (vidNode != null) {
                String xml = vidNode.asXML();
                StringBuilder patternBuilder = new StringBuilder();
                text = text.replaceAll("\\(", "\\\\\\(").replaceAll("\\)", "\\\\\\)");
                patternBuilder.append("<Value>").append(text).append("</Value>");
                Pattern pattern = Pattern.compile(patternBuilder.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
                Matcher matcher = pattern.matcher(xml);
                found = matcher.find();
            } 
        } 
        return found;
    }

    @SuppressWarnings("unchecked")
    public void addModel(String[] vids, Model model) throws DocumentException, IOException {
        for (String vid : vids) {
            String vidFile = getVidFileByVid(vid);
            File file = new File(vidFile);
            Document document;
            if (file.exists()) {
                document = parse(file);

            } else {
                document = newVidDocument(vid);
            }
            addModel(document, vid, model);
            output(document, vidFile);
        }
    }

    private void addModel(Document document, String vid, Model model) throws DocumentException, IOException {
        // Sort model value nodes.
        sortValues(vid, document);

        String xPath = String.format("//VID[@Value='%s']/Modals", vid);
        Node modelsNode = document.selectSingleNode(xPath);
        // Insert a new model element.
        Element modelsElement = (Element) modelsNode;
        //TODO support multi values.
        Element existingModelElement = getExistModel(modelsElement, model.getValues().get(0));
        if (model.getValues().size() == 1 && existingModelElement != null) {
            // Update model, add product id node to existing model node.
            List<Element> productIdElements = createProductIdElement(model);
            for (Element productIdElement : productIdElements) {
                existingModelElement.add(productIdElement);
            }
        } else {
            // Insert a new device element.  
            Element modelElement = createModelElement(model);
            modelsElement.elements().add(modelElement);
            // Sort elements.
            sortModels(document, vid);
        }
    }

    private Element getExistModel(Element modelsElement, String model) {
        List<Element> modelElements = modelsElement.elements();
        for (Element modelElement : modelElements) {
            List<Node> valueNodes = modelElement.selectNodes("Value");
            for (Node valueNode : valueNodes) {
                if (valueNode.getText().equalsIgnoreCase(model)) {
                    return modelElement;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void sortModels(String vid) throws DocumentException, IOException {
        String vidFile = getVidFileByVid(vid);
        File file = new File(vidFile);
        if (file.exists()) {
            Document document = parse(file);
            sortModels(document, vid);
            output(document, vidFile);
        }
    }

    private void sortModels(Document document, String vid) throws DocumentException, IOException {
        String xPath = String.format("//VID[@Value='%s']/Modals", vid);
        Node modelsNode = document.selectSingleNode(xPath);
        // Insert a new model element.
        Element modelsElement = (Element) modelsNode;
        // Sort elements.
        String xPathOfmodels = String.format("//VID[@Value='%s']/Modals/Modal", vid);
        //TODO 转换为LinkedList.
        List<Node> modelNodes = document.selectNodes(xPathOfmodels, "lower-case(Value)");
        //Sort values
        sortValues(modelNodes);
        // 让model有包含关系的element倒序
        List<Node> reversedModelNodes = reverseContainedElementForMultiValue(modelNodes);
        modelsElement.clearContent();
        for (Node node : reversedModelNodes) {
            modelsElement.add(node);
        }
    }

    /**
     *
     *
     * @param vid
     * @throws DocumentException
     * @throws IOException
     */
    public void sortValues(String vid) throws DocumentException, IOException {
        String vidFile = getVidFileByVid(vid);
        File file = new File(vidFile);
        if (file.exists()) {
            Document document = parse(file);
            String xPathOfmodels = String.format("//VID[@Value='%s']/Modals/Modal", vid);
            @SuppressWarnings("unchecked")
            List<Node> modelNodes = document.selectNodes(xPathOfmodels);
            for (Node modelNode : modelNodes) {
                @SuppressWarnings("unchecked")
                //Order by text.
                List<Node> valueNodes = modelNode.selectNodes("Value", ".");
                if (valueNodes.size() > 1) {
                    Element modelElement = (Element) modelNode;
                    modelElement.clearContent();
                    for (Node valueNode : valueNodes) {
                        modelElement.add(valueNode);
                    }
                }
            }
            // Out put updated document.
            output(document, vidFile);
        }
    }

    /**
     * Sort model values.
     *
     *
     * @param vid
     * @param document
     * @throws DocumentException
     * @throws IOException
     */
    private void sortValues(String vid, Document document) throws DocumentException, IOException {
        String xPathOfmodels = String.format("//VID[@Value='%s']/Modals/Modal", vid);
        @SuppressWarnings("unchecked")
        List<Node> modelNodes = document.selectNodes(xPathOfmodels);
        //对一个model节点下的value节点按字典顺序排序.
        for (Node modelNode : modelNodes) {
            @SuppressWarnings("unchecked")
            List<Node> valueNodes = modelNode.selectNodes("Value", ".");
            valueNodes = new LinkedList(valueNodes);
            List<Node> productIdNodes = modelNode.selectNodes("ProductId");
            //In case of multi model values.
            if (valueNodes.size() > 1) {
                Element modelElement = (Element) modelNode;
                //调整有包含关系的value值.
                List<Node> newValueNodes = reverseContainedValue(valueNodes);
                //Clear content.
                modelElement.clearContent();
                //Readd content.         
                for (Node valueNode : newValueNodes) {
                    modelElement.add(valueNode);
                }
                //加上product id node.
                for (Node productIdNode : productIdNodes) {
                    modelElement.add(productIdNode);
                }
            }
        }
    }

    /**
     * Sort model values.
     *
     *
     * @param vid
     * @param document
     * @throws DocumentException
     * @throws IOException
     */
    private void sortValues(List<Node> modelNodes) throws DocumentException, IOException {
        //对一个model节点下的value节点按字典顺序排序.
        for (Node modelNode : modelNodes) {
            @SuppressWarnings("unchecked")
            List<Node> valueNodes = modelNode.selectNodes("Value", ".");
            if (valueNodes.size() > 1) {
                valueNodes = new LinkedList(valueNodes);
                List<Node> productIdNodes = modelNode.selectNodes("ProductId");
                //In case of multi model values.
                if (valueNodes.size() > 1) {
                    Element modelElement = (Element) modelNode;
                    //调整有包含关系的value值.
                    List<Node> newValueNodes = reverseContainedValue(valueNodes);
                    //Clear content.
                    modelElement.clearContent();
                    //Readd content.         
                    for (Node valueNode : newValueNodes) {
                        modelElement.add(valueNode);
                    }
                    //加上product id node.
                    for (Node productIdNode : productIdNodes) {
                        modelElement.add(productIdNode);
                    }
                }
            }
        }
    }

    /**
     *
     *
     * @param valueNodes
     * @return
     */
    private List<Node> reverseContainedValue(List<Node> valueNodes) {
        Element currentValueNode;
        Element nextValueNode;
        int count = 0;
        for (int i = 0; i < valueNodes.size() - 1;) {
            currentValueNode = (Element) valueNodes.get(i);
            for (int j = i; j < valueNodes.size(); j++) {
                nextValueNode = (Element) valueNodes.get(j);
                if (containsValue(currentValueNode, nextValueNode)) {
                    valueNodes.add(i, valueNodes.remove(j));
                    count++;
                }
            }
            //当有多个Node被移动到当前节点前，有可能这个几个被移动的Node存在包含关系。
            if (count <= 1) {
                i++;
            }
            //Reset count;
            count = 0;
        }
        return valueNodes;
    }

    private boolean containsValue(Element currentValueNode, Element nextValueNode) {
        String currentValue = currentValueNode.getText().trim();
        String nextValue = nextValueNode.getText().trim();
        return StringUtils.containsIgnoreCase(nextValue, currentValue);
    }

    private List<Node> reverseContainedElementForMultiValue(List<Node> modelNodes) {
        int count = 0;
        List<List<String>> valueList = initValueList(modelNodes);
        //性能优化
        // modelNodes = new LinkedList(modelNodes);
        //初始化value list.
        for (int i = 0; i < modelNodes.size() - 1;) {
            for (int j = i + 1; j < modelNodes.size(); j++) {
                //跟所有的后面的Model节点比较，如果存在contains关系，则将包含model节点移到被包含节点的前面。
                if (contains(valueList, i, j)) {
                    //将Element J移动到当前节点的前面.
                    modelNodes.add(i, modelNodes.remove(j));
                    //valueList的下标与modelNodes保持一致。
                    valueList.add(i, valueList.remove(j));
                    count++;
                }
            }
            //当有多个Node被移动到当前节点前，有可能这个几个被移动的Node存在包含关系。
            if (count <= 1) {
                i++;
            }
            //Reset count;
            count = 0;
        }
        return modelNodes;
    }

    /**
     *
     * @param modelNodes
     * @return
     * [[modelValue1,modelValue2]:model1,[[modelValue1,modelValue2]:model2]]
     */
    private List<List<String>> initValueList(List<Node> modelNodes) {
        List<List<String>> valueList = new ArrayList<>();
        List<Node> valueNodes;
        List<String> values;
        for (Node node : modelNodes) {
            values = new ArrayList<>();
            valueNodes = ((Element) node).elements("Value");
            for (Node valueNode : valueNodes) {
                values.add(valueNode.getText().trim());
            }
            valueList.add(values);
        }
        return valueList;
    }

    public static boolean contains(Element curModelNode, Element otherModelNode) {
        //不能存在交叉覆盖的情况，如果存在，需要将多值model节点手动拆分.
        List<Node> currentValueNodes = curModelNode.elements("Value");
        List<Node> nextValueNodes = otherModelNode.elements("Value");
        String nextModelValue;
        String currentModelValue;
        for (Node nextValueNode : nextValueNodes) {
            nextModelValue = nextValueNode.getText().trim();
            for (Node currentValueNode : currentValueNodes) {
                currentModelValue = currentValueNode.getText().trim();
                if (StringUtils.containsIgnoreCase(nextModelValue, currentModelValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean contains(List<List<String>> valueList, int currentIndex, int nextIndex) {
        List<String> currentValues = valueList.get(currentIndex);
        List<String> nextValues = valueList.get(nextIndex);
        //不能存在交叉覆盖的情况，如果存在，需要将多值model节点手动拆分.
        for (String nextModelValue : nextValues) {
            for (String currentModelValue : currentValues) {
                if (StringUtils.containsIgnoreCase(nextModelValue, currentModelValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Element createModelElement(Model model) {
        Element modelElement = DocumentHelper.createElement("Modal");
        // Add value element.
        for (String value : model.getValues()) {
            Element valueElement = DocumentHelper.createElement("Value");
            valueElement.setText(value);
            modelElement.add(valueElement);
        }

        // Add Product Id element.
        for (ProductId productId : model.getProductIds()) {
            Element productIdElement = DocumentHelper.createElement("ProductId");
            productIdElement.setText(productId.getValue());
            // Add condition attribute if exist.
            if (productId.getConditions() != null && !productId.getConditions().isEmpty()) {
                productIdElement.setAttributeValue("c", parseCondition(productId.getConditions()));
            }
            modelElement.add(productIdElement);
        }
        return modelElement;
    }

    private List<Element> createProductIdElement(Model model) {
        List<Element> productIdElements = new ArrayList<>();
        // Add Product Id element.
        for (ProductId productId : model.getProductIds()) {
            Element productIdElement = DocumentHelper.createElement("ProductId");
            productIdElement.setText(productId.getValue());
            // Add condition attribute if exist.
            if (productId.getConditions() != null && !productId.getConditions().isEmpty()) {
                productIdElement.setAttributeValue("c", parseCondition(productId.getConditions()));
            }
            productIdElements.add(productIdElement);
        }
        return productIdElements;
    }

    private String parseCondition(Map<String, String> conditions) {
        StringBuilder conditionBuilder = new StringBuilder();
        int count = 0;
        for (Entry<String, String> condition : conditions.entrySet()) {
            conditionBuilder.append(condition.getKey());
            conditionBuilder.append("=");
            conditionBuilder.append(condition.getValue());
            if (count < conditions.entrySet().size() - 1) {
                conditionBuilder.append("&");
            }
            count++;
        }
        return conditionBuilder.toString();
    }

    private Element createDeviceElement(Device device) {
        Element deviceElement = DocumentHelper.createElement("Device");
        // Product ID.
        Element productIdElement = DocumentHelper.createElement("ProductId");
        productIdElement.setText(device.getProductId());
        deviceElement.add(productIdElement);
        // Manufactory.
        Element manufactoryElement = DocumentHelper.createElement("Manufactory");
        manufactoryElement.setText(StringUtils.defaultString(device.getManufactory()));
        deviceElement.add(manufactoryElement);
        // Brand.
        Element brandElement = DocumentHelper.createElement("Brand");
        brandElement.setText(device.getBrand());
        deviceElement.add(brandElement);
        // Product
        Element productElement = DocumentHelper.createElement("Product");
        productElement.setText(device.getProduct());
        deviceElement.add(productElement);
        // Alias
        Element aliasElement = DocumentHelper.createElement("Alias");
        aliasElement.setText(StringUtils.defaultString(device.getAlias()));
        deviceElement.add(aliasElement);
        // Support
//        Element supportElement = DocumentHelper.createElement("Support");
//        supportElement.setText(device.getSupport());
//        deviceElement.add(supportElement);
        // Type
        if (device.getType() != 0) {
            Element typeElement = DocumentHelper.createElement("Type");
            typeElement.setText(device.getType().toString());
            deviceElement.add(typeElement);
        }
        // SmallIconPath
        Element smallIconPathElement = DocumentHelper.createElement("SmallIconPath");
        smallIconPathElement.setText(StringUtils.defaultString(device.getSmallIconPath()));
        deviceElement.add(smallIconPathElement);
        // LargeIconPath
        Element largeIconPathElement = DocumentHelper.createElement("LargeIconPath");
        largeIconPathElement.setText(StringUtils.defaultString(device.getLargeIconPath()));
        deviceElement.add(largeIconPathElement);
        return deviceElement;
    }

    private Element createVidElement(String vid) {
        Element vidElement = DocumentHelper.createElement("VID");
        vidElement.addAttribute("Value", vid);
        vidElement.addElement("PIDs");
        vidElement.addElement("Names");
        vidElement.addElement("Modals");
        return vidElement;
    }

    private void output(Document doc, String file) throws IOException {
        // There is bug in format, it may output duplicated elements.
        // OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new StandaloneWriter(new FileOutputStream(file));
        writer.write(doc);
        writer.close();
    }

    private Document parse(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }

}
