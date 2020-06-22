package com.example.practicaloginschoolsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicaloginschoolsqlite.dto.SubjectDTO;
import com.example.practicaloginschoolsqlite.dto.SubjectDTO;

import java.util.ArrayList;

public class AdapterSubject extends BaseAdapter {

    private Context context;
    private ArrayList<SubjectDTO> arrayListSubjectDTO;

    public AdapterSubject(Context context, ArrayList<SubjectDTO> arrayListSubjectDTO) {
        this.arrayListSubjectDTO = arrayListSubjectDTO;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListSubjectDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListSubjectDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubjectDTO subjectDTOItem = (SubjectDTO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_subject, null);
        TextView textViewldSubject = convertView.findViewById(R.id.textViewAlertIdSubject);
        TextView textViewNameSubject = convertView.findViewById(R.id.textViewAlertNameSubject);
        TextView textViewSubjectCredit = convertView.findViewById(R.id.textViewAlertSubjectCredit);

        textViewldSubject.setText(subjectDTOItem.getIdSubject());
        textViewNameSubject.setText(subjectDTOItem.getNameSubject());
        textViewSubjectCredit.setText(subjectDTOItem.getCreditSubject() + "");


        return convertView;
    }
}
