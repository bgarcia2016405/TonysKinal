package org.byrongarcia.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.byrongarcia.bean.Plato;
import org.byrongarcia.bean.Servicio;
import org.byrongarcia.bean.ServiciosHasPlatos;
import org.byrongarcia.db.Conexion;
import org.byrongarcia.system.MainApp;


public class EscenaServiciosHasPlatosController implements Initializable {
    private MainApp stage;
    private ObservableList<Plato>listaPlato;
    private ObservableList<Servicio>listaServicio;
    private ObservableList<ServiciosHasPlatos>listaServiciosHasPlatos;
    @FXML private ComboBox cbServicios;
    @FXML private ComboBox cbPlatos;
    @FXML private TableView tblServiciosHasPlatos;
    @FXML private TableColumn colServicios;
    @FXML private TableColumn colPlatos;
    
    public ObservableList<Plato>getPlato(){
        ArrayList<Plato>lista = new ArrayList<Plato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlatos()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new Plato(resultado.getInt("codigoPlato"),
                                    resultado.getInt("cantidad"),
                                    resultado.getString("nombrePlato"),
                                    resultado.getString("descripcionPlato"),
                                    resultado.getDouble("precioPlato"),
                                    resultado.getInt("TipoPlato_codigoTipoPlato")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaPlato = FXCollections.observableArrayList(lista);
        }
    public ObservableList<Servicio>getServicio(){
        ArrayList<Servicio>lista = new ArrayList<Servicio>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new Servicio(
            resultado.getInt("codigoServicios"),
            resultado.getDate("fechaServicios"),
            resultado.getString("tipoServicios"),
            resultado.getString("horaServicio"),
            resultado.getString("lugarSevicio"),
            resultado.getString("telefonoContacto"),
            resultado.getInt("Empresas_codigoEmpresa")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaServicio = FXCollections.observableArrayList(lista);
    }
    public ObservableList<ServiciosHasPlatos>getServiciosHasPlatos(){
        ArrayList<ServiciosHasPlatos>lista = new ArrayList<ServiciosHasPlatos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServiciosHasPlatos()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new ServiciosHasPlatos(resultado.getInt("Servicios_CodigoServicio"),
                                    resultado.getInt("Platos_codigoPlato")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaServiciosHasPlatos = FXCollections.observableArrayList(lista);
        }
    public void cargarDatos(){
        
        tblServiciosHasPlatos.setItems(getServiciosHasPlatos());
        colServicios.setCellValueFactory(new PropertyValueFactory<ServiciosHasPlatos, Integer>("Servicios_CodigoServicio"));
        colPlatos.setCellValueFactory(new PropertyValueFactory<ServiciosHasPlatos, Integer>("Platos_codigoPlato"));
    }
    public Plato buscarPlato(int codigoPlato){
        Plato resultado = null;
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPlatos(?)}");
          procedimiento.setInt(1, codigoPlato);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
              resultado = new Plato(registro.getInt("codigoPlato"),registro.getInt("cantidad"),
                                        registro.getString("nombrePlato"),registro.getString("descripcionPlato"),
                                        registro.getDouble("precioPlato"),registro.getInt("TipoPlato_codigoTipoPlato"));
          }
        }catch(Exception e){
          e.printStackTrace();
            
        }
        return resultado;
    }
    public Servicio buscarServicio(int codigoServicios){
        Servicio resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarServicios(?)}");
            procedimiento.setInt(1, codigoServicios);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Servicio(registro.getInt("codigoServicios"),registro.getDate("fechaServicios"),
                                        registro.getString("tipoServicios"),registro.getString("horaServicio"),
                                        registro.getString("lugarSevicio"),registro.getString("telefonoContacto"),
                                        registro.getInt("Empresas_codigoEmpresa"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }
    public void  seleccionarElementos(){
        if (tblServiciosHasPlatos.getSelectionModel().getSelectedItem() == null){}
        else{
            cbServicios.getSelectionModel().select(buscarServicio(((ServiciosHasPlatos)tblServiciosHasPlatos.getSelectionModel().getSelectedItem()).getServicios_CodigoServicio()));
            cbPlatos.getSelectionModel().select(buscarPlato(((ServiciosHasPlatos)tblServiciosHasPlatos.getSelectionModel().getSelectedItem()).getPlatos_codigoPlato()));
        };
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      cbServicios.setItems(getServicio());
      cbPlatos.setItems(getPlato());
      cargarDatos();
    }    
    public void escenaServiciosHasPlatos(){
        stage.escenaServiciosHasPlatos();
    }
    public MainApp getStage() {
        return stage;
    }

    public void setStage(MainApp stage) {
        this.stage = stage;
    }
    public void menuPrincipal(){
        stage.menuPrincipal();
    }

    
    
}
