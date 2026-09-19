package com.example.healthsakhi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    CardView cardEditProfile, cardChangePassword, cardLanguage, cardNotification, cardHelpSupport, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Restore language before UI loads
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String langCode = prefs.getString("language", "en");
        setAppLocale(langCode);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize CardViews
        cardEditProfile = findViewById(R.id.card_edit_profile);
        cardChangePassword = findViewById(R.id.card_change_password);
        cardLanguage = findViewById(R.id.card_language_settings);
        cardNotification = findViewById(R.id.card_notifications);
        cardHelpSupport = findViewById(R.id.card_help_support);
        cardLogout = findViewById(R.id.card_logout);

        // Edit Profile
        cardEditProfile.setOnClickListener(v -> {
            Log.d("SettingActivity", "Edit Profile clicked");
            Intent intent = new Intent(SettingActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        // Change Password
        cardChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });

        // Language Settings
        cardLanguage.setOnClickListener(v -> showLanguageDialog());

        // Notification Settings
        cardNotification.setOnClickListener(v -> showNotificationToggleDialog());

        // Help & Support
        cardHelpSupport.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@healthsakhi.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help & Support");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Describe your issue here...");
            try {
                startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });

        // Logout
        cardLogout.setOnClickListener(v -> showLogoutConfirmation());
    }

    private void showLanguageDialog() {
        String[] languages = {"English", "Hindi", "Marathi"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Language");
        builder.setItems(languages, (dialog, which) -> {
            switch (which) {
                case 0:
                    setLocale("en");
                    break;
                case 1:
                    setLocale("hi");
                    break;
                case 2:
                    setLocale("mr");
                    break;
            }
        });
        builder.show();
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Save selected language
        SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("language", langCode);
        editor.apply();

        recreate();
    }

    private void setAppLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private void showNotificationToggleDialog() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean enabled = prefs.getBoolean("notifications", true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notifications");
        builder.setMessage("Notifications are currently " + (enabled ? "enabled." : "disabled."));
        builder.setPositiveButton(enabled ? "Disable" : "Enable", (dialog, which) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("notifications", !enabled);
            editor.apply();
            Toast.makeText(this, "Notifications " + (!enabled ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Clear session
                    SharedPreferences.Editor editor = getSharedPreferences("user_session", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();

                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
