package com.example.practicaloginsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.practicaloginsqlite.dto.AccessDTO;
import com.example.practicaloginsqlite.dto.RolUserDTO;
import com.example.practicaloginsqlite.dto.UserDTO;

import java.util.ArrayList;

public class AccessDAO implements Crud<AccessDTO> {

    public static final String TABLE_ACCESS = "access";
    public static final String FIELD_ID_ACCESS = "idAccess";
    public static final String FIELD_ACCESS_ID_ROL_USER = "idRolUser";
    public static final String FIELD_ACCESS_ID_USER = "idUser";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";
    public static final String CREATE_TABLE_ACCESS =
            "CREATE TABLE " + TABLE_ACCESS + "(" +
                    FIELD_ID_ACCESS + " TEXT," +
                    FIELD_ACCESS_ID_ROL_USER + " TEXT," +
                    FIELD_ACCESS_ID_USER + " TEXT," +
                    FIELD_USERNAME + " TEXT," +
                    FIELD_PASSWORD + " TEXT," +
                    "PRIMARY KEY (" + FIELD_ID_ACCESS + "), " +
                    "FOREIGN KEY (" + FIELD_ACCESS_ID_USER + ") REFERENCES user("+FIELD_ACCESS_ID_USER+"), " +
                    "FOREIGN KEY (" + FIELD_ACCESS_ID_ROL_USER + ") REFERENCES roluser("+FIELD_ACCESS_ID_ROL_USER+") " +
                    ")";

    private ConnectionSQLite conn;
    private SQLiteDatabase db;
    Context context;

    public AccessDAO(Context context) {
        this.context = context;
        conn = new ConnectionSQLite(context, TABLE_ACCESS, null,1);
    }

    @Override
    public void create() {
        conn = new ConnectionSQLite(context, "access", null, 1);
    }

    @Override
    public ArrayList<AccessDTO> read() {
        ArrayList<AccessDTO> accessDTOList = new ArrayList();
        db = conn.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCESS, null, null, null,null,null, null);

        UserDAO userDAO = new UserDAO(context);
        RolUserDAO rolUserDAO = new RolUserDAO(context);
        final String TAG = "MyActivity";
        while(cursor.moveToNext()) {
            //Se crean los objetos para agregarlos a la lista se consultan por el ID consultado en la base de datos
            UserDTO userDTO = userDAO.readbyid(cursor.getString(2));
            RolUserDTO rolUserDTO = rolUserDAO.readbyid(cursor.getString(1));

            AccessDTO accessDTO;
            accessDTO = new AccessDTO(cursor.getString(0), rolUserDTO, userDTO,cursor.getString(3),cursor.getString(4));
            accessDTOList.add(accessDTO);
        }
        return accessDTOList;
    }

    @Override
    public AccessDTO readbyid(Object id) {
        db = conn.getReadableDatabase();
        String[] parameters = {(String) id};
        String[] fields = {FIELD_ID_ACCESS, FIELD_ACCESS_ID_ROL_USER, FIELD_ACCESS_ID_USER, FIELD_USERNAME, FIELD_PASSWORD};
        AccessDTO accessDTO = null;

        UserDAO userDAO = new UserDAO(context);
        RolUserDAO rolUserDAO = new RolUserDAO(context);

        try {
            Cursor cursor = db.query(TABLE_ACCESS, fields, FIELD_ID_ACCESS +"=?", parameters,null,null,null);
            cursor.moveToFirst();
            //Se crean los objetos para agregarlos a la lista se consultan por el ID consultado en la base de datos
            UserDTO userDTO = userDAO.readbyid(cursor.getString(2));
            RolUserDTO rolUserDTO = rolUserDAO.readbyid(cursor.getString(1));

            accessDTO = new AccessDTO(cursor.getString(0), rolUserDTO, userDTO,cursor.getString(3),cursor.getString(4));            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return accessDTO;
    }

    @Override
    public boolean update(AccessDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {obj.getIdAccess()};
        ContentValues values = new ContentValues();
        values.put(FIELD_ACCESS_ID_ROL_USER, obj.getObjRolUser().getIdRol());
        values.put(FIELD_ACCESS_ID_USER, obj.getObjUser().getIdUser());
        values.put(FIELD_USERNAME, obj.getUserName());
        values.put(FIELD_PASSWORD, obj.getPassword());

        boolean result = false;
        db.update(TABLE_ACCESS, values, FIELD_ID_ACCESS +"=?", parameters);
        Toast.makeText(context, "Se actualizarón los datos del ID: " + obj.getIdAccess(), Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id};

        boolean result = false;
        db.delete(TABLE_ACCESS, FIELD_ID_ACCESS +"=?", parameters);
//        db.delete(TABLE_ACCESS, null, null);
        Toast.makeText(context, "Se eliminó el registro con ID: " + id, Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean insert(AccessDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ID_ACCESS, obj.getIdAccess());
        values.put(FIELD_ACCESS_ID_ROL_USER, obj.getObjRolUser().getIdRol());
        values.put(FIELD_ACCESS_ID_USER, obj.getObjUser().getIdUser());
        values.put(FIELD_USERNAME, obj.getUserName());
        values.put(FIELD_PASSWORD, obj.getPassword());

        boolean result = false;
        Long idResult = db.insert(TABLE_ACCESS, FIELD_ID_ACCESS, values);
        Toast.makeText(context, "ID: " + idResult.toString(), Toast.LENGTH_SHORT).show();
        if(idResult.equals(-1)) {
            Toast.makeText(context, "Ha ocurrido un error al insertar el registro: El ID: " + obj.getIdUser() + " ya se encuentra registrado en la base de datos", Toast.LENGTH_SHORT).show();
            result = false;
        } else {
            Toast.makeText(context, "Registro insertado correctamente ID: " + idResult, Toast.LENGTH_SHORT).show();
            db.close();
            result = true;
        }

        return result;
    }

    /*
    * Método para iniciar sesión en la app consultando en la base de datos SQLite
    * */
    public AccessDTO readbyusernameandpassword(String username, String password) {
        db = conn.getReadableDatabase();
        String[] parameters = {username, password};
        String[] fields = {FIELD_ID_ACCESS, FIELD_ACCESS_ID_ROL_USER, FIELD_ACCESS_ID_USER, FIELD_USERNAME, FIELD_PASSWORD};

        AccessDTO accessDTO = null;

        UserDAO userDAO = new UserDAO(context);
        RolUserDAO rolUserDAO = new RolUserDAO(context);

        try {
            Cursor cursor = db.query(TABLE_ACCESS, fields, FIELD_USERNAME +"=? AND " + FIELD_PASSWORD + "=?", parameters,null,null,null);
            cursor.moveToFirst();
            //Se crean los objetos para agregarlos a la lista se consultan por el ID consultado en la base de datos
            UserDTO userDTO = userDAO.readbyid(cursor.getString(2));
            RolUserDTO rolUserDTO = rolUserDAO.readbyid(cursor.getString(1));

            accessDTO = new AccessDTO(cursor.getString(0), rolUserDTO, userDTO,cursor.getString(3),cursor.getString(4));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }

        return accessDTO;
    }
}
