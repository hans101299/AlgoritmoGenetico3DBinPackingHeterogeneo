import algoritmoGenetico.AlgoritmoGenetico;
import estructuras.Contenedor;
import estructuras.Paquete;
import estructuras.Rotacion;

import java.util.ArrayList;
import java.util.Arrays;

import IO.EntradaDatos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App{
    public static void main(String[] args) {
        ArrayList<Paquete> paquetes = null;
        ArrayList<Contenedor> contenedores = null;
        try {
            paquetes = EntradaDatos.leerDatosPaquetes();
            contenedores = EntradaDatos.leerDatosContenedores();
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<Contenedor> solucion;
        AlgoritmoGenetico alg = new AlgoritmoGenetico();
        solucion=alg.ejecutarAlgoritmoGenetico(paquetes,contenedores,50,100,50,0.85f,
                0.7f,2,0.7f,1,2);
    }
}
