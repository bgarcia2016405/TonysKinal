package org.byrongarcia.bean;

import java.util.Date;

public class ServiciosHasEmpleados {
    private int codigo;
    private int Servicios_CodigoServicios;
    private int Empleados_codigoEmpleado;
    private Date fecha;
    private String horeEvento;
    private String lugarEvento;

public ServiciosHasEmpleados(){}

public ServiciosHasEmpleados(int codigo,int Servicios_CodigoServicios,int Empleados_codigoEmpleado,
                            Date fecha, String horeEvento, String lugarEvento){
    this.codigo                     = codigo;
    this.Servicios_CodigoServicios  = Servicios_CodigoServicios;
    this.Empleados_codigoEmpleado   = Empleados_codigoEmpleado;
    this.fecha                      = fecha;
    this.horeEvento                 = horeEvento;
    this.lugarEvento                = lugarEvento;
            }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    public int getServicios_CodigoServicios() {
        return Servicios_CodigoServicios;
    }

    public void setServicios_CodigoServicios(int Servicios_CodigoServicios) {
        this.Servicios_CodigoServicios = Servicios_CodigoServicios;
    }

    public int getEmpleados_codigoEmpleado() {
        return Empleados_codigoEmpleado;
    }

    public void setEmpleados_codigoEmpleado(int Empleados_codigoEmpleado) {
        this.Empleados_codigoEmpleado = Empleados_codigoEmpleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHoreEvento() {
        return horeEvento;
    }

    public void setHoreEvento(String horeEvento) {
        this.horeEvento = horeEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

}
