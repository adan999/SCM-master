package com.scm.scm.Model;

//Modelo de Mayorista, donde las variables hacen referencia a las columnas de su respectiva tabla en la base de datos
public class Mayorista {
    private int idMayorista;

    private String rfc;

    private String nomEmpresa;

    private String dir;

    private String numTel;

    private String nomUsuario;

    //En esta seccion se generan los metodos get() y set()
    public int getIdMayorista() {
        return idMayorista;
    }

    public void setIdMayorista(int idMayorista) {
        this.idMayorista = idMayorista;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNomEmpresa() {
        return nomEmpresa;
    }

    public void setNomEmpresa(String nomEmpresa) {
        this.nomEmpresa = nomEmpresa;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }
}
