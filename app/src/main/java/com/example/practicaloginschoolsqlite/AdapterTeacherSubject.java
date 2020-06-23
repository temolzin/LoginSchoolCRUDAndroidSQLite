package com.example.practicaloginschoolsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicaloginschoolsqlite.dto.TeacherSubjectDTO;

import java.util.ArrayList;

public class AdapterTeacherSubject extends BaseAdapter {

    private Context context;
    private ArrayList<TeacherSubjectDTO> arrayListTeacherSubjectDTO;

    public AdapterTeacherSubject(Context context, ArrayList<TeacherSubjectDTO> arrayListTeacherSubjectDTO) {
        this.arrayListTeacherSubjectDTO = arrayListTeacherSubjectDTO;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListTeacherSubjectDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListTeacherSubjectDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TeacherSubjectDTO teacherSubjectDTOItem = (TeacherSubjectDTO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_teacher_subject, null);
        TextView textViewldTeacherSubject = convertView.findViewById(R.id.textViewAlertIdTeacherSubject);
        TextView textViewTeacherTS = convertView.findViewById(R.id.textViewAlertIdTeacherTS);
        TextView textViewSubjectTS = convertView.findViewById(R.id.textViewAlertIdSubjectTS);

        //En caso de que no haya registros en el acceso para que no muestre error de objeto nulo, se muestra String vació
        if(teacherSubjectDTOItem.getTeacher() == null) {
            textViewTeacherTS.setText("");
        } else if(teacherSubjectDTOItem.getSubject() == null) {
            textViewSubjectTS.setText("");
        } else {
            textViewTeacherTS.setText(teacherSubjectDTOItem.getTeacher().getNameTeacher());
            textViewSubjectTS.setText(teacherSubjectDTOItem.getSubject().getNameSubject());
        }
        textViewldTeacherSubject.setText(teacherSubjectDTOItem.getIdTeacherSubject());



        return convertView;
    }
}
