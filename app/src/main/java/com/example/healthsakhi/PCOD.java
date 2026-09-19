package com.example.healthsakhi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PCOD extends AppCompatActivity {

    RadioGroup q1, q2, q3, q4, q5;
    Button btnCheck;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcod);

        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        q4 = findViewById(R.id.q4);
        q5 = findViewById(R.id.q5);
        btnCheck = findViewById(R.id.btnCheck);
        tvResult = findViewById(R.id.tvResult);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int score = 0;

                if (q1.getCheckedRadioButtonId() == R.id.q1_yes) score++;
                if (q2.getCheckedRadioButtonId() == R.id.q2_yes) score++;
                if (q3.getCheckedRadioButtonId() == R.id.q3_yes) score++;
                if (q4.getCheckedRadioButtonId() == R.id.q4_yes) score++;
                if (q5.getCheckedRadioButtonId() == R.id.q5_yes) score++;

                String result;
                if (score >= 3) {
                    result = "You might be showing early signs of PCOD. Please consult a healthcare professional.";
                } else {
                    result = "You seem to have minimal symptoms of PCOD, but always consult a doctor for confirmation.";
                }

                tvResult.setText(result);
                tvResult.setVisibility(View.VISIBLE);
            }
        });
    }
}
