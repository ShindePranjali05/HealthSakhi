package com.example.healthsakhi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.healthsakhi.room.AppDatabase;
import com.example.healthsakhi.room.User;
import com.example.healthsakhi.room.UserDao;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CardView cardProfile, cardHealth, cardPeriod, cardBot;
    TextView textQuote;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    String[] quotesAndShayaris = {
            "You are stronger than you think and braver than you feel.",
            "Believe in yourself, even when no one else does.",
            "Tumhara waqt aayega. Bas mehnat jaari rakho.",
            "Khud ko itna kamzor mat samjho, tum mein taqat hai har mushkil se ladne ki.",
            "Every day is a fresh start. Breathe and begin again.",
            "Muskuraiye, aapki muskurahat kisi ki umeed ban sakti hai.",
            "Don't give up, great things take time.",
            "Zindagi jeene ke do hi tareeke hote hain – ek, jo ho raha hai hone do; doosra, zimmedari uthao aur badal do.",
            "Stay patient and trust your journey.",
            "Aasman mein udne ke liye paron ki nahi, hauslon ki zarurat hoti hai."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Toolbar & Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_drawer,
                R.string.close_drawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Initialize Cards
        cardProfile = findViewById(R.id.card_profile);
        cardHealth = findViewById(R.id.card_health_checkup);
        cardPeriod = findViewById(R.id.card_period_tracker);
        cardBot = findViewById(R.id.card_talk_to_bot);

        // Optional: Daily motivational quote
        textQuote = findViewById(R.id.quoteText);
        int randomIndex = (int) (Math.random() * quotesAndShayaris.length);
        textQuote.setText(quotesAndShayaris[randomIndex]);

        // Apply animations to CardViews
        applyCardAnimation(cardProfile);
        applyCardAnimation(cardHealth);
        applyCardAnimation(cardPeriod);

        // Set up card click listeners
        cardProfile.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, ProfileActivity.class)));
        cardHealth.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, HealthCheckupActivity.class)));
        cardPeriod.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, PeriodTrackerActivity.class)));

        // Floating button click - open Copilot chatbot
        cardBot.setOnClickListener(v -> {
            Animation slideUp = AnimationUtils.loadAnimation(DashboardActivity.this, R.anim.slide_up_fade);
            cardBot.startAnimation(slideUp);
            String url = "https://copilotstudio.microsoft.com/environments/Default-7c7f599b-538b-471f-88e8-718c1ca8b0e5/bots/a6ad9d0e-a3e3-ef11-be20-7c1e523ae1e5/adaptive/cdf7d7a4-c7e9-4fa2-8b45-6dc382905eb6/triggers/main";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });

        // Fetch header view and username TextView
        View headerView = navigationView.getHeaderView(0);
        TextView navUserNameTextView = headerView.findViewById(R.id.nav_user_name);

        // Fetch user email from SharedPreferences (you can also use Intent extra if you prefer)
        String userEmail = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                .getString("user_email", null);

        // If email is found, fetch user name from Room database
        if (userEmail != null) {
            new Thread(() -> {
                UserDao userDao = AppDatabase.getInstance(getApplicationContext()).userDao();
                User user = userDao.getUserByEmail(userEmail);

                if (user != null) {
                    runOnUiThread(() -> navUserNameTextView.setText(user.name));
                }
            }).start();
        }

    }

    private void applyCardAnimation(CardView cardView) {
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade);
        cardView.startAnimation(slideUp);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_health_checkup) {
            startActivity(new Intent(this, HealthCheckupActivity.class));
        } else if (id == R.id.nav_period_tracker) {
            startActivity(new Intent(this, PeriodTrackerActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingActivity.class));
        } else if (id == R.id.nav_exit) {
            finishAffinity();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
