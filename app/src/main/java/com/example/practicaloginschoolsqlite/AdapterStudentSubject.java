package com.example.practicaloginschoolsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicaloginschoolsqlite.dto.StudentSubjectDTO;

import java.util.ArrayList;

public class AdapterStudentSubject extends BaseAdapter {

    private Context context;
    private ArrayList<StudentSubjectDTO> arrayListStudentSubjectDTO;

    public AdapterStudentSubject(Context context, ArrayList<StudentSubjectDTO> arrayListStudentSubjectDTO) {
        this.arrayListStudentSubjectDTO = arrayListStudentSubjectDTO;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListStudentSubjectDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListStudentSubjectDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudentSubjectDTO studentSubjectDTOItem = (StudentSubjectDTO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_student_subject, null);
        TextView textViewldStudentSubject = convertView.findViewById(R.id.textViewAlertIdStudentSubject);
        TextView textViewStudentSS = convertView.findViewById(R.id.textViewAlertIdStudentSS);
        TextView textViewSubjectSS = convertView.findViewById(R.id.textViewAlertIdSubjectSS);

        textViewldStudentSubject.setText(studentSubjectDTOItem.getIdStudentSubject());
        textViewStudentSS.setText(studentSubjectDTOItem.getStudent().getNameStudent());
        textViewSubjectSS.setText(studentSubjectDTOItem.getSubject().getNameSubject());


        return convertView;
    }
}
