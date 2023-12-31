package com.backend.tomato.controllers;

import com.backend.tomato.entitites.MyOrder;
import com.backend.tomato.entitites.OrderItem;
import com.backend.tomato.services.OrderService;
import com.razorpay.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private OrderService orderService;

    @Value("${razorpay.public.key}")
    private String key;

    @Value("${razorpay.secret.key}")
    private String secret;

    Logger logger= LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/createorder")
    public String getOrder(@RequestBody Map<String,Object> data) throws RazorpayException {
//        System.out.println("Got Order");
        int amount=Integer.parseInt(data.get("amount").toString());
        RazorpayClient razorpay =  new RazorpayClient(key,secret);
        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_11");
            Order order = razorpay.Orders.create(orderRequest);
            System.out.println(order);

            logger.info("Order {}"+order);

            /*  Saving In DB */

//            MyOrder myOrder=new MyOrder();
//            myOrder.setAmount(order.get("amount"));
//            myOrder.setTimestamp(order.get("created_at"));
//            myOrder.setOrderStatus(order.get("status"));
//            myOrder.setOrderId(order.get("id"));
//            User id + items
//            myOrder.setUserId(data.getUserId());
//            myOrder.setOrderItems(data.getOrderItems());
//            System.out.println(myOrder);
//            save my order in db
//            this.orderService.saveOrder(myOrder);
            return order.toString();
        } catch (RazorpayException e) {
            // Handle Exception
//            System.out.println(e.getMessage());
            logger.info("Exception Occured {}" + e.getMessage());
        }
        return "";
    }
}
