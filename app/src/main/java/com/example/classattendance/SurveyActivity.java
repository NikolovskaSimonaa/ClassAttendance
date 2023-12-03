package com.example.classattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity {
    private TextView areaClass;
    private Button submitb;
    private EditText areaComment;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        int userId = getIntent().getIntExtra("USER_ID", -1);
        int classId = getIntent().getIntExtra("CLASS_ID", -1);
        areaClass = findViewById(R.id.areaClass);
        submitb= findViewById(R.id.submitb);
        areaComment = findViewById(R.id.areaComment);
        databaseHandler=new DatabaseHandler(SurveyActivity.this);
        if (classId!= -1) {
            ClassModel c = databaseHandler.getClassById(classId);
            if (c != null) {
                areaClass.setText(c.getTitle());
            } else {
                areaClass.setText("Class not found.");
            }
        } else {
            areaClass.setText("Invalid class ID");
        }
        RadioButton radiob1 = findViewById(R.id.radiob1);
        RadioButton radiob2 = findViewById(R.id.radiob2);
        RadioButton radiob3 = findViewById(R.id.radiob3);
        RadioButton radiob4 = findViewById(R.id.radiob4);

        submitb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grade;
                String c = areaComment.getText().toString().trim();
                SurveyModel sm;
                if (radiob1.isChecked()) grade="1";
                else if (radiob2.isChecked()) grade="2";
                else if (radiob3.isChecked()) grade="3";
                else if (radiob4.isChecked()) grade="4";
                else grade="5";

                if (c.isEmpty()) {
                    Toast.makeText(SurveyActivity.this, "Please write a short comment about this class", Toast.LENGTH_SHORT).show();
                } else {
                    try{
                        sm = new SurveyModel(-1, grade, areaComment.getText().toString());
                        Toast.makeText(SurveyActivity.this, "Survey saved successfully.", Toast.LENGTH_SHORT).show();
                    } catch(Exception exception) {
                        Toast.makeText(SurveyActivity.this, "Error, this survey was not saved. Try again.", Toast.LENGTH_SHORT).show();
                        sm = new SurveyModel(-1, "error", "error");
                    }

                    DatabaseHandler databaseHandler=new DatabaseHandler(SurveyActivity.this);
                    boolean success = databaseHandler.NewSurvey(sm, userId, classId);
                    Toast.makeText(SurveyActivity.this, "Success= "+success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), StudentHomeScreenActivity.class);
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);
                }
            }
        });
    }
}