package estructuras;

import algoritmoGenetico.AlgoritmoGenetico;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class EMSTest {

    private static Contenedor cont = new Contenedor(6,20,10,100000);

    @Test
    void posicionarTest() {
        Paquete paquete = new Paquete("P1",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2))));
        Paquete paquete2 = new Paquete("P2",1,1,1,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(1,1,1,2))));
        Empaque emp = new Empaque(cont.getEms().get(0),paquete,paquete.getRotaciones().get(0));
        cont.posicionar(emp);
        Assert.assertTrue(cont.getEms().get(0).posicionar(paquete2.getRotaciones().get(0),cont.getSolucion(),cont.getLimitePeso(),cont.getPesoCargado()));
        Assert.assertTrue(cont.getEms().get(1).posicionar(paquete2.getRotaciones().get(0),cont.getSolucion(),cont.getLimitePeso(),cont.getPesoCargado()));
    }
}
