package org.byrongarcia.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import static java.lang.Double.parseDouble;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.byrongarcia.bean.Empresa;
import org.byrongarcia.bean.Presupuesto;
import org.byrongarcia.db.Conexion;
import org.byrongarcia.report.GenerarReporte;
import org.byrongarcia.system.MainApp;

public class EscenaPresupuestoController implements Initializable {
    private MainApp stage;
    private ObservableList<Presupuesto>listaPresupuesto;
    private ObservableList<Empresa>listaEmpresa;
    private DatePicker fecha;
    @FXML private TextField txtCantidadPresupuesto;
    @FXML private ComboBox cbCodigoEmpresa;
    @FXML private TextField txtCodigoPresupuesto;
    @FXML private TableView tblPresupuesto;
    @FXML private TableColumn colCodigoPresupuesto;
    @FXML private TableColumn colFechaDeSolicitud;
    @FXML private GridPane grpFechaSolicitud;
    @FXML private TableColumn colCapital;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private Menu mAgregar;
    @FXML private MenuItem miNuevo;
    @FXML private MenuItem miAgregar;
    @FXML private Menu mEliminar;
    @FXML private Menu mReporte;
    @FXML private MenuItem miEliminar;
    @FXML private Menu mModificar;
    @FXML private MenuItem miActualizar;
    @FXML private MenuItem miGuardar;
    @FXML private Button btnCancelar;

    //Metodo para desactivar controles
    public void desactivarControles(){
    txtCodigoPresupuesto.setEditable(false);
    txtCantidadPresupuesto.setEditable(false);
    cbCodigoEmpresa.setEditable(false);
    cbCodigoEmpresa.setDisable(true);
    grpFechaSolicitud.setDisable(true);
    miAgregar.setDisable(true);
    }
    //Metodo para activar Controles
    public void activarControles(){
        mModificar.setDisable(true);
        mEliminar.setDisable(true);
        miAgregar.setDisable(false);
        btnCancelar.setDisable(false);
        limpiarControles();
        txtCantidadPresupuesto.setEditable(true);
        cbCodigoEmpresa.setDisable(false);
        grpFechaSolicitud.setDisable(false);
    }
    //Metodo para limpiar controles
    public void limpiarControles(){
        txtCodigoPresupuesto.setText("");
        txtCantidadPresupuesto.setText("");
        cbCodigoEmpresa.getSelectionModel().clearSelection();
    }
    public void cancelar(){
        mAgregar.setDisable(false);
        mEliminar.setDisable(false);
        mModificar.setDisable(false);
        desactivarControles();
        limpiarControles();
        btnCancelar.setDisable(true);
    }
    //Guardar los datos
    public void guardar(){
        Presupuesto presupuestoNuevo = new Presupuesto();
        presupuestoNuevo.setFechaSolicitud(fecha.getSelectedDate());
        presupuestoNuevo.setCantidadPresupuesto(parseDouble(txtCantidadPresupuesto.getText()));
        presupuestoNuevo.setEmpresas_codigoEmpresa(((Empresa)cbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        
        try{
        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPresupuesto(?,?,?)}");
        sp.setDate(1, new java.sql.Date(presupuestoNuevo.getFechaSolicitud().getTime()));
        sp.setDouble(2,presupuestoNuevo.getCantidadPresupuesto());
        sp.setInt(3,presupuestoNuevo.getEmpresas_codigoEmpresa());
        sp.execute();
        listaPresupuesto.add(presupuestoNuevo);
        }catch(Exception e){
        e.printStackTrace();
        }
    }
    //Metodo para el menu item nuevo
    public void nuevo(){
        if(txtCantidadPresupuesto.getText().equals("") || cbCodigoEmpresa.getSelectionModel().getSelectedItem() == null ||
                (java.sql.Date)fecha.getSelectedDate() == null ){
            JOptionPane.showMessageDialog(null, "Debe de llenar la todas las celdas indicadas");
            
        }else{
        mModificar.setDisable(false);
        mEliminar.setDisable(false);
        guardar();
        desactivarControles();
        limpiarControles();
        btnCancelar.setDisable(true);
        cargarDatos();
    }}
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
    public ObservableList<Presupuesto>getPresupuesto(){
        ArrayList<Presupuesto>lista = new ArrayList<Presupuesto>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPresupuestos()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new Presupuesto(
            resultado.getInt("codigoPresupuesto"),
            resultado.getDate("fechaSolicitud"),
            resultado.getDouble("cantidadPresupuesto"),
            resultado.getInt("Empresas_codigoEmpresa")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaPresupuesto = FXCollections.observableArrayList(lista);
    }
    public void cargarDatos(){
        tblPresupuesto.setItems(getPresupuesto());
        colCodigoPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer>("codigoPresupuesto"));
        colFechaDeSolicitud.setCellValueFactory(new PropertyValueFactory<Presupuesto,Date>("fechaSolicitud"));
        colCapital.setCellValueFactory(new PropertyValueFactory<Presupuesto, Double>("cantidadPresupuesto") );
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Presupuesto, Integer>("Empresas_codigoEmpresa") );
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
        if(tblPresupuesto.getSelectionModel().getSelectedItem() == null){}
        else{
        txtCodigoPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCodigoPresupuesto()));
        fecha.selectedDateProperty().set(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getFechaSolicitud());
        txtCantidadPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCantidadPresupuesto()));
        cbCodigoEmpresa.getSelectionModel().select(buscarEmpresa(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getEmpresas_codigoEmpresa()));
        };
    }
    //Metodo para eliminar empleado
    public void eliminar(){
        //Verificamos que tenga seleccionado un registro
        if(tblPresupuesto.getSelectionModel().getSelectedItem() !=null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPresupuesto(?)}");
                        sp.setInt(1,((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCodigoPresupuesto());
                        sp.execute();
                        listaPresupuesto.remove(tblPresupuesto.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        JOptionPane.showMessageDialog(null,"Presupuesto eliminado con exito");
                        
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
        if(tblPresupuesto.getSelectionModel().getSelectedItem() !=null){
            mAgregar.setDisable(true);
            mEliminar.setDisable(true);
            txtCantidadPresupuesto.setEditable(true);
            grpFechaSolicitud.setDisable(false);
            //cbCodigoEmpresa.setDisable(false);
            //cbCodigoEmpresa.setEditable(true);
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
            txtCantidadPresupuesto.setEditable(false);
            grpFechaSolicitud.setDisable(true); 
            cbCodigoEmpresa.setEditable(false);
         miGuardar.setDisable(true);
         btnCancelar.setDisable(true);
         limpiarControles();
    }
    //Metodo para actualizar los datos del modelo y del tableview ejecutando el sp
    public void actualizar(){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPresupuesto(?,?,?)}");
            Presupuesto presupuestoActualizado= ((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem());
            //obteniendo los datos actualizados de la tabla
            presupuestoActualizado.setFechaSolicitud((java.sql.Date)fecha.getSelectedDate());
            presupuestoActualizado.setCantidadPresupuesto(parseDouble(txtCantidadPresupuesto.getText()));
            //servicioActualizado.setEmpresas_codigoEmpresa(((Empresa)cbEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
            //enviando los datos actualizados al motor
            sp.setInt(1, presupuestoActualizado.getCodigoPresupuesto());
            sp.setDate(2,new java.sql.Date(presupuestoActualizado.getFechaSolicitud().getTime()));
            sp.setDouble(3,presupuestoActualizado.getCantidadPresupuesto());
            //sp.setInt(7,servicioActualizado.getEmpresas_codigoEmpresa());
            sp.execute();
            JOptionPane.showMessageDialog(null, "Datos actualizados");
            cargarDatos();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
   public void imprimirReporte(){
       if(tblPresupuesto.getSelectionModel().getSelectedItem() !=null){
       Map parametros = new HashMap();
       
       int codEmpresa = Integer.valueOf(((Empresa)cbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
       parametros.put("codEmpresa", codEmpresa);
       GenerarReporte.mostrarReporte("ReportePresupuesto.jasper","Reporte de Presupuesto",parametros);}
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
       grpFechaSolicitud.add(fecha,1,1);
       fecha.getStylesheets().add("/org/byrongarcia/resurse/DatePicker.css");
       cbCodigoEmpresa.setItems(getEmpresa());
    }    
    public void escenaPresupuesto(){
        stage.escenaPresupuesto();
    }
    
    public MainApp getStage() {
        return stage;
    }

    public void setStage(MainApp stage) {
        this.stage = stage;
    }

    public void escenaEmpresas(){
        stage.escenaEmpresas();
    }

}
