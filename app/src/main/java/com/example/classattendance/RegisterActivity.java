package com.example.classattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText pass;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.areaName);
        surname = findViewById(R.id.areaSurname);
        button = findViewById(R.id.registerButton);
        email = findViewById(R.id.areaEmail);
        pass = findViewById(R.id.areaPass);
        RadioButton radioButtonYes = findViewById(R.id.radioButtonYes);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isProfessor;
                String n = name.getText().toString().trim();
                String s = surname.getText().toString().trim();
                String e = email.getText().toString().trim();
                String p = pass.getText().toString().trim();
                UserModel um;
                if (radioButtonYes.isChecked()) {
                    isProfessor = "true";
                } else {
                    isProfessor = "false";
                }
                if (e.isEmpty() && p.isEmpty() && n.isEmpty() && s.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "In order to register fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHandler databaseHandler=new DatabaseHandler(RegisterActivity.this);
                    if(databaseHandler.checkEmail(e)==true){
                        Toast.makeText(RegisterActivity.this, "User already exists.", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            um = new UserModel(-1, name.getText().toString(), surname.getText().toString(), isProfessor, email.getText().toString(), pass.getText().toString());
                            boolean success = databaseHandler.RegisterUser(um);
                            if(success){
                                Toast.makeText(RegisterActivity.this, "User added successfully.", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } catch (Exception exception) {
                            Toast.makeText(RegisterActivity.this, "Error creating user,try again.", Toast.LENGTH_SHORT).show();
                            um = new UserModel(-1, "error", "error", "error", "error", "error");
                        }
                    }
                }
            }
        });
    }
    public void goToLogin(View view){
        Intent newActivityIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(newActivityIntent);
    }
}