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

import com.example.practicaloginschoolsqlite.dao.CareerDAO;
import com.example.practicaloginschoolsqlite.dao.StudentDAO;
import com.example.practicaloginschoolsqlite.dto.CareerDTO;
import com.example.practicaloginschoolsqlite.dto.StudentDTO;

import java.util.ArrayList;

public class Student extends AppCompatActivity {

    EditText editTextIdStudent;
    EditText editTextNameStudent;
    EditText editTextAgeStudent;
    EditText editTextSemesterStudent;
    EditText editTextGenreStudent;
    Spinner spinnerIdCareerStudent;
    Button buttonRegisterStudent, buttonConsultStudent, buttonUpdateStudent, buttonDeleteStudent, buttonConsultAllStudent;

    StudentDAO studentDAO;
    StudentDTO studentDTO;
    
    AdapterStudent adapterStudent;

    //Array para llenar el spinner
    ArrayAdapter<CareerDTO> arrayAdapterCareer;

    ArrayList<CareerDTO> arrayListCareer;
    CareerDAO careerDAO;
    CareerDTO careerDTO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentcrud);

        this.linkFields();

        studentDAO = new StudentDAO(this);
        careerDAO = new CareerDAO(this);

        //llenar Spinner
        this.fillSpinnerCareer();

        //Se inhabilita el boton de actualizar y eliminar hasta que el usuario
        buttonUpdateStudent.setEnabled(false);
        buttonDeleteStudent.setEnabled(false);

        //Botón que registra al rol de usuario en la base de datos
        buttonRegisterStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdStudent.getText().toString().isEmpty() || editTextNameStudent.getText().toString().isEmpty() || editTextAgeStudent.getText().toString().isEmpty() || editTextSemesterStudent.getText().toString().isEmpty() || editTextGenreStudent.getText().toString().isEmpty()) {
                    Toast.makeText(Student.this, "Todos los campos deben de ser llenados", Toast.LENGTH_SHORT).show();
                } else {
                    careerDTO = (CareerDTO) spinnerIdCareerStudent.getSelectedItem();
                    studentDTO = new StudentDTO(editTextIdStudent.getText().toString(), editTextNameStudent.getText().toString(), Integer.parseInt(editTextAgeStudent.getText().toString()), editTextSemesterStudent.getText().toString(), editTextGenreStudent.getText().toString(), careerDTO);
                    studentDAO.insert(studentDTO);
                    cleanFields();
                }
            }
        });

        //Boton para consultar por el id del rol de usuario
        buttonConsultStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se valida que el id usuario no este vacío
                if(editTextIdStudent.getText().toString().isEmpty()) {
                    Toast.makeText(Student.this, "Debes ingresar un ID", Toast.LENGTH_SHORT).show();
                    buttonUpdateStudent.setEnabled(false);
                    buttonDeleteStudent.setEnabled(false);
                } else {
                    studentDTO = studentDAO.readbyid(editTextIdStudent.getText().toString());
                    if(studentDTO == null) {
                        Toast.makeText(Student.this, "El ID ingresado no está registrado en la base de datos", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    } else {
                        editTextNameStudent.setText(studentDTO.getNameStudent());
                        editTextAgeStudent.setText(studentDTO.getAgeStudent());
                        editTextSemesterStudent.setText(studentDTO.getSemesterStudent());
                        editTextGenreStudent.setText(studentDTO.getGenreStudent());
                        editTextGenreStudent.setText(studentDTO.getIdCareer().toString());

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateStudent.setEnabled(true);
                        buttonDeleteStudent.setEnabled(true);
                    }
                }
            }
        });

        //Botón que actualiza los campos del rol de usuario
        buttonUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdStudent.getText().toString().isEmpty() || editTextNameStudent.getText().toString().isEmpty() || editTextAgeStudent.getText().toString().isEmpty() || editTextSemesterStudent.getText().toString().isEmpty() || editTextGenreStudent.getText().toString().isEmpty()) {
                    Toast.makeText(Student.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    careerDTO = (CareerDTO) spinnerIdCareerStudent.getSelectedItem();
                    studentDTO = new StudentDTO(editTextIdStudent.getText().toString(), editTextNameStudent.getText().toString(), Integer.parseInt(editTextAgeStudent.getText().toString()), editTextSemesterStudent.getText().toString(), editTextGenreStudent.getText().toString(), careerDTO);
                    studentDAO.update(studentDTO);
                    cleanFields();
                }

            }
        });

        //Botón que elimina los campos del usuario
        buttonDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdStudent.getText().toString().isEmpty() && editTextNameStudent.toString().isEmpty()) {
                    Toast.makeText(Student.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    careerDTO = (CareerDTO) spinnerIdCareerStudent.getSelectedItem();
                    studentDTO = new StudentDTO(editTextIdStudent.getText().toString(), editTextNameStudent.getText().toString(), Integer.parseInt(editTextAgeStudent.getText().toString()), editTextSemesterStudent.getText().toString(), editTextGenreStudent.getText().toString(), careerDTO);
                    studentDAO.delete(studentDTO.getIdStudent());
                    cleanFields();
                }
            }
        });

        //Para consultar todos los rol de usuarios y mostrarlos en una alerta con un listView
        buttonConsultAllStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterStudent = new AdapterStudent(Student.this, studentDAO.read());

                AlertDialog.Builder builder = new AlertDialog.Builder(Student.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewAlert = inflater.inflate(R.layout.activity_alert_student, null);
                builder.setView(viewAlert);

                ListView listViewStudents = viewAlert.findViewById(R.id.ListViewStudent);
                listViewStudents.setAdapter(adapterStudent);

                final AlertDialog dialog = builder.create();
                dialog.show();

                listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        studentDTO = (StudentDTO) parent.getItemAtPosition(position);
                        editTextIdStudent.setText(studentDTO.getIdStudent());
                        editTextNameStudent.setText(studentDTO.getNameStudent());
                        editTextAgeStudent.setText(studentDTO.getAgeStudent() + "");
                        editTextSemesterStudent.setText(studentDTO.getSemesterStudent());
                        editTextGenreStudent.setText(studentDTO.getGenreStudent());

                        spinnerIdCareerStudent.setSelection(arrayListCareer.indexOf(studentDTO.getIdCareer()));

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateStudent.setEnabled(true);
                        buttonDeleteStudent.setEnabled(true);

                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public void linkFields() {
        editTextIdStudent = findViewById(R.id.editTextIdStudent);
        editTextNameStudent = findViewById(R.id.editTextNameStudent);
        editTextAgeStudent = findViewById(R.id.editTextAgeStudent);
        editTextSemesterStudent = findViewById(R.id.editTextSemesterStudent);
        editTextGenreStudent = findViewById(R.id.editTextGenderStudent);
        spinnerIdCareerStudent = findViewById(R.id.spinnerStudentCareer);

        buttonRegisterStudent = findViewById(R.id.buttonRegisterStudent);
        buttonConsultStudent = findViewById(R.id.buttonConsultStudent);
        buttonConsultAllStudent = findViewById(R.id.buttonConsultAllStudent);
        buttonUpdateStudent = findViewById(R.id.buttonUpdateStudent);
        buttonDeleteStudent = findViewById(R.id.buttonDeleteStudent);
    }

    private void cleanFields() {
        this.editTextIdStudent.setText("");
        this.editTextNameStudent.setText("");
        this.editTextAgeStudent.setText("");
        this.editTextSemesterStudent.setText("");
        this.editTextGenreStudent.setText("");
    }

    private void fillSpinnerCareer() {
        this.arrayListCareer = careerDAO.read();
        arrayAdapterCareer = new ArrayAdapter<CareerDTO>(getApplicationContext(), android.R.layout.simple_spinner_item , arrayListCareer);
        this.spinnerIdCareerStudent.setAdapter(arrayAdapterCareer);
    }
}
