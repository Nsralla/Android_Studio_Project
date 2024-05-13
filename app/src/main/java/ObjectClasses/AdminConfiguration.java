package ObjectClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.a1200134_nsralla_hassan_finalproject.R;
import Database.DataBaseHelper;
import Hashing.Hash;

public class AdminConfiguration {
    private String loggedInEmail;
    private View rootView;
    private Context context;

    EditText emailText;
    EditText phoneText;
    EditText  fNameText;
    EditText lNameText;
    EditText genderText;

    EditText passwordText;
    String  hashedPassword;


    public AdminConfiguration(String loggedInEmail, View rootView, Context context ){
        this.loggedInEmail = loggedInEmail;
        this.rootView = rootView;
        this.context = context;
    }


    public void validateInputs(){
        boolean isValid = true;
        StringBuilder errors = new StringBuilder();


        //TODO:VALIDATE THE PHONE NUMBER
        if(!phoneText.getText().toString().matches("05\\d{8}"))
        {
            errors.append("Phone number must start with '05' and be 10 digits long.\n");
            isValid = false;
        }

        //TODO:VALIDATE THE FIRST NAME
        if (fNameText.getText().toString().length() < 3 || lNameText.getText().toString().length() < 3) {
            errors.append("First name and Last name must be at least 3 characters long.\n");
            isValid = false;
        }

        //TODO: VALIDATE THE PASSWORD
        if (passwordText.getText().toString().length() < 8 || !passwordText.getText().toString().matches(".*[a-zA-Z].*") || !passwordText.getText().toString().matches(".*\\d.*")) {
            errors.append("Password must be at least 8 characters long and include at least one letter and one number.\n");
            isValid = false;
        }

        if(!isValid){
            Toast.makeText(context, errors.toString(), Toast.LENGTH_LONG).show();
        }else{
            //TODO:SAVE THE USER INFO INTO DB
            DataBaseHelper dataBaseHelper =new DataBaseHelper(context,"1200134_nsralla_hassan_finalProject",null,1);
            Admin admin = new Admin();
            admin.setEmail(emailText.getText().toString());
            admin.setFirstName(fNameText.getText().toString());
            admin.setLastName(lNameText.getText().toString());
            admin.setPhone(phoneText.getText().toString());
            hashedPassword = Hash.hashPassword(passwordText.getText().toString());
            admin.setHashedPassword(hashedPassword);
            admin.setGender(genderText.getText().toString());

            dataBaseHelper.updateAdmin(admin);
            //TODO:NAVIGATE TO
            Toast.makeText(context, "Data updated sucssesfully", Toast.LENGTH_SHORT).show();
        }

    }


    public void getAdminFromDB( ){
        DataBaseHelper db = new DataBaseHelper(context, "1200134_nsralla_hassan_finalProject", null, 1);
        Cursor cursor = db.getAllAdmins();
        if (cursor != null) {
            System.out.println("CURSOR IS NOT NULL");
            try {
                while (cursor.moveToNext()) {
                    int emailColIndex = cursor.getColumnIndex("EMAIL");
                    System.out.println("LOGGED IN EMAIL: "+loggedInEmail);
                    System.out.println("email index : "+ emailColIndex);
                    if (emailColIndex != -1 && cursor.getString(emailColIndex).equals(loggedInEmail)) {
                        System.out.println("ADMIN FOUND");
                        displayClientInfo(cursor, loggedInEmail);
                    }
                }
            } finally {
                cursor.close(); // Close the cursor to avoid memory leaks

            }
        }
        else{
            System.out.println("CURSOR IS NULL");
        }
    }

    public void displayClientInfo(Cursor cursor, String loggedInEmail){
        int phoneColIndex = cursor.getColumnIndex("PHONE");
        int firstNameColIndex = cursor.getColumnIndex("FIRSTNAME");
        int lastNameColIndex = cursor.getColumnIndex("LASTNAME");
        int genderColIndex = cursor.getColumnIndex("GENDER");
        System.out.println("LOGGED EMAIL: "+ loggedInEmail);


        // Now set these values to EditTexts using rootView
        emailText.setText(loggedInEmail);
        phoneText.setText(cursor.getString(phoneColIndex));
        fNameText.setText(cursor.getString(firstNameColIndex));
        lNameText.setText(cursor.getString(lastNameColIndex));
        genderText.setText(cursor.getString(genderColIndex));// Assuming you retrieved this from the database

        SharedPreferences sharedPreferences = context.getSharedPreferences("PasswordPrefs", Context.MODE_PRIVATE);
        String loggedInPassword = sharedPreferences.getString("currentLoggedInUserPassword", null);
        passwordText.setText(loggedInPassword);
        System.out.println("PASSWORD: "+ loggedInPassword);

    }

    public void defineTheElements(View rootView){
        emailText = rootView.findViewById(R.id.editTextEmail);
        fNameText = rootView.findViewById(R.id.editTextFirstName);
        lNameText = rootView.findViewById(R.id.editTextLastName);
        phoneText = rootView.findViewById(R.id.editTextPhone);
        genderText = rootView.findViewById(R.id.editTextGender);
        passwordText = rootView.findViewById(R.id.editTextPassword);
    }


}
