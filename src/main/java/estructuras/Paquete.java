package estructuras;

import java.util.List;

public class Paquete {
    private String id;
    private final float ancho;
    private final float largo;
    private final float alto;
    private final float peso;
    private final float limiteApilacion;
    private final List<Rotacion> rotaciones;

    public Paquete(String id, float ancho, float largo, float alto, float peso, float limiteApilacion, List<Rotacion> rotaciones) {
        this.id = id;
        this.ancho = ancho;
        this.largo = largo;
        this.alto = alto;
        this.peso = peso;
        this.limiteApilacion = limiteApilacion;
        this.rotaciones = rotaciones;
    }

    public float getAncho() {
        return ancho;
    }

    public float getLargo() {
        return largo;
    }

    public float getAlto() {
        return alto;
    }

    public float getPeso() {
        return peso;
    }

    public float getLimiteApilacion() {
        return limiteApilacion;
    }

    public List<Rotacion> getRotaciones() {
        return rotaciones;
    }

    public float getVolumen() {
        return ancho*largo*alto;
    }

    public String getId() {
        return id;
    }
}
