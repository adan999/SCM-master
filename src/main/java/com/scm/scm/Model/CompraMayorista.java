package com.scm.scm.Model;

//Modelo de CompraMayorista, donde las variables hacen referencia a las columnas de su respectiva tabla en la base de datos
public class CompraMayorista {

    private String folioComp;

    private String fechaComp;

    private double cantidad;

    private double precio;

    private double subTotalComp;

    private int iva;

    private double totalComp;

    private String estadoComp;

    private int idMaterial;

    private String nomMaterial;

    private String nomUsuario;

    private int idMayorista;

    private String nomEmpresa;

    //En esta seccion se generan los metodos get() y set()
    public String getFolioComp() {
        return folioComp;
    }

    public void setFolioComp(String folioComp) {
        this.folioComp = folioComp;
    }

    public String getFechaComp() {
        return fechaComp;
    }

    public void setFechaComp(String fechaComp) {
        this.fechaComp = fechaComp;
    }

    public double getSubTotalComp() {
        return subTotalComp;
    }

    public void setSubTotalComp(double subTotalComp) {
        this.subTotalComp = subTotalComp;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public double getTotalComp() {
        return totalComp;
    }

    public void setTotalComp(double totalComp) {
        this.totalComp = totalComp;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdMayorista() {
        return idMayorista;
    }

    public void setIdMayorista(int idMayorista) {
        this.idMayorista = idMayorista;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNomMaterial() {
        return nomMaterial;
    }

    public void setNomMaterial(String nomMaterial) {
        this.nomMaterial = nomMaterial;
    }

    public String getNomEmpresa() {
        return nomEmpresa;
    }

    public void setNomEmpresa(String nomEmpresa) {
        this.nomEmpresa = nomEmpresa;
    }

    public String getEstadoComp() {
        return estadoComp;
    }

    public void setEstadoComp(String estadoComp) {
        this.estadoComp = estadoComp;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }
}
