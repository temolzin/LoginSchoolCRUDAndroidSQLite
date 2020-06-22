package com.example.practicaloginschoolsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicaloginschoolsqlite.dto.StudentDTO;

import java.util.ArrayList;

public class AdapterStudent extends BaseAdapter {

    private Context context;
    private ArrayList<StudentDTO> arrayListStudentDTO;

    public AdapterStudent(Context context, ArrayList<StudentDTO> arrayListStudentDTO) {
        this.arrayListStudentDTO = arrayListStudentDTO;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListStudentDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListStudentDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudentDTO studentDTOItem = (StudentDTO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_student, null);
        TextView textViewIdStudent = convertView.findViewById(R.id.textViewAlertIdStudent);
        TextView textViewNameStudent = convertView.findViewById(R.id.textViewAlertNameStudent);
        TextView textViewAddressStudent = convertView.findViewById(R.id.textViewAlertAgeStudent);
        TextView textViewSemesterStudent = convertView.findViewById(R.id.textViewAlertSemesterStudent);
        TextView textViewGenderStudent = convertView.findViewById(R.id.textViewAlertGenderStudent);
        TextView textViewAlerIdCareerStudent = convertView.findViewById(R.id.textViewAlertStudentCareer);

        textViewIdStudent.setText(studentDTOItem.getIdStudent());
        textViewNameStudent.setText(studentDTOItem.getNameStudent());
        textViewAddressStudent.setText(studentDTOItem.getAgeStudent() + "");
        textViewSemesterStudent.setText(studentDTOItem.getSemesterStudent());
        textViewGenderStudent.setText(studentDTOItem.getGenreStudent());
        textViewAlerIdCareerStudent.setText(studentDTOItem.getIdCareer().toString());

        return convertView;
    }
}
