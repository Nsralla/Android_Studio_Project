package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a1200134_nsralla_hassan_finalproject.R;

public class LoginAndRegistration extends AppCompatActivity {

    Button loginButton;
    Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_registration);

        loginButton = findViewById(R.id.login_button);
        signinButton = findViewById(R.id.signin_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAndRegistration.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAndRegistration.this, SigninActivity.class);
                startActivity(intent);
            }
        });

    }
}