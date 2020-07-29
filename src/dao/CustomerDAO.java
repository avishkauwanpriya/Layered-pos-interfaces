package dao;

import entity.Customer;

import java.util.List;

public interface CustomerDAO {
    public  List<Customer> findAllCustomers();
    public  Customer findCustomer(String custId);
    public  boolean saveCustomer(Customer customer);
    public  boolean deleteCustomer(String custId);
    public  boolean updateCustomer(Customer customer);
    public String getLastCustomerID();


}
