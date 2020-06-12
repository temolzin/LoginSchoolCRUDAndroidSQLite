package com.example.practicaloginschoolsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.practicaloginschoolsqlite.dto.CareerDTO;

import java.util.ArrayList;

public class CareerDAO implements Crud<CareerDTO> {

    public static final String TABLE_CARRER = "career";
    public static final String FIELD_ID_CARRER = "idCareer";
    public static final String FIELD_NAME_CARRER = "nameCareer";
    public static final String FIELD_DURATION_CARRER = "durationCareer";
    public static final String CREATE_TABLE_CARRER =
            "CREATE TABLE " + TABLE_CARRER + "(" +
                    FIELD_ID_CARRER + " TEXT," +
                    FIELD_NAME_CARRER + " TEXT," +
                    FIELD_DURATION_CARRER + " TEXT," +
            "PRIMARY KEY (" + FIELD_ID_CARRER + ")" +
            ")";

    private ConnectionSQLite conn;
    private SQLiteDatabase db;
    Context context;

    public CareerDAO(Context context) {
        this.context = context;
        conn = new ConnectionSQLite(context, TABLE_CARRER, null,1);
    }

    @Override
    public void create() {
        conn = new ConnectionSQLite(context, TABLE_CARRER, null, 1);
    }

    @Override
    public ArrayList<CareerDTO> read() {
        ArrayList<CareerDTO> careerDTOList = new ArrayList();
        db = conn.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CARRER, null, null, null,null,null, null);
        while(cursor.moveToNext()) {
            CareerDTO careerDTO;
            careerDTO = new CareerDTO(cursor.getString(0), cursor.getString(1),cursor.getString(2));
            careerDTOList.add(careerDTO);
        }
        return careerDTOList;
    }

    @Override
    public CareerDTO readbyid(Object id) {
        db = conn.getReadableDatabase();
        String[] parameters = {(String) id};
        String[] fields = {FIELD_ID_CARRER, FIELD_NAME_CARRER, FIELD_DURATION_CARRER};
        CareerDTO careerDTO = null;

        try {
            Cursor cursor = db.query(TABLE_CARRER, fields, FIELD_ID_CARRER+"=?", parameters,null,null,null);
            cursor.moveToFirst();

            careerDTO = new CareerDTO(cursor.getString(0), cursor.getString(1),cursor.getString(2));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return careerDTO;
    }


    @Override
    public boolean insert(CareerDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ID_CARRER, obj.getIdCareer());
        values.put(FIELD_NAME_CARRER, obj.getNameCareer());
        values.put(FIELD_DURATION_CARRER, obj.getDurationCareer());

        boolean result = false;
        Long idResult = db.insert(TABLE_CARRER, FIELD_ID_CARRER, values);
        Toast.makeText(context, "ID: " + idResult.toString(), Toast.LENGTH_SHORT).show();
        if(idResult.equals(-1)) {
            Toast.makeText(context, "Ha ocurrido un error al insertar el registro: El ID: " + obj.getIdCareer() + " ya se encuentra registrado en la base de datos", Toast.LENGTH_SHORT).show();
            result = false;
        } else {
            Toast.makeText(context, "Registro insertado correctamente ID: " + obj.getIdCareer(), Toast.LENGTH_SHORT).show();
            db.close();
            result = true;
        }

        return result;
    }

    @Override
    public boolean update(CareerDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {obj.getIdCareer()};
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME_CARRER, obj.getNameCareer());
        values.put(FIELD_DURATION_CARRER, obj.getDurationCareer());

        boolean result = false;
        int idResult = db.update(TABLE_CARRER, values, FIELD_ID_CARRER+"=?", parameters);
        Toast.makeText(context, "Se actualizarón los datos del ID: " + obj.getIdCareer(), Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id};

        boolean result = false;
        int idResult = db.delete(TABLE_CARRER, FIELD_ID_CARRER+"=?", parameters);

        Toast.makeText(context, "Se eliminó el registro con ID: " + id, Toast.LENGTH_SHORT).show();

        return result;
    }
}
