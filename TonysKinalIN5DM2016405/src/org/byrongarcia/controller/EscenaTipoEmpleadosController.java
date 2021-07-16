package org.byrongarcia.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.byrongarcia.bean.TipoEmpleado;
import org.byrongarcia.db.Conexion;
import org.byrongarcia.system.MainApp;

public class EscenaTipoEmpleadosController implements Initializable {
    private MainApp Stage;
    
    private ObservableList<TipoEmpleado>listaTipoEmpleado;
    @FXML private TableColumn colCodigoTipoEmpleado;
    @FXML private TableView tblTipoEmpleado;
    @FXML private TableColumn colDescripccion;
    @FXML private TextField txtCodigoTipoEmpleado;
    @FXML private TextField txtDescripcion;
    @FXML private Button btnCancelar;
    @FXML private Menu mAgregar;
    @FXML private MenuItem miNuevo;
    @FXML private MenuItem miAgregar;
    @FXML private Menu mEliminar;
    @FXML private MenuItem miEliminar;
    @FXML private Menu mModificar;
    @FXML private MenuItem miActualizar;
    @FXML private MenuItem miGuardar;
   //Metodo para desactivar controles
    public void desactivarControles(){
        txtCodigoTipoEmpleado.setEditable(false);
        txtDescripcion.setEditable(false);
        miAgregar.setDisable(true);
    }
    
    public void activarControles(){
        mModificar.setDisable(true);
        mEliminar.setDisable(true);
        txtDescripcion.setEditable(true);
        miAgregar.setDisable(false);
        btnCancelar.setDisable(false);
        limpiarControles();
    }
     //Metodo para limpiar los controles
    public void limpiarControles(){
        txtCodigoTipoEmpleado.setText("");
        txtDescripcion.setText("");
    }
    //Metodo para el menu item nuevo
    
    public void nuevo(){

        if(txtDescripcion.getText().equals("")){
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
    
     public void cancelar(){
    mAgregar.setDisable(false);
    mEliminar.setDisable(false);
    mModificar.setDisable(false);
    desactivarControles();
    limpiarControles();
    btnCancelar.setDisable(true);
    }
    //Metodo para guardar los datos
    public void guardar(){
        TipoEmpleado tipoEmpleadoNuevo = new TipoEmpleado();
        tipoEmpleadoNuevo.setDescripcion(txtDescripcion.getText());
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoEmpleado(?)}");
            sp.setString(1,tipoEmpleadoNuevo.getDescripcion());
            sp.execute();
            listaTipoEmpleado.add(tipoEmpleadoNuevo); 
        }catch(Exception e){
        e.printStackTrace();
        }
    }
    //Método para cargar los datos a la vista
    public void cargarDatos(){
        tblTipoEmpleado.setItems(getTipoEmpleado());
        colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado, Integer>("codigoTipoEmpleado") );
        colDescripccion.setCellValueFactory(new PropertyValueFactory<TipoEmpleado, String>("descripcion") );
        desactivarControles();
    }
     //este es un Metodo para ejecutar el procedimiento almacenado y llenar una lista del resultado
    public ObservableList<TipoEmpleado>getTipoEmpleado(){
        ArrayList<TipoEmpleado>lista = new ArrayList<TipoEmpleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoEmpleado()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new TipoEmpleado(
                    resultado.getInt("codigoTipoEmpleado"),
                    resultado.getString("descripcion")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaTipoEmpleado = FXCollections.observableArrayList(lista);
        }
        //metodod para seleccionar elementos de al tabla mostrarlos en los controles
    public void seleccionarElementos(){
        if(tblTipoEmpleado.getSelectionModel().getSelectedItem() == null){}
        else{
        txtCodigoTipoEmpleado.setText(String.valueOf(((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
        txtDescripcion.setText(((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getDescripcion());
        };
    }
        //Metodo para eliminar empresas
    
    public void eliminar(){
        //Verificamos que tenga seleccionado un registro
        if(tblTipoEmpleado.getSelectionModel().getSelectedItem() !=null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Tipo Empleado", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
                        sp.setInt(1,((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
                        sp.execute();
                        listaTipoEmpleado.remove(tblTipoEmpleado.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        JOptionPane.showMessageDialog(null,"Tipo de empleado eliminada con exito");
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
        }else{
            JOptionPane.showMessageDialog(null, "Debe selecionar algun registro de la tabla");
        }
    }
    @FXML
    public void editar(){
        //Verificamos que tenga seleccionado un registro
        if(tblTipoEmpleado.getSelectionModel().getSelectedItem() !=null){
            mAgregar.setDisable(true);
            mEliminar.setDisable(true);
            txtDescripcion.setEditable(true);
            miGuardar.setDisable(false);
            btnCancelar.setDisable(false);
            
        }else{
            JOptionPane.showMessageDialog(null, "Debe selecionar algun registro de la tabla");
        }
    }
    @FXML
    public void guardandoCambios(){
         actualizar();
         mAgregar.setDisable(false);
         mEliminar.setDisable(false);
         txtDescripcion.setEditable(false);
         miGuardar.setDisable(true);
         btnCancelar.setDisable(true);
         limpiarControles();
    }
    //Metodo para actualizar los datos del modelo y del tableview ejecutando el sp
    public void actualizar(){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoEmpleado(?,?)}");
            TipoEmpleado empresaActualizada = ((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem());
            //obteniendo los datos actualizados de la tabla
            empresaActualizada.setDescripcion(txtDescripcion.getText());
            //enviando los datos actualizados al motor
            sp.setInt(1, empresaActualizada.getCodigoTipoEmpleado());
            sp.setString(2,empresaActualizada.getDescripcion());
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

    public void escenaTipoEmpleados(){
        Stage.escenaTipoEmpleados();
    }
    
    public MainApp getStage() {
        return Stage;
    }

    public void setStage(MainApp Stage) {
        this.Stage = Stage;
    }
    
    public void menuPrincipal(){
        Stage.menuPrincipal();
    }
    public void escenaEmpleados(){
        Stage.escenaEmpleados();
    }
    

    
}
