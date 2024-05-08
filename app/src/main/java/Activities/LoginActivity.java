package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import FragmentsManager.Home_layout_admin;
import FragmentsManager.Home_layout_user;
import com.example.a1200134_nsralla_hassan_finalproject.R;

import Database.DataBaseHelper;

public class LoginActivity extends AppCompatActivity {

    Button login;
    CheckBox checkBox;
    EditText emailT;
    EditText passwordT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.loginButton_l);
        checkBox = findViewById(R.id.rememberMeCheckbox);
        emailT = findViewById(R.id.email_l);
        passwordT = findViewById(R.id.password_l);

        // check if the email and passwords exists in data base.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String email = emailT.getText().toString();
//                String password = passwordT.getText().toString();
//                String encryptedPassword = Hash.hashPassword(password);
//
//                boolean isValid = isValidLogin(email,encryptedPassword);
//                if(isValid){
                    //TODO: REMEMBER ME
//                    if(checkBox.isChecked())
//                        rememberUser(email, password);
                    //TODO:
                    // Proceed to next activity or home screen
                    // Proceed to next activity or home screen
                    Intent intent = new Intent(LoginActivity.this, Home_layout_admin.class);
                    startActivity(intent);
//                }else{
//                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
//                }

            }
        });


    }

    private boolean isValidLogin(String email, String encryptedPassword){
        DataBaseHelper dataBaseHelper =new DataBaseHelper(LoginActivity.this,"1200134_nsralla_hassan_finalProject",null,1);
        Cursor cursor = dataBaseHelper.getAllUsers();
        if(cursor!=null && cursor.moveToFirst()){
            int emailIndex = cursor.getColumnIndex("EMAIL");
            int passwordIndex = cursor.getColumnIndex("HASHEDPASSWORD");

            if (emailIndex != -1 && passwordIndex != -1) {
                do {
                    String dbEmail = cursor.getString(emailIndex);
                    String dbPassword = cursor.getString(passwordIndex);

                    if (dbEmail.equals(email) && dbPassword.equals(encryptedPassword)) {
                        cursor.close();
                        return true;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();

        }
        return false;
    }

    private void rememberUser(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

}