package org.byrongarcia.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.byrongarcia.controller.EscenaEmpleadosController;
import org.byrongarcia.controller.EscenaPrincipalController;
import org.byrongarcia.controller.EscenaEmpresasController;
import org.byrongarcia.controller.EscenaPlatosController;
import org.byrongarcia.controller.EscenaPresupuestoController;
import org.byrongarcia.controller.EscenaProductosController;
import org.byrongarcia.controller.EscenaProductosHasPlatosController;
import org.byrongarcia.controller.EscenaServiciosController;
import org.byrongarcia.controller.EscenaServiciosHasEmpleadosController;
import org.byrongarcia.controller.EscenaServiciosHasPlatosController;
import org.byrongarcia.controller.EscenaTipoEmpleadosController;
import org.byrongarcia.controller.EscenaTipoPlatoController;
public class MainApp extends Application {
    private final String PAQUETE_VISTA = "/org/byrongarcia/vistas/";
    private Stage stage;
    private Scene scene;
    
    @Override
    public void start(Stage stage) /*throws Exception*/ {
        /*Parent root = FXMLLoader.load(getClass().getResource("EscenaPrincipalView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
        this.stage=stage;
        this.stage.setTitle("Proyecto TonysKinal 2020");
        menuPrincipal();
        stage.show();
    }
    public void menuPrincipal() {
        try{
        EscenaPrincipalController escenaPrincipal = (EscenaPrincipalController) cambiarEscena("EscenaPrincipalView.fxml");
        escenaPrincipal.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    public void escenaEmpresas() {
        try{
        EscenaEmpresasController escenaPrincipal = (EscenaEmpresasController) cambiarEscena("EscenaEmpresasView.fxml");
        escenaPrincipal.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    public void escenaTipoEmpleados(){
        try{
        EscenaTipoEmpleadosController escenaPrincipal = (EscenaTipoEmpleadosController) cambiarEscena("EscenaTipoEmpleadosView.fxml");
        escenaPrincipal.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void escenaEmpleados(){
       try{
        EscenaEmpleadosController escenaPrincipal = (EscenaEmpleadosController) cambiarEscena("EscenaEmpleadosView.fxml");
        escenaPrincipal.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   public void escenaPresupuesto(){
       try{
        EscenaPresupuestoController escenaPrincipal = (EscenaPresupuestoController) cambiarEscena("EscenaPresupuestoView.fxml");
        escenaPrincipal.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   public void escenaServicios(){
       try{
        EscenaServiciosController escenaPrincipal = (EscenaServiciosController) cambiarEscena("EscenaServiciosView.fxml");
        escenaPrincipal.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   public void escenaTipoPlatos(){
       try{
           EscenaTipoPlatoController escenaPrincipal = (EscenaTipoPlatoController) cambiarEscena("EscenaTipoPlatoView.fxml");
           escenaPrincipal.setStage(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   public void escenaPlato(){
       try{
                   
           EscenaPlatosController escenaPrincipal = (EscenaPlatosController) cambiarEscena("EscenaPlatosView.fxml");
                            
           escenaPrincipal.setStage(this);
       }
           catch(Exception e){
                   e.printStackTrace();
                   }
   }
   public void escenaProductos(){
       try{
           EscenaProductosController escenaPrincipal = (EscenaProductosController) cambiarEscena("EscenaProductosView.fxml");
           escenaPrincipal.setStage(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   public void escenaProductosHasPlatos(){
       try{
       EscenaProductosHasPlatosController escenaPrincipal = (EscenaProductosHasPlatosController) cambiarEscena("EscenaProductos_has_PlatosView.fxml");
       escenaPrincipal.setStage(this);
       }
       catch(Exception e){
           e.printStackTrace();
       }
   }
   public void escenaServiciosHasEmpleados(){
       try{
        EscenaServiciosHasEmpleadosController escenaPrincipal = (EscenaServiciosHasEmpleadosController) cambiarEscena("EscenaServicio_has_EmpleadosView.fxml");
        escenaPrincipal.setStage(this);
       }
       catch(Exception e){
           e.printStackTrace();
       }
   }
   public void escenaServiciosHasPlatos(){
       try{
       EscenaServiciosHasPlatosController escenaPrincipal = (EscenaServiciosHasPlatosController) cambiarEscena("EscenaServicio_has_PlatosView.fxml");
       escenaPrincipal.setStage(this);
       }
       catch(Exception e){e.printStackTrace();}
   }
    public static void main(String[] args) {
        launch(args);
    }
    public Initializable cambiarEscena(String fxml) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML=new FXMLLoader();
        InputStream archivo=MainApp.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(MainApp.class.getResource(PAQUETE_VISTA+fxml));
        scene=new Scene((AnchorPane)cargadorFXML.load(archivo));
        stage.setScene(scene);
        stage.sizeToScene();
        resultado=(Initializable) cargadorFXML.getController();
        return resultado;
    }

    
}
