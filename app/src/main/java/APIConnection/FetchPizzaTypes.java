package APIConnection;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import Activities.LoginAndRegistration;
import ObjectClasses.PizzaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FetchPizzaTypes extends AsyncTask<Void, Void, String>{
    private String responseText;
    private boolean isSuccess = true;
    private Activity activity;

    public  FetchPizzaTypes(Activity activity){
        this.activity = activity;
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null");
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        String jsonData = "{ \"types\": [ \"Margarita\", \"Neapolitan\", \"Hawaiian\", \"Pepperoni\", \"New York Style\", \"Calzone\", \"Tandoori Chicken Pizza\", \"BBQ Chicken Pizza\", \"Seafood Pizza\", \"Vegetarian Pizza\", \"Buffalo Chicken Pizza\", \"Mushroom Truffle Pizza\", \"Pesto Chicken Pizza\" ] }";

        try {
            // Directly use the jsonData
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray typesArray = jsonObject.getJSONArray("types");

            StringBuilder response = new StringBuilder();
            for (int i = 0; i < typesArray.length(); i++) {
                if (i > 0) response.append(", ");
                response.append(typesArray.getString(i));
            }

            isSuccess = true;  // Assume success since we are not actually making a network call
            return response.toString();
        } catch (JSONException e) {
            isSuccess = false;
            Log.e("API", "Failed to parse JSON data: " + e.getMessage(), e);
            return "Failed to parse JSON data: " + e.getMessage();
        }
//        try {
//            URL url = new URL("https://18fbea62d74a40eab49f72e12163fe6c.api.mockbin.io/");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setConnectTimeout(15000); // 15 seconds
//            connection.setReadTimeout(15000); // 15 seconds
//
//            connection.setRequestMethod("GET");
//            Log.i("API", "Initiating GET request to URL: " + url);
//
//            int responseCode = connection.getResponseCode();
//            Log.i("API", "Received HTTP response code: " + responseCode);
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//                return response.toString();
//            } else {
//                isSuccess = false;
//                return "Failed to connect to server: HTTP status " + responseCode;
//            }
//        } catch (IOException e) {
//            isSuccess = false;
//            Log.e("API", "Failed to connect to server: " + e.getMessage(), e);
//            return "Failed to connect to server: " + e.getMessage();
//        }

    }




    protected  void onPostExecute(String s){
        super.onPostExecute(s);
        if (isSuccess) {
            Log.i("AsyncTask", "Data: " + s);
            // convert the string to array list
            // Splitting the string by comma and trimming spaces
            String[] pizzaArray = s.split(",\\s*");

            // Create an ArrayList of PizzaType
            ArrayList<PizzaType> pizzaTypes = new ArrayList<>();

// Example default values
            double defaultPrice = 9.99; // Default price for each pizza
            String defaultSize = "Medium"; // Default size
            String[] categories = {"Chicken", "Beef", "Veggie", "Others"}; // Categories array

// Populate the ArrayList with PizzaType objects
            for (int i = 0; i < pizzaArray.length; i++) {
                String pizzaName = pizzaArray[i];
                String category = categories[i % categories.length]; // Cycle through categories
                pizzaTypes.add(new PizzaType(pizzaName, defaultPrice, defaultSize, category));
            }

// Save them in the class
            PizzaType.setPizzaTypes(pizzaTypes);


            // open new Activity
            Intent intent = new Intent(activity, LoginAndRegistration.class);
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, "Error: " + s, Toast.LENGTH_LONG).show();
        }


//        super.onPostExecute(s);
//        if(isSuccess){
//            try {
//                if (s == null) {
//                    Log.e("AsyncTask", "Received null data from the server");
//                    return;
//                }
//                Log.i("AsyncTask",s);
//                ArrayList<PizzaType> pizzaTypes = PizzaTypeJsonParser.getObjectFromJson(s);
//                if (pizzaTypes == null) {
//                    Log.e("AsyncTask", "Failed to parse the JSON data");
//                    return;
//                }
//                PizzaType.setPizzaTypes(pizzaTypes);
//                //TODO: navigate to Login and registration
//                Intent intent = new Intent(activity, LoginAndRegistration.class);
//                activity.startActivity(intent);
//            } catch (ClassCastException e) {
//                Log.e("AsyncTask", "Activity must be an instance of MainActivity", e);
//            }
//        }
//        else{
//            Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
//        }
    }
}