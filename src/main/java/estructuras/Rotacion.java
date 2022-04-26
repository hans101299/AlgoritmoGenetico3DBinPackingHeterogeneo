package estructuras;

public class Rotacion {
    private final float ancho;//x
    private final float largo;//y
    private final float alto;//z
    private final float peso;

    public Rotacion(float ancho, float largo, float alto, float peso) {
        this.ancho = ancho;
        this.largo = largo;
        this.alto = alto;
        this.peso = peso;
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
}
