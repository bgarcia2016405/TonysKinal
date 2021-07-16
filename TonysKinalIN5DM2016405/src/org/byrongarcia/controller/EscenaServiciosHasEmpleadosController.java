package org.byrongarcia.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.byrongarcia.bean.Empleado;
import org.byrongarcia.bean.Servicio;
import org.byrongarcia.bean.ServiciosHasEmpleados;
import org.byrongarcia.db.Conexion;
import org.byrongarcia.system.MainApp;

public class EscenaServiciosHasEmpleadosController implements Initializable {
    private MainApp stage;
    private DatePicker fecha;
    private ObservableList<Servicio>listaServicio;
    private ObservableList<Empleado>listaEmpleado;
    private ObservableList<ServiciosHasEmpleados>listaServiciosHasEmpleados;
    @FXML private GridPane grpServiciosHasEmpleados;
    @FXML private ComboBox cbCodigoServicio;
    @FXML private ComboBox cbCodigoEmpleado;
    @FXML private TextField txtHora;
    @FXML private TextField txtLugar;
    @FXML private TextField txtCodigo;
    @FXML private Menu mAgregar;
    @FXML private MenuItem miNuevo;
    @FXML private MenuItem miAgregar;
    @FXML private Menu mEliminar;
    @FXML private MenuItem miEliminar;
    @FXML private Menu mModificar;
    @FXML private MenuItem miActualizar;
    @FXML private MenuItem miGuardar;
    @FXML private Menu mReporte;
    @FXML private TableColumn colServicio;
    @FXML private TableColumn colEmpleado;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colHora;
    @FXML private TableColumn colLugar;
    @FXML private TableColumn colCodigo;
    @FXML private Button btnCancelar;
    @FXML private TableView tblServiciosEmpleados;
    //Metodo para desactivar controles
    public void desactivarControles(){
        txtCodigo.setEditable(false);
        txtHora.setEditable(false);
        txtLugar.setEditable(false);
        grpServiciosHasEmpleados.setDisable(true);
        miAgregar.setDisable(true);
        cbCodigoEmpleado.setEditable(false);
        cbCodigoEmpleado.setDisable(true);
        cbCodigoServicio.setEditable(false);
        cbCodigoServicio.setDisable(true);
    }
    public void activarControles(){
        mModificar.setDisable(true);
        mEliminar.setDisable(true);
        miAgregar.setDisable(false);
        btnCancelar.setDisable(false);
        limpiarControles();
        txtHora.setEditable(true);
        grpServiciosHasEmpleados.setDisable(false);
        txtLugar.setEditable(true);
        cbCodigoEmpleado.setDisable(false);
        cbCodigoServicio.setDisable(false);
    }
    //Metodo para limpiar los controles
    public void limpiarControles(){
        txtCodigo.setText("");
        txtHora.setText("");
        txtLugar.setText("");
        cbCodigoEmpleado.getSelectionModel().clearSelection();
        cbCodigoServicio.getSelectionModel().clearSelection();
   }
    //Metodo para el menu item nuevo
    public void nuevo(){
        mModificar.setDisable(false);
        mEliminar.setDisable(false);
        guardar();
        desactivarControles();
        limpiarControles();
        btnCancelar.setDisable(true);
        cargarDatos();
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
        ServiciosHasEmpleados serviciosHasEmpleadosNuevo = new ServiciosHasEmpleados();
        serviciosHasEmpleadosNuevo.setFecha(fecha.getSelectedDate());
        serviciosHasEmpleadosNuevo.setHoreEvento(txtHora.getText());
        serviciosHasEmpleadosNuevo.setLugarEvento(txtLugar.getText());
        serviciosHasEmpleadosNuevo.setServicios_CodigoServicios(((Servicio)cbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicios());
        serviciosHasEmpleadosNuevo.setEmpleados_codigoEmpleado(((Empleado)cbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicios_has_Empleados(?,?,?,?,?)}");
            sp.setInt(1,serviciosHasEmpleadosNuevo.getServicios_CodigoServicios());
            sp.setInt(2,serviciosHasEmpleadosNuevo.getEmpleados_codigoEmpleado());
            sp.setDate(3, new java.sql.Date(serviciosHasEmpleadosNuevo.getFecha().getTime()));
            sp.setString(4,serviciosHasEmpleadosNuevo.getHoreEvento());
            sp.setString(5,serviciosHasEmpleadosNuevo.getLugarEvento());
            sp.execute();
            listaServiciosHasEmpleados.add(serviciosHasEmpleadosNuevo); 
        }catch(Exception e){
        e.printStackTrace();
        }
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
    public ObservableList<ServiciosHasEmpleados>getServiciosHasEmpleados(){
        ArrayList<ServiciosHasEmpleados>lista = new ArrayList<ServiciosHasEmpleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServiciosHasEmpleados()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new ServiciosHasEmpleados(
            resultado.getInt("codigo"),
            resultado.getInt("Servicios_CodigoServicios"),
            resultado.getInt("Empleados_codigoEmpleado"),
            resultado.getDate("fecha"),
            resultado.getString("horeEvento"),
            resultado.getString("lugarEvento")));
        }
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaServiciosHasEmpleados = FXCollections.observableArrayList(lista);
    }
    public void cargarDatos(){
        tblServiciosEmpleados.setItems(getServiciosHasEmpleados());
        colCodigo.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados, Integer>("codigo") );
        colServicio.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados, Integer>("Servicios_CodigoServicios") );
        colEmpleado.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados, Integer>("Empleados_codigoEmpleado") );
        colFecha.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados, Date>("fecha") );
        colHora.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados, String>("horeEvento"));
        colLugar.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados, String>("lugarEvento") );
        desactivarControles();
    }
    public ObservableList<Empleado>getEmpleado(){
        ArrayList<Empleado>lista = new ArrayList<Empleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new Empleado(
            resultado.getInt("codigoEmpleado"),
            resultado.getString("apellidosEmpleado"),
            resultado.getString("nombresEmpleado"),
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
    public Empleado buscarEmpleado(int codigoEmpleado){
        Empleado resultado = null;
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleados(?)}");
          procedimiento.setInt(1, codigoEmpleado);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
              resultado = new Empleado(registro.getInt("codigoEmpleado"),registro.getString("apellidosEmpleado"),
                                    registro.getString("nombresEmpleado"),registro.getString("direccionEmpleado"),
                                    registro.getString("telefonoContacto"),registro.getString("gradoCocinero"),
                                    registro.getInt("TipoEmpleado_codigoTipoEmpleado"));
          }
        }catch(Exception e){
         e.printStackTrace();
        }
       return resultado; 
    }
    public void  seleccionarElementos(){
        if (tblServiciosEmpleados.getSelectionModel().getSelectedItem() == null){}
        else{
            txtCodigo.setText(String.valueOf(((ServiciosHasEmpleados)tblServiciosEmpleados.getSelectionModel().getSelectedItem()).getCodigo()));
            cbCodigoServicio.getSelectionModel().select(buscarServicio(((ServiciosHasEmpleados)tblServiciosEmpleados.getSelectionModel().getSelectedItem()).getServicios_CodigoServicios()));
            cbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((ServiciosHasEmpleados)tblServiciosEmpleados.getSelectionModel().getSelectedItem()).getEmpleados_codigoEmpleado()));
            fecha.selectedDateProperty().set(((ServiciosHasEmpleados)tblServiciosEmpleados.getSelectionModel().getSelectedItem()).getFecha());
            txtHora.setText(((ServiciosHasEmpleados)tblServiciosEmpleados.getSelectionModel().getSelectedItem()).getHoreEvento());
            txtLugar.setText(((ServiciosHasEmpleados)tblServiciosEmpleados.getSelectionModel().getSelectedItem()).getLugarEvento());
        };
    }
    public void eliminar(){
        //Verificamos que tenga seleccionado un registro
        if(tblServiciosEmpleados.getSelectionModel().getSelectedItem() !=null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_Eliminarservicios_has_empleados(?)}");
                        sp.setInt(1,((ServiciosHasEmpleados)tblServiciosEmpleados.getSelectionModel().getSelectedItem()).getServicios_CodigoServicios());
                        sp.execute();
                        listaServiciosHasEmpleados.remove(tblServiciosEmpleados.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        JOptionPane.showMessageDialog(null,"Registro eliminado con exito");
                        
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
        if(tblServiciosEmpleados.getSelectionModel().getSelectedItem() !=null){
            mAgregar.setDisable(true);
            mEliminar.setDisable(true);
            txtHora.setEditable(true);
            txtLugar.setEditable(true);
            grpServiciosHasEmpleados.setDisable(false);
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
         txtHora.setEditable(false);
        txtLugar.setEditable(false);
         grpServiciosHasEmpleados.setDisable(true); 
         miGuardar.setDisable(true);
         btnCancelar.setDisable(true);
         limpiarControles();
    }
    public void actualizar(){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizrServiciosHasEmpleados(?,?,?,?)}");
            ServiciosHasEmpleados Actualizado = ((ServiciosHasEmpleados)tblServiciosEmpleados.getSelectionModel().getSelectedItem());
            //obteniendo los datos actualizados de la tabla
            Actualizado.setFecha((java.sql.Date)fecha.getSelectedDate());
            Actualizado.setHoreEvento(txtHora.getText());
            Actualizado.setLugarEvento(txtLugar.getText());
            sp.setInt(1, Actualizado.getCodigo());
            sp.setDate(2,new java.sql.Date(Actualizado.getFecha().getTime()));
            sp.setString(3,Actualizado.getHoreEvento());
            sp.setString(4,Actualizado.getLugarEvento());
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
       cbCodigoServicio.setItems(getServicio());
       cbCodigoEmpleado.setItems(getEmpleado());
       fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(false);
        grpServiciosHasEmpleados.add(fecha, 1,3);
        fecha.getStylesheets().add("/org/byrongarcia/resurse/DatePicker.css");
    }    
    public void menuPrincipal(){
        stage.menuPrincipal();
    }
    public MainApp getStage() {
        return stage;
    }

    public void setStage(MainApp stage) {
        this.stage = stage;
    }
    public void escenaServiciosHasEmpleados(){
        stage.escenaServiciosHasEmpleados();
    }

    
    
}
