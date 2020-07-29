package dao;

import db.DBConnection;
import entity.Customer;
import entity.Item;
import entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public static List<Order> findAllOrders(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM order");
            ArrayList<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getString(1),
                        resultSet.getDate(2),
                        resultSet.getString(3)

                ));

            }
            return  orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }

    }
    public static Order findOrder(String orderId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM order WHERE id =(?)");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return new Order(resultSet.getString(1),
                        resultSet.getDate(2),
                        resultSet.getString(3)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }

        return null;

    }
    public static boolean saveOrder(Order order){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO order VALUES (?,?,?)");
            preparedStatement.setObject(1,order.getId());
            preparedStatement.setObject(2,order.getDate());
            preparedStatement.setObject(3,order.getCustomerId());
            return preparedStatement.executeUpdate()>0;


        } catch (SQLException e) {
            e.printStackTrace();
            return  false;

        }

    }
    public static boolean deleteOrder(String OrderId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM order WHERE id = (?)");
            preparedStatement.setObject(1,OrderId);

            return preparedStatement.executeUpdate()>0;


        } catch (SQLException e) {
            e.printStackTrace();
            return  false;

        }

    }
    public static boolean updateOrder(Order order){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE order SET  date = (?), customerId = (?) WHERE id = (?)");
            preparedStatement.setObject(1,order.getDate());
            preparedStatement.setObject(2,order.getCustomerId());
            preparedStatement.setObject(3,order.getId());


            return preparedStatement.executeUpdate()>0;


        } catch (SQLException e) {
            e.printStackTrace();
            return  false;

        }
    }
}
