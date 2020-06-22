package com.example.practicaloginschoolsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.practicaloginschoolsqlite.dto.CareerDTO;
import com.example.practicaloginschoolsqlite.dto.StudentDTO;

import java.util.ArrayList;

public class StudentDAO implements Crud<StudentDTO> {

    public static final String TABLE_STUDENT = "student";
    public static final String FIELD_ID_STUDENT = "idStudent";
    public static final String FIELD_NAME_STUDENT = "nameStudent";
    public static final String FIELD_AGE_STUDENT = "ageStudent";
    public static final String FIELD_SEMESTER_STUDENT = "semesterStudent";
    public static final String FIELD_GENRE_STUDENT = "genreStudent";
    public static final String FIELD_ID_CAREER_STUDENT = "idCareer";
    public static final String CREATE_TABLE_STUDENT =
            "CREATE TABLE " + TABLE_STUDENT + " (" +
                    FIELD_ID_STUDENT + " TEXT," +
                    FIELD_NAME_STUDENT + " TEXT," +
                    FIELD_AGE_STUDENT + " integer," +
                    FIELD_SEMESTER_STUDENT + " TEXT," +
                    FIELD_GENRE_STUDENT + " TEXT," +
                    FIELD_ID_CAREER_STUDENT + " TEXT," +
                    "PRIMARY KEY (" + FIELD_ID_STUDENT + ")," +
                    "FOREIGN KEY (" + FIELD_ID_CAREER_STUDENT + ") REFERENCES career("+FIELD_ID_CAREER_STUDENT+")" +
            ")";

    private ConnectionSQLite conn;
    private SQLiteDatabase db;
    Context context;

    public StudentDAO(Context context) {
        this.context = context;
        conn = new ConnectionSQLite(this.context, TABLE_STUDENT, null,1);
    }

    @Override
    public void create() {
        conn = new ConnectionSQLite(context, TABLE_STUDENT, null, 1);
    }

    @Override
    public ArrayList<StudentDTO> read() {
        ArrayList<StudentDTO> studentDTOList = new ArrayList();
        db = conn.getReadableDatabase();
        CareerDAO careerDAO = new CareerDAO(context);

        Cursor cursor = db.query(TABLE_STUDENT, null, null, null,null,null, null);
        while(cursor.moveToNext()) {
            StudentDTO studentDTO;
            CareerDTO careerDTO = careerDAO.readbyid(cursor.getString(5));
            studentDTO = new StudentDTO(cursor.getString(0), cursor.getString(1),cursor.getInt(2),cursor.getString(3), cursor.getString(4), careerDTO);
            studentDTOList.add(studentDTO);
        }
        return studentDTOList;
    }

    @Override
    public StudentDTO readbyid(Object id) {
        db = conn.getReadableDatabase();
        String[] parameters = {(String) id};
        String[] fields = {FIELD_ID_STUDENT, FIELD_NAME_STUDENT, FIELD_AGE_STUDENT, FIELD_SEMESTER_STUDENT, FIELD_GENRE_STUDENT, FIELD_ID_CAREER_STUDENT};
        StudentDTO studentDTO = null;
        CareerDAO careerDAO = new CareerDAO(context);

        try {
            Cursor cursor = db.query(TABLE_STUDENT, fields, FIELD_ID_STUDENT+"=?", parameters,null,null,null);
            cursor.moveToFirst();
            CareerDTO careerDTO = careerDAO.readbyid(cursor.getString(5));
            studentDTO = new StudentDTO(cursor.getString(0), cursor.getString(1),cursor.getInt(2),cursor.getString(3), cursor.getString(4), careerDTO);
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return studentDTO;
    }


    @Override
    public boolean insert(StudentDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ID_STUDENT, obj.getIdStudent());
        values.put(FIELD_NAME_STUDENT, obj.getNameStudent());
        values.put(FIELD_AGE_STUDENT, obj.getAgeStudent());
        values.put(FIELD_GENRE_STUDENT, obj.getGenreStudent());
        values.put(FIELD_SEMESTER_STUDENT, obj.getSemesterStudent());
        values.put(FIELD_ID_CAREER_STUDENT, obj.getIdCareer().getIdCareer());

        boolean result = false;
        Long idResult = db.insert(TABLE_STUDENT, FIELD_ID_STUDENT, values);
        Toast.makeText(context, "ID: " + idResult.toString(), Toast.LENGTH_SHORT).show();
        if(idResult.equals(-1)) {
            Toast.makeText(context, "Ha ocurrido un error al insertar el registro: El ID: " + obj.getIdStudent() + " ya se encuentra registrado en la base de datos", Toast.LENGTH_SHORT).show();
            result = false;
        } else {
            Toast.makeText(context, "Registro insertado correctamente ID: " + idResult, Toast.LENGTH_SHORT).show();
            db.close();
            result = true;
        }

        return result;
    }

    @Override
    public boolean update(StudentDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {obj.getIdStudent()};
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME_STUDENT, obj.getNameStudent());
        values.put(FIELD_AGE_STUDENT, obj.getAgeStudent());
        values.put(FIELD_GENRE_STUDENT, obj.getGenreStudent());
        values.put(FIELD_SEMESTER_STUDENT, obj.getSemesterStudent());
        values.put(FIELD_ID_CAREER_STUDENT, obj.getIdCareer().getIdCareer());

        boolean result = false;
        int idResult = db.update(TABLE_STUDENT, values, FIELD_ID_STUDENT+"=?", parameters);
        Toast.makeText(context, "Se actualizarón los datos del ID: " + obj.getIdStudent(), Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id};

        boolean result = false;
        int idResult = db.delete(TABLE_STUDENT, FIELD_ID_STUDENT+"=?", parameters);
        Toast.makeText(context, "Se eliminó el registro con ID: " + id, Toast.LENGTH_SHORT).show();

        return result;
    }
}
