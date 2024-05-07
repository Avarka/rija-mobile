package hu.vadavar.rija;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;
    private Button switchToRegisterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        switchToRegisterButton = findViewById(R.id.switch_register);

        loginButton.setOnClickListener(v -> signIn(emailField.getText().toString(), passwordField.getText().toString()));

        switchToRegisterButton.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void signIn(String email, String password) {
        //log email and password
        Log.d(TAG, "signIn: " + email + " password: " + password);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, HomeActivity.class));
                    } else {
                        showLoginFailed(R.string.login_failed);
                    }
                });
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}