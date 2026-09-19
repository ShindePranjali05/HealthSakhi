package com.example.healthsakhi;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthsakhi.room.AppDatabase;
import com.example.healthsakhi.room.User;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTextView, emailTextView, heightTextView, weightTextView, ageTextView;
    private AppDatabase db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        heightTextView = findViewById(R.id.heightTextView);
        weightTextView = findViewById(R.id.weightTextView);
        ageTextView = findViewById(R.id.ageTextView);

        db = AppDatabase.getInstance(this);

        // Get the logged-in user's email
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userEmail = prefs.getString("user_email", "");

        if (!userEmail.isEmpty()) {
            AppDatabase.getExecutorService().execute(() -> {
                User user = db.userDao().getUserByEmail(userEmail);

                if (user != null) {
                    runOnUiThread(() -> {
                        nameTextView.setText("Name: " + user.name);
                        emailTextView.setText("Email: " + user.email);
                        heightTextView.setText("Height: " + user.height + " cm");
                        weightTextView.setText("Weight: " + user.weight + " kg");
                        ageTextView.setText("Age: " + user.age);
                    });
                }
            });
        }
    }
}
