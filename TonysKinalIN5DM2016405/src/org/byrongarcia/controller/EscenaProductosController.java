package org.byrongarcia.controller;

import static java.lang.Integer.parseInt;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import static javax.xml.bind.DatatypeConverter.parseInteger;
import org.byrongarcia.bean.Producto;
import org.byrongarcia.db.Conexion;
import org.byrongarcia.system.MainApp;

public class EscenaProductosController implements Initializable {
    private MainApp stage;
    private ObservableList<Producto>listaProducto;
    @FXML private Menu mAgregar;
    @FXML private MenuItem miNuevo;
    @FXML private MenuItem miAgregar;
    @FXML private Menu mEliminar;
    @FXML private MenuItem miEliminar;
    @FXML private Menu mModificar;
    @FXML private MenuItem miActualizar;
    @FXML private MenuItem miGuardar;
    @FXML private TextField txtCodigoProducto;
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtCantidad;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colNombreProducto;
    @FXML private TableColumn colCantidad;
    @FXML private Button btnCancelar;
        public void desactivarControles(){
            txtCodigoProducto.setEditable(false);
            txtNombreProducto.setEditable(false);
            txtCantidad.setEditable(false);
            miAgregar.setDisable(true);
        }
        public void activarControles(){
            mModificar.setDisable(true);
            mEliminar.setDisable(true);
            miAgregar.setDisable(false);
            btnCancelar.setDisable(false);
            limpiarControles();
            txtNombreProducto.setEditable(true);
            txtCantidad.setEditable(true);
        }
        public void limpiarControles(){
            txtCodigoProducto.setText("");
            txtNombreProducto.setText("");
            txtCantidad.setText("");
        }
        public void cancelar(){
            mAgregar.setDisable(false);
            mEliminar.setDisable(false);
            mModificar.setDisable(false);
            desactivarControles();
            limpiarControles();
            btnCancelar.setDisable(true);
        }
        public void guardar(){
        Producto productoNuevo = new Producto();
        productoNuevo.setNombreProducto(txtNombreProducto.getText());
        productoNuevo.setCantidad(parseInt(txtCantidad.getText()));
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProductos(?,?)}");
            sp.setString(1,productoNuevo.getNombreProducto());
            sp.setInt(2,productoNuevo.getCantidad());
            sp.execute();
            listaProducto.add(productoNuevo); 
        }catch(Exception e){
        e.printStackTrace();
        }
    }
        public void nuevo(){
        if(txtCantidad.getText().equals("") || txtNombreProducto.getText().equals("") ){
            JOptionPane.showMessageDialog(null, "Debe de llenar la todas las celdas indicadas");
            
        }else{
        mModificar.setDisable(false);
        mEliminar.setDisable(false);
        guardar();
        desactivarControles();
        limpiarControles();
        btnCancelar.setDisable(true);
        cargarDatos();
        }
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
     public void cargarDatos(){
        tblProductos.setItems(getProducto());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("codigoProductos") );
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto") );
        colCantidad.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidad") );
        desactivarControles();
    }
    public void seleccionarElementos(){
        if(tblProductos.getSelectionModel().getSelectedItem() == null){}
        else{
        txtCodigoProducto.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProductos()));
        txtNombreProducto.setText(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getNombreProducto());
        txtCantidad.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCantidad()));
    };
    }
    public void eliminar(){
        //Verificamos que tenga seleccionado un registro
        if(tblProductos.getSelectionModel().getSelectedItem() !=null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Producto", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProductos(?)}");
                        sp.setInt(1,((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProductos());
                        sp.execute();
                        listaProducto.remove(tblProductos.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        JOptionPane.showMessageDialog(null,"Producto eliminado con exito");
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
        }else{
            JOptionPane.showMessageDialog(null, "Debe selecionar algun registro de la tabla");
        }
    }
    public void editar(){
        if(tblProductos.getSelectionModel().getSelectedItem() !=null){
            mAgregar.setDisable(true);
            mEliminar.setDisable(true);
            txtNombreProducto.setEditable(true);
            txtCantidad.setEditable(true);
            miGuardar.setDisable(false);
            btnCancelar.setDisable(false);
            
        }else{
            JOptionPane.showMessageDialog(null, "Debe selecionar algun registro de la tabla");
        }
    }
    public void guardandoCambios(){
         actualizar();
         mAgregar.setDisable(false);
         mEliminar.setDisable(false);
         txtNombreProducto.setEditable(false);
         txtCantidad.setEditable(false);
         miGuardar.setDisable(true);
         btnCancelar.setDisable(true);
         limpiarControles();
    }
    //Metodo para actualizar los datos del modelo y del tableview ejecutando el sp
    public void actualizar(){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProductos(?,?,?)}");
            Producto productoActualizado = ((Producto)tblProductos.getSelectionModel().getSelectedItem());
            productoActualizado.setNombreProducto(txtNombreProducto.getText());
            productoActualizado.setCantidad(parseInt(txtCantidad.getText()));
            sp.setInt(1, productoActualizado.getCodigoProductos());
            sp.setString(2,productoActualizado.getNombreProducto());
            sp.setInt(3,productoActualizado.getCantidad());
            sp.execute();
            JOptionPane.showMessageDialog(null, "Datos actualizados");
            cargarDatos();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    

    public void escenaProductos(){
        stage.escenaProductos();
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
