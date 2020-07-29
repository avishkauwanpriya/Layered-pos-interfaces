package dao.impl;

import dao.OrderDetailDAO;
import db.DBConnection;
import entity.Customer;
import entity.Order;
import entity.OrderDetail;
import entity.OrderDetailPK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public  List<OrderDetail> findAllOrderDetails(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orderdetail");
            ArrayList<OrderDetail> orderDetails = new ArrayList<>();
            while (resultSet.next()) {
                orderDetails.add(
                        new OrderDetail(new OrderDetailPK(resultSet.getString(1),resultSet.getString(2)),
                                resultSet.getInt(3),
                                resultSet.getBigDecimal(4)


                ));

            }
            return  orderDetails;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }


    }
    @Override
    public   List<OrderDetail> findOrderDetail(OrderDetailPK orderDetailPK){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM orderdetail WHERE orderId=(?) and itemCode=(?)");
            preparedStatement.setObject(1,orderDetailPK.getOrderId());
            preparedStatement.setObject(1,orderDetailPK.getOrderId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){

            }


        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }

        return null;

    }
    @Override
    public  boolean saveOrderDetail(OrderDetail orderDetail){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into OrderDetail values (?,?,?,?)");
            preparedStatement.setObject(1,orderDetail.getOrderDetailPK().getOrderId());
            preparedStatement.setObject(2,orderDetail.getOrderDetailPK().getItemCode());
            preparedStatement.setObject(3,orderDetail.getQty());
            preparedStatement.setObject(4,orderDetail.getUnitPrice());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    @Override
    public  boolean deleteOrderDetail(OrderDetailPK orderDetailPK){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete OrderDetail where orderId = ? and itemCode = ?");
            preparedStatement.setObject(1,orderDetailPK.getOrderId());
            preparedStatement.setObject(2,orderDetailPK.getItemCode());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }
    @Override
    public  boolean updateOrderDetail(OrderDetail orderDetail){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update OrderDetail set qty =  ?,unitPrice = ? where orderId = ? and itemCode = ?");
            preparedStatement.setObject(1,orderDetail.getQty());
            preparedStatement.setObject(2,orderDetail.getUnitPrice());
            preparedStatement.setObject(3,orderDetail.getOrderDetailPK());
            preparedStatement.setObject(3,orderDetail.getOrderDetailPK().getItemCode());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public  String getLastOrderDetailID() {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from `Order` order by id desc limit 1");
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
