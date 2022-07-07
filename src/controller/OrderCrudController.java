package controller;

import model.Order;
import util.CrudUtil;
import view.tm.CartTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
    public static boolean saveOrder(Order order){
        try {
            if (CrudUtil.execute("INSERT INTO `order`(orderId, customerId, transactionId, trackingNo, date, qty," +
                            " totalPrice, platform, paymentStatus, paymentMethod, deliveryStatus) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getTransactionId(),
                    order.getTrackingNo(),
                    order.getDate(),
                    order.getQty(),
                    order.getTotalPrice(),
                    order.getPlatform(),
                    order.getPaymentStatus(),
                    order.getPaymentMethod(),
                    order.getDeliveryStatus()
            )){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveCart(CartTM tm){
        try {
            if (CrudUtil.execute("INSERT INTO orderDetails(orderId, productId, designSku, qty, unitPrice, subTotal) VALUES (?,?,?,?,?,?)",
                    tm.getOrderId(),
                    tm.getProductId(),
                    tm.getDesignSku(),
                    tm.getQty(),
                    tm.getUnitPrice(),
                    tm.getSubTotal()
            )){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
