package com.scm.scm.Model;

import lombok.Data;


public class CompraMinorista {

    private String fechaComp;
    private String folioSegura;
    private String nomCliente;
    private String dirCliente;
    private double cantidad;
    private double precio;
    private String nomMaterial;
    private double subTotalComp;
    private int iva;
    private double totalComp;
    private String estadoComp;
    private int idMaterial;
    private String nomUsuario;

    public String getFechaComp() {
        return fechaComp;
    }

    public void setFechaComp(String fechaComp) {
        this.fechaComp = fechaComp;
    }

    public String getFolioSegura() {
        return folioSegura;
    }

    public void setFolioSegura(String folioSegura) {
        this.folioSegura = folioSegura;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public String getDirCliente() {
        return dirCliente;
    }

    public void setDirCliente(String dirCliente) {
        this.dirCliente = dirCliente;
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

    public String getEstadoComp() {
        return estadoComp;
    }

    public void setEstadoComp(String estadoComp) {
        this.estadoComp = estadoComp;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }
}
