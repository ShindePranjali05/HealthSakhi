package com.example.healthsakhi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthsakhi.room.AppDatabase;
import com.example.healthsakhi.room.User;
import com.example.healthsakhi.room.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private UserDao userDao;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Init views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        // Init Room database
        userDao = AppDatabase.getInstance(this).userDao();
        executorService = Executors.newSingleThreadExecutor();

        // Login Button Click
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Run database query in background thread
            executorService.execute(() -> {
                User user = userDao.login(email, password);
                runOnUiThread(() -> {
                    if (user != null) {
                        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("user_email", user.email);
                        editor.apply();
                        // Successful login
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    } else {
                        // Invalid credentials
                        Toast.makeText(LoginActivity.this, "Invalid Email or Password!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });

        // Sign Up Button Click
        signUpButton.setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, SignupActivity.class))
        );
    }
}
