package ObjectClasses;

public class SpecialOffer {
    String pizzaType;
    String size;
    String startingOfferDate;
    String endingOfferDate;
    double totalPrice;

    public SpecialOffer() {
    }

    public SpecialOffer(String pizzaType, String size, String startingOfferDate, String endingOfferDate, double totalPrice) {
        this.pizzaType = pizzaType;
        this.size = size;
        this.startingOfferDate = startingOfferDate;
        this.endingOfferDate = endingOfferDate;
        this.totalPrice = totalPrice;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStartingOfferDate() {
        return startingOfferDate;
    }

    public void setStartingOfferDate(String startingOfferDate) {
        this.startingOfferDate = startingOfferDate;
    }

    public String getEndingOfferDate() {
        return endingOfferDate;
    }

    public void setEndingOfferDate(String endingOfferDate) {
        this.endingOfferDate = endingOfferDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public String toString() {
        return "SpecialOffer{" +
                "pizzaType='" + pizzaType + '\'' +
                ", size='" + size + '\'' +
                ", startingOfferDate='" + startingOfferDate + '\'' +
                ", endingOfferDate='" + endingOfferDate + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }
}
