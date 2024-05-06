package com.example.a1200134_nsralla_hassan_finalproject;

import java.util.ArrayList;

public class PizzaType {

    private String pizzaType;
    private static ArrayList<PizzaType> pizzaTypes = new ArrayList<>();

    public PizzaType(){

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
