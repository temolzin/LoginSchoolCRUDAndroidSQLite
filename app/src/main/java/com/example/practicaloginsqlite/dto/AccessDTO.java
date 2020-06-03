package com.example.practicaloginsqlite.dto;

public class AccessDTO {
    private String idAcceso;
    private RolUserDTO objRolUser;
    private UserDTO objUser;
    private String userName;
    private String password;

    public AccessDTO(String idAcceso, RolUserDTO objRolUser, UserDTO objUser, String userName, String password) {
        this.idAcceso = idAcceso;
        this.objRolUser = objRolUser;
        this.objUser = objUser;
        this.userName = userName;
        this.password = password;
    }

    public String getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(String idAcceso) {
        this.idAcceso = idAcceso;
    }

    public RolUserDTO getObjRolUser() {
        return objRolUser;
    }

    public void setObjRolUser(RolUserDTO objRolUser) {
        this.objRolUser = objRolUser;
    }

    public UserDTO getObjUser() {
        return objUser;
    }

    public void setObjUser(UserDTO objUser) {
        this.objUser = objUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
