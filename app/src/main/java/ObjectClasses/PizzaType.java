package ObjectClasses;

import java.util.ArrayList;

public class PizzaType {

    private String pizzaType;
    private double price; // Price of the pizza
    private String size;  // Size of the pizza
    private String category; // Category (e.g., chicken, beef, veggies)
    private static ArrayList<PizzaType> pizzaTypes = new ArrayList<>();

    public PizzaType(){

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

    public static void setPizzaTypes(ArrayList<PizzaType> pizzaTypes) {
        PizzaType.pizzaTypes = pizzaTypes;
    }
}
