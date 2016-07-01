package com.demo.app;

import com.demo.services.Order;
import com.demo.services.OrderProcess;
import com.demo.services.OrderProcessImplService;
import org.apache.log4j.Logger;

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
    }
}
