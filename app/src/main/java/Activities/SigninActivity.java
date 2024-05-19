package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import Hashing.Hash;
import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;

import Database.DataBaseHelper;
import ObjectClasses.Client;

public class SigninActivity extends AppCompatActivity {
    Spinner genderSpinner;
    Button signinButton;

    EditText emailText;
    EditText fNameText;
    EditText lNameText;
    EditText passwordText;
    EditText confirmPasswordText;
    EditText phoneText;

    TextView emailR;
    TextView phoneR;
    TextView passR;
    TextView confR;
    TextView firstR;
    TextView lastR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        genderSpinner = findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        signinButton = findViewById(R.id.signin_Button);
        emailText = findViewById(R.id.email_l2);
        fNameText = findViewById(R.id.firstNameEditText);
        lNameText = findViewById(R.id.lastNameEditText);
        passwordText = findViewById(R.id.password_l2);
        confirmPasswordText = findViewById(R.id.confirmPasswordEditText);
        phoneText = findViewById(R.id.phoneEditText);
        emailR = findViewById(R.id.emailR);
        phoneR = findViewById(R.id.phoneR);
        firstR = findViewById(R.id.firstR);
        lastR = findViewById(R.id.lastR);
        passR = findViewById(R.id.passR);
        confR = findViewById(R.id.confR);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    validateInputs();
            }
        });
    }

    public void validateInputs(){
        boolean isValid = true;
        StringBuilder errors = new StringBuilder();

        String email = emailText.getText().toString();
        String phoneNumber = phoneText.getText().toString();
        String firstName = fNameText.getText().toString();
        String lastName = lNameText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();
        String selectedGender = genderSpinner.getSelectedItem().toString();

        //VALIDATE THAT THE EMAIL HAS NOT BEEN USED BEFORE
        //GET ALL THE EMAILS
        ArrayList<String> emails = new ArrayList<>();
        DataBaseHelper db = new DataBaseHelper(SigninActivity.this,"1200134_nsralla_hassan_finalProject", null, 1);
        emails = db.getAllCustomersEmails();

        for(int i = 0; i< emails.size(); i++){
            if(emails.get(i).equals(email)){
                errors.append("Email already in use.\n");
                emailR.setText("Email already in use.\n");
                isValid = false;
            }
        }
        if(isValid)
            emailR.setText("");

        //TODO: Validate the email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.append("Invalid email format.\n");
            emailR.setText("Invalid email format.\n");
            isValid = false;
        }
        else if(isValid){
            emailR.setText("");
        }

        //TODO:VALIDATE THE PHONE NUMBER
        if(!phoneNumber.matches("05\\d{8}"))
        {
            errors.append("Phone number must start with '05' and be 10 digits long.\n");
            phoneR.setText("Phone number must start with '05' and be 10 digits long.\n");
            isValid = false;
        }
        else{
            phoneR.setText("");
        }


        //TODO:VALIDATE THE FIRST NAME
        if (firstName.length() < 3 || lastName.length() < 3) {
            errors.append("First name and Last name must be at least 3 characters long.\n");
            firstR.setText("First name and Last name must be at least 3 characters long.\n");
            isValid = false;
        }
        else{
            firstR.setText("");
        }


        //TODO: VALIDATE THE PASSWORD
        if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            errors.append("Password must be at least 8 characters long and include at least one letter and one number.\n");
            passR.setText("Password must be at least 8 characters long and include at least one letter and one number.\n");
            isValid = false;
        }
        else{
            passR.setText("");
        }


        //TODO: VALIDATE CONFIRM PASSWORD
        if (!confirmPassword.equals(password)) {
            errors.append("Confirm password does not match the password.\n");
            confR.setText("Confirm password does not match the password.\n");
            isValid = false;
        }
        else{
            confR.setText("");
        }


        if(!isValid){
            Toast.makeText(this, errors.toString(), Toast.LENGTH_LONG).show();
        }else{
            String encryptedPassword = Hash.hashPassword(password); //TODO: HASH THE PASSWORD
            DataBaseHelper dataBaseHelper =new DataBaseHelper(SigninActivity.this,"1200134_nsralla_hassan_finalProject",null,1);
            Client client = new Client();
            client.setEmail(email);
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setPhone(phoneNumber);
            client.setHashedPassword(encryptedPassword);
            client.setGender(selectedGender);
            dataBaseHelper.insertClient(client);
            //TODO:NAVIGATE TO LOGIN PAGE
            Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
            startActivity(intent);
            Intent intent1 = new Intent(SigninActivity.this, LoginActivity.class);
            startActivity(intent1);
        }

    }


}