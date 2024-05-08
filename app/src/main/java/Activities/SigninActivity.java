package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import Hashing.Hash;
import com.example.a1200134_nsralla_hassan_finalproject.R;

import Database.DataBaseHelper;
import ObjectClasses.Admin;
import ObjectClasses.Client;
import ObjectClasses.User;

public class SigninActivity extends AppCompatActivity {
    Spinner genderSpinner;
    Button signinButton;

    EditText emailText;
    EditText fNameText;
    EditText lNameText;
    EditText passwordText;
    EditText confirmPasswordText;
    EditText phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        genderSpinner = findViewById(R.id.genderSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderSpinner.setAdapter(adapter);


        // get the buttons and Plain texts
        signinButton = findViewById(R.id.signin_Button);
        emailText = findViewById(R.id.email_l);
        fNameText = findViewById(R.id.firstNameEditText);
        lNameText = findViewById(R.id.lastNameEditText);
        passwordText = findViewById(R.id.password_l);
        confirmPasswordText = findViewById(R.id.confirmPasswordEditText);
        phoneText = findViewById(R.id.phoneEditText);
        // get the gender


        //add event listener
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
        //TODO: Validate the email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.append("Invalid email format.\n");
            isValid = false;
        }

        //TODO:VALIDATE THE PHONE NUMBER
        if(!phoneNumber.matches("05\\d{8}"))
        {
            errors.append("Phone number must start with '05' and be 10 digits long.\n");
            isValid = false;
        }

        //TODO:VALIDATE THE FIRST NAME
        if (firstName.length() < 3 || lastName.length() < 3) {
            errors.append("First name and Last name must be at least 3 characters long.\n");
            isValid = false;
        }

        //TODO: VALIDATE THE PASSWORD
        if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            errors.append("Password must be at least 8 characters long and include at least one letter and one number.\n");
            isValid = false;
        }

        //TODO: VALIDATE CONFIRM PASSWORD
        if (!confirmPassword.equals(password)) {
            errors.append("Confirm password does not match the password.\n");
            isValid = false;
        }

        if(!isValid){
            Toast.makeText(this, errors.toString(), Toast.LENGTH_LONG).show();
        }else{
            String encryptedPassword = Hash.hashPassword(password); //TODO: HASH THE PASSWORD
            //TODO:SAVE THE USER INFO INTO DB
            DataBaseHelper dataBaseHelper =new DataBaseHelper(SigninActivity.this,"1200134_nsralla_hassan_finalProject",null,1);
            Client client = new Client();
            client.setEmail(email);
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setPhone(phoneNumber);
            client.setHashedPassword(encryptedPassword);
            client.setGender(selectedGender);
//            user.setIs_Admin(false);
            dataBaseHelper.insertClient(client);
            //TODO:NAVIGATE TO LOGIN PAGE
            Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
            startActivity(intent);
            Intent intent1 = new Intent(SigninActivity.this, LoginActivity.class);
            startActivity(intent1);
        }

    }


}