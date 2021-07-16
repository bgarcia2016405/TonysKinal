package org.byrongarcia.bean;

import java.util.Date;
public class Presupuesto {
    private int codigoPresupuesto;
    private Date fechaSolicitud;
    private Double cantidadPresupuesto;
    private int Empresas_codigoEmpresa;
    
    public Presupuesto(){
        
    }
    public Presupuesto(int codigoPresupuesto, Date fechaSolicitud, Double cantidadPresupuesto,
            int Empresas_codigoEmpresa){
        this.codigoPresupuesto = codigoPresupuesto;
        this.fechaSolicitud    = fechaSolicitud;
        this.cantidadPresupuesto = cantidadPresupuesto;
        this.Empresas_codigoEmpresa = Empresas_codigoEmpresa;
    }

    public int getCodigoPresupuesto() {
        return codigoPresupuesto;
    }

    public void setCodigoPresupuesto(int codigoPresupuesto) {
        this.codigoPresupuesto = codigoPresupuesto;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Double getCantidadPresupuesto() {
        return cantidadPresupuesto;
    }

    public void setCantidadPresupuesto(Double cantidadPresupuesto) {
        this.cantidadPresupuesto = cantidadPresupuesto;
    }

    public int getEmpresas_codigoEmpresa() {
        return Empresas_codigoEmpresa;
    }

    public void setEmpresas_codigoEmpresa(int Empresas_codigoEmpresa) {
        this.Empresas_codigoEmpresa = Empresas_codigoEmpresa;
    }
    
    
}
