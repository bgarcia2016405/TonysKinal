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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.byrongarcia.bean.Plato;
import org.byrongarcia.bean.Producto;
import org.byrongarcia.bean.ProductosHasPlatos;
import org.byrongarcia.bean.TipoPlato;
import org.byrongarcia.db.Conexion;
import org.byrongarcia.system.MainApp;

public class EscenaProductosHasPlatosController implements Initializable {
       private MainApp stage;
       
       private ObservableList<Producto>listaProducto;
       private ObservableList<Plato>listaPlato;
       private ObservableList<ProductosHasPlatos>listaProductosHasPlatos;
    @FXML private Menu mAgregar;
    @FXML private MenuItem miNuevo;
    @FXML private MenuItem miAgregar;
    @FXML private Menu mEliminar;
    @FXML private MenuItem miEliminar;
    @FXML private Menu mModificar;
    @FXML private MenuItem miActualizar;
    @FXML private MenuItem miGuardar;
    @FXML private ComboBox cbProductos;
    @FXML private ComboBox cbPlatos;
    @FXML private TableView tblProductosHasPlatos;
    @FXML private TableColumn colPlatos;
    @FXML private TableColumn colProductos;
    @FXML private Button btnCancelar;
    
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
    
    public ObservableList<Producto>getProducto(){
        ArrayList<Producto>lista = new ArrayList<Producto>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new Producto(resultado.getInt("codigoProductos"),
                                    resultado.getString("nombreProducto"),
                                    resultado.getInt("cantidad")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaProducto = FXCollections.observableArrayList(lista);
        }
    public ObservableList<ProductosHasPlatos>getProductosHasPlatos(){
        ArrayList<ProductosHasPlatos>lista = new ArrayList<ProductosHasPlatos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductosHasPlatos()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new ProductosHasPlatos(resultado.getInt("Productos_codigoProducto"),
                                    resultado.getInt("Platos_codigoPlato")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaProductosHasPlatos = FXCollections.observableArrayList(lista);
        }
    public void cargarDatos(){
        tblProductosHasPlatos.setItems(getProductosHasPlatos());
        colProductos.setCellValueFactory(new PropertyValueFactory<ProductosHasPlatos, Integer>("Productos_codigoProducto"));
        colPlatos.setCellValueFactory(new PropertyValueFactory<ProductosHasPlatos, Integer>("Platos_codigoPlato"));
    }
    public Producto buscarProducto(int codigoProductos){
        Producto resultado = null;
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos(?)}");
          procedimiento.setInt(1, codigoProductos);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
              resultado = new Producto(registro.getInt("codigoProductos"),registro.getString("nombreProducto"),
                                        registro.getInt("cantidad"));
          }
        }catch(Exception e){
          e.printStackTrace();
            
        }
        return resultado;
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
    public void seleccionarElementos(){
        if (tblProductosHasPlatos.getSelectionModel().getSelectedItem() == null){}
        else{
        cbProductos.getSelectionModel().select(buscarProducto(((ProductosHasPlatos)tblProductosHasPlatos.getSelectionModel().getSelectedItem()).getProductos_codigoProducto()));
        cbPlatos.getSelectionModel().select(buscarPlato(((ProductosHasPlatos)tblProductosHasPlatos.getSelectionModel().getSelectedItem()).getPlatos_codigoPlato()));
                };
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cbPlatos.setItems(getPlato());
        cbProductos.setItems(getProducto());
    }    

    public void EscenaProductosHasPlatos(){
        stage.escenaProductosHasPlatos();
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
