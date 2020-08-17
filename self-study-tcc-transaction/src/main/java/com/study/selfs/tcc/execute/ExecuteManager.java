package com.study.selfs.tcc.execute;

import com.study.selfs.tcc.mode.OrderStatus;
import com.study.selfs.tcc.mode.PayOrder;
import com.study.selfs.tcc.service.CreditService;
import com.study.selfs.tcc.service.InventoryService;
import com.study.selfs.tcc.service.NoticeDeliveryService;
import com.study.selfs.tcc.service.OrderService;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecuteManager {

    private static int minProcessors;
    private static int maxProcessors;

    private OrderService orderService;

    static {
        minProcessors = Runtime.getRuntime().availableProcessors();
        maxProcessors = Runtime.getRuntime().availableProcessors() * 2;
    }

    public ExecuteManager() {
        initialize();
    }

    private void initialize() {
        orderService = new OrderService();
        orderService.setNext(new InventoryService())
                    .setNext(new CreditService())
                    .setNext(new NoticeDeliveryService());
    }

    private ExecutorService executor = new ThreadPoolExecutor(minProcessors,maxProcessors,0l, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(),new DefaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());
    // 下订单未支付
    public PayOrder placeAnOrder(Map<String,Integer> productAmount, Double money){
        // 下订单等待支付
        // 这里就需要冻结库存，直到订单超时
        PayOrder payOrder = new PayOrder();
        payOrder.setProducts(productAmount);
        payOrder.setMoney(money);
        orderService.saveOrderCache(payOrder);
        return payOrder;
    }
    // 订单支付
    public void pay(String orderNum){
        PayOrder payOrder = orderService.getPayOrder(orderNum);

    }

}
