package com.example.practicaloginschoolsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.practicaloginschoolsqlite.dto.AccessDTO;

import java.util.ArrayList;

public class AdapterAccess extends BaseAdapter {

    private Context context;
    private ArrayList<AccessDTO> arrayListAccessDTO;

    public AdapterAccess(Context context, ArrayList<AccessDTO> arrayListAccessDTO) {
        this.arrayListAccessDTO = arrayListAccessDTO;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListAccessDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListAccessDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AccessDTO accessDTOItem = (AccessDTO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_access, null);
        TextView textViewAccessIdUser = convertView.findViewById(R.id.textViewAccessIdAccess);
        TextView textViewAccessRolUser = convertView.findViewById(R.id.textViewAccessRolUser);
        TextView textViewAccessUser = convertView.findViewById(R.id.textViewAccessUser);
        TextView textViewAccessUsername = convertView.findViewById(R.id.textViewAccessUsername);
        TextView textViewAccessPassowrd = convertView.findViewById(R.id.textViewAccessPassword);

        textViewAccessIdUser.setText(accessDTOItem.getIdAccess());
        //En caso de que no haya registros en el acceso para que no muestre error de objeto nulo, se muestra String vació
        if(accessDTOItem.getObjUser() == null) {
            textViewAccessUser.setText(accessDTOItem.getIdUser());
        } else if(accessDTOItem.getObjRolUser() == null) {
            textViewAccessRolUser.setText(accessDTOItem.getIdRolUser());
        } else {
            textViewAccessRolUser.setText(accessDTOItem.getObjRolUser().getNameRol());
            textViewAccessUser.setText(accessDTOItem.getObjUser().getName());
        }

        textViewAccessUsername.setText(accessDTOItem.getUserName());
        textViewAccessPassowrd.setText(accessDTOItem.getPassword());

        return convertView;
    }
}
