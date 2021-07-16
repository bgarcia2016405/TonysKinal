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
import javafx.scene.control.Menu;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.byrongarcia.system.MainApp;
import org.byrongarcia.bean.TipoEmpleado;
import org.byrongarcia.bean.Empleado;
import org.byrongarcia.db.Conexion;

public class EscenaEmpleadosController implements Initializable {
    private MainApp stage;
    private ObservableList<Empleado>listaEmpleado;
    private ObservableList<TipoEmpleado>listaTipoEmpleado;
    
    @FXML
    private ComboBox cbTipoEmpleado;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtGradoCocinero;
    @FXML
    private TextField txtTipoEmpleado;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtNumeroEmpleado;
    @FXML
    private TextField txtCodigoEmpleado;
    @FXML
    private TableView tblEmpleados;
    @FXML
    private Menu mAgregar;
    @FXML
    private MenuItem miAgregar;
    @FXML
    private MenuItem miNuevo;
    @FXML
    private Menu mEliminar;
    @FXML
    private MenuItem miEliminar;
    @FXML
    private Menu mModificar;
    @FXML
    private MenuItem miActualizar;
    @FXML
    private MenuItem miGuardar;
    @FXML
    private TableColumn colCodigoEmpleado;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colNombres;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colGradoConcinero;
    @FXML
    private TableColumn colTipoEmpleado;
    //Desactivar Controles
    public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtApellidos.setEditable(false);
        txtNombres.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        txtGradoCocinero.setEditable(false);
        txtTipoEmpleado.setEditable(false);
        miAgregar.setDisable(true);
        cbTipoEmpleado.setDisable(true);
        
    }
    public void activarControles(){
        mModificar.setDisable(true);
        mEliminar.setDisable(true);
        miAgregar.setDisable(false);
        btnCancelar.setDisable(false);
        limpiarControles();
        txtApellidos.setEditable(true);
        txtNombres.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        txtGradoCocinero.setEditable(true);
        txtTipoEmpleado.setEditable(true);
        cbTipoEmpleado.setDisable(false);
    }
    //Metodo para limpiar los controles
    public void limpiarControles(){
        txtCodigoEmpleado.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtNombres.setText("");
        txtDireccion.setText("");
        txtGradoCocinero.setText("");
        txtTipoEmpleado.setText("");
        
        cbTipoEmpleado.getSelectionModel().clearSelection();
        
   }
    //Metodo para el menu item nuevo
    public void nuevo(){
        if(txtApellidos.getText().equals("") || txtNombres.getText().equals("") || txtTelefono.getText().equals("")
                || txtDireccion.getText().equals("") || cbTipoEmpleado.getSelectionModel().getSelectedItem() == null){
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
        Empleado empleadoNuevo = new Empleado();
        empleadoNuevo.setApellidosEmpleado(txtApellidos.getText());
        empleadoNuevo.setNombresEmpleado(txtNombres.getText());
        empleadoNuevo.setDireccionEmpleado(txtDireccion.getText());
        empleadoNuevo.setTelefonoContacto(txtTelefono.getText());
        empleadoNuevo.setGradoCocinero(txtGradoCocinero.getText());
        empleadoNuevo.setTipoEmpleado_codigoTipoEmpleado(((TipoEmpleado)cbTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleados(?,?,?,?,?,?)}");
            sp.setString(1,empleadoNuevo.getApellidosEmpleado());
            sp.setString(2,empleadoNuevo.getNombresEmpleado());
            sp.setString(3,empleadoNuevo.getDireccionEmpleado());
            sp.setString(4,empleadoNuevo.getTelefonoContacto());
            sp.setString(5,empleadoNuevo.getGradoCocinero());
            sp.setInt(6,empleadoNuevo.getTipoEmpleado_codigoTipoEmpleado());
            sp.execute();
            listaEmpleado.add(empleadoNuevo); 
        }catch(Exception e){
        e.printStackTrace();
        }
    }
     //este es un Metodo para ejecutar el procedimiento almacenado y llenar una lista del resultado
    public ObservableList<Empleado>getEmpleado(){
        ArrayList<Empleado>lista = new ArrayList<Empleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new Empleado(
            resultado.getInt("codigoEmpleado"),
            resultado.getString("apellidosEmpleado"),
            resultado.getString("nombresempleado"),
            resultado.getString("direccionEmpleado"),
            resultado.getString("telefonoContacto"),
            resultado.getString("gradoCocinero"),
            resultado.getInt("TipoEmpleado_codigoTipoEmpleado")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaEmpleado = FXCollections.observableArrayList(lista);
    }
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
    //Método para cargar los datos a la vista
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleado());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleado") );
        colApellidos.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidosEmpleado") );
        colNombres.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombresEmpleado") );
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccionEmpleado"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoContacto") );
        colGradoConcinero.setCellValueFactory(new PropertyValueFactory<Empleado, String>("gradoCocinero") );
        colTipoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("TipoEmpleado_codigoTipoEmpleado") );
        
        desactivarControles();
    }
    public TipoEmpleado buscarTipoEmpleado(int codigoTipoEmpleado){
        TipoEmpleado resultado = null;
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoEmpleado(?)}");
          procedimiento.setInt(1, codigoTipoEmpleado);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
              resultado = new TipoEmpleado(registro.getInt("codigoTipoEmpleado"),registro.getString("descripcion"));
          }
        }catch(Exception e){
          e.printStackTrace();
            
        }
        return resultado;
    }
    //metodod para seleccionar elementos de al tabla mostrarlos en los controles
    public void seleccionarElementos(){
        if(tblEmpleados.getSelectionModel().getSelectedItem() == null){}
        else{
        txtCodigoEmpleado.setText(String.valueOf(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtApellidos.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
        txtNombres.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtDireccion.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccionEmpleado());
        txtTelefono.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoContacto());
        txtGradoCocinero.setText(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getGradoCocinero());
        cbTipoEmpleado.getSelectionModel().select(buscarTipoEmpleado(((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getTipoEmpleado_codigoTipoEmpleado()));
    };
    }
     //Metodo para eliminar empleado
    public void eliminar(){
        //Verificamos que tenga seleccionado un registro
        if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleados(?)}");
                        sp.setInt(1,((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                        sp.execute();
                        listaEmpleado.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        JOptionPane.showMessageDialog(null,"Empleado eliminad@ con exito");
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
        }else{
            JOptionPane.showMessageDialog(null, "Debe selecionar algun registro de la tabla");
        }
    }
    public void editar(){
        //Verificamos que tenga seleccionado un registro
        if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
            mAgregar.setDisable(true);
            mEliminar.setDisable(true);
            txtApellidos.setEditable(true);
            txtDireccion.setEditable(true);
            txtTelefono.setEditable(true);
            txtNombres.setEditable(true);
            txtGradoCocinero.setEditable(true); 
            //cbTipoEmpleado.setDisable(false);
            //cbTipoEmpleado.setEditable(true);
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
         txtApellidos.setEditable(false);
            txtDireccion.setEditable(false);
            txtTelefono.setEditable(false);
            txtNombres.setEditable(false);
            txtGradoCocinero.setEditable(false); 
            //cbTipoEmpleado.setEditable(false);
         miGuardar.setDisable(true);
         btnCancelar.setDisable(true);
         limpiarControles();
    }
    //Metodo para actualizar los datos del modelo y del tableview ejecutando el sp
    public void actualizar(){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpleados(?,?,?,?,?,?)}");
            Empleado empleadoActualizado = ((Empleado)tblEmpleados.getSelectionModel().getSelectedItem());
            //obteniendo los datos actualizados de la tabla
            empleadoActualizado.setApellidosEmpleado(txtApellidos.getText());
            empleadoActualizado.setNombresEmpleado(txtNombres.getText());
            empleadoActualizado.setDireccionEmpleado(txtDireccion.getText());
            empleadoActualizado.setTelefonoContacto(txtTelefono.getText());
            empleadoActualizado.setGradoCocinero(txtGradoCocinero.getText());
            //empleadoActualizado.setTipoEmpleado_codigoTipoEmpleado(((TipoEmpleado)cbTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
            //enviando los datos actualizados al motor
            sp.setInt(1, empleadoActualizado.getCodigoEmpleado());
            sp.setString(2,empleadoActualizado.getApellidosEmpleado());
            sp.setString(3,empleadoActualizado.getNombresEmpleado());
            sp.setString(4,empleadoActualizado.getDireccionEmpleado());
            sp.setString(5,empleadoActualizado.getTelefonoContacto());
            sp.setString(6,empleadoActualizado.getGradoCocinero());
            //sp.setInt(7,empleadoActualizado.getTipoEmpleado_codigoTipoEmpleado());
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
        cbTipoEmpleado.setItems(getTipoEmpleado());
    }    

    public void escenaEmpleados(){
        stage.escenaEmpleados();
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
     public void escenaTipoEmpleados(){
        stage.escenaTipoEmpleados();
    }
}
