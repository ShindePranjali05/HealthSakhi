package com.example.healthsakhi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthsakhi.room.AppDatabase;
import com.example.healthsakhi.room.User;
import com.example.healthsakhi.room.UserDao;
import java.util.concurrent.ExecutorService;

public class SignupActivity extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private UserDao userDao;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Views
        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        Button signUpButton = findViewById(R.id.signUpButton);
        TextView loginRedirectText = findViewById(R.id.loginTextView);

        // Initialize Room Database
        userDao = AppDatabase.getInstance(this).userDao();
        executorService = AppDatabase.getExecutorService();

        // Sign Up Button Click
        signUpButton.setOnClickListener(v -> validateAndSignUp());

        // Redirect to Login Page
        loginRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Method to validate input and perform signup logic
    private void validateAndSignUp() {
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create User object and insert into the database in the background
        User user = new User(email, password);

        executorService.execute(() -> {
            userDao.insertUser(user);  // Insert user into the database

            // Show a message on the UI thread once the user is inserted successfully
            runOnUiThread(() -> {
                Toast.makeText(this, "Sign-up successful! Please login.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            });
        });
    }
}
