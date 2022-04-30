package estructuras;

import algoritmoGenetico.AlgoritmoGenetico;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CromosomaTest {

    @Test
    void Cromosoma() {
        AlgoritmoGenetico alg = new AlgoritmoGenetico();
        ArrayList<Paquete> paquetes = new ArrayList<>();
        ArrayList<Contenedor> contenedores = new ArrayList<>();
        paquetes.add(new Paquete("P1",4,2,2,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P2",5,2,2,2,4,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(5,2,2,2)))));
        paquetes.add(new Paquete("P3",3,5,1,2,10,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(3,5,1,2)))));
        paquetes.add(new Paquete("P4",5,5,5,2,2,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(5,5,5,2)))));
        paquetes.add(new Paquete("P5",6,7,3,2,10,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(6,7,3,2)))));
        paquetes.add(new Paquete("P6",4,2,1,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P7",4,2,1,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P8",4,2,1,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));

        contenedores.add(new Contenedor(6,20,10,100000));
        contenedores.add(new Contenedor(6,10,5,50000));

        Cromosoma crom = new Cromosoma(alg.crearSecPaquetesAleatorio(paquetes,0.8f),alg.crearSecContenedoresAleatorio(contenedores,paquetes.size()));
        Cromosoma crom2 = new Cromosoma(alg.crearSecPaquetesAleatorio(paquetes,0.8f),alg.crearSecContenedoresAleatorio(contenedores,paquetes.size()));
        Assert.assertNull(crom.getSolucion());
        crom.calcularFitness(1,2);
        float fitnessAct = crom.getFitness();
        Assert.assertNotNull(crom.getSolucion());
        crom = crom.cruzar(crom2);
        Assert.assertNotEquals(fitnessAct,crom.getFitness(),0.0000001f);
        crom.mutar();
        Assert.assertNotEquals(fitnessAct,crom.getFitness(),0.0000001f);
    }
}
