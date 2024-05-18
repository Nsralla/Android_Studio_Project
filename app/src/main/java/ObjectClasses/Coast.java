package ObjectClasses;

public class Coast {
    private String pizzaType;
    private int quantity;
    private double price;
    private double totalPrice;

    public Coast() {
    }

    public Coast(String pizzaType, int quantity, double price, double totalPrice) {
        this.pizzaType = pizzaType;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
