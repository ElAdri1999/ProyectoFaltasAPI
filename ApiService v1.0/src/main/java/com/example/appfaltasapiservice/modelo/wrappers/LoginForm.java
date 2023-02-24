package com.example.appfaltasapiservice.modelo.wrappers;

public class LoginForm {
    private String usuario;
    private String contra;

    public LoginForm() {
    }

    public LoginForm(String usuario, String contra) {
        this.usuario = usuario;
        this.contra = contra;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
}





