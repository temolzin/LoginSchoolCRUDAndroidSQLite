package com.example.practicaloginsqlite;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.practicaloginsqlite.dao.UserDAO;
import com.example.practicaloginsqlite.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class Access extends AppCompatActivity {

    EditText editTextUserName, editTextPassword;
    Spinner spinnerUser, spinnerRolUser;
    ArrayAdapter<RolUser> adapterRolUser;
    ArrayAdapter<UserDTO> adapterUser;
    ArrayList<RolUser> arrayListRolUser;
    ArrayList<UserDTO> arrayListUser;
    Button buttonRegister, buttonConsult, buttonUpdate, buttonDelete;

    UserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        this.linkFields();

        this.fillSpinnerUsers();

//        spinnerUser.setAdapter(adapterRolUser);


    }

    public void linkFields() {
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.editTextUserName = findViewById(R.id.editTextUserName);
        this.spinnerRolUser = findViewById(R.id.spinnerRolUsers);
        this.spinnerUser = findViewById(R.id.spinnerUsers);
    }

    public void fillSpinnerUsers() {
        arrayListUser = userDAO.read();
        //Se llena el Spinner
        adapterUser = new ArrayAdapter<UserDTO>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayListUser);
        spinnerUser.setAdapter(adapterUser);
    }



}
