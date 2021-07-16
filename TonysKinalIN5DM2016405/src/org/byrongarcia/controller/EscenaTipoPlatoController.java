package org.byrongarcia.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableListValue;
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
import org.byrongarcia.bean.TipoPlato;
import org.byrongarcia.db.Conexion;
import org.byrongarcia.system.MainApp;

public class EscenaTipoPlatoController implements Initializable {
    private MainApp stage;
    private ObservableList<TipoPlato>listaTipoPlato;
    @FXML private Menu mAgregar;
    @FXML private MenuItem miNuevo;
    @FXML private MenuItem miAgregar;
    @FXML private Menu mEliminar;
    @FXML private Menu mModificar;
    @FXML private MenuItem miActualizar;
    @FXML private MenuItem miGuardar;
    @FXML private TextField txtCodigoTipoPlato;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblTipoPlato;
    @FXML private TableColumn colCodigoTipoPlato;
    @FXML private TableColumn colDescripccion;   
    @FXML private Button btnCancelar;
    
    public void desactivarControles(){
        txtCodigoTipoPlato.setEditable(false);
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
    
    public void limpiarControles(){
        txtCodigoTipoPlato.setText("");
        txtDescripcion.setText("");
    }
    
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
    
    public void guardar(){
        TipoPlato tipoPlatoNuevo = new TipoPlato();
        tipoPlatoNuevo.setDescriccion(txtDescripcion.getText());
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoPlato(?)}");
            sp.setString(1,tipoPlatoNuevo.getDescriccion());
            sp.execute();
            listaTipoPlato.add(tipoPlatoNuevo); 
        }catch(Exception e){
        e.printStackTrace();
        }
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
        return listaTipoPlato = FXCollections.observableArrayList(lista);
        }
    public void cargarDatos(){
        tblTipoPlato.setItems(getTipoPlato());
        colCodigoTipoPlato.setCellValueFactory(new PropertyValueFactory<TipoPlato, Integer>("codigoTipoPlato") );
        colDescripccion.setCellValueFactory(new PropertyValueFactory<TipoPlato, String>("descriccion") );
        desactivarControles();
    }
     public void seleccionarElementos(){
         if(tblTipoPlato.getSelectionModel().getSelectedItem() == null)
         {}else{
        txtCodigoTipoPlato.setText(String.valueOf(((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
        txtDescripcion.setText(((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getDescriccion());
         };
    }
     public void eliminar(){
        //Verificamos que tenga seleccionado un registro
        if(tblTipoPlato.getSelectionModel().getSelectedItem() !=null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Tipo Plato", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_NO_OPTION){
                    try{
                        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoPlato(?)}");
                        sp.setInt(1,((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
                        sp.execute();
                        listaTipoPlato.remove(tblTipoPlato.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        JOptionPane.showMessageDialog(null,"Tipo de plato eliminada con exito");
                        
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
        if(tblTipoPlato.getSelectionModel().getSelectedItem() !=null){
            mAgregar.setDisable(true);
            mEliminar.setDisable(true);
            txtDescripcion.setEditable(true);
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
         txtDescripcion.setEditable(false);
         miGuardar.setDisable(true);
         btnCancelar.setDisable(true);
         limpiarControles();
    }
    //Metodo para actualizar los datos del modelo y del tableview ejecutando el sp
    public void actualizar(){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizrTipoPlato(?,?)}");
            TipoPlato platoActualizado = ((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem());
            //obteniendo los datos actualizados de la tabla
            platoActualizado.setDescriccion(txtDescripcion.getText());
            //enviando los datos actualizados al motor
            sp.setInt(1, platoActualizado.getCodigoTipoPlato());
            sp.setString(2,platoActualizado.getDescriccion());
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
    
    public void escenaTipoPlato(){
        stage.escenaTipoPlatos();
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
    public void escenaPlato(){
        System.out.println("Error");
        stage.escenaPlato();
    }
}
