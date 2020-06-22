package com.example.practicaloginschoolsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicaloginschoolsqlite.dto.TeacherDTO;

import java.util.ArrayList;

public class AdapterTeacher extends BaseAdapter {

    private Context context;
    private ArrayList<TeacherDTO> arrayListTeacherDTO;

    public AdapterTeacher(Context context, ArrayList<TeacherDTO> arrayListTeacherDTO) {
        this.arrayListTeacherDTO = arrayListTeacherDTO;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListTeacherDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListTeacherDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TeacherDTO teacherDTOItem = (TeacherDTO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_teacher, null);
        TextView textViewIdTeacher = convertView.findViewById(R.id.textViewAlertIdTeacher);
        TextView textViewNameTeacher = convertView.findViewById(R.id.textViewAlertNameTeacher);
        TextView textViewAddressTeacher = convertView.findViewById(R.id.textViewAlertAddressTeacher);
        TextView textViewPhoneTeacher = convertView.findViewById(R.id.textViewAlertPhoneTeacher);
        TextView textViewScheduleTeacher = convertView.findViewById(R.id.textViewAlertScheduleTeacher);

        textViewIdTeacher.setText(teacherDTOItem.getIdTeacher());
        textViewNameTeacher.setText(teacherDTOItem.getNameTeacher());
        textViewAddressTeacher.setText(teacherDTOItem.getAddressTeacher());
        textViewPhoneTeacher.setText(teacherDTOItem.getPhoneTeacher());
        textViewScheduleTeacher.setText(teacherDTOItem.getScheduleTeacher());

        return convertView;
    }
}
