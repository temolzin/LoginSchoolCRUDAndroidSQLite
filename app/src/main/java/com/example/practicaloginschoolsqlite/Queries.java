package com.example.practicaloginschoolsqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practicaloginschoolsqlite.dao.StudentDAO;
import com.example.practicaloginschoolsqlite.dao.SubjectDAO;
import com.example.practicaloginschoolsqlite.dao.TeacherDAO;
import com.example.practicaloginschoolsqlite.dto.RolUserDTO;
import com.example.practicaloginschoolsqlite.dto.SubjectDTO;
import com.example.practicaloginschoolsqlite.dto.TeacherDTO;


import java.util.ArrayList;

public class Queries extends AppCompatActivity {

    Spinner spinnerTypeObject;
    Spinner spinnerSubject;
    Button buttonConsultQuery;

    StudentDAO studentDAO;
    TeacherDAO teacherDAO;
    SubjectDAO subjectDAO;
    
    AdapterStudent adapterStudent;
    AdapterTeacher adapterTeacher;

    ArrayList<SubjectDTO> arrayListSubjects;
    ArrayAdapter<SubjectDTO> arrayAdapterSubject;

    String[] typeObjects;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_teacher_student_subject);

        this.linkFields();

        typeObjects = new String[]{"Profesores","Estudiantes"};

        studentDAO = new StudentDAO(this);
        teacherDAO = new TeacherDAO(this);
        subjectDAO = new SubjectDAO(this);

        fillSpinnerTypeObject();
        fillSpinnerSubjects();

        //Para consultar todos los rol de usuarios y mostrarlos en una alerta con un listView
        buttonConsultQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(spinnerTypeObject.getSelectedItem().toString().equals("Estudiantes")) {
                    SubjectDTO subjectDTO = (SubjectDTO) spinnerSubject.getSelectedItem();
                    adapterStudent = new AdapterStudent(Queries.this, studentDAO.readbyidsubject(subjectDTO));

                    AlertDialog.Builder builder = new AlertDialog.Builder(Queries.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View viewAlert = inflater.inflate(R.layout.activity_alert_student, null);
                    builder.setView(viewAlert);

                    ListView listViewUsers = viewAlert.findViewById(R.id.ListViewStudent);
                    listViewUsers.setAdapter(adapterStudent);

                    final AlertDialog dialog = builder.create();
                    dialog.show();
                } else if(spinnerTypeObject.getSelectedItem().toString().equals("Profesores")) {
                    SubjectDTO subjectDTO = (SubjectDTO) spinnerSubject.getSelectedItem();
                    adapterTeacher = new AdapterTeacher(Queries.this, teacherDAO.readbyidsubject(subjectDTO));

                    AlertDialog.Builder builder = new AlertDialog.Builder(Queries.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View viewAlert = inflater.inflate(R.layout.activity_alert_teacher, null);
                    builder.setView(viewAlert);

                    ListView listViewTeachers = viewAlert.findViewById(R.id.ListViewTeacher);
                    listViewTeachers.setAdapter(adapterTeacher);

                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }

    private void fillSpinnerTypeObject() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, typeObjects);
        spinnerTypeObject.setAdapter(adapter);
    }

    private void fillSpinnerSubjects() {
        arrayListSubjects = subjectDAO.read();

        //Se llena el Spinner
        arrayAdapterSubject = new ArrayAdapter<SubjectDTO>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayListSubjects);
        spinnerSubject.setAdapter(arrayAdapterSubject);
    }

    public void linkFields() {
        spinnerSubject = findViewById(R.id.spinnerSubjectQuery);
        spinnerTypeObject = findViewById(R.id.spinnerTypeQuery);
        buttonConsultQuery = findViewById(R.id.buttonConsultQuery);
    }

    private void cleanFields() {
        this.spinnerTypeObject.setSelection(0);
        this.spinnerSubject.setSelection(0);
    }
}
