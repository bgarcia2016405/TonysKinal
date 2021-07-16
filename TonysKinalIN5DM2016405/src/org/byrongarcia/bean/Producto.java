package org.byrongarcia.bean;

public class Producto {
    private int codigoProductos;
    private String nombreProducto;
    private int cantidad;
    
    public Producto(){}
    
    public Producto(int codigoProductos,
                    String nombreProducto,
                    int cantidad){
        this.codigoProductos = codigoProductos;
        this.nombreProducto = nombreProducto;
        this.cantidad        = cantidad;
    }

    public int getCodigoProductos() {
        return codigoProductos;
    }

    public void setCodigoProductos(int codigoProductos) {
        this.codigoProductos = codigoProductos;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

   

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public String toString(){
        return getCodigoProductos()+ " | " + getNombreProducto();
    }
}
