package com.example.healthsakhi;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;


public class HealthCheckupActivity extends AppCompatActivity {

    CardView cardPCOS, cardPCOD, cardBreastCancer, cardCervicalCancer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_checkup);

        cardPCOS = findViewById(R.id.card_pcos);
        cardPCOD = findViewById(R.id.card_pcod);
        cardBreastCancer = findViewById(R.id.card_breast_cancer);
        cardCervicalCancer = findViewById(R.id.card_cervical_cancer);

        cardPCOS.setOnClickListener(v -> {
            Intent intent = new Intent(HealthCheckupActivity.this, PCOS.class);
            startActivity(intent);
        });

        cardPCOD.setOnClickListener(v -> {
            Intent intent = new Intent(HealthCheckupActivity.this, PCOD.class);
            startActivity(intent);
        });

        cardBreastCancer.setOnClickListener(v -> {
            Intent intent = new Intent(HealthCheckupActivity.this, BreastCancer.class);
            startActivity(intent);
        });

        cardCervicalCancer.setOnClickListener(v -> {
            Intent intent = new Intent(HealthCheckupActivity.this, CervicalCancer.class);
            startActivity(intent);
        });
    }
}
