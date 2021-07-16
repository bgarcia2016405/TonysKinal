package org.byrongarcia.bean;
public class ServiciosHasPlatos {
    private int Servicios_CodigoServicio;
    private int Platos_codigoPlato;
    
    public ServiciosHasPlatos(){}
    
    public ServiciosHasPlatos(int Servicios_CodigoServicio, int Platos_codigoPlato){
        this.Servicios_CodigoServicio=Servicios_CodigoServicio;
        this.Platos_codigoPlato    =Platos_codigoPlato;
    }

    public int getServicios_CodigoServicio() {
        return Servicios_CodigoServicio;
    }

    public void setServicios_CodigoServicio(int Servicios_CodigoServicio) {
        this.Servicios_CodigoServicio = Servicios_CodigoServicio;
    }

    public int getPlatos_codigoPlato() {
        return Platos_codigoPlato;
    }

    public void setPlatos_codigoPlato(int Platos_codigoPlato) {
        this.Platos_codigoPlato = Platos_codigoPlato;
    }

    
}
