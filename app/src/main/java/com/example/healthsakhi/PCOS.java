package com.example.healthsakhi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PCOS extends AppCompatActivity {

    private RadioGroup radioGroupIrregularPeriods, radioGroupExcessiveHairGrowth,
            radioGroupDifficultyLosingWeight, radioGroupAcneOilySkin, radioGroupThinningHair;

    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcos);

        // Bind views
        radioGroupIrregularPeriods = findViewById(R.id.radioGroupIrregularPeriods);
        radioGroupExcessiveHairGrowth = findViewById(R.id.radioGroupExcessiveHairGrowth);
        radioGroupDifficultyLosingWeight = findViewById(R.id.radioGroupDifficultyLosingWeight);
        radioGroupAcneOilySkin = findViewById(R.id.radioGroupAcneOilySkin);
        radioGroupThinningHair = findViewById(R.id.radioGroupThinningHair);

        textResult = findViewById(R.id.textResult);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // Set button click listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPCOSStatus();
            }
        });
    }

    private void checkPCOSStatus() {
        if (!isAllQuestionsAnswered()) {
            Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
            return;
        }

        int score = 0;

        score += getScoreFromRadioGroup(radioGroupIrregularPeriods);
        score += getScoreFromRadioGroup(radioGroupExcessiveHairGrowth);
        score += getScoreFromRadioGroup(radioGroupDifficultyLosingWeight);
        score += getScoreFromRadioGroup(radioGroupAcneOilySkin);
        score += getScoreFromRadioGroup(radioGroupThinningHair);

        String resultText;
        if (score >= 4) {
            resultText = "High possibility of PCOS.\nPlease consult a doctor for further diagnosis.";
        } else if (score == 3) {
            resultText = "Moderate symptoms of PCOS.\nMonitoring and a checkup are recommended.";
        } else {
            resultText = "Low chance of PCOS.\nStay healthy and keep track of your symptoms.";
        }

        textResult.setText(resultText);
    }

    private int getScoreFromRadioGroup(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) return 0;

        RadioButton selected = findViewById(selectedId);
        return selected.getText().toString().equalsIgnoreCase("Yes") ? 1 : 0;
    }

    private boolean isAllQuestionsAnswered() {
        return radioGroupIrregularPeriods.getCheckedRadioButtonId() != -1 &&
                radioGroupExcessiveHairGrowth.getCheckedRadioButtonId() != -1 &&
                radioGroupDifficultyLosingWeight.getCheckedRadioButtonId() != -1 &&
                radioGroupAcneOilySkin.getCheckedRadioButtonId() != -1 &&
                radioGroupThinningHair.getCheckedRadioButtonId() != -1;
    }
}
