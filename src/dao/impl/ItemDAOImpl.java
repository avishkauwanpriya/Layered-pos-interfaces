package dao.impl;

import dao.ItemDAO;
import db.DBConnection;
import entity.Customer;
import entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public  List<Item> findAllItems(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item");
            ArrayList<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(new Item(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getBigDecimal(3),
                        resultSet.getInt(4)

                ));

            }
            return  items;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }


    }
    @Override
    public  Item findItem(String itemId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM customer WHERE code =(?)");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return new Item(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getBigDecimal(3),
                        resultSet.getInt(4)

                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }

        return null;

    }
    @Override
    public  boolean saveItem(Item item){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item  VALUES (?,?,?,?)");
            preparedStatement.setObject(1,item.getCode());
            preparedStatement.setObject(2,item.getDescription());
            preparedStatement.setObject(3,item.getUnitPrice());
            preparedStatement.setObject(4,item.getQtyOnHand());
            return preparedStatement.executeUpdate()>0;


        } catch (SQLException e) {
            e.printStackTrace();
            return  false;

        }


    }
    @Override
    public  boolean deleteItem(String itemId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM item WHERE code = (?)");
            preparedStatement.setObject(1,itemId);

            return preparedStatement.executeUpdate()>0;


        } catch (SQLException e) {
            e.printStackTrace();
            return  false;

        }

    }
    @Override
    public  boolean updateItem(Item item){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item SET description = (?), unitPrice = (?), qtyOnHand = (?) WHERE code = (?)");
            preparedStatement.setObject(1,item.getDescription());
            preparedStatement.setObject(2,item.getUnitPrice());
            preparedStatement.setObject(3,item.getQtyOnHand());
            preparedStatement.setObject(4,item.getCode());


            return preparedStatement.executeUpdate()>0;


        } catch (SQLException e) {
            e.printStackTrace();
            return  false;

        }

    }
    @Override
    public String getLastItemID() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Item order by code desc limit 1");
            if(resultSet.next()){
                return resultSet.getString(1);
            }else{
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
