package estructuras;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cromosoma {
    private List<Paquete> secPaquetesCromosoma;
    private List<Contenedor> secContenedorCromosoma;
    private ArrayList<Contenedor> solucion;
    private float fitness;

    public Cromosoma(List<Paquete> secPaquetesCromosoma, List<Contenedor> secContenedorCromosoma) {
        this.secPaquetesCromosoma = secPaquetesCromosoma;
        this.secContenedorCromosoma = secContenedorCromosoma;
    }

    public List<Paquete> getSecPaquetesCromosoma() {
        return secPaquetesCromosoma;
    }

    public List<Contenedor> getSecContenedorCromosoma() {
        return secContenedorCromosoma;
    }

    public float getFitness() {
        return fitness;
    }

    public ArrayList<Contenedor> getSolucion() {
        return solucion;
    }

    public void calcularFitness(int numEMSProbar, int numPaquetesProbar) {
        int k;
        Contenedor contenedorAbierto = null;
        ArrayList<EMS> ems;
        ArrayList<Contenedor> contenedoresAbiertos = new ArrayList<>();
        ArrayList<Paquete> secPaquetes = new ArrayList<Paquete>(List.copyOf(this.secPaquetesCromosoma));
        ArrayList<Contenedor> secContenedores = new ArrayList<Contenedor>(List.copyOf(this.secContenedorCromosoma));
        while (secPaquetes.size()!=0){
            ArrayList<Empaque> opciones = new ArrayList<Empaque>();
            boolean paqueteUbicado = false;
            for (Contenedor contenedor: contenedoresAbiertos) {
                ems = contenedor.getEms();
                for (int i = 0; i < ems.size() && !paqueteUbicado; i++) {
                    k=i+numEMSProbar;
                    while (i<k && i<ems.size()){
                        for (int j = 0; j < numPaquetesProbar && j<secPaquetes.size(); j++) {
                            for (Rotacion rotacion: secPaquetes.get(j).getRotaciones()) {
                                if (ems.get(i).posicionar(rotacion,contenedor.getSolucion(),contenedor.getLimitePeso(),contenedor.getPesoCargado())){
                                    opciones.add(new Empaque(ems.get(i),secPaquetes.get(j),rotacion));
                                }
                            }
                        }
                        i++;
                    }
                    if(opciones.size()!=0){
                        //Ordenar o tener una cola de prioridad para opciones
                        //Actualizar ems en la misma funcion
                        contenedor.posicionar(opciones.get(0));
                        //eliminar el paquete de la lista (agregar Id al paquete)
                        paqueteUbicado = true;
                        secPaquetes.removeIf(obj->obj.getId()==opciones.get(0).getPaquete().getId());
                    }
                }
                if (paqueteUbicado) break;
            }
            while(secContenedores.size()!=0 && !paqueteUbicado){
                try {
                    contenedorAbierto = secContenedores.remove(0).clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                ems = contenedorAbierto.getEms();
                for (int i = 0; i < numPaquetesProbar && i<secPaquetes.size(); i++) {
                    for (Rotacion rotacion: secPaquetes.get(i).getRotaciones()) {
                        if (ems.get(0).posicionar(rotacion, contenedorAbierto.getSolucion(), contenedorAbierto.getLimitePeso(), contenedorAbierto.getPesoCargado())){
                            opciones.add(new Empaque(ems.get(0),secPaquetes.get(i),rotacion));
                        }
                    }
                }
                if(opciones.size()!=0){
                    //Ordenar o tener una cola de prioridad para opciones
                    //Actualizar ems en la misma funcion
                    contenedorAbierto.posicionar(opciones.get(0));
                    //eliminar el paquete de la lista (agregar Id al paquete)
                    paqueteUbicado = true;
                    secPaquetes.removeIf(obj->obj.getId()==opciones.get(0).getPaquete().getId());
                    contenedoresAbiertos.add(contenedorAbierto);
                }
            }
            if (!paqueteUbicado){
                fitness = 9999999;
                return;
            }
        }
        float sumaVolumenPaquetes=0;
        float sumaVolumenContenedores=0;
        for (Paquete paquete:this.secPaquetesCromosoma) {
            sumaVolumenPaquetes+=paquete.getVolumen();
        }
        for (Contenedor contenedor: contenedoresAbiertos) {
            sumaVolumenContenedores+=contenedor.getVolumen();
        }
        fitness=sumaVolumenContenedores-sumaVolumenPaquetes;
        solucion=contenedoresAbiertos;
    }

    public Cromosoma cruzar(Cromosoma padre2) {
        Random rand = new Random();
        int punto1, punto2, indicepadre2 = 0;
        float prob;
        ArrayList<Paquete> secPaquetesNuevo = new ArrayList<>();
        ArrayList<Contenedor> secContenedoresNuevo = new ArrayList<>();
        Cromosoma nuevoCromosoma;
        //Para los paquetes
        punto1=rand.nextInt(this.secPaquetesCromosoma.size());
        punto2=rand.nextInt(this.secPaquetesCromosoma.size());

        if(punto2<punto1){
            int aux=punto1;
            punto1=punto2;
            punto2=aux;
        }

        while(secPaquetesNuevo.size() < this.secPaquetesCromosoma.size()) secPaquetesNuevo.add(null);
        for(int i=punto1;i<=punto2;i++){
            secPaquetesNuevo.set(i,this.secPaquetesCromosoma.get(i));
        }
        for(int i=punto2+1;i<secPaquetesNuevo.size();i++){
            while(secPaquetesNuevo.contains(padre2.getSecPaquetesCromosoma().get(indicepadre2))){
                indicepadre2++;
            }
            secPaquetesNuevo.set(i,padre2.getSecPaquetesCromosoma().get(indicepadre2));
        }
        for(int i=0;i<punto1;i++){
            while(secPaquetesNuevo.contains(padre2.getSecPaquetesCromosoma().get(indicepadre2))){
                indicepadre2++;
            }
            secPaquetesNuevo.set(i,padre2.getSecPaquetesCromosoma().get(indicepadre2));
        }

        int punto = rand.nextInt(this.secContenedorCromosoma.size());
        for (int i =0 ;i<this.secContenedorCromosoma.size();i++){
            if(i<punto) {
                secContenedoresNuevo.add(this.secContenedorCromosoma.get(i));
            }
            else {
                secContenedoresNuevo.add(padre2.secContenedorCromosoma.get(i));
            }
        }

        nuevoCromosoma=new Cromosoma(secPaquetesNuevo,secContenedoresNuevo);
        return nuevoCromosoma;
    }

    public void mutar() {
        int punto1, punto2;
        Random rand = new Random();
        Paquete aux;
        punto1=rand.nextInt(this.secPaquetesCromosoma.size());
        punto2=rand.nextInt(this.secPaquetesCromosoma.size());

        aux=this.secPaquetesCromosoma.get(punto1);
        this.secPaquetesCromosoma.set(punto1,this.secPaquetesCromosoma.get(punto2));
        this.secPaquetesCromosoma.set(punto2,aux);
    }
}
