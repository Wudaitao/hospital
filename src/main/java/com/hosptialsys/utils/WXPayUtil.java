package com.hosptialsys.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class WXPayUtil {
	

	/**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }

    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }
    
    /**
     * 生成签名 
     * @param params 发送的数据
     * @param key key为商户平台设置的密钥key
     * @return
     */
    public static String createSign(SortedMap<String, String> params, String key ) {
		StringBuilder sBuilder = new StringBuilder();
		Set<Map.Entry<String, String>> es = params.entrySet();
		Iterator<Map.Entry<String, String>> iterator = es.iterator();
		//生成stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
		while(iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if(v != null && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sBuilder.append(k+"="+v+"&");
			}
		}
		sBuilder.append("key=").append(key);
		String sign = CommonUtil.MD5(sBuilder.toString()).toUpperCase();
		return sign;
	}
    
    /**
     * 校验签名是否正确
     * @param params
     * @param key
     * @return
     */
    public static boolean isCorretSign(SortedMap<String, String> params, String key ) {
        /*StringBuilder sBuilder = new StringBuilder();
		Set<Map.Entry<String, String>> es = params.entrySet();
		Iterator<Map.Entry<String, String>> iterator = es.iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if(v != null && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sBuilder.append(k+"="+v+"&");
			}
		}
		sBuilder.append("key=").append(key);*/
		String sign = createSign(params, key);
		String weixinPaySign = params.get("sign").toUpperCase();
		return sign.equals(weixinPaySign);
	}
    
    /**
     * 将普通的map转成有序的map
     */
    public static SortedMap<String, String> getSortMap(Map<String, String> map) {
		SortedMap<String, String> sortedMap = new TreeMap<String, String>();
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = map.get(key);
			String tempValue = "";
			if(value!=null) {
				 tempValue = value.trim();
			}
			sortedMap.put(key, tempValue);
		}
		return sortedMap;
	}
}
