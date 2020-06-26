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

import com.example.practicaloginschoolsqlite.dao.StudentDAO;
import com.example.practicaloginschoolsqlite.dao.StudentSubjectDAO;
import com.example.practicaloginschoolsqlite.dao.SubjectDAO;
import com.example.practicaloginschoolsqlite.dto.StudentDTO;
import com.example.practicaloginschoolsqlite.dto.StudentSubjectDTO;
import com.example.practicaloginschoolsqlite.dto.SubjectDTO;

import java.util.ArrayList;

public class StudentSubject extends AppCompatActivity {

    EditText editTextIdStudentSubject;
    Spinner spinnerStudent;
    Spinner spinnerSubject;

    StudentDTO studentDTO;
    SubjectDTO subjectDTO;

    StudentDAO studentDAO;
    SubjectDAO subjectDAO;

    Button buttonRegisterStudentSubject, buttonConsultStudentSubject, buttonUpdateStudentSubject, buttonDeleteStudentSubject, buttonConsultAllStudentSubject;

    StudentSubjectDAO studentSubjectDAO;
    StudentSubjectDTO studentSubjectDTO;
    
    AdapterStudentSubject adapterStudentSubject;

    ArrayAdapter<SubjectDTO> arrayAdapterSubject;
    ArrayAdapter<StudentDTO> arrayAdapterStudent;

    ArrayList<StudentDTO> arrayListStudents;
    ArrayList<SubjectDTO> arrayListSubjects;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_subject);

        this.linkFields();

        //Se inicializan los objetos DAO
        studentDAO = new StudentDAO(this);
        subjectDAO = new SubjectDAO(this);

        //Se llenan los spinners
        this.fillSpinnerStudents();
        this.fillSpinnerSubjects();

        studentSubjectDAO = new StudentSubjectDAO(this);

        //Se inhabilita el boton de actualizar y eliminar hasta que el usuario
        buttonUpdateStudentSubject.setEnabled(false);
        buttonDeleteStudentSubject.setEnabled(false);

        //Botón que registra al rol de usuario en la base de datos
        buttonRegisterStudentSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdStudentSubject.getText().toString().isEmpty() ) {
                    Toast.makeText(StudentSubject.this, "Todos los campos deben de ser llenados", Toast.LENGTH_SHORT).show();
                } else {
                    studentDTO = (StudentDTO) spinnerStudent.getSelectedItem();
                    subjectDTO = (SubjectDTO) spinnerSubject.getSelectedItem();
                    studentSubjectDTO = new StudentSubjectDTO(editTextIdStudentSubject.getText().toString(), studentDTO, subjectDTO);
                    studentSubjectDAO.insert(studentSubjectDTO);
                    cleanFields();
                }
            }
        });

        //Boton para consultar por el id del rol de usuario
        buttonConsultStudentSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se valida que el id usuario no este vacío
                if(editTextIdStudentSubject.getText().toString().isEmpty()) {
                    Toast.makeText(StudentSubject.this, "Debes ingresar un ID", Toast.LENGTH_SHORT).show();
                    buttonUpdateStudentSubject.setEnabled(false);
                    buttonDeleteStudentSubject.setEnabled(false);
                } else {
                    studentSubjectDTO = studentSubjectDAO.readbyid(editTextIdStudentSubject.getText().toString());
                    if(studentSubjectDTO == null) {
                        Toast.makeText(StudentSubject.this, "El ID ingresado no está registrado en la base de datos", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    } else {

                        spinnerStudent.setSelection(arrayListStudents.indexOf(studentSubjectDTO.getStudent()));
                        spinnerSubject.setSelection(arrayListSubjects.indexOf(studentSubjectDTO.getStudent()));

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateStudentSubject.setEnabled(true);
                        buttonDeleteStudentSubject.setEnabled(true);
                    }
                }
            }
        });

        //Botón que actualiza los campos del rol de usuario
        buttonUpdateStudentSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdStudentSubject.getText().toString().isEmpty() && spinnerStudent.toString().isEmpty()) {
                    Toast.makeText(StudentSubject.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    studentSubjectDTO = new StudentSubjectDTO(editTextIdStudentSubject.getText().toString(), (StudentDTO) spinnerStudent.getSelectedItem(), (SubjectDTO) spinnerSubject.getSelectedItem());
                    studentSubjectDAO.update(studentSubjectDTO);
                    cleanFields();
                }

            }
        });

        //Botón que elimina los campos del usuario
        buttonDeleteStudentSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdStudentSubject.getText().toString().isEmpty() && spinnerStudent.toString().isEmpty()) {
                    Toast.makeText(StudentSubject.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    studentSubjectDTO = new StudentSubjectDTO(editTextIdStudentSubject.getText().toString(), (StudentDTO) spinnerStudent.getSelectedItem(), (SubjectDTO)spinnerSubject.getSelectedItem());
                    studentSubjectDAO.delete(studentSubjectDTO.getIdStudentSubject());
                    cleanFields();
                }
            }
        });

        //Para consultar todos los rol de usuarios y mostrarlos en una alerta con un listView
        buttonConsultAllStudentSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterStudentSubject = new AdapterStudentSubject(StudentSubject.this, studentSubjectDAO.read());

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentSubject.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewAlert = inflater.inflate(R.layout.activity_alert_student_subject, null);
                builder.setView(viewAlert);

                ListView listViewStudentSubjects = viewAlert.findViewById(R.id.ListViewStudentSubject);
                listViewStudentSubjects.setAdapter(adapterStudentSubject);

                final AlertDialog dialog = builder.create();
                dialog.show();

                listViewStudentSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        studentSubjectDTO = (StudentSubjectDTO) parent.getItemAtPosition(position);
                        editTextIdStudentSubject.setText(studentSubjectDTO.getIdStudentSubject());

                        //Se recorre la lista para seleccionar en el spinner el objeto que selecciono el usuario para eliminarlo o editarlo
                        for(StudentDTO studentDTO : arrayListStudents) {
                            if(studentDTO.getIdStudent().equals(studentSubjectDTO.getStudent().getIdStudent())) {
                                spinnerStudent.setSelection(arrayListStudents.indexOf(studentDTO));
                            }
                        }

                        spinnerSubject.setSelection(arrayListSubjects.indexOf(studentSubjectDTO.getSubject()));

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateStudentSubject.setEnabled(true);
                        buttonDeleteStudentSubject.setEnabled(true);

                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public void linkFields() {
        editTextIdStudentSubject = findViewById(R.id.editTextIDStudentSubject);
        spinnerStudent = findViewById(R.id.spinnerStudentSS);
        spinnerSubject = findViewById(R.id.spinnerSubjectSS);
        buttonRegisterStudentSubject = findViewById(R.id.buttonRegisterStudentSubject);
        buttonConsultStudentSubject = findViewById(R.id.buttonConsultStudentSubject);
        buttonConsultAllStudentSubject = findViewById(R.id.buttonConsultAllStudentSubject);
        buttonUpdateStudentSubject = findViewById(R.id.buttonUpdateStudentSubject);
        buttonDeleteStudentSubject = findViewById(R.id.buttonDeleteStudentSubject);
    }

    private void fillSpinnerStudents() {
        arrayListStudents = studentDAO.read();

        //Se llena el Spinner
        arrayAdapterStudent = new ArrayAdapter<StudentDTO>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayListStudents);
        spinnerStudent.setAdapter(arrayAdapterStudent);
    }

    private void fillSpinnerSubjects() {
        arrayListSubjects = subjectDAO.read();

        //Se llena el Spinner
        arrayAdapterSubject = new ArrayAdapter<SubjectDTO>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayListSubjects);
        spinnerSubject.setAdapter(arrayAdapterSubject);
    }

    private void cleanFields() {
        this.editTextIdStudentSubject.setText("");
        this.spinnerStudent.setSelection(0);
        this.spinnerSubject.setSelection(0);
    }
}
