package ObjectClasses;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int orderId;
    private String customerEmail;
//    private String pizzaType;
    private ArrayList<PizzaType> pizzas;
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


    public Order( String customerEmail, ArrayList<PizzaType> pizzas, int quantity, String orderDateTime, double totalPrice) {
        this.customerEmail = customerEmail;
        this.pizzas = pizzas;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public Order(String customerEmail, ArrayList<PizzaType> pizzas , String pizzaSize, double pizzaPrice,
                 int quantity, String orderDateTime, double totalPrice, String category) {
        this.customerEmail = customerEmail;
//        this.pizzaType = pizzaType;
        this.pizzas = pizzas;
        this.pizzaSize = pizzaSize;
        this.pizzaPrice = pizzaPrice;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
        this.category = category;
    }


    public Order( int orderId, String customerEmail, ArrayList<PizzaType> pizzas, String pizzaSize, double pizzaPrice,
                  int quantity,  String orderDateTime, double totalPrice) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
//        this.pizzaType = pizzaType;
        this.pizzas = pizzas;
        this.pizzaSize = pizzaSize;
        this.pizzaPrice = pizzaPrice;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
//        this.category = category;
    }


    public Order( int orderId, String customerEmail, ArrayList<PizzaType> pizzas, String pizzaSize, double pizzaPrice,
                  int quantity,  String orderDateTime, double totalPrice, String category) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
//        this.pizzaType = pizzaType;
        this.pizzas = pizzas;
        this.pizzaSize = pizzaSize;
        this.pizzaPrice = pizzaPrice;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
        this.category = category;
    }






    // Getters and Setters


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ArrayList<PizzaType> getPizzas() {
        return pizzas;
    }

    public void setPizzas(ArrayList<PizzaType> pizzas) {
        this.pizzas = pizzas;
    }



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

//    public String getPizzaType() {
//        return pizzaType;
//    }

//    public void setPizzaType(String pizzaType) {
//        this.pizzaType = pizzaType;
//    }

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
                "orderId=" + orderId +
                ", customerEmail='" + customerEmail + '\'' +
                ", pizzas=" + pizzas +
                ", pizzaSize='" + pizzaSize + '\'' +
                ", pizzaPrice=" + pizzaPrice +
                ", quantity=" + quantity +
                ", orderDateTime='" + orderDateTime + '\'' +
                ", totalPrice=" + totalPrice +
                ", category='" + category + '\'' +
                '}';
    }
}
