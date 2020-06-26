package com.example.practicaloginschoolsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PanelAdmin extends AppCompatActivity {
    public TextView textViewNameUser;
    public TextView textViewRolUser;
    public RadioButton radioButtonStudent, radioButtonTeacher, radioButtonCareer, radioButtonSubject, radioButtonTeacherSubject, radioButtonStudentSubject, radioButtonQueries;
    Button buttonSalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                                                                 
        setContentView(R.layout.activity_paneladmin);
        this.asociarCampos();

        Bundle parametros = this.getIntent().getExtras();
        if(parametros != null){
            String nameUser = parametros.getString("nameUser");
            textViewNameUser.setText(nameUser);
            String nameRolUser = parametros.getString("nameRol");
            textViewRolUser.setText(nameRolUser);
        }

        radioButtonSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Subject.class);
                startActivity(intent);
            }
        });

        radioButtonCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Career.class);
                startActivity(intent);
            }
        });

        radioButtonTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Teacher.class);
                startActivity(intent);
            }
        });

        radioButtonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Student.class);
                startActivity(intent);
            }
        });

        radioButtonTeacherSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeacherSubject.class);
                startActivity(intent);
            }
        });

        radioButtonStudentSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentSubject.class);
                startActivity(intent);
            }
        });

        radioButtonQueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Queries.class);
                startActivity(intent);
            }
        });

        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    public void asociarCampos() {
        this.textViewNameUser = findViewById(R.id.textViewFullNameUser);
        this.textViewRolUser = findViewById(R.id.textViewRolUser);
        this.buttonSalir = findViewById(R.id.buttonSalir);
        this.radioButtonCareer = findViewById(R.id.radioButtonCareer);
        this.radioButtonStudent = findViewById(R.id.radioButtonStudent);
        this.radioButtonTeacher = findViewById(R.id.radioButtonTeacher);
        this.radioButtonSubject = findViewById(R.id.radioButtonSubject);
        this.radioButtonTeacherSubject = findViewById(R.id.radioButtonTeacherSubject);
        this.radioButtonStudentSubject = findViewById(R.id.radioButtonStudentSubject);
        this.radioButtonQueries = findViewById(R.id.radioButtonQueries);
    }
}
