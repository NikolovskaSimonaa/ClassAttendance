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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class ProfHomeScreenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PSubjectAdapter subjectAdapter;
    private Button buttonNewSubject;
    private Button buttonLogout;
    private TextView username;
    private DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_home_screen);

        databaseHandler = new DatabaseHandler(this);
        List<SubjectModel> subjects = getSubjectsFromDatabase();

        int userId = getIntent().getIntExtra("USER_ID", -1);
        username = findViewById(R.id.username);
        if (userId != -1) {
            UserModel user = databaseHandler.getUserById(userId);
            if (user != null) {
                username.setText(user.getEmail());
            } else {
                username.setText("User email not found.");
            }
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
        buttonNewSubject = findViewById(R.id.buttonNewSubject);
        buttonNewSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewSubjectActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);

            }
        });

        recyclerView = findViewById(R.id.subjectsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectAdapter = new PSubjectAdapter(subjects,userId);
        recyclerView.setAdapter(subjectAdapter);
        username = findViewById(R.id.username);
    }
    private List<SubjectModel> getSubjectsFromDatabase() {
        List<SubjectModel> subjects = new ArrayList<>();
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = databaseHandler.getSubjects();

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