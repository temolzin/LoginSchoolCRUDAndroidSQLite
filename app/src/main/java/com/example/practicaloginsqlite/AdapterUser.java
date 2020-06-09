package com.example.practicaloginsqlite;

import android.content.Context;

import com.example.practicaloginsqlite.dto.UserDTO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterUser extends BaseAdapter {

    private Context context;
    private ArrayList<UserDTO> arrayListUserDTO;

    public AdapterUser(Context context, ArrayList<UserDTO> arrayListUserDTO) {
        this.arrayListUserDTO = arrayListUserDTO;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListUserDTO.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListUserDTO.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserDTO userDTOItem = (UserDTO) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_user, null);
        TextView textViewIdUser = convertView.findViewById(R.id.textViewAlertIdUser);
        TextView textViewNameUser = convertView.findViewById(R.id.textViewAlertNameUser);
        TextView textViewPhoneUser = convertView.findViewById(R.id.textViewAlertPhoneUser);
        TextView textViewEmailUser = convertView.findViewById(R.id.textViewAlertEmailUser);

        textViewIdUser.setText(userDTOItem.getIdUser());
        textViewNameUser.setText(userDTOItem.getName());
        textViewPhoneUser.setText(userDTOItem.getPhone());
        textViewEmailUser.setText(userDTOItem.getEmail());

        return convertView;
    }
}
