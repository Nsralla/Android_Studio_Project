package ObjectClasses;
/**
 * Class to represent a favorite item for a user in the pizza application.
 * This class stores details about the user's favorite pizzas.
 */
public class Favorite {
    private String customerEmail;  // Email of the customer who marked the item as favorite
    private String pizzaType;      // Type of the pizza
    private String pizzaSize;      // Preferred size of the pizza
    private double pizzaPrice;     // Price of the pizza

    private String pizzaCategory;

    // Constructor
    public Favorite(String customerEmail, String pizzaType, String pizzaSize, double pizzaPrice, String pizzaCategory) {
        this.customerEmail = customerEmail;
        this.pizzaType = pizzaType;
        this.pizzaSize = pizzaSize;
        this.pizzaPrice = pizzaPrice;
        this.pizzaCategory = pizzaCategory;
    }

    public String getPizzaCategory() {
        return pizzaCategory;
    }

    public void setPizzaCategory(String pizzaCategory) {
        this.pizzaCategory = pizzaCategory;
    }

    // Getter and Setter for customerEmail
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    // Getter and Setter for pizzaType
    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    // Getter and Setter for pizzaSize
    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    // Getter and Setter for pizzaPrice
    public double getPizzaPrice() {
        return pizzaPrice;
    }

    public void setPizzaPrice(double pizzaPrice) {
        this.pizzaPrice = pizzaPrice;
    }

    // Method to display favorite pizza details
    @Override
    public String toString() {
        return "Favorite{" +
                "customerEmail='" + customerEmail + '\'' +
                ", pizzaType='" + pizzaType + '\'' +
                ", pizzaSize='" + pizzaSize + '\'' +
                ", pizzaPrice=" + pizzaPrice +
                '}';
    }
}
