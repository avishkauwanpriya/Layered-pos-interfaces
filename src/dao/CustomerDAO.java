package dao;

import db.DBConnection;
import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public static List<Customer> findAllCustomers(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
            ArrayList<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)

                        ));

            }
            return  customers;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }


    }
    public static Customer findCustomer(String custId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM customer WHERE id =(?)");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return new Customer(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                        );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }

    return null;
    }
    public static boolean saveCustomer(Customer customer){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?)");
            preparedStatement.setObject(1,customer.getId());
            preparedStatement.setObject(2,customer.getName());
            preparedStatement.setObject(3,customer.getAddress());
            return preparedStatement.executeUpdate()>0;


        } catch (SQLException e) {
            e.printStackTrace();
            return  false;

        }




    }
    public static boolean deleteCustomer(String custId){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE id = (?)");
            preparedStatement.setObject(1,custId);

            return preparedStatement.executeUpdate()>0;


        } catch (SQLException e) {
            e.printStackTrace();
            return  false;

        }

    }
    public static boolean updateCustomer(Customer customer){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET name = (?), address = (?) WHERE id = (?)");
            preparedStatement.setObject(1,customer.getName());
            preparedStatement.setObject(2,customer.getAddress());
            preparedStatement.setObject(3,customer.getId());


            return preparedStatement.executeUpdate()>0;


        } catch (SQLException e) {
            e.printStackTrace();
            return  false;

        }

    }


}
