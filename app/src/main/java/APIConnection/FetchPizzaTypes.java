package APIConnection;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import Activities.LoginAndRegistration;
import ObjectClasses.PizzaType;
import ObjectClasses.PizzaTypeJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

        try {
            URL url = new URL("https://mocki.io/v1/899779af-2fe7-4b58-9a2c-e7473173120e");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000); // 15 seconds
            connection.setReadTimeout(15000); // 15 seconds

            connection.setRequestMethod("GET");
            Log.i("API", "Initiating GET request to URL: " + url);

            int responseCode = connection.getResponseCode();
            Log.i("API", "Received HTTP response code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } else {
                isSuccess = false;
                return "Failed to connect to server: HTTP status " + responseCode;
            }
        } catch (IOException e) {
            isSuccess = false;
            Log.e("API", "Failed to connect to server: " + e.getMessage(), e);
            return "Failed to connect to server: " + e.getMessage();
        }

    }




    protected  void onPostExecute(String s){
        super.onPostExecute(s);
        super.onPostExecute(s);
        if(isSuccess){
            try {
                if (s == null) {
                    Log.e("AsyncTask", "Received null data from the server");
                    return;
                }
                Log.i("AsyncTask",s);
                ArrayList<PizzaType> pizzaTypes = PizzaTypeJsonParser.getObjectFromJson(s);
                if (pizzaTypes == null) {
                    Log.e("AsyncTask", "Failed to parse the JSON data");
                    return;
                }
                PizzaType.setPizzaTypes(pizzaTypes);
                //TODO: navigate to Login and registration
                Intent intent = new Intent(activity, LoginAndRegistration.class);
                activity.startActivity(intent);
            } catch (ClassCastException e) {
                Log.e("AsyncTask", "Activity must be an instance of MainActivity", e);
            }
        }
        else{
            Toast.makeText(activity, s, Toast.LENGTH_LONG).show();
        }
    }
}