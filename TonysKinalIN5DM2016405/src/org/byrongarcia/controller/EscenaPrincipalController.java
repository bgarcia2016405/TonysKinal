package org.byrongarcia.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.byrongarcia.system.MainApp;

public class EscenaPrincipalController implements Initializable {
    private MainApp stage;
    @FXML private Menu mEmpresas;
    @FXML private MenuItem miEmpresas;
    @FXML private MenuItem miTipoEmpleados;
    @FXML private MenuItem miServicios;
    @FXML private MenuItem miPlatos;
    @FXML private MenuItem miEmpleados;
    @FXML private MenuItem miProductos;
    @FXML private Menu mAcercaDe;
    @FXML private MenuItem miFuncionamiento;
    @FXML private MenuItem miPresupuesto;
    @FXML private MenuItem miTipoPlato;
    @FXML private MenuItem miProductosHasPlatos;
    @FXML private MenuItem miServiciosHasEmpleados;
    @FXML private MenuItem miServiciosHasPlatos;

    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
     
    
    }
    public void escenaServiciosHasPlatos(){
        stage.escenaServiciosHasPlatos();
    }
    public void escenaProductosHasPlatos(){
        stage.escenaProductosHasPlatos();
    }
    public void escenaServiciosHasEmpleados(){
        stage.escenaServiciosHasEmpleados();
    }
    
    public void escenaProductos(){
        stage.escenaProductos();
    }
    
    public void escenaEmpresas(){
        stage.escenaEmpresas();
    }
    public void escenaTipoEmpleados(){
        stage.escenaTipoEmpleados();
    }
    public void escenaTipoPlatos(){
        stage.escenaTipoPlatos();
    }
    
    
    public MainApp getStage() {
        return stage;
    }
    
    public void setStage(MainApp stage) {
        this.stage = stage;
    }
    
    
}
