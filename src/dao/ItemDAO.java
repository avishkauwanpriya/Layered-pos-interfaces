package dao;

import entity.Item;

import java.util.List;

public interface ItemDAO {
    public List<Item> findAllItems();
    public  Item findItem(String itemId);
    public  boolean saveItem(Item item);
    public  boolean deleteItem(String itemId);
    public  boolean updateItem(Item item);
    public String getLastItemID();
}
