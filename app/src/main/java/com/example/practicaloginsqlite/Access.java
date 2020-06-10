package com.example.practicaloginsqlite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practicaloginsqlite.dao.AccessDAO;
import com.example.practicaloginsqlite.dao.RolUserDAO;
import com.example.practicaloginsqlite.dao.UserDAO;
import com.example.practicaloginsqlite.dto.AccessDTO;
import com.example.practicaloginsqlite.dto.RolUserDTO;
import com.example.practicaloginsqlite.dto.UserDTO;

import java.util.ArrayList;

public class Access extends AppCompatActivity {

    EditText editTextIdAccess,editTextUserName, editTextPassword;
    Spinner spinnerUser, spinnerRolUser;

    ArrayAdapter<RolUserDTO> adapterRolUser;
    ArrayAdapter<UserDTO> adapterUser;
    ArrayList<RolUserDTO> arrayListRolUser;
    ArrayList<UserDTO> arrayListUser;
    ArrayList<AccessDTO> arrayListAccess;

    Button buttonRegisterAccess, buttonConsultAccess, buttonUpdateAccess, buttonDeleteAccess, buttonConsultAllAccess;

    UserDTO userDTO;
    RolUserDTO rolUserDTO;

    UserDAO userDAO;
    RolUserDAO rolUserDAO;

    AccessDAO accessDAO;
    AccessDTO accessDTO;

    AdapterAccess adapterAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesscrud);

        accessDAO = new AccessDAO(this);

        userDAO = new UserDAO(this);
        rolUserDAO = new RolUserDAO(this);

        this.linkFields();

        // Se llenan los spiners
        this.fillSpinnerUsers();
        this.fillSpinnerRolUsers();


        //Se inhabilita el boton de actualizar y eliminar hasta que el usuario
        buttonUpdateAccess.setEnabled(false);
        buttonDeleteAccess.setEnabled(false);

        //Botón que registra al usuario en la base de datos
        buttonRegisterAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextUserName.getText().toString().isEmpty() || editTextUserName.getText().toString().isEmpty()) {
                    Toast.makeText(Access.this, "Todos los campos deben de ser llenados", Toast.LENGTH_SHORT).show();
                } else {
                    userDTO = (UserDTO) spinnerUser.getSelectedItem();
                    rolUserDTO = (RolUserDTO) spinnerRolUser.getSelectedItem();
                    accessDTO = new AccessDTO(editTextIdAccess.getText().toString(),rolUserDTO, userDTO, editTextUserName.getText().toString(), editTextPassword.getText().toString());
                    accessDAO.insert(accessDTO);
                    cleanFields();
                }
            }
        });

        //Boton para consultar por el id del usuario
        buttonConsultAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se valida que el id usuario no este vacío
                if(editTextIdAccess.getText().toString().isEmpty()) {
                    Toast.makeText(Access.this, "Debes ingresar un ID", Toast.LENGTH_SHORT).show();
                    buttonUpdateAccess.setEnabled(false);
                    buttonDeleteAccess.setEnabled(false);
                } else {
                    accessDTO = accessDAO.readbyid(editTextIdAccess.getText().toString());
                    if(accessDTO == null) {
                        Toast.makeText(Access.this, "El ID ingresado no está registrado en la base de datos", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    } else {
                        editTextUserName.setText(accessDTO.getUserName());
                        editTextPassword.setText(accessDTO.getPassword());

                        spinnerUser.setSelection(arrayListUser.indexOf(accessDTO.getObjUser()));
                        spinnerRolUser.setSelection(arrayListRolUser.indexOf(accessDTO.getObjRolUser()));

                        buttonUpdateAccess.setEnabled(true);
                        buttonDeleteAccess.setEnabled(true);
                    }
                }
            }
        });

        //Botón que actualiza los campos del usuario
        buttonUpdateAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdAccess.getText().toString().isEmpty() && editTextUserName.toString().isEmpty() && editTextPassword.toString().isEmpty()) {
                    Toast.makeText(Access.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    accessDTO = new AccessDTO(editTextIdAccess.getText().toString(), (RolUserDTO) spinnerRolUser.getSelectedItem(),(UserDTO) spinnerUser.getSelectedItem(), editTextIdAccess.getText().toString(), editTextUserName.getText().toString());
                    accessDAO.update(accessDTO);
                    cleanFields();
                }

            }
        });

        //Botón que elimina los campos del usuario
        buttonDeleteAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdAccess.getText().toString().isEmpty() && editTextUserName.toString().isEmpty() && editTextPassword.toString().isEmpty()) {
                    Toast.makeText(Access.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    accessDTO = new AccessDTO(editTextIdAccess.getText().toString(), (RolUserDTO) spinnerRolUser.getSelectedItem(),(UserDTO) spinnerUser.getSelectedItem(), editTextIdAccess.getText().toString(), editTextUserName.getText().toString());
                    accessDAO.delete(accessDTO.getIdAccess());
                    cleanFields();
                }
            }
        });

        //Para consultar todos los usuarios y mostrarlos en una alerta con un listView
        buttonConsultAllAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapterAccess = new AdapterAccess(Access.this, accessDAO.read());

                AlertDialog.Builder builder = new AlertDialog.Builder(Access.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewAlert = inflater.inflate(R.layout.activity_alert_access, null);
                builder.setView(viewAlert);

                ListView listViewAccess = viewAlert.findViewById(R.id.ListViewAccess);
                listViewAccess.setAdapter(adapterAccess);

                final AlertDialog dialog = builder.create();
                dialog.show();

                listViewAccess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        accessDTO = (AccessDTO) parent.getItemAtPosition(position);

                        editTextIdAccess.setText(accessDTO.getIdAccess());
                        editTextUserName.setText(accessDTO.getUserName());
                        editTextPassword.setText(accessDTO.getPassword());

                        spinnerUser.setSelection(arrayListUser.indexOf(accessDTO.getObjUser()));
                        spinnerRolUser.setSelection(arrayListRolUser.indexOf(accessDTO.getObjRolUser()));


                        //Se activan los botones al consultar algún cliente
                        buttonUpdateAccess.setEnabled(true);
                        buttonDeleteAccess.setEnabled(true);

                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public void linkFields() {
        this.editTextIdAccess = findViewById(R.id.editTextAccessIdAccess);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.editTextUserName = findViewById(R.id.editTextUserName);
        this.spinnerRolUser = findViewById(R.id.spinnerRolUsers);
        this.spinnerUser = findViewById(R.id.spinnerUsers);
        this.buttonConsultAllAccess = findViewById(R.id.buttonConsultAllAccess);
        this.buttonConsultAccess = findViewById(R.id.buttonConsultAccess);
        this.buttonDeleteAccess = findViewById(R.id.buttonDeleteAccess);
        this.buttonUpdateAccess = findViewById(R.id.buttonUpdateAccess);
        this.buttonRegisterAccess = findViewById(R.id.buttonRegisterAccess);
    }

    public void fillSpinnerUsers() {
        arrayListUser = userDAO.read();

        //Se llena el Spinner
        adapterUser = new ArrayAdapter<UserDTO>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayListUser);
        spinnerUser.setAdapter(adapterUser);
    }

    public void fillSpinnerRolUsers() {
        arrayListRolUser = rolUserDAO.read();
        //Se llena el Spinner
        adapterRolUser = new ArrayAdapter<RolUserDTO>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayListRolUser);
        spinnerRolUser.setAdapter(adapterRolUser);
    }

    public void cleanFields() {
        this.editTextIdAccess.setText("");
        this.editTextUserName.setText("");
        this.editTextPassword.setText("");

        this.spinnerRolUser.setSelection(0);
        this.spinnerUser.setSelection(0);
    }

}
