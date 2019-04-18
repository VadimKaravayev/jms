package bean;

import java.io.Serializable;

public class Order implements Serializable {
    private int userId;
    private TypeOfGoods type;
    private double volume;
    private double total;

    public Order(int userId, TypeOfGoods type, double volume) {
        this.userId = userId;
        this.type = type;
        this.volume = volume;
        this.total = type.getPrice() * volume;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", type=" + type +
                ", volume=" + volume +
                ", total=" + total +
                '}';
    }
}
