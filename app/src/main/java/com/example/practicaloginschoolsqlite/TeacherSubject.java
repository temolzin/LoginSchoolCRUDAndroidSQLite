package com.example.practicaloginschoolsqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practicaloginschoolsqlite.dao.TeacherDAO;
import com.example.practicaloginschoolsqlite.dao.TeacherSubjectDAO;
import com.example.practicaloginschoolsqlite.dao.SubjectDAO;
import com.example.practicaloginschoolsqlite.dto.TeacherDTO;
import com.example.practicaloginschoolsqlite.dto.TeacherSubjectDTO;
import com.example.practicaloginschoolsqlite.dto.SubjectDTO;

import java.util.ArrayList;

public class TeacherSubject extends AppCompatActivity {

    EditText editTextIdTeacherSubject;
    Spinner spinnerTeacher;
    Spinner spinnerSubject;

    TeacherDTO teacherDTO;
    SubjectDTO subjectDTO;

    TeacherDAO teacherDAO;
    SubjectDAO subjectDAO;

    Button buttonRegisterTeacherSubject, buttonConsultTeacherSubject, buttonUpdateTeacherSubject, buttonDeleteTeacherSubject, buttonConsultAllTeacherSubject;

    TeacherSubjectDAO teacherSubjectDAO;
    TeacherSubjectDTO teacherSubjectDTO;
    
    AdapterTeacherSubject adapterTeacherSubject;

    ArrayAdapter<SubjectDTO> arrayAdapterSubject;
    ArrayAdapter<TeacherDTO> arrayAdapterTeacher;

    ArrayList<TeacherDTO> arrayListTeachers;
    ArrayList<SubjectDTO> arrayListSubjects;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_subject);

        this.linkFields();

        //Se inicilizan los objectos DAO
        teacherDAO = new TeacherDAO(this);
        subjectDAO = new SubjectDAO(this);

        //Se llenan los spinners
        this.fillSpinnerTeachers();
        this.fillSpinnerSubjects();

        teacherSubjectDAO = new TeacherSubjectDAO(this);

        //Se inhabilita el boton de actualizar y eliminar hasta que el usuario
        buttonUpdateTeacherSubject.setEnabled(false);
        buttonDeleteTeacherSubject.setEnabled(false);

        //Botón que registra al rol de usuario en la base de datos
        buttonRegisterTeacherSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdTeacherSubject.getText().toString().isEmpty() ) {
                    Toast.makeText(TeacherSubject.this, "Todos los campos deben de ser llenados", Toast.LENGTH_SHORT).show();
                } else {
                    teacherDTO = (TeacherDTO) spinnerTeacher.getSelectedItem();
                    subjectDTO = (SubjectDTO) spinnerSubject.getSelectedItem();
                    teacherSubjectDTO = new TeacherSubjectDTO(editTextIdTeacherSubject.getText().toString(), teacherDTO, subjectDTO);
                    teacherSubjectDAO.insert(teacherSubjectDTO);
                    cleanFields();
                }
            }
        });

        //Boton para consultar por el id del rol de usuario
        buttonConsultTeacherSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se valida que el id usuario no este vacío
                if(editTextIdTeacherSubject.getText().toString().isEmpty()) {
                    Toast.makeText(TeacherSubject.this, "Debes ingresar un ID", Toast.LENGTH_SHORT).show();
                    buttonUpdateTeacherSubject.setEnabled(false);
                    buttonDeleteTeacherSubject.setEnabled(false);
                } else {
                    teacherSubjectDTO = teacherSubjectDAO.readbyid(editTextIdTeacherSubject.getText().toString());
                    if(teacherSubjectDTO == null) {
                        Toast.makeText(TeacherSubject.this, "El ID ingresado no está registrado en la base de datos", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    } else {

                        spinnerTeacher.setSelection(arrayListTeachers.indexOf(teacherSubjectDTO.getTeacher()));
                        spinnerSubject.setSelection(arrayListSubjects.indexOf(teacherSubjectDTO.getTeacher()));

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateTeacherSubject.setEnabled(true);
                        buttonDeleteTeacherSubject.setEnabled(true);
                    }
                }
            }
        });

        //Botón que actualiza los campos del rol de usuario
        buttonUpdateTeacherSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdTeacherSubject.getText().toString().isEmpty() && spinnerTeacher.toString().isEmpty()) {
                    Toast.makeText(TeacherSubject.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    teacherSubjectDTO = new TeacherSubjectDTO(editTextIdTeacherSubject.getText().toString(), (TeacherDTO) spinnerTeacher.getSelectedItem(), (SubjectDTO) spinnerSubject.getSelectedItem());
                    teacherSubjectDAO.update(teacherSubjectDTO);
                    cleanFields();
                }

            }
        });

        //Botón que elimina los campos del usuario
        buttonDeleteTeacherSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdTeacherSubject.getText().toString().isEmpty() && spinnerTeacher.toString().isEmpty()) {
                    Toast.makeText(TeacherSubject.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    teacherSubjectDTO = new TeacherSubjectDTO(editTextIdTeacherSubject.getText().toString(), (TeacherDTO) spinnerTeacher.getSelectedItem(), (SubjectDTO)spinnerSubject.getSelectedItem());
                    teacherSubjectDAO.delete(teacherSubjectDTO.getIdTeacherSubject());
                    cleanFields();
                }
            }
        });

        //Para consultar todos los rol de usuarios y mostrarlos en una alerta con un listView
        buttonConsultAllTeacherSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterTeacherSubject = new AdapterTeacherSubject(TeacherSubject.this, teacherSubjectDAO.read());

                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherSubject.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewAlert = inflater.inflate(R.layout.activity_alert_teacher_subject, null);
                builder.setView(viewAlert);

                ListView listViewTeacherSubjects = viewAlert.findViewById(R.id.ListViewTeacherSubject);
                listViewTeacherSubjects.setAdapter(adapterTeacherSubject);

                final AlertDialog dialog = builder.create();
                dialog.show();

                listViewTeacherSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        teacherSubjectDTO = (TeacherSubjectDTO) parent.getItemAtPosition(position);
                        editTextIdTeacherSubject.setText(teacherSubjectDTO.getIdTeacherSubject());
                        spinnerTeacher.setSelection(arrayListTeachers.indexOf(teacherSubjectDTO.getTeacher()));
                        spinnerSubject.setSelection(arrayListSubjects.indexOf(teacherSubjectDTO.getSubject()));

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateTeacherSubject.setEnabled(true);
                        buttonDeleteTeacherSubject.setEnabled(true);

                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public void linkFields() {
        editTextIdTeacherSubject = findViewById(R.id.editTextIdTeacherSubject);
        spinnerTeacher = findViewById(R.id.spinnerTeacherTS);
        spinnerSubject = findViewById(R.id.spinnerSubjectTS);
        buttonRegisterTeacherSubject = findViewById(R.id.buttonRegisterTeacherSubject);
        buttonConsultTeacherSubject = findViewById(R.id.buttonConsultTeacherSubject);
        buttonConsultAllTeacherSubject = findViewById(R.id.buttonConsultAllTeacherSubject);
        buttonUpdateTeacherSubject = findViewById(R.id.buttonUpdateTeacherSubject);
        buttonDeleteTeacherSubject = findViewById(R.id.buttonDeleteTeacherSubject);
    }

    private void fillSpinnerTeachers() {
        arrayListTeachers = teacherDAO.read();

        //Se llena el Spinner
        arrayAdapterTeacher = new ArrayAdapter<TeacherDTO>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayListTeachers);
        spinnerTeacher.setAdapter(arrayAdapterTeacher);
    }

    private void fillSpinnerSubjects() {
        arrayListSubjects = subjectDAO.read();

        //Se llena el Spinner
        arrayAdapterSubject = new ArrayAdapter<SubjectDTO>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayListSubjects);
        spinnerSubject.setAdapter(arrayAdapterSubject);
    }

    private void cleanFields() {
        this.editTextIdTeacherSubject.setText("");
        this.spinnerTeacher.setSelection(0);
        this.spinnerSubject.setSelection(0);
    }
}
