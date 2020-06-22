package com.example.practicaloginschoolsqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practicaloginschoolsqlite.dao.TeacherDAO;
import com.example.practicaloginschoolsqlite.dto.TeacherDTO;

public class Teacher extends AppCompatActivity {

    EditText editTextIdTeacher;
    EditText editTextNameTeacher;
    EditText editTextAddressTeacher;
    EditText editTextPhoneTeacher;
    EditText editTextScheduleTeacher;
    Button buttonRegisterTeacher, buttonConsultTeacher, buttonUpdateTeacher, buttonDeleteTeacher, buttonConsultAllTeacher;

    TeacherDAO teacherDAO;
    TeacherDTO teacherDTO;
    
    AdapterTeacher adapterTeacher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachercrud);

        this.linkFields();

        teacherDAO = new TeacherDAO(this);

        //Se inhabilita el boton de actualizar y eliminar hasta que el usuario
        buttonUpdateTeacher.setEnabled(false);
        buttonDeleteTeacher.setEnabled(false);

        //Botón que registra al rol de usuario en la base de datos
        buttonRegisterTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdTeacher.getText().toString().isEmpty() || editTextNameTeacher.getText().toString().isEmpty() || editTextAddressTeacher.getText().toString().isEmpty() || editTextPhoneTeacher.getText().toString().isEmpty() || editTextScheduleTeacher.getText().toString().isEmpty()) {
                    Toast.makeText(Teacher.this, "Todos los campos deben de ser llenados", Toast.LENGTH_SHORT).show();
                } else {
                    teacherDTO = new TeacherDTO(editTextIdTeacher.getText().toString(), editTextNameTeacher.getText().toString(), editTextAddressTeacher.getText().toString(), editTextPhoneTeacher.getText().toString(), editTextScheduleTeacher.getText().toString());
                    teacherDAO.insert(teacherDTO);
                    cleanFields();
                }
            }
        });

        //Boton para consultar por el id del rol de usuario
        buttonConsultTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se valida que el id usuario no este vacío
                if(editTextIdTeacher.getText().toString().isEmpty()) {
                    Toast.makeText(Teacher.this, "Debes ingresar un ID", Toast.LENGTH_SHORT).show();
                    buttonUpdateTeacher.setEnabled(false);
                    buttonDeleteTeacher.setEnabled(false);
                } else {
                    teacherDTO = teacherDAO.readbyid(editTextIdTeacher.getText().toString());
                    if(teacherDTO == null) {
                        Toast.makeText(Teacher.this, "El ID ingresado no está registrado en la base de datos", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    } else {
                        editTextNameTeacher.setText(teacherDTO.getNameTeacher());
                        editTextAddressTeacher.setText(teacherDTO.getAddressTeacher());
                        editTextAddressTeacher.setText(teacherDTO.getPhoneTeacher());
                        editTextScheduleTeacher.setText(teacherDTO.getScheduleTeacher());

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateTeacher.setEnabled(true);
                        buttonDeleteTeacher.setEnabled(true);
                    }
                }
            }
        });

        //Botón que actualiza los campos del rol de usuario
        buttonUpdateTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdTeacher.getText().toString().isEmpty() && editTextNameTeacher.toString().isEmpty()) {
                    Toast.makeText(Teacher.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    teacherDTO = new TeacherDTO(editTextIdTeacher.getText().toString(), editTextNameTeacher.getText().toString(), editTextAddressTeacher.getText().toString(), editTextPhoneTeacher.getText().toString(), editTextScheduleTeacher.getText().toString());
                    teacherDAO.update(teacherDTO);
                    cleanFields();
                }

            }
        });

        //Botón que elimina los campos del usuario
        buttonDeleteTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdTeacher.getText().toString().isEmpty() && editTextNameTeacher.toString().isEmpty()) {
                    Toast.makeText(Teacher.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    teacherDTO = new TeacherDTO(editTextIdTeacher.getText().toString(), editTextNameTeacher.getText().toString(), editTextAddressTeacher.getText().toString(), editTextPhoneTeacher.getText().toString(), editTextScheduleTeacher.getText().toString());
                    teacherDAO.delete(teacherDTO.getIdTeacher());
                    cleanFields();
                }
            }
        });

        //Para consultar todos los rol de usuarios y mostrarlos en una alerta con un listView
        buttonConsultAllTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterTeacher = new AdapterTeacher(Teacher.this, teacherDAO.read());

                AlertDialog.Builder builder = new AlertDialog.Builder(Teacher.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewAlert = inflater.inflate(R.layout.activity_alert_teacher, null);
                builder.setView(viewAlert);

                ListView listViewTeachers = viewAlert.findViewById(R.id.ListViewTeacher);
                listViewTeachers.setAdapter(adapterTeacher);

                final AlertDialog dialog = builder.create();
                dialog.show();

                listViewTeachers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        teacherDTO = (TeacherDTO) parent.getItemAtPosition(position);
                        editTextIdTeacher.setText(teacherDTO.getIdTeacher());
                        editTextNameTeacher.setText(teacherDTO.getNameTeacher());
                        editTextAddressTeacher.setText(teacherDTO.getAddressTeacher());
                        editTextPhoneTeacher.setText(teacherDTO.getPhoneTeacher());
                        editTextScheduleTeacher.setText(teacherDTO.getScheduleTeacher());

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateTeacher.setEnabled(true);
                        buttonDeleteTeacher.setEnabled(true);

                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public void linkFields() {
        editTextIdTeacher = findViewById(R.id.editTextIdTeacher);
        editTextNameTeacher = findViewById(R.id.editTextNameTeacher);
        editTextAddressTeacher = findViewById(R.id.editTextAddressTeacher);
        editTextPhoneTeacher = findViewById(R.id.editTextPhoneTeacher);
        editTextScheduleTeacher = findViewById(R.id.editTextScheduleTeacher);

        buttonRegisterTeacher = findViewById(R.id.buttonRegisterTeacher);
        buttonConsultTeacher = findViewById(R.id.buttonConsultTeacher);
        buttonConsultAllTeacher = findViewById(R.id.buttonConsultAllTeacher);
        buttonUpdateTeacher = findViewById(R.id.buttonUpdateTeacher);
        buttonDeleteTeacher = findViewById(R.id.buttonDeleteTeacher);
    }

    private void cleanFields() {
        this.editTextIdTeacher.setText("");
        this.editTextNameTeacher.setText("");
        this.editTextAddressTeacher.setText("");
        this.editTextPhoneTeacher.setText("");
        this.editTextScheduleTeacher.setText("");
    }
}
