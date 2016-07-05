package com.demo.app;

import com.demo.services.Order;
import com.demo.services.OrderProcess;
import com.demo.services.OrderProcessImplService;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger           LOGGER      = Logger.getLogger(App.class);

    public static void main( String[] args )
    {
        OrderProcessImplService orderProcess = new OrderProcessImplService();
        OrderProcess port = orderProcess.getOrderProcessImplPort();

        Order order = new Order();
        order.setCustomerID("C001");
        order.setItemID("I001");
        order.setPrice(100.00);
        order.setQty(20);

        //        String result = port.processOrder(order);
        LOGGER.info("port: "+ port);
        LOGGER.info("processOrder result: "+ port.processOrder(order));
        LOGGER.info(new App().formatXML("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body>" +
        //        "\n" +
                "<select xmlns=\"http://mdws.va.gov/EmrSvc\">" +
        //        "\n" +
                "<DFN>100718</DFN></select></soap:Body></soap:Envelope>"));
    }

    public String formatXML(String input)
    {
        try
        {
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "3");

            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(parseXml(input));
            transformer.transform(source, result);
            return result.getWriter().toString();
        } catch (Exception e)
        {
            e.printStackTrace();
            return input;
        }
    }

    private Document parseXml(String in)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
