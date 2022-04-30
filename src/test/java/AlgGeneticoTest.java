import algoritmoGenetico.AlgoritmoGenetico;
import estructuras.Contenedor;
import estructuras.Cromosoma;
import estructuras.Paquete;
import estructuras.Rotacion;
import org.junit.Test;
import org.junit.Assert;

import java.util.*;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AlgGeneticoTest {
    @Test
    public void testAlgGenetico() throws Exception{
        ArrayList<Paquete> paquetes = new ArrayList<>();
        ArrayList<Contenedor> contenedores = new ArrayList<>();
        ArrayList<Contenedor> solucion;
        paquetes.add(new Paquete("P1",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P2",5,2,2,2,4,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(5,2,2,2)))));
        paquetes.add(new Paquete("P3",3,5,1,2,10,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(3,5,1,2)))));
        paquetes.add(new Paquete("P4",5,5,5,2,2,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(5,5,5,2)))));
        paquetes.add(new Paquete("P5",6,7,3,2,10,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(6,7,3,2)))));
        paquetes.add(new Paquete("P6",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P7",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P8",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P9",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P10",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P12",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P13",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P14",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P15",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));
        paquetes.add(new Paquete("P16",4,2,7,2,1000,new ArrayList<Rotacion>(Arrays.asList(new Rotacion(4,2,7,2)))));

        contenedores.add(new Contenedor(6,20,10,100000));
        contenedores.add(new Contenedor(6,10,5,50000));

        AlgoritmoGenetico alg = new AlgoritmoGenetico();
        List<Cromosoma> pob = alg.crearPoblacionInicial(paquetes,contenedores,15,0.8f,2,3);
        Assert.assertEquals(pob.size(), 15);
        for (Cromosoma crom:pob) {
            Assert.assertEquals(crom.getSecPaquetesCromosoma().size(),paquetes.size());
            Assert.assertEquals(crom.getSecContenedorCromosoma().size(),paquetes.size());
        }
        pob=alg.generarNuevaGeneracion(pob,15,0.9f,0.8f,
                5, 2,1);
        Assert.assertEquals(pob.size(), 15);
        for (Cromosoma crom:pob) {
            Assert.assertEquals(crom.getSecPaquetesCromosoma().size(),paquetes.size());
            Assert.assertEquals(crom.getSecContenedorCromosoma().size(),paquetes.size());
        }
        List<Contenedor> secContAleatoria = alg.crearSecContenedoresAleatorio(contenedores, paquetes.size());
        Assert.assertEquals(secContAleatoria.size(),paquetes.size());
        for (Contenedor cont: secContAleatoria) {
            Assert.assertThat(contenedores,hasItem(cont));
        }
        Collections.sort(paquetes, Comparator.comparing(Paquete::getVolumen)
                .thenComparing(Paquete::getLimiteApilacion)
                .thenComparing(Paquete::getLargo)
                .thenComparing(Paquete::getAncho).reversed());
        List<Paquete> secPaquetes = alg.crearSecPaquetesAleatorio(paquetes, 0.8f);
        Assert.assertEquals(secPaquetes.size(),paquetes.size());
        ArrayList<String> ids = new ArrayList<>();
        for (Paquete paquete: secPaquetes) {
            Assert.assertThat(ids,not(hasItem(paquete.getId())));
            ids.add(paquete.getId());
        }

        List<Paquete> rcl = alg.crearRCL(paquetes, 0.8f);
        Assert.assertTrue(rcl.size()<paquetes.size());
        ids = new ArrayList<>();
        for (Paquete paquete: rcl) {
            Assert.assertThat(ids,not(hasItem(paquete.getId())));
            ids.add(paquete.getId());
        }
    }
}
