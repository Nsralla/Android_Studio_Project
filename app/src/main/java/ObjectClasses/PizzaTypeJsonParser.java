package ObjectClasses;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ObjectClasses.PizzaType;

public class PizzaTypeJsonParser {

    public static ArrayList<PizzaType> getObjectFromJson(String json) {
        ArrayList<PizzaType> pizzaTypes = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("types");
            for (int i = 0; i < jsonArray.length(); i++) {
                String pizzaType = jsonArray.getString(i);
                Log.i("in parser",pizzaType);
                PizzaType newPizzaType = new PizzaType(pizzaType);
                pizzaTypes.add(newPizzaType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ParseTask", "Error converting JSON pizza type", e);
            return null; // Consider handling error more gracefully
        }
        return pizzaTypes;
    }
}
