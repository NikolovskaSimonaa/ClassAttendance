package com.example.classattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class MyCalendarActivity extends AppCompatActivity {
    private ImageButton back;
    DatabaseHandler databaseHandler;
    private RecyclerView recyclerView;
    private ClassAdapter classAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        databaseHandler = new DatabaseHandler(this);
        int userId = getIntent().getIntExtra("USER_ID", -1);
        back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StudentHomeScreenActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recyclerViewClasses);
        List<ClassModel> classes=databaseHandler.getClassesForUser(userId);
        if(classes==null){
            Toast.makeText(MyCalendarActivity.this, "You don't have any classes", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), StudentHomeScreenActivity.class);
            intent.putExtra("USER_ID", userId); //pass the signed user_id to the home screen
            startActivity(intent);
        }else {
            classAdapter = new ClassAdapter(this, classes, databaseHandler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(classAdapter);
        }
    }
}