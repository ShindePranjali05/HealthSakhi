package com.example.healthsakhi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PeriodTrackerActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView lastPeriodInfoTextView, nextPeriodTextView, ovulationDateTextView;

    // Default cycle length (28 days) and luteal phase (14 days for ovulation)
    private static final int CYCLE_LENGTH = 28;
    private static final int OVULATION_PHASE = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_tracker);

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        lastPeriodInfoTextView = findViewById(R.id.last_period_info);
        nextPeriodTextView = findViewById(R.id.tv_next_period);
        ovulationDateTextView = findViewById(R.id.tv_ovulation_date);
        Button startPeriodButton = findViewById(R.id.btn_start_period);
        Button endPeriodButton = findViewById(R.id.btn_end_period);

        // Set initial text for "Last Tracked"
        lastPeriodInfoTextView.setText(getString(R.string.last_tracked_not_available));

        // Start Period button click listener
        startPeriodButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // Get the selected date from the CalendarView
                long selectedDate = calendarView.getDate();
                // Format the selected date into a readable format
                String lastPeriodDate = formatDate(selectedDate);
                lastPeriodInfoTextView.setText(getString(R.string.last_period) + ": " + lastPeriodDate);

                // Calculate next period and ovulation date
                calculateNextPeriod(selectedDate);
            }
        });

        // End Period button click listener
        endPeriodButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // Reset the fields when the period is ended
                lastPeriodInfoTextView.setText(getString(R.string.last_tracked_not_available));
                nextPeriodTextView.setText("Next Period Start Date: --/--/----");
                ovulationDateTextView.setText("Ovulation Date: --/--/----");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void calculateNextPeriod(long lastPeriodDateMillis) {
        // Use a Calendar instance to add the cycle length (28 days) to the last period start date
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastPeriodDateMillis);

        // Calculate the next period date
        calendar.add(Calendar.DAY_OF_YEAR, CYCLE_LENGTH);

        // Calculate the ovulation date (14 days before the next period)
        Calendar ovulationCalendar = (Calendar) calendar.clone();
        ovulationCalendar.add(Calendar.DAY_OF_YEAR, -OVULATION_PHASE);

        // Format the dates to display in TextViews
        String nextPeriodDate = formatDate(calendar.getTimeInMillis());
        String ovulationDate = formatDate(ovulationCalendar.getTimeInMillis());

        // Display the dates
        nextPeriodTextView.setText("Next Period Start Date: " + nextPeriodDate);
        ovulationDateTextView.setText("Ovulation Date: " + ovulationDate);
    }

    private String formatDate(long millis) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date(millis));
    }
}
