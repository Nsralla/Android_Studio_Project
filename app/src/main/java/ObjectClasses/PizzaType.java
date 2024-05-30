package ObjectClasses;

import java.util.ArrayList;

public class PizzaType {

    private String pizzaType;
    private double price; // Price of the pizza
    private String size;  // Size of the pizza
    private String category; // Category (e.g., chicken, beef, veggies)
    private String pizzaImage;
    private static ArrayList<PizzaType> pizzaTypes = new ArrayList<>();

    private int quantity;

    public PizzaType(){

    }



    public PizzaType(String pizzaType, String size,double price, int quantity) {
        this.pizzaType = pizzaType;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
    }

    public PizzaType(String pizzaType, String size,double price, int quantity, String category) {
        this.pizzaType = pizzaType;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
        this.category = category;
    }

    public PizzaType(String pizzaType, double price, String size, String category) {
        this.pizzaType = pizzaType;
        this.price = price;
        this.size = size;
        this.category = category;
    }

    public PizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PizzaType(ArrayList<PizzaType> pizzaTypes) {
        PizzaType.pizzaTypes = pizzaTypes;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public static ArrayList<PizzaType> getPizzaTypes() {
        return pizzaTypes;
    }


    public String getPizzaImage() {
        return pizzaImage;
    }

    public void setPizzaImage(String pizzaImage) {
        this.pizzaImage = pizzaImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static void setPizzaTypes(ArrayList<PizzaType> pizzaTypes) {
        PizzaType.pizzaTypes = pizzaTypes;
    }
}
