package com.example.classattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class SurveyAnswersForClassActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    private ImageButton back;
    private SurveyAdapter surveyAdapter;
    private RecyclerView recyclerView;
    private TextView subjectName,className;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_answers_for_class);

        databaseHandler = new DatabaseHandler(this);
        int userId = getIntent().getIntExtra("USER_ID", -1);
        int subjectId = getIntent().getIntExtra("SUBJECT_ID", -1);
        int classId = getIntent().getIntExtra("CLASS_ID", -1);

        back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AttendedStudentsForClassActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("SUBJECT_ID", subjectId);
                intent.putExtra("CLASS_ID", classId);
                startActivity(intent);
            }
        });

        subjectName = findViewById(R.id.subjectName);
        className = findViewById(R.id.className);
        if (subjectId != -1) {
            SubjectModel s = databaseHandler.getSubjectById(subjectId);
            if (s != null) {
                subjectName.setText(s.getName());
            } else {
                subjectName.setText("Subject not found.");
            }
        } else {
            subjectName.setText("Invalid subject ID");
        }
        if (classId != -1) {
            ClassModel c = databaseHandler.getClassById(classId);
            List<SurveyModel> answers = databaseHandler.getSurveysForClass(classId);
            recyclerView = findViewById(R.id.answerList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            surveyAdapter = new SurveyAdapter(this,answers);
            recyclerView.setAdapter(surveyAdapter);
            if (c != null) {
                className.setText(c.getTitle());
            } else {
                className.setText("Class topic not found.");
            }
        } else {
            className.setText("Invalid class ID");
        }
    }
}