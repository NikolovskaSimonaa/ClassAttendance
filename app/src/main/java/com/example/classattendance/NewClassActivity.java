package com.example.classattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class NewClassActivity extends AppCompatActivity {
    private ImageButton buttonBack;
    private EditText areaTitle;
    private DatePicker datePicker;
    private TimePicker timePickerStart;
    private TimePicker timePickerEnd;
    private Button buttonSubmit;
    DatabaseHandler databaseHandler;
    private int subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);
        int userId = getIntent().getIntExtra("USER_ID", -1);
        buttonBack = findViewById(R.id.buttonBack);
        areaTitle = findViewById(R.id.areaTitle);
        datePicker = findViewById(R.id.datePicker);
        timePickerStart = findViewById(R.id.timePickerStart);
        timePickerEnd = findViewById(R.id.timePickerEnd);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        databaseHandler = new DatabaseHandler(this);
        subjectId = getIntent().getIntExtra("SUBJECT_ID", -1);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewClassActivity.this, ProfHomeScreenActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewClass();
            }
        });
    }
    private void createNewClass() {
        String title = areaTitle.getText().toString().trim();
        if (title.isEmpty()) {
            Toast.makeText(this, "Enter a title.", Toast.LENGTH_SHORT).show();
            return;
        }
        //get the selected date and time for the class start
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePickerStart.getHour();
        int minute = timePickerStart.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        long start = calendar.getTimeInMillis();

        //get the selected time for the class end
        int hour1 = timePickerEnd.getHour();
        int minute1 = timePickerEnd.getMinute();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(year, month, day, hour1, minute1);

        long end= calendar1.getTimeInMillis();
        if (subjectId != -1) {
            boolean isAdded = databaseHandler.addClass(subjectId, title, start, end);
            if (isAdded==true) {
                Toast.makeText(this, "Class added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid subject ID", Toast.LENGTH_SHORT).show();
        }
    }

}