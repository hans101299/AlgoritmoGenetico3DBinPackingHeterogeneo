package estructuras;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Arrays;

public class ContenedorTest {

    private static Contenedor cont = new Contenedor(6,20,10,100000);

    @Test
    public void crearContenedorTest() {
        Assert.assertEquals(1,cont.getEms().size());
        Assert.assertEquals(0, cont.getSolucion().size());
    }

    @Test
    public void posicionarTest() {
        Paquete paquete = new Paquete("P1",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2))));
        Empaque emp = new Empaque(cont.getEms().get(0),paquete,paquete.getRotaciones().get(0));
        cont.posicionar(emp);
        Assert.assertNotEquals(1,cont.getEms().size());
        Assert.assertEquals(cont.getPesoCargado(),paquete.getPeso(),0.0000001f);
        Assert.assertEquals(cont.getSolucion().size(), 1);
    }

    @Test
    public void inscribedOverlappedEMSTest() {
        EMS ems1 = new EMS(new float[]{0,0,0},new float[]{5,5,5});
        EMS ems2 = new EMS(new float[]{5,0,0},new float[]{10,10,10});
        EMS ems3 = new EMS(new float[]{0,0,0},new float[]{3,3,3});
        EMS ems4 = new EMS(new float[]{0,0,0},new float[]{7,2,3});
        Assert.assertTrue(!cont.inscribed(ems1,ems2));
        Assert.assertTrue(cont.inscribed(ems3,ems1));
        Assert.assertTrue(!cont.inscribed(ems4,ems1));
        Assert.assertTrue(cont.overlapped(ems4,ems1));
        Assert.assertTrue(!cont.overlapped(ems1,ems2));
    }
}
