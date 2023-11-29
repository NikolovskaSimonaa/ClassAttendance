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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class StudentHomeScreenActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SSubjectAdapter subjectAdapter;
    private TextView username;
    private Button buttonLogout;
    private Button buttonMyCallendar;
    private Button allSubjects;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_screen);

        databaseHandler = new DatabaseHandler(this);
        int userId = getIntent().getIntExtra("USER_ID", -1);
        username = findViewById(R.id.username);
        if (userId != -1) {
            UserModel user = databaseHandler.getUserById(userId);
            if (user != null) {
                username.setText(user.getEmail());

            } else {
                username.setText("User email not found.");
            }
            List<SubjectModel> subjects = getEnrolledSubjectsFromDatabase(userId);
            recyclerView = findViewById(R.id.subjectsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            subjectAdapter = new SSubjectAdapter(subjects);
            recyclerView.setAdapter(subjectAdapter);
        } else {
            username.setText("Invalid user ID");
        }

        buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        allSubjects = findViewById(R.id.buttonAllSubjects);
        allSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EnrollActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);

            }
        });
        buttonMyCallendar = findViewById(R.id.buttonMyCalendar);
        buttonMyCallendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyCalendarActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
    }
   private List<SubjectModel> getEnrolledSubjectsFromDatabase(int userId) {
       List<SubjectModel> subjects = new ArrayList<>();
       SQLiteDatabase db = databaseHandler.getReadableDatabase();
       Cursor cursor = databaseHandler.getSubjectsForUser(userId);

       if (cursor != null && cursor.moveToFirst()) {
           do {
               @SuppressLint("Range") int subjectId = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.COLUMN_ID));
               @SuppressLint("Range") String subjectName = cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_SUBJECT_NAME));

               SubjectModel subject = new SubjectModel(subjectId, subjectName);
               subjects.add(subject);
           } while (cursor.moveToNext());

           cursor.close();
       }

       db.close();
       return subjects;
   }
}