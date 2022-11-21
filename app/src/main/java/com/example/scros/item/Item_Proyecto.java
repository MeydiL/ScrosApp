package com.example.scros.item;

public class Item_Proyecto {

    String idProyecto, uidUsuario, nombreProyecto, descripcionProyecto;

    public Item_Proyecto(){

    }
    public Item_Proyecto(String idProyecto, String uidUsuario, String nombreProyecto, String descripcionProyecto){
        this.idProyecto = idProyecto;
        this.uidUsuario = uidUsuario;
        this.nombreProyecto = nombreProyecto;
        this.descripcionProyecto = descripcionProyecto;
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }
}
