package dao;

import entity.Order;

import java.util.List;

public interface OrderDAO {
    public List<Order> findAllOrders();
    public  Order findOrder(String orderId);
    public  boolean saveOrder(Order order);
    public  boolean deleteOrder(String OrderId);
    public  boolean updateOrder(Order order);
}

