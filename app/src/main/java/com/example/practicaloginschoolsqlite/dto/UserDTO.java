package com.example.practicaloginschoolsqlite.dto;

import androidx.annotation.NonNull;

import java.util.Objects;

public class UserDTO {

    private String idUser;
    private String name;
    private String phone;
    private String email;

    public UserDTO(String idUser, String name, String phone, String email) {
        this.idUser = idUser;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(idUser, userDTO.idUser) &&
                Objects.equals(name, userDTO.name) &&
                Objects.equals(phone, userDTO.phone) &&
                Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser,name,phone,email);
    }

    @NonNull
    @Override
    public String toString() {
        return "IdUser: " + this.getIdUser() + ", Nombre: " + getName();
    }
}
