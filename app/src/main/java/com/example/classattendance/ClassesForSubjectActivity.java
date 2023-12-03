package com.example.classattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClassesForSubjectActivity extends AppCompatActivity {
    private TextView subjectName;
    DatabaseHandler databaseHandler;
    private ClassesForSubjectAdapter classesForSubjectAdapter;
    private ImageButton back;
    private RecyclerView recyclerView;
    int userId,subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_for_subject);
        subjectId = getIntent().getIntExtra("SUBJECT_ID", -1);
        userId = getIntent().getIntExtra("USER_ID", -1);
        subjectName = findViewById(R.id.subjectName);
        databaseHandler=new DatabaseHandler(ClassesForSubjectActivity.this);
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
        back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassesForSubjectActivity.this, ProfHomeScreenActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
        List<ClassModel> classes = databaseHandler.getClassesForSubject(subjectId);
        recyclerView = findViewById(R.id.classesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classesForSubjectAdapter = new ClassesForSubjectAdapter(this,classes,databaseHandler,userId,subjectId);
        recyclerView.setAdapter(classesForSubjectAdapter);
    }
}