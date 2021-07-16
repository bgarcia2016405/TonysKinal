package org.byrongarcia.bean;
public class TipoPlato {
    private int codigoTipoPlato;
    private String descriccion;
    
    public TipoPlato(){
    
    }
    
    public TipoPlato(int codigoTipoPlato,String descriccion){
            this.codigoTipoPlato = codigoTipoPlato;
            this.descriccion     = descriccion;
    }

    public int getCodigoTipoPlato() {
        return codigoTipoPlato;
    }

    public void setCodigoTipoPlato(int codigoTipoPlato) {
        this.codigoTipoPlato = codigoTipoPlato;
    }

    public String getDescriccion() {
        return descriccion;
    }

    public void setDescriccion(String descriccion) {
        this.descriccion = descriccion;
    }
    public String toString(){
        return getCodigoTipoPlato()+ " | " + getDescriccion();
    }
    
}
