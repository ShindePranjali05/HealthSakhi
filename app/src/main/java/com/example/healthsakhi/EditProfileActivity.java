package com.example.healthsakhi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthsakhi.room.AppDatabase;
import com.example.healthsakhi.room.User;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName, editEmail, editHeight, editWeight, editAge;
    private AppDatabase db;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editHeight = findViewById(R.id.editHeight);
        editWeight = findViewById(R.id.editWeight);
        editAge = findViewById(R.id.editAge);
        Button saveButton = findViewById(R.id.saveButton);

        db = AppDatabase.getInstance(this);

        // Replace with actual logged-in email
        String userEmail = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("user_email", "");

        AppDatabase.getExecutorService().execute(() -> {
            currentUser = db.userDao().getUserByEmail(userEmail);
            if (currentUser != null) {
                runOnUiThread(() -> {
                    editName.setText(currentUser.name);
                    editEmail.setText(currentUser.email);
                    editHeight.setText(currentUser.height != 0 ? String.valueOf(currentUser.height) : "");
                    editWeight.setText(currentUser.weight != 0 ? String.valueOf(currentUser.weight) : "");
                    editAge.setText(currentUser.age != 0 ? String.valueOf(currentUser.age) : "");

                });
            }
        });

        saveButton.setOnClickListener(v -> {
            if (currentUser != null) {
                currentUser.name = editName.getText().toString().trim();
                currentUser.email = editEmail.getText().toString().trim();
                try {
                    currentUser.height = Integer.parseInt(editHeight.getText().toString().trim());
                    currentUser.weight = Integer.parseInt(editWeight.getText().toString().trim());
                    currentUser.age = Integer.parseInt(editAge.getText().toString().trim());
                } catch (NumberFormatException e) {
                    // Handle invalid input (non-integer values)
                    Toast.makeText(this, "Please enter valid numeric values for height, weight, and age.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AppDatabase.getExecutorService().execute(() -> {
                    db.userDao().updateUser(currentUser);
                    runOnUiThread(() ->
                            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show());

                    Intent intent = new Intent(EditProfileActivity.this, SettingActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }
}
