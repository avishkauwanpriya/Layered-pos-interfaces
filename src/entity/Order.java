package entity;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable {
    private String id;
    private Date date;
    private String customerId;

    public Order(String id, Date date, String customerId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
    }

    public Order() {
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
