package com.example.practicaloginschoolsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicaloginschoolsqlite.dto.CareerDTO;
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
        CareerDTO careerDTOItem = (CareerDTO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_career, null);
        TextView textViewldCareer = convertView.findViewById(R.id.textViewAlertIdCareer);
        TextView textViewNameCareer = convertView.findViewById(R.id.textViewAlertNameCareer);
        TextView textViewDurationCareer = convertView.findViewById(R.id.textViewAlertCareerDuration);

        textViewldCareer.setText(careerDTOItem.getIdCareer());
        textViewNameCareer.setText(careerDTOItem.getNameCareer());
        textViewDurationCareer.setText(careerDTOItem.getDurationCareer());


        return convertView;
    }
}
