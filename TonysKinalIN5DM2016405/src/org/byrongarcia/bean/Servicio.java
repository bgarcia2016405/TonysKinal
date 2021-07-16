package org.byrongarcia.bean;

import java.util.Date;

public class Servicio {
    private int codigoServicios;
    private Date fechaServicios;
    private String tipoServicios;
    private String horaServicio;
    private String lugarSevicio;
    private String telefonoContacto;
    private int Empresas_codigoEmpresa;
    
public Servicio(){
    
}
public Servicio(int codigoServicios, Date fechaServicios,
                String tipoServicios, String horaServicio,
                String lugarSevicio, String telefonoContacto,
                int Empreasa_codigoEmpresa){
    this.codigoServicios = codigoServicios;
    this.fechaServicios = fechaServicios;
    this.tipoServicios = tipoServicios;
    this.horaServicio = horaServicio;
    this.lugarSevicio = lugarSevicio;
    this.telefonoContacto = telefonoContacto;
    this.Empresas_codigoEmpresa = Empreasa_codigoEmpresa;
}


    public int getCodigoServicios() {
        return codigoServicios;
    }

    public void setCodigoServicio(int codigoServicios) {
        this.codigoServicios = codigoServicios;
    }

    public Date getFechaServicios() {
        return fechaServicios;
    }

    public void setFechaServicios(Date fechaServicios) {
        this.fechaServicios = fechaServicios;
    }

    public String getTipoServicios() {
        return tipoServicios;
    }

    public void setTipoServicios(String tipoServicios) {
        this.tipoServicios = tipoServicios;
    }

    public String getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(String horaServicio) {
        this.horaServicio = horaServicio;
    }

    public String getLugarSevicio() {
        return lugarSevicio;
    }

    public void setLugarSevicio(String lugarSevicio) {
        this.lugarSevicio = lugarSevicio;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public int getEmpresas_codigoEmpresa() {
        return Empresas_codigoEmpresa;
    }

    public void setEmpresas_codigoEmpresa(int Empresas_codigoEmpresa) {
        this.Empresas_codigoEmpresa = Empresas_codigoEmpresa;
    }

    public String toString(){
        return getCodigoServicios()+ " | "+ getTipoServicios();
    }
}
