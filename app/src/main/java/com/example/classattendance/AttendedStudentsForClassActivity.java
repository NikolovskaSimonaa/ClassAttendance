package com.example.classattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AttendedStudentsForClassActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    private RecyclerView recyclerView;
    private TextView subjectName;
    private AttendedUserAdapter studentAdapter;
    private TextView className;
    private ImageButton back;
    private Button buttonSeeSurveyAnswers;
    private ClassModel c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended_students_for_class);

        databaseHandler = new DatabaseHandler(this);
        int userId = getIntent().getIntExtra("USER_ID", -1);
        int subjectId = getIntent().getIntExtra("SUBJECT_ID", -1);
        int classId = getIntent().getIntExtra("CLASS_ID", -1);

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
            c = databaseHandler.getClassById(classId);
            List<UserModel> students = databaseHandler.getAttendedStudentsForClass(classId);
            recyclerView = findViewById(R.id.studentsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            studentAdapter = new AttendedUserAdapter(this,students);
            recyclerView.setAdapter(studentAdapter);
            if (c != null) {
                className.setText(c.getTitle());
            } else {
                className.setText("Class topic not found.");
            }
        } else {
            className.setText("Invalid class ID");
        }

        back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ClassesForSubjectActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("SUBJECT_ID", subjectId);
                startActivity(intent);
            }
        });
        buttonSeeSurveyAnswers= findViewById(R.id.buttonSeeSurveyAnswers);
        if(System.currentTimeMillis() < Long.parseLong(c.getEndTimestamp())) {
            buttonSeeSurveyAnswers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "You can't see the answers until the class is finished", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            buttonSeeSurveyAnswers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SurveyAnswersForClassActivity.class);
                    intent.putExtra("USER_ID", userId);
                    intent.putExtra("SUBJECT_ID", subjectId);
                    intent.putExtra("CLASS_ID", c.getId());
                    startActivity(intent);
                }
            });
        }
    }
}