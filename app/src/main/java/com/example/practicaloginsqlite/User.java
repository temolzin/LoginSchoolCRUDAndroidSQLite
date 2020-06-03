package com.example.practicaloginsqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.practicaloginsqlite.dao.UserDAO;
import com.example.practicaloginsqlite.dto.UserDTO;

import java.util.ArrayList;


public class User extends AppCompatActivity {
    AdapterUser adapterUser;

    EditText editTextEmail;
    EditText editTextPhone;
    EditText editTextName;
    EditText editTextIdUser;
    Button buttonRegister, buttonConsult, buttonUpdate, buttonDelete, buttonConsultAll;

    UserDTO userDTO;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregister);

        this.linkFields();

        userDAO = new UserDAO(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDTO = new UserDTO(editTextIdUser.getText().toString(), editTextName.getText().toString(), editTextPhone.getText().toString(), editTextEmail.getText().toString());
                userDAO.insert(userDTO);
                cleanFields();
            }
        });

        buttonConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArrayList<UserDTO> userDTOS = userDAO.read();
//                UserDTO userDTO = null;
//                String cadena = "";
//                for(UserDTO userDTO1 : userDTOS) {
//                    cadena += userDTO1.getName() + "  ";
//                }
//                editTextConsulta.setText(cadena);
                userDTO = userDAO.readbyid(editTextIdUser.getText().toString());
                if(userDTO == null) {
                    Toast.makeText(User.this, "El ID ingresado no está registrado en la base de datos", Toast.LENGTH_SHORT).show();
                    cleanFields();
                } else {
                    editTextName.setText(userDTO.getName());
                    editTextPhone.setText(userDTO.getPhone());
                    editTextEmail.setText(userDTO.getEmail());
                }
            }
        });

        buttonConsultAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterUser = new AdapterUser(User.this, userDAO.read());

                AlertDialog.Builder builder = new AlertDialog.Builder(User.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewAlert = inflater.inflate(R.layout.activity_alert_user, null);
                builder.setView(viewAlert);

                ListView listViewUsers = viewAlert.findViewById(R.id.ListViewUsers);
                listViewUsers.setAdapter(adapterUser);

                final AlertDialog dialog = builder.create();
                dialog.show();

//                ArrayList<UserDTO> userDTOS = userDAO.read();
//                UserDTO userDTO = null;
//                String cadena = "";
//                for(UserDTO userDTO1 : userDTOS) {
//                    cadena += userDTO1.getName() + "  ";
//                }

            }
        });
    }

    private void linkFields() {
        this.editTextIdUser = findViewById(R.id.editTextIdUser);
        this.editTextName = findViewById(R.id.editTextNameUser);
        this.editTextPhone = findViewById(R.id.editTextPhoneUser);
        this.editTextEmail = findViewById(R.id.editTextEmailUser);
        this.buttonRegister = findViewById(R.id.buttonRegister);
        this.buttonConsult = findViewById(R.id.buttonConsult);
        this.buttonConsultAll = findViewById(R.id.buttonConsultAllUser);
    }

    private void cleanFields() {
        this.editTextIdUser.setText("");
        this.editTextName.setText("");
        this.editTextPhone.setText("");
        this.editTextEmail.setText("");
    }

}

