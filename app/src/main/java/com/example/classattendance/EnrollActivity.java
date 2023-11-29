package com.example.classattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EnrollActivity extends AppCompatActivity {
    private ImageButton buttonBack;
    private ESubjectAdapter subjectAdapter;
    private RecyclerView recView;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        buttonBack = findViewById(R.id.buttonBack);

        int userId = getIntent().getIntExtra("USER_ID", -1);

        databaseHandler = new DatabaseHandler(this);
        List<SubjectModel> subjects = getNotEnrolledSubjectsFromDatabase(userId);
        recView = findViewById(R.id.enrollSubjectsList);
        recView.setLayoutManager(new LinearLayoutManager(this));
        subjectAdapter = new ESubjectAdapter(subjects,userId);
        subjectAdapter.setEnrollClickListener(new ESubjectAdapter.EnrollClickListener() {
            @Override
            public void onEnrollClick(int subjectId, int userId) {
                enrollUserInSub(subjectId, userId);
            }
        });
        recView.setAdapter(subjectAdapter);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnrollActivity.this, StudentHomeScreenActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
    }
    private List<SubjectModel> getNotEnrolledSubjectsFromDatabase(int userId) {
        List<SubjectModel> subjects = new ArrayList<>();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = databaseHandler.getSubjectsThatUserIsNotEnrolledTo(userId);

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
    private void enrollUserInSub(int subjectId, int userId) {
        if (databaseHandler.enrollUserInSubject(userId, subjectId)) {
            List<SubjectModel> updatedSubjects = getNotEnrolledSubjectsFromDatabase(userId);
            subjectAdapter.setSubjects(updatedSubjects);
            subjectAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}