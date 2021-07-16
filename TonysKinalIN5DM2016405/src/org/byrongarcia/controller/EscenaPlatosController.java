package org.byrongarcia.controller;

import static java.lang.Double.parseDouble;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.byrongarcia.bean.Plato;
import org.byrongarcia.bean.TipoPlato;
import org.byrongarcia.db.Conexion;
import org.byrongarcia.system.MainApp;

public class EscenaPlatosController implements Initializable {
    private MainApp stage;
    private ObservableList<Plato>listaPlatos;
    private ObservableList<TipoPlato>listaTipoPlatos;
    @FXML    private TextField txtPrecio;
    @FXML    private TextField txtNombrePlato;
    @FXML    private TextField txtCantidad;
    @FXML    private TextField txtCodigoPlato;
    @FXML    private Button btnCancelar;
    @FXML    private ComboBox cbTipoPlato;
    @FXML    private TextField txtDescripcionPlato;
    @FXML    private Menu mAgregar;
    @FXML    private MenuItem miAgregar;
    @FXML    private MenuItem miNuevo;
    @FXML    private Menu mEliminar;
    @FXML    private MenuItem miEliminar;
    @FXML    private Menu mModificar;
    @FXML    private MenuItem miActualizar;
    @FXML    private MenuItem miGuardar;
    @FXML    private TableView tblPlatos;
    @FXML    private TableColumn colCodigoPlatos;
    @FXML    private TableColumn colCantidad;
    @FXML    private TableColumn colPlatillo;
    @FXML    private TableColumn colDescripción;
    @FXML    private TableColumn colPrecio;
    @FXML    private TableColumn colTipoPlatp;
    public void desactivarControles(){
        txtCodigoPlato.setEditable(false);
        txtCantidad.setEditable(false);
        txtNombrePlato.setEditable(false);
        txtPrecio.setEditable(false);
        txtDescripcionPlato.setEditable(false);
        cbTipoPlato.setEditable(false);
        cbTipoPlato.setDisable(true);
        miAgregar.setDisable(true);
    }
    public void activarControles(){
        mModificar.setDisable(true);
        mEliminar.setDisable(true);
        miAgregar.setDisable(false);
        btnCancelar.setDisable(false);
        limpiarControles();
        txtCantidad.setEditable(true);
        txtNombrePlato.setEditable(true);
        txtPrecio.setEditable(true);
        txtDescripcionPlato.setEditable(true);
        cbTipoPlato.setDisable(false);
    }
    //Metodo para limpiar los controles
    public void limpiarControles(){
        txtCodigoPlato.setText("");
        txtCantidad.setText("");
        txtNombrePlato.setText("");
        txtPrecio.setText("");
        txtDescripcionPlato.setText("");        
        cbTipoPlato.getSelectionModel().clearSelection();
        
   }
    //Metodo para el menu item nuevo
    public void nuevo(){
         if(txtCantidad.getText().equals("") || txtNombrePlato.getText().equals("") || txtPrecio.getText().equals("")
                 || txtDescripcionPlato.getText().equals("") || cbTipoPlato.getSelectionModel().getSelectedItem() == null){
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
    public void cancelar(){
    mAgregar.setDisable(false);
    mEliminar.setDisable(false);
    mModificar.setDisable(false);
    desactivarControles();
    limpiarControles();
    btnCancelar.setDisable(true);
    }
    public void guardar(){
        Plato platoNuevo = new Plato();
        platoNuevo.setCantidad(parseInt(txtCantidad.getText()));
        platoNuevo.setNombrePlato(txtNombrePlato.getText());
        platoNuevo.setDescripcionPlato(txtDescripcionPlato.getText());
        platoNuevo.setPrecioPlato(parseDouble(txtPrecio.getText()));
        platoNuevo.setTipoPlato_codigoTipoPlato(((TipoPlato)cbTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
        try{
            
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPlatos(?,?,?,?,?)}");
            sp.setInt(1,platoNuevo.getCantidad());
            sp.setString(2,platoNuevo.getNombrePlato());
            sp.setString(3,platoNuevo.getDescripcionPlato());
            sp.setDouble(4,platoNuevo.getPrecioPlato());
            sp.setInt(5,platoNuevo.getTipoPlato_codigoTipoPlato());
            sp.execute();
            listaPlatos.add(platoNuevo); 
        }catch(Exception e){
        e.printStackTrace();
        System.out.println("Hola Error");
        }
    }
    public ObservableList<Plato>getPlato(){
        ArrayList<Plato>lista = new ArrayList<Plato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlatos()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new Plato(
            resultado.getInt("codigoPlato"),
            resultado.getInt("cantidad"),
            resultado.getString("nombrePlato"),
            resultado.getString("descripcionPlato"),
            resultado.getDouble("precioPlato"),
            resultado.getInt("TipoPlato_codigoTipoPlato")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaPlatos = FXCollections.observableArrayList(lista);
    }
   public ObservableList<TipoPlato>getTipoPlato(){
        ArrayList<TipoPlato>lista = new ArrayList<TipoPlato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoPlato()}");
            ResultSet resultado= procedimiento.executeQuery();
            while (resultado.next()){
            lista.add(new TipoPlato(resultado.getInt("codigoTipoPlato"),
                                    resultado.getString("descriccion")));
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
        return listaTipoPlatos = FXCollections.observableArrayList(lista);
        }
   public void cargarDatos(){
        tblPlatos.setItems(getPlato());
        colCodigoPlatos.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("codigoPlato") );
        colCantidad.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("cantidad") );
        colPlatillo.setCellValueFactory(new PropertyValueFactory<Plato, String>("nombrePlato") );
        colDescripción.setCellValueFactory(new PropertyValueFactory<Plato, String>("descripcionPlato"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Plato, Double>("precioPlato") );
        colTipoPlatp.setCellValueFactory(new PropertyValueFactory<Plato, Integer>("TipoPlato_codigoTipoPlato") );
        
        desactivarControles();
    }
   public TipoPlato buscarTipoPlato(int codigoTipoPlato){
        TipoPlato resultado = null;
        try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoPlato(?)}");
          procedimiento.setInt(1, codigoTipoPlato);
          ResultSet registro = procedimiento.executeQuery();
          while(registro.next()){
              resultado = new TipoPlato(registro.getInt("codigoTipoPlato"),registro.getString("descriccion"));
          }
        }catch(Exception e){
          e.printStackTrace();
            
        }
        return resultado;
    }
    //metodod para seleccionar elementos de al tabla mostrarlos en los controles
    public void seleccionarElementos(){
        if(tblPlatos.getSelectionModel().getSelectedItem() == null){}
        else{
        txtCodigoPlato.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        txtCantidad.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCantidad()));
        txtNombrePlato.setText(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getNombrePlato());
        txtDescripcionPlato.setText(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getDescripcionPlato());
        txtPrecio.setText(String.valueOf(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getPrecioPlato()));
        cbTipoPlato.getSelectionModel().select(buscarTipoPlato(((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getTipoPlato_codigoTipoPlato()));
        };
    }
     //Metodo para eliminar empleado
    public void eliminar(){
        //Verificamos que tenga seleccionado un registro
        if(tblPlatos.getSelectionModel().getSelectedItem() !=null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Plato", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPlatos(?)}");
                        sp.setInt(1,((Plato)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato());
                        sp.execute();
                        listaPlatos.remove(tblPlatos.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        JOptionPane.showMessageDialog(null,"Plato eliminado con exito");
                        
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
        if(tblPlatos.getSelectionModel().getSelectedItem() !=null){
            mAgregar.setDisable(true);
            mEliminar.setDisable(true);
            txtCantidad.setEditable(true);
            txtNombrePlato.setEditable(true);
            txtDescripcionPlato.setEditable(true);
            txtPrecio.setEditable(true);
            //cbTipoPlato.setDisable(false);
            //cbTipoPlato.setEditable(true);
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
         txtCantidad.setEditable(false);
            txtNombrePlato.setEditable(false);
            txtDescripcionPlato.setEditable(false);
            txtPrecio.setEditable(false);
            //cbTipoEmpleado.setEditable(false);
         miGuardar.setDisable(true);
         btnCancelar.setDisable(true);
         limpiarControles();
    }
    //Metodo para actualizar los datos del modelo y del tableview ejecutando el sp
    public void actualizar(){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPlatos(?,?,?,?,?)}");
            Plato platoActualizado = ((Plato)tblPlatos.getSelectionModel().getSelectedItem());
            //obteniendo los datos actualizados de la tabla
            platoActualizado.setCantidad(parseInt(txtCantidad.getText()));
            platoActualizado.setNombrePlato(txtNombrePlato.getText());
            platoActualizado.setDescripcionPlato(txtDescripcionPlato.getText());
            platoActualizado.setPrecioPlato(parseDouble(txtPrecio.getText()));
            //enviando los datos actualizados al motor
            sp.setInt(1, platoActualizado.getCodigoPlato());
            sp.setInt(2,platoActualizado.getCantidad());
            sp.setString(3,platoActualizado.getNombrePlato());
            sp.setString(4,platoActualizado.getDescripcionPlato());
            sp.setDouble(5,platoActualizado.getPrecioPlato());
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
        cbTipoPlato.setItems(getTipoPlato());
    }
    public void escenaPlato(){
        stage.escenaPlato();
    }
    public MainApp getStage() {
        return stage;
    }

    public void setStage(MainApp stage) {
        this.stage = stage;
    }
    public void escenaTipoPlatos(){
        stage.escenaTipoPlatos();
    }
    
    
}
