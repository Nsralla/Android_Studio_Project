package com.example.a1200134_nsralla_hassan_finalproject;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        ArrayList<String> pizzaTypes = new ArrayList<>();
        try {
            URL url = new URL("https://18fbea62d74a40eab49f72e12163fe6c.api.mockbin.io/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Log.i("data","Get request....");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Log.i("data","After builder....");
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            responseText = response.toString();
        } catch (IOException e) {
            isSuccess = false;
            responseText = "Failed to connect to server.";
        }
        return responseText;
    }

    protected  void onPostExecute(String s){
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
//                Log.i("AsyncTask",pizzaTypes.toString());
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