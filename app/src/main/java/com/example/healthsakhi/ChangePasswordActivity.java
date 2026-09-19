package com.example.healthsakhi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthsakhi.room.AppDatabase;
import com.example.healthsakhi.room.User;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etOldPassword, etNewPassword, etConfirmPassword;
    Button btnChangePassword;
    AppDatabase db;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnChangePassword = findViewById(R.id.btn_change_password);

        db = AppDatabase.getInstance(this);

        String email = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                .getString("user_email", "");

        AppDatabase.getExecutorService().execute(() -> {
            currentUser = db.userDao().getUserByEmail(email);

            runOnUiThread(() -> {
                btnChangePassword.setOnClickListener(v -> {
                    if (currentUser == null) {
                        Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String oldPass = etOldPassword.getText().toString().trim();
                    String newPass = etNewPassword.getText().toString().trim();
                    String confirmPass = etConfirmPassword.getText().toString().trim();

                    if (!oldPass.equals(currentUser.password)) {
                        Toast.makeText(this, "Old password is incorrect.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!newPass.equals(confirmPass)) {
                        Toast.makeText(this, "New passwords do not match.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    currentUser.password = newPass;

                    AppDatabase.getExecutorService().execute(() -> {
                        db.userDao().updateUser(currentUser);
                        runOnUiThread(() -> Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show());
                    });
                });
            });
        });
    }
}
