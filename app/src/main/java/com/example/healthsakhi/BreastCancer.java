package com.example.healthsakhi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthsakhi.R;

public class BreastCancer extends AppCompatActivity {

    RadioGroup q1Group, q2Group, q3Group, q4Group, q5Group;
    Button checkButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breast_cancer);

        q1Group = findViewById(R.id.q1_group);
        q2Group = findViewById(R.id.q2_group);
        q3Group = findViewById(R.id.q3_group);
        q4Group = findViewById(R.id.q4_group);
        q5Group = findViewById(R.id.q5_group);
        checkButton = findViewById(R.id.checkButton);

        checkButton.setOnClickListener(view -> evaluateAnswers());
    }

    private void evaluateAnswers() {
        int score = 0;

        if (getSelectedAnswer(q1Group)) score++;
        if (getSelectedAnswer(q2Group)) score++;
        if (getSelectedAnswer(q3Group)) score++;
        if (getSelectedAnswer(q4Group)) score++;
        if (getSelectedAnswer(q5Group)) score++;

        String result;
        if (score >= 3) {
            result = "⚠️ You may be at risk for breast cancer. Please consult a doctor for further evaluation.";
        } else if (score == 2) {
            result = "❗ You may have mild symptoms. Stay alert and consider consulting a doctor.";
        } else {
            result = "✅ You seem to be safe, but always stay aware of any new symptoms.";
        }

        new AlertDialog.Builder(this)
                .setTitle("Breast Cancer Checkup Result")
                .setMessage(result)
                .setPositiveButton("OK", null)
                .show();
    }

    private boolean getSelectedAnswer(RadioGroup group) {
        int selectedId = group.getCheckedRadioButtonId();
        if (selectedId == -1) return false; // No answer selected
        RadioButton selectedButton = findViewById(selectedId);
        return selectedButton.getText().toString().equalsIgnoreCase("Yes");
    }
}
