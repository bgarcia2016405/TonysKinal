package org.byrongarcia.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.byrongarcia.bean.Empresa;
import org.byrongarcia.db.Conexion;
import org.byrongarcia.report.GenerarReporte;
import org.byrongarcia.system.MainApp;

public class EscenaEmpresasController implements Initializable {
    private MainApp stage;

    private ObservableList<Empresa>listaEmpresa;
    @FXML private MenuItem miGeneral;
    @FXML private MenuItem miNuevo;
    @FXML private MenuItem miEliminar;
    @FXML private MenuItem miGuardar;
    @FXML private MenuItem miAgregar;
    @FXML private MenuItem miActualizar;
    @FXML private MenuItem miEspecifico;
    @FXML private Button btnCancelar;
    @FXML private Menu mAgregar;
    @FXML private Menu mEliminar;
    @FXML private Menu mModificar;
    @FXML private Menu mReporte;
    @FXML private TextField txtNombreEmpresa;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCodigoEmpresa;
    @FXML private TableView tblEmpresas;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private TableColumn colNombreEmpresa;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTelefono;
    //Metodo para desactivar controles
    public void desactivarControles(){
        txtNombreEmpresa.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        miAgregar.setDisable(true);
        txtCodigoEmpresa.setEditable(false);
    }
    //Metodo para activar controles
    public void activarControles(){
        mModificar.setDisable(true);
        mEliminar.setDisable(true);
        mReporte.setDisable(true);
        txtNombreEmpresa.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        miAgregar.setDisable(false);
        //txtCodigoEmpresa.setEditable(true);
        btnCancelar.setDisable(false);
        limpiarControles();
    }
    //Metodo para limpiar los controles
    public void limpiarControles(){
        
        txtNombreEmpresa.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCodigoEmpresa.setText("");
    }
    //Metodo para el menu item nuevo
    public void nuevo(){
        if(txtNombreEmpresa.getText().equals("") || txtDireccion.getText().equals("") || txtTelefono.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Debe de llenar la todas las celdas indicadas");
            
        }else{
        mModificar.setDisable(false);
        mEliminar.setDisable(false);
        mReporte.setDisable(false);
        guardar();
        desactivarControles();
        limpiarControles();
        btnCancelar.setDisable(true);
        cargarDatos();
        }
    }
    //Metodo para guardar los datos
    public void guardar(){
        Empresa empresaNueva = new Empresa();
        empresaNueva.setNombreEmpresa(txtNombreEmpresa.getText());
        empresaNueva.setDireccion(txtDireccion.getText());
        empresaNueva.setTelefono(txtTelefono.getText());
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpresas(?,?,?)}");
            sp.setString(1,empresaNueva.getNombreEmpresa());
            sp.setString(2,empresaNueva.getDireccion());
            sp.setString(3,empresaNueva.getTelefono());
            sp.execute();
            listaEmpresa.add(empresaNueva); 
        }catch(Exception e){
        e.printStackTrace();
        }
    }
    //Metodo para cancelar un guardar
    public void cancelar(){
    mAgregar.setDisable(false);
    mEliminar.setDisable(false);
    mModificar.setDisable(false);
    desactivarControles();
    limpiarControles();
    btnCancelar.setDisable(true);
    }
    //Método para cargar los datos a la vista
    public void cargarDatos(){
        tblEmpresas.setItems(getEmpresa());
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, Integer>("codigoEmpresa") );
        colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nombreEmpresa") );
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empresa, String>("direccion") );
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empresa, String>("telefono") );
        desactivarControles();

    }
    
    //este es un Metodo para ejecutar el procedimiento almacenado y llenar una lista del resultado
    public ObservableList<Empresa>getEmpresa(){
        ArrayList<Empresa>lista = new ArrayList<Empresa>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpresas()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new Empresa(resultado.getInt("codigoEmpresa"),
                    resultado.getString("nombreEmpresa"),
                    resultado.getString("direccion"),
                    resultado.getString("telefono")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaEmpresa = FXCollections.observableArrayList(lista);
    }
    //metodod para seleccionar elementos de al tabla mostrarlos en los controles
    public void seleccionarElementos(){
        if(tblEmpresas.getSelectionModel().getSelectedItem() == null){}
        else{
        txtCodigoEmpresa.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
        txtNombreEmpresa.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getNombreEmpresa());
        txtDireccion.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getDireccion());
        txtTelefono.setText(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getTelefono());
        };
    }
   
    //Metodo para eliminar empresas
    public void eliminarEmpresa(){
        //Verificamos que tenga seleccionado un registro
        if(tblEmpresas.getSelectionModel().getSelectedItem() !=null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Empresa", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpresas(?)}");
                        sp.setInt(1,((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
                        sp.execute();
                        listaEmpresa.remove(tblEmpresas.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        JOptionPane.showMessageDialog(null,"Empresa eliminada con exito");
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
        }else{
            JOptionPane.showMessageDialog(null, "Debe selecionar algun registro de la tabla");
        }
    }
    public void editarEmpresas(){
        //Verificamos que tenga seleccionado un registro
        if(tblEmpresas.getSelectionModel().getSelectedItem() !=null){
            mAgregar.setDisable(true);
            mEliminar.setDisable(true);
            txtNombreEmpresa.setEditable(true);
            txtDireccion.setEditable(true);
            txtTelefono.setEditable(true);
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
         txtNombreEmpresa.setEditable(false);
         txtDireccion.setEditable(false);
         txtTelefono.setEditable(false);
         miGuardar.setDisable(true);
         btnCancelar.setDisable(true);
         limpiarControles();
    }
    //Metodo para actualizar los datos del modelo y del tableview ejecutando el sp
    public void actualizar(){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpresas(?,?,?,?)}");
            Empresa empresaActualizada = ((Empresa)tblEmpresas.getSelectionModel().getSelectedItem());
            //obteniendo los datos actualizados de la tabla
            empresaActualizada.setNombreEmpresa(txtNombreEmpresa.getText());
            empresaActualizada.setDireccion(txtDireccion.getText());
            empresaActualizada.setTelefono(txtTelefono.getText());
            //enviando los datos actualizados al motor
            sp.setInt(1, empresaActualizada.getCodigoEmpresa());
            sp.setString(2,empresaActualizada.getNombreEmpresa());
            sp.setString(3,empresaActualizada.getDireccion());
            sp.setString(4,empresaActualizada.getTelefono());
            sp.execute();
            JOptionPane.showMessageDialog(null, "Datos actualizados");
            cargarDatos();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoEmpresa", null);
        GenerarReporte.mostrarReporte("ReporteEmpresas.jasper", "Reporte de Empresas", parametros);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO lo que se programa aca se ejecuta cuando se llama la vista
        cargarDatos();
        
        
        
    }    
 
public void escenaEmpresas(){
        stage.escenaEmpresas();
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
   public void escenaPresupuesto(){
        stage.escenaPresupuesto();
    }
   public void escenaServicios(){
        stage.escenaServicios();
    }
   
}
 
    

