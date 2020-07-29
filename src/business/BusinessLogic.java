package business;

import dao.*;
import dao.impl.*;
import dao.impl.CustomerDAOImpl;
import dao.impl.ItemDAOImpl;
import db.DBConnection;
import entity.*;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessLogic {

    public static String getNewCustomerId(){
        CustomerDAO customerDAO = new CustomerDAOImpl();
        String lastCustomerId =customerDAO.getLastCustomerID();
        if (lastCustomerId == null){
            return "C001";
        }else{
           int maxId=  Integer.parseInt(lastCustomerId.replace("C",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
            }
            return id;
        }
    }

    public static String getNewItemCode(){
        ItemDAO itemDAO = new ItemDAOImpl();
        String lastItemCode =itemDAO.getLastItemID();
        if (lastItemCode == null){
            return "I001";
        }else{
            int maxId=  Integer.parseInt(lastItemCode.replace("I",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            return id;
        }
    }

    public static String getNewOrderId(){
        OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
        String oldID = orderDetailDAO.getLastOrderDetailID();
        if (oldID == null){
            return "OD001";
        }else{
            int maxId=  Integer.parseInt(oldID.replace("OD",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OD00" + maxId;
            } else if (maxId < 100) {
                id = "OD0" + maxId;
            } else {
                id = "OD" + maxId;
            }
            return id;
        }
    }

    public static List<CustomerTM> getAllCustomers(){
        CustomerDAO customerDAO = new CustomerDAOImpl();

        ArrayList<CustomerTM> allCustomerTMs = new ArrayList<>();

        for (Customer customer:customerDAO.findAllCustomers()) {
            allCustomerTMs.add(new CustomerTM(customer.getId(),customer.getName(),customer.getAddress()));


        }
        return allCustomerTMs;
    }

    public static boolean saveCustomer(String id, String name, String address){
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.saveCustomer(new Customer(id,name,address));

    }

    public static boolean deleteCustomer(String customerId){
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.deleteCustomer(customerId);
    }

    public static boolean updateCustomer(String name, String address, String customerId){
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.updateCustomer(new Customer(customerId, name, address));
    }

    public static List<ItemTM> getAllItems(){
        ItemDAO itemDAO = new ItemDAOImpl();
        List<Item> allItems = itemDAO.findAllItems();
        List<ItemTM> itemTMS = new ArrayList<>();

        for (Item allItem : allItems) {
            itemTMS.add(new ItemTM(allItem.getCode(),allItem.getDescription(),allItem.getQtyOnHand(),allItem.getUnitPrice().doubleValue()));
        }
        return itemTMS;


    }

    public static boolean saveItem(String code, String description, int qtyOnHand, BigDecimal unitPrice){
        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.saveItem(
                new Item(code, description, unitPrice,qtyOnHand));
    }

    public static boolean deleteItem(String itemCode){
        ItemDAO itemDAO = new ItemDAOImpl();


        return itemDAO.deleteItem(itemCode);
    }

    public static boolean updateItem(String description, int qtyOnHand, BigDecimal unitPrice, String itemCode){
        ItemDAO itemDAO = new ItemDAOImpl();
        return itemDAO.updateItem(new Item(itemCode, description, unitPrice,qtyOnHand));
    }

    public static boolean placeOrder(Order order, List<OrderDetailTM> orderDetails){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            OrderDAO orderDAO = new OrderDAOImpl();
            connection.setAutoCommit(false);

            boolean result = orderDAO.saveOrder(order);
            if (!result){
                connection.rollback();
                return false;
            }
            for (OrderDetailTM orderDetail : orderDetails) {
                OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
                result = orderDetailDAO.saveOrderDetail(new OrderDetail(new OrderDetailPK(order.getId(), orderDetail.getCode()),orderDetail.getQty(),  BigDecimal.valueOf(orderDetail.getUnitPrice())));
                if (!result) {
                    connection.rollback();
                    return false;
                }

                ItemDAO itemDAO = new ItemDAOImpl();
                result = itemDAO.updateItem(new Item(orderDetail.getCode(), orderDetail.getDescription(), BigDecimal.valueOf(orderDetail.getUnitPrice()), orderDetail.getQty()));
                if (!result) {
                    connection.rollback();
                    return false;
                }

                connection.commit();

            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
    public static String autoGeneratePlaceOrderID(){

        String oldID = new OrderDetailDAOImpl().getLastOrderDetailID();
        oldID = oldID.substring(2, 5);

        int newID = Integer.parseInt(oldID) + 1;

        if (newID < 10) {
            return  "OD00" + newID;
        } else if (newID < 100) {
            return  "OD0" + newID;
        } else {
            return  "OD" + newID;
        }
    }


}
