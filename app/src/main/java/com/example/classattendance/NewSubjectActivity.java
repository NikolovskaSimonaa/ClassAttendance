package com.example.classattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NewSubjectActivity extends AppCompatActivity {
    private EditText name;
    private ImageButton buttonBack;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_subject);

        int userId = getIntent().getIntExtra("USER_ID", -1);

        name = findViewById(R.id.areaNameS);
        buttonBack = findViewById(R.id.buttonBack);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSubjectActivity.this, ProfHomeScreenActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString().trim();
                SubjectModel sm;
                if (n.isEmpty()) {
                    Toast.makeText(NewSubjectActivity.this, "Give your subject a name first.", Toast.LENGTH_SHORT).show();
                } else {
                    try{
                        sm = new SubjectModel(-1, name.getText().toString());
                        Toast.makeText(NewSubjectActivity.this, "Subject added successfully.", Toast.LENGTH_SHORT).show();
                    } catch(Exception exception) {
                        Toast.makeText(NewSubjectActivity.this, "Error creating subject,try again.", Toast.LENGTH_SHORT).show();
                        sm = new SubjectModel(-1, "error");
                    }

                    DatabaseHandler databaseHandler=new DatabaseHandler(NewSubjectActivity.this);
                    boolean success = databaseHandler.AddSubject(sm);
                    Toast.makeText(NewSubjectActivity.this, "Success= "+success, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(NewSubjectActivity.this, ProfHomeScreenActivity.class);
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);
                }
            }
        });

    }
}