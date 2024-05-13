package ObjectClasses;

import java.util.Date;

public class Order {
    private int orderId;
    private String customerEmail; // Replaced customerId with customerEmail
    private String pizzaType;
    private String pizzaSize;
    private double pizzaPrice;
    private int quantity;
//    private String orderStatus;
    private String orderDateTime;
//    private String specialRequests;
    private double totalPrice;
    private String category;

    public Order(){

    }


    public Order( String customerEmail, String pizzaType, String pizzaSize, double pizzaPrice,
                 int quantity,  String orderDateTime, double totalPrice, String category) {
        this.customerEmail = customerEmail;
        this.pizzaType = pizzaType;
        this.pizzaSize = pizzaSize;
        this.pizzaPrice = pizzaPrice;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
        this.category = category;
    }


    public Order( int orderId, String customerEmail, String pizzaType, String pizzaSize, double pizzaPrice,
                  int quantity,  String orderDateTime, double totalPrice) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.pizzaType = pizzaType;
        this.pizzaSize = pizzaSize;
        this.pizzaPrice = pizzaPrice;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
//        this.category = category;
    }


    public Order( int orderId, String customerEmail, String pizzaType, String pizzaSize, double pizzaPrice,
                  int quantity,  String orderDateTime, double totalPrice, String category) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.pizzaType = pizzaType;
        this.pizzaSize = pizzaSize;
        this.pizzaPrice = pizzaPrice;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
        this.category = category;
    }






    // Getters and Setters


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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



    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
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
                ", customerEmail='" + customerEmail + '\'' +
                ", pizzaType='" + pizzaType + '\'' +
                ", pizzaSize='" + pizzaSize + '\'' +
                ", pizzaPrice=" + pizzaPrice +
                ", quantity=" + quantity +
                ", orderDateTime=" + orderDateTime +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
