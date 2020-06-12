package com.example.practicaloginschoolsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicaloginschoolsqlite.dto.RolUserDTO;

import java.util.ArrayList;

public class AdapterRolUser extends BaseAdapter {

    private Context context;
    private ArrayList<RolUserDTO> arrayListRolUserDTO;

    public AdapterRolUser(Context context, ArrayList<RolUserDTO> arrayListRolUserDTO) {
        this.arrayListRolUserDTO = arrayListRolUserDTO;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListRolUserDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListRolUserDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RolUserDTO userDTOItem = (RolUserDTO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_rol_user, null);
        TextView textViewIdRolUser = convertView.findViewById(R.id.textViewAlertIdRolUser);
        TextView textViewNameRolUser = convertView.findViewById(R.id.textViewAlertNameRolUser);

        textViewIdRolUser.setText(userDTOItem.getIdRol());
        textViewNameRolUser.setText(userDTOItem.getNameRol());


        return convertView;
    }
}
