package com.example.practicaloginschoolsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.practicaloginschoolsqlite.dto.StudentDTO;
import com.example.practicaloginschoolsqlite.dto.StudentSubjectDTO;
import com.example.practicaloginschoolsqlite.dto.SubjectDTO;

import java.util.ArrayList;

public class StudentSubjectDAO implements Crud<StudentSubjectDTO> {

    public static final String TABLE_STUDENT_SUBJECT = "studentSubject";
    public static final String FIELD_ID_STUDENT_SUBJECT = "idStudentSubject";
    public static final String FIELD_ID_STUDENT = "idStudentSS";
    public static final String FIELD_ID_SUBJECT = "idSubjectSS";
    public static final String CREATE_TABLE_STUDENT_SUBJECT =
            "CREATE TABLE " + TABLE_STUDENT_SUBJECT + " (" +
                    FIELD_ID_STUDENT_SUBJECT + " TEXT," +
                    FIELD_ID_STUDENT + " TEXT," +
                    FIELD_ID_SUBJECT + " TEXT," +
            "PRIMARY KEY (" + FIELD_ID_STUDENT_SUBJECT + "), " +
            "FOREIGN KEY (" + FIELD_ID_STUDENT + ") REFERENCES student(idStudent), " +
            "FOREIGN KEY (" + FIELD_ID_SUBJECT + ") REFERENCES subject(idSubject) " +
            ")";

    private ConnectionSQLite conn;
    private SQLiteDatabase db;
    Context context;

    //Variables para consultar los objetos y mandarlos a la base de datos.
    StudentDAO studentDAO;
    SubjectDAO subjectDAO;

    public StudentSubjectDAO(Context context) {
        this.context = context;
        conn = new ConnectionSQLite(context, TABLE_STUDENT_SUBJECT, null,1);
    }

    @Override
    public void create() {
        conn = new ConnectionSQLite(context, TABLE_STUDENT_SUBJECT, null, 1);
    }

    @Override
    public ArrayList<StudentSubjectDTO> read() {
        ArrayList<StudentSubjectDTO> subjectDTOList = new ArrayList();
        db = conn.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENT_SUBJECT, null, null, null,null,null, null);
        while(cursor.moveToNext()) {

            studentDAO = new StudentDAO(context);
            StudentDTO studentDTO = studentDAO.readbyid(cursor.getString(1));

            subjectDAO = new SubjectDAO(context);
            SubjectDTO subjectDTO = subjectDAO.readbyid(cursor.getString(2));

            StudentSubjectDTO studentSubjectDTO;
            studentSubjectDTO = new StudentSubjectDTO(cursor.getString(0), studentDTO, subjectDTO);
            subjectDTOList.add(studentSubjectDTO);
        }
        return subjectDTOList;
    }

    @Override
    public StudentSubjectDTO readbyid(Object id) {
        db = conn.getReadableDatabase();
        String[] parameters = {(String) id};
        String[] fields = {FIELD_ID_STUDENT_SUBJECT, FIELD_ID_STUDENT, FIELD_ID_SUBJECT};
        StudentSubjectDTO studentSubjectDTO = null;

        try {
            Cursor cursor = db.query(TABLE_STUDENT_SUBJECT, fields, FIELD_ID_STUDENT +"=?", parameters,null,null,null);
            cursor.moveToFirst();

            studentDAO = new StudentDAO(context);
            StudentDTO studentDTO = studentDAO.readbyid(cursor.getString(1));

            subjectDAO = new SubjectDAO(context);
            SubjectDTO subjectDTO = subjectDAO.readbyid(cursor.getString(2));

            studentSubjectDTO = new StudentSubjectDTO(cursor.getString(0), studentDTO, subjectDTO);

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return studentSubjectDTO;
    }


    @Override
    public boolean insert(StudentSubjectDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ID_STUDENT_SUBJECT, obj.getIdStudentSubject());
        values.put(FIELD_ID_STUDENT, obj.getStudent().getIdStudent());
        values.put(FIELD_ID_SUBJECT, obj.getSubject().getIdSubject());

        boolean result = false;
        Long idResult = db.insert(TABLE_STUDENT_SUBJECT, FIELD_ID_STUDENT, values);
        Toast.makeText(context, "ID: " + idResult.toString(), Toast.LENGTH_SHORT).show();
        if(idResult.equals(-1)) {
            Toast.makeText(context, "Ha ocurrido un error al insertar el registro: El ID: " + obj.getIdStudentSubject() + " ya se encuentra registrado en la base de datos", Toast.LENGTH_SHORT).show();
            result = false;
        } else {
            Toast.makeText(context, "Registro insertado correctamente ID: " + obj.getIdStudentSubject(), Toast.LENGTH_SHORT).show();
            db.close();
            result = true;
        }

        return result;
    }

    @Override
    public boolean update(StudentSubjectDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {obj.getIdStudentSubject()};
        ContentValues values = new ContentValues();
        values.put(FIELD_ID_STUDENT, obj.getStudent().getIdStudent());
        values.put(FIELD_ID_SUBJECT, obj.getSubject().getIdSubject());

        boolean result = false;
        int idResult = db.update(TABLE_STUDENT_SUBJECT, values, FIELD_ID_STUDENT_SUBJECT +"=?", parameters);
        Toast.makeText(context, "Se actualizarón los datos del ID: " + obj.getIdStudentSubject(), Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id};

        boolean result = false;
        int idResult = db.delete(TABLE_STUDENT_SUBJECT, FIELD_ID_STUDENT_SUBJECT +"=?", parameters);

        Toast.makeText(context, "Se eliminó el registro con ID: " + id, Toast.LENGTH_SHORT).show();

        return result;
    }

    /*
    Metodo para leer todos los datos de la TABLA, filtrados por el id de la materia (idSubject)
 */
    public ArrayList<StudentSubjectDTO> readbyidsubject(SubjectDTO subjectDTO) {
        ArrayList<StudentSubjectDTO> arrayListStudentSubjectDTO = new ArrayList<StudentSubjectDTO>();
        db = conn.getReadableDatabase();
        String[] parameters = {subjectDTO.getIdSubject()};
        String[] fields = {FIELD_ID_STUDENT_SUBJECT, FIELD_ID_STUDENT, FIELD_ID_SUBJECT};

        try {
            Cursor cursor = db.query(TABLE_STUDENT_SUBJECT, fields, FIELD_ID_SUBJECT + "=?", parameters, null, null, null);

            while(cursor.moveToNext()) {
                studentDAO = new StudentDAO(context);
                StudentDTO studentDTO = studentDAO.readbyid(cursor.getString(1));

                subjectDAO = new SubjectDAO(context);
                SubjectDTO subjectDTO1 = subjectDAO.readbyid(cursor.getString(2));

                StudentSubjectDTO studentSubjectDTO = new StudentSubjectDTO(cursor.getString(0), studentDTO, subjectDTO1);
                arrayListStudentSubjectDTO.add(studentSubjectDTO);
            }


            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return arrayListStudentSubjectDTO;
    }
}
