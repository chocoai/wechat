package com.miyzh.framework.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * User: rizenguo
 * Date: 2014/11/1
 * Time: 14:06
 */
public class XMLParser {

//    /**
//     * 从REFUNQUERYRESPONSESTRING里面解析出退款订单数据
//     * @PARAM REFUNDQUERYRESPONSESTRING REFUNDQUERY API返回的数据
//     * @RETURN 因为订单数据有可能是多个，所以返回一个列表
//     */
//    PUBLIC STATIC LIST<REFUNDORDERDATA> GETREFUNDORDERLIST(STRING REFUNDQUERYRESPONSESTRING) THROWS IOEXCEPTION, SAXEXCEPTION, PARSERCONFIGURATIONEXCEPTION {
//        LIST LIST = NEW ARRAYLIST();
//
//        MAP<STRING,OBJECT> MAP = XMLPARSER.GETMAPFROMXML(REFUNDQUERYRESPONSESTRING);
//
//       INT COUNT = INTEGER.PARSEINT((STRING) MAP.GET("REFUND_COUNT"));
//       UTIL.LOG("COUNT:" + COUNT);
//
//        IF(COUNT<1){
//            RETURN LIST;
//        }
//
//        REFUNDORDERDATA REFUNDORDERDATA;
//
//        FOR(INT I=0;I<COUNT;I++){
//            REFUNDORDERDATA = NEW REFUNDORDERDATA();
//
//            REFUNDORDERDATA.SETOUTREFUNDNO(UTIL.GETSTRINGFROMMAP(MAP,"OUT_REFUND_NO_" + I,""));
//            REFUNDORDERDATA.SETREFUNDID(UTIL.GETSTRINGFROMMAP(MAP,"REFUND_ID_" + I,""));
//            REFUNDORDERDATA.SETREFUNDCHANNEL(UTIL.GETSTRINGFROMMAP(MAP,"REFUND_CHANNEL_" + I,""));
//            REFUNDORDERDATA.SETREFUNDFEE(UTIL.GETINTFROMMAP(MAP,"REFUND_FEE_" + I));
//            REFUNDORDERDATA.SETCOUPONREFUNDFEE(UTIL.GETINTFROMMAP(MAP,"COUPON_REFUND_FEE_" + I));
//            REFUNDORDERDATA.SETREFUNDSTATUS(UTIL.GETSTRINGFROMMAP(MAP,"REFUND_STATUS_" + I,""));
//            LIST.ADD(REFUNDORDERDATA);
//        }
//
//        RETURN LIST;
//    }

    public static Map<String,Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is =  Util.getStringStream(xmlString);
        Document document = builder.parse(is);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;

    }


}
