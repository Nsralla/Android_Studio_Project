package ObjectClasses;

public class OrderIncome {
    private String pizzaType;
    private int numberOfOrders;
    private double totalIncome;

    public OrderIncome(String pizzaType, int numberOfOrders, double totalIncome) {
        this.pizzaType = pizzaType;
        this.numberOfOrders = numberOfOrders;
        this.totalIncome = totalIncome;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }
}
