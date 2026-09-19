package com.example.healthsakhi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthsakhi.R;
public class CervicalCancer extends AppCompatActivity {

    RadioGroup bleedingGroup, pelvicPainGroup, dischargeGroup, backPainGroup, periodsGroup;
    Button checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cervical_cancer);

        bleedingGroup = findViewById(R.id.bleeding_group);
        pelvicPainGroup = findViewById(R.id.pelvic_pain_group);
        dischargeGroup = findViewById(R.id.discharge_group);
        backPainGroup = findViewById(R.id.backpain_group);
        periodsGroup = findViewById(R.id.periods_group);
        checkButton = findViewById(R.id.checkButton);

        checkButton.setOnClickListener(view -> evaluateAnswers());
    }

    private void evaluateAnswers() {
        int score = 0;

        if (isYesSelected(bleedingGroup)) score++;
        if (isYesSelected(pelvicPainGroup)) score++;
        if (isYesSelected(dischargeGroup)) score++;
        if (isYesSelected(backPainGroup)) score++;
        if (isYesSelected(periodsGroup)) score++;

        String result;
        if (score >= 3) {
            result = "⚠️ You may be at risk for cervical cancer. Please consult a gynecologist for proper screening.";
        } else if (score == 2) {
            result = "❗ You may be experiencing early symptoms. Stay alert and consider a medical consultation.";
        } else {
            result = "✅ No major symptoms detected. Keep monitoring your health and stay informed.";
        }

        new AlertDialog.Builder(this)
                .setTitle("Cervical Cancer Checkup Result")
                .setMessage(result)
                .setPositiveButton("OK", null)
                .show();
    }

    private boolean isYesSelected(RadioGroup group) {
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId == -1) return false;
        RadioButton selected = findViewById(selectedId);
        return selected.getText().toString().equalsIgnoreCase(getString(R.string.yes));
    }
}
