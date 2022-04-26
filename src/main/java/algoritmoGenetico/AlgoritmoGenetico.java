package algoritmoGenetico;

import estructuras.Contenedor;
import estructuras.Cromosoma;
import estructuras.Paquete;

import java.util.*;

public class AlgoritmoGenetico {
    public ArrayList<Contenedor> ejecutarAlgoritmoGenetico(ArrayList<Paquete> secPaquetes, ArrayList<Contenedor> secContenedores, int tamPoblacion,
                                                      int maxGeneraciones, int maxEstancamientos, float probCruzamiento, float probMutacion,
                                                      int numParaNuevaGeneracion, float alfa, int numEMSProbar, int numPaquetesProbar){
        List<Cromosoma> poblacion = crearPoblacionInicial(secPaquetes,secContenedores,tamPoblacion,alfa,numEMSProbar,numPaquetesProbar);
        Cromosoma mejorSolucionAnterior = poblacion.stream().min(Comparator.comparingDouble(Cromosoma::getFitness)).get();
        Cromosoma mejorSolucionActual;
        int numIteracion=0, numEstancamiento=0;
        boolean estancamiento;
        System.out.println("Desperdicio: "+mejorSolucionAnterior.getFitness()+" ----- Contenedores Usados: "+mejorSolucionAnterior.getSolucion().size());
        while (numIteracion<maxGeneraciones && numEstancamiento<maxEstancamientos){
            estancamiento=false;
            poblacion=generarNuevaGeneracion(poblacion,tamPoblacion,probCruzamiento,probMutacion,
                    numParaNuevaGeneracion, numEMSProbar,numPaquetesProbar);
            mejorSolucionActual=poblacion.stream().min(Comparator.comparingDouble(Cromosoma::getFitness)).get();
            if(Math.abs(mejorSolucionActual.getFitness()-mejorSolucionAnterior.getFitness())<0.00001){
                numEstancamiento++;
                estancamiento=true;
            }
            if(mejorSolucionActual.getFitness()<mejorSolucionAnterior.getFitness()){
                mejorSolucionAnterior=mejorSolucionActual;
                if(!estancamiento) numEstancamiento=0;
            }
            System.out.println("Desperdicio: "+mejorSolucionAnterior.getFitness()+" ----- Contenedores Usados: "+mejorSolucionAnterior.getSolucion().size());
        }
        //return mejorSolucionAnterior.decodificar();
        return mejorSolucionAnterior.getSolucion();
    }

    private List<Cromosoma> generarNuevaGeneracion(List<Cromosoma> poblacion, int tamPoblacion, float probCruzamiento,
                                                   float probMutacion, int numParaNuevaGeneracion, int numEMSProbar, int numPaquetesProbar) {
        ArrayList<Cromosoma> poblacionNueva= new ArrayList<Cromosoma>(List.copyOf(poblacion));
        int tamRestoPoblacion;
        Cromosoma padre1,padre2,hijo1,hijo2;
        Random rand = new Random();
        Collections.sort(poblacionNueva, Comparator.comparing(Cromosoma::getFitness));
        if((tamPoblacion-numParaNuevaGeneracion)%2==1){
            numParaNuevaGeneracion++;
        }
        //revisar indices de sublista
        ArrayList<Cromosoma> restoPoblacion= new ArrayList<>(poblacionNueva.subList(numParaNuevaGeneracion,poblacionNueva.size()));
        tamRestoPoblacion=tamPoblacion-numParaNuevaGeneracion;
        for(int i=0;i<tamRestoPoblacion/2;i++){
            padre1=restoPoblacion.remove(rand.nextInt(restoPoblacion.size()));
            padre2=restoPoblacion.remove(rand.nextInt(restoPoblacion.size()));
            if(rand.nextFloat()<=probCruzamiento){
                hijo1=padre1.cruzar(padre2);
                hijo2=padre2.cruzar(padre1);
                if(rand.nextFloat()<=probMutacion){
                    hijo1.mutar();
                }
                if(rand.nextFloat()<=probMutacion){
                    hijo2.mutar();
                }
                //implementar
                hijo1.calcularFitness(numEMSProbar, numPaquetesProbar);
                hijo2.calcularFitness(numEMSProbar, numPaquetesProbar);
                poblacionNueva.set(numParaNuevaGeneracion+i*2,hijo1);
                poblacionNueva.set(numParaNuevaGeneracion+i*2+1,hijo2);
            }
            else {
                poblacionNueva.set(numParaNuevaGeneracion+i*2,padre1);
                poblacionNueva.set(numParaNuevaGeneracion+i*2+1,padre2);
            }

        }
        return poblacionNueva;
    }

    private List<Cromosoma> crearPoblacionInicial(ArrayList<Paquete> secPaquetes, ArrayList<Contenedor> secContenedores,
                                                  int tamPoblacion, float alfa, int numContenedoresProbar, int numPaquetesProbar) {
        ArrayList<Cromosoma> Poblacion = new ArrayList<Cromosoma>();
        int tamPaquetes;
        ArrayList<Paquete> secPaquetesOrdenado = new ArrayList<Paquete>(List.copyOf(secPaquetes));
        Collections.sort(secPaquetesOrdenado, Comparator.comparing(Paquete::getVolumen)
            .thenComparing(Paquete::getLimiteApilacion)
            .thenComparing(Paquete::getLargo)
            .thenComparing(Paquete::getAncho).reversed());
        tamPaquetes = secPaquetesOrdenado.size();
        for (int i=0; i <tamPoblacion; i++){
            Cromosoma cromosoma = new Cromosoma(crearSecPaquetesAleatorio(secPaquetesOrdenado,alfa),
                    crearSecContenedoresAleatorio(secContenedores, tamPaquetes));
            cromosoma.calcularFitness(numContenedoresProbar,numPaquetesProbar);
            Poblacion.add(cromosoma);
        }
        return Poblacion;
    }

    private List<Contenedor> crearSecContenedoresAleatorio(List<Contenedor> secContenedores, int tamPaquetes) {
        ArrayList<Contenedor> SecContenedoresAleatorio = new ArrayList<>();
        Random rand = new Random();
        for(int i=0;i<tamPaquetes;i++){
            SecContenedoresAleatorio.add(secContenedores.get(rand.nextInt(secContenedores.size())));
        }
        return SecContenedoresAleatorio;
    }

    private List<Paquete> crearSecPaquetesAleatorio(List<Paquete> secPaquetesOrdenado, float alfa) {
        ArrayList<Paquete> SecPaquetesAleatorio = new ArrayList<>();
        ArrayList<Paquete> secPaquetesOrdenadoCopia = new ArrayList<Paquete>(List.copyOf(secPaquetesOrdenado));
        Random rand = new Random();
        while (secPaquetesOrdenadoCopia.size()!=0){
            List<Paquete> RCL = crearRCL(secPaquetesOrdenadoCopia,alfa);
            Paquete paqueteSeleccionado = RCL.get(rand.nextInt(RCL.size()));
            SecPaquetesAleatorio.add(paqueteSeleccionado);
            secPaquetesOrdenadoCopia.remove(paqueteSeleccionado);
        }
        return SecPaquetesAleatorio;
    }

    private List<Paquete> crearRCL(ArrayList<Paquete> secPaquetesOrdenadoCopia, float alfa) {
        ArrayList<Paquete> RCL = new ArrayList<>();
        float max = secPaquetesOrdenadoCopia.get(0).getVolumen();
        float min = secPaquetesOrdenadoCopia.get(secPaquetesOrdenadoCopia.size()-1).getVolumen();
        for (Paquete paquete: secPaquetesOrdenadoCopia) {
            if(paquete.getVolumen()>=min+alfa*(max-min)){
                RCL.add(paquete);
            }
        }
        return RCL;
    }
}
