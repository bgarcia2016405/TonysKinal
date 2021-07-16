package org.byrongarcia.controller;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
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
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.util.Map;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.byrongarcia.bean.Empresa;
import org.byrongarcia.bean.Servicio;
import org.byrongarcia.db.Conexion;
import org.byrongarcia.report.GenerarReporte;
import org.byrongarcia.system.MainApp;

public class EscenaServiciosController implements Initializable {
    private MainApp Stage;
    private ObservableList<Servicio>listaServicio;
    private ObservableList<Empresa>listaEmpresa;
    private DatePicker fecha;
    @FXML private GridPane grpFechaServicio;
    @FXML private TextField txtLugarServicio;
    @FXML private TextField txtTelefonoContacto;
    @FXML private TextField txtHoraServicio;
    @FXML private TextField txtTipoServicio;
    @FXML private TextField txtFechaServicio;
    @FXML private TextField txtCodigoServicio;
    @FXML private Button btnCancelar;
    @FXML private ComboBox cbEmpresa;
    @FXML private TextField txtTipoEmpleado;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colTipoServicio;
    @FXML private TableColumn colHora;
    @FXML private TableColumn colLugar;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colEmpresa;
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
        txtCodigoServicio.setEditable(false);
        txtTipoServicio.setEditable(false);
        txtHoraServicio.setEditable(false);
        txtLugarServicio.setEditable(false);
        txtTelefonoContacto.setEditable(false);
        grpFechaServicio.setDisable(true);
        miAgregar.setDisable(true);
        cbEmpresa.setEditable(false);
        cbEmpresa.setDisable(true);
        
    }
    public void activarControles(){
        mModificar.setDisable(true);
        mEliminar.setDisable(true);
        miAgregar.setDisable(false);
        btnCancelar.setDisable(false);
        limpiarControles();
        txtTipoServicio.setEditable(true);
        grpFechaServicio.setDisable(false);
        txtHoraServicio.setEditable(true);
        txtTelefonoContacto.setEditable(true);
        txtLugarServicio.setEditable(true);
        cbEmpresa.setDisable(false);
        
    }
    //Metodo para limpiar los controles
    public void limpiarControles(){
        txtTipoServicio.setText("");
        txtHoraServicio.setText("");
        txtTelefonoContacto.setText("");
        txtLugarServicio.setText("");
        txtCodigoServicio.setText("");
        txtTipoEmpleado.setText("");
        cbEmpresa.getSelectionModel().clearSelection();
        
   }
    //Metodo para el menu item nuevo
    public void nuevo(){
        if(txtTipoServicio.getText().equals("") || txtHoraServicio.getText().equals("") || txtTelefonoContacto.getText().equals("")
               ||txtLugarServicio.getText().equals("")  || txtTipoEmpleado.getText().equals("") || cbEmpresa.getSelectionModel().getSelectedItem() == null){
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
        Servicio servicioNuevo = new Servicio();
        servicioNuevo.setFechaServicios(fecha.getSelectedDate());
        servicioNuevo.setTipoServicios(txtTipoEmpleado.getText());
        servicioNuevo.setHoraServicio(txtHoraServicio.getText());
        servicioNuevo.setLugarSevicio(txtLugarServicio.getText());
        servicioNuevo.setTelefonoContacto(txtTelefonoContacto.getText());
        servicioNuevo.setEmpresas_codigoEmpresa(((Empresa)cbEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicios(?,?,?,?,?,?)}");
            sp.setDate(1, new java.sql.Date(servicioNuevo.getFechaServicios().getTime()));
            sp.setString(2,servicioNuevo.getTipoServicios());
            sp.setString(3,servicioNuevo.getHoraServicio());
            sp.setString(4,servicioNuevo.getLugarSevicio());
            sp.setString(5,servicioNuevo.getTelefonoContacto());
            sp.setInt(6,servicioNuevo.getEmpresas_codigoEmpresa());
            sp.execute();
            listaServicio.add(servicioNuevo); 
        }catch(Exception e){
        e.printStackTrace();
        }
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
    public void cargarDatos(){
        tblEmpleados.setItems(getServicio());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicio, Integer>("codigoServicios") );
        colFecha.setCellValueFactory(new PropertyValueFactory<Servicio, Date>("fechaServicios") );
        colTipoServicio.setCellValueFactory(new PropertyValueFactory<Servicio, String>("tipoServicios") );
        colHora.setCellValueFactory(new PropertyValueFactory<Servicio, String>("horaServicio"));
        colLugar.setCellValueFactory(new PropertyValueFactory<Servicio, String>("lugarSevicio") );
        colTelefono.setCellValueFactory(new PropertyValueFactory<Servicio, String>("telefonoContacto") );
        colEmpresa.setCellValueFactory(new PropertyValueFactory<Servicio, Integer>("Empresas_codigoEmpresa") );
        
        desactivarControles();
    }
    public Empresa buscarEmpresa(int codigoEmpresa){
        Empresa resultado = null;
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpresas(?)}");
          procedimiento.setInt(1, codigoEmpresa);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
              resultado = new Empresa(registro.getInt("codigoEmpresa"),registro.getString("nombreEmpresa"),
                                        registro.getString("direccion"),registro.getString("telefono"));
          }
        }catch(Exception e){
          e.printStackTrace();
            
        }
        return resultado;
    }
    public void seleccionarElementos(){
        if(tblEmpleados.getSelectionModel().getSelectedItem() == null){
        }
        else{
        txtCodigoServicio.setText(String.valueOf(((Servicio)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoServicios()));
        fecha.selectedDateProperty().set(((Servicio)tblEmpleados.getSelectionModel().getSelectedItem()).getFechaServicios());
        txtTipoServicio.setText(((Servicio)tblEmpleados.getSelectionModel().getSelectedItem()).getTipoServicios());
        txtHoraServicio.setText(((Servicio)tblEmpleados.getSelectionModel().getSelectedItem()).getHoraServicio());
        txtLugarServicio.setText(((Servicio)tblEmpleados.getSelectionModel().getSelectedItem()).getLugarSevicio());
        txtTelefonoContacto.setText(((Servicio)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoContacto());
        cbEmpresa.getSelectionModel().select(buscarEmpresa(((Servicio)tblEmpleados.getSelectionModel().getSelectedItem()).getEmpresas_codigoEmpresa()));
        };
    }
    //Metodo para eliminar empleado
    public void eliminar(){
        //Verificamos que tenga seleccionado un registro
        if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarServicios(?)}");
                        sp.setInt(1,((Servicio)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoServicios());
                        sp.execute();
                        listaServicio.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        JOptionPane.showMessageDialog(null,"Servicio eliminado con exito");
                        
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
            txtTipoServicio.setEditable(true);
            txtHoraServicio.setEditable(true);
            txtLugarServicio.setEditable(true);
            txtTelefonoContacto.setEditable(true);
            grpFechaServicio.setDisable(false);
            //cbEmpresa.setDisable(false);
            //cbEmpresa.setEditable(true);
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
         txtTipoServicio.setEditable(false);
            txtHoraServicio.setEditable(false);
            txtTelefonoContacto.setEditable(false);
            txtLugarServicio.setEditable(false);
            grpFechaServicio.setDisable(true); 
            //cbEmpresa.setEditable(false);
         miGuardar.setDisable(true);
         btnCancelar.setDisable(true);
         limpiarControles();
    }
    //Metodo para actualizar los datos del modelo y del tableview ejecutando el sp
    public void actualizar(){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServicios(?,?,?,?,?,?)}");
            Servicio servicioActualizado = ((Servicio)tblEmpleados.getSelectionModel().getSelectedItem());
            //obteniendo los datos actualizados de la tabla
            servicioActualizado.setFechaServicios((java.sql.Date)fecha.getSelectedDate());
            servicioActualizado.setTipoServicios(txtTipoServicio.getText());
            servicioActualizado.setHoraServicio(txtHoraServicio.getText());
            servicioActualizado.setLugarSevicio(txtLugarServicio.getText());
            servicioActualizado.setTelefonoContacto(txtTelefonoContacto.getText());
            //servicioActualizado.setEmpresas_codigoEmpresa(((Empresa)cbEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
            //enviando los datos actualizados al motor
            sp.setInt(1, servicioActualizado.getCodigoServicios());
            sp.setDate(2,new java.sql.Date(servicioActualizado.getFechaServicios().getTime()));
            sp.setString(3,servicioActualizado.getTipoServicios());
            sp.setString(4,servicioActualizado.getHoraServicio());
            sp.setString(5,servicioActualizado.getLugarSevicio());
            sp.setString(6,servicioActualizado.getTelefonoContacto());
            
            sp.execute();
            JOptionPane.showMessageDialog(null, "Datos actualizados");
            cargarDatos();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void imprimirReporte(){
       if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
       Map parametros = new HashMap();
       
       int codServicio = Integer.valueOf(txtCodigoServicio.getText());
       parametros.put("codServicio", codServicio);
       GenerarReporte.mostrarReporte("ReporteServicio.jasper","Reporte de Servicio",parametros);}
       else{
            JOptionPane.showMessageDialog(null, "Debe selecionar algun registro de la tabla");
       };
   }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(false);
        grpFechaServicio.add(fecha, 1,1);
        fecha.getStylesheets().add("/org/byrongarcia/resurse/DatePicker.css");
        cbEmpresa.setItems(getEmpresa());
    }    
    
    public void escenaServicios(){
        Stage.escenaServicios();
    }
    public MainApp getStage(){
        return Stage;
    }
    public void setStage(MainApp Stage) {
        this.Stage = Stage;
    }
    
    public void menuPrincipal(){
        Stage.menuPrincipal();
    }
    public void escenaEmpresas(){
        Stage.escenaEmpresas();
    }
}
