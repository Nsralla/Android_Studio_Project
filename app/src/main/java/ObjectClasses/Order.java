package ObjectClasses;

import java.util.Date;

public class Order {
    private int orderId;
    private String customerEmail; // Replaced customerId with customerEmail
    private String pizzaType;
    private String pizzaSize;
    private double pizzaPrice;
    private int quantity;
    private String orderStatus;
    private Date orderDateTime;
    private String specialRequests;
    private double totalPrice;

    public Order(int orderId, String customerEmail, String pizzaType, String pizzaSize, double pizzaPrice,
                 int quantity, String orderStatus, Date orderDateTime, String specialRequests, double totalPrice) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.pizzaType = pizzaType;
        this.pizzaSize = pizzaSize;
        this.pizzaPrice = pizzaPrice;
        this.quantity = quantity;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.specialRequests = specialRequests;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    // Continue with getters and setters for other fields

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public double getPizzaPrice() {
        return pizzaPrice;
    }

    public void setPizzaPrice(double pizzaPrice) {
        this.pizzaPrice = pizzaPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Example method to calculate total price
    public void calculateTotalPrice() {
        this.totalPrice = this.pizzaPrice * this.quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerEmail='" + customerEmail + '\'' +
                ", pizzaType='" + pizzaType + '\'' +
                ", pizzaSize='" + pizzaSize + '\'' +
                ", pizzaPrice=" + pizzaPrice +
                ", quantity=" + quantity +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDateTime=" + orderDateTime +
                ", specialRequests='" + specialRequests + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
