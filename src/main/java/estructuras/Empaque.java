package estructuras;

public class Empaque {
    private float xAxis;
    private float yAxis;
    private float zAxis;
    private float xAxis2;
    private float yAxis2;
    private float zAxis2;
    private Paquete paquete;
    private float pesoTotalApilado;

    public Empaque(EMS ems, Paquete paquete, Rotacion rotacion) {
        xAxis=ems.getP1()[0];
        yAxis=ems.getP1()[1];
        zAxis=ems.getP1()[2];
        xAxis2=ems.getP2()[0];
        yAxis2=ems.getP2()[1];
        zAxis2=ems.getP2()[2];
        this.paquete= new Paquete(paquete.getId(),rotacion.getAncho(),rotacion.getLargo(), rotacion.getAlto(), paquete.getPeso(),paquete.getLimiteApilacion(),null);
        pesoTotalApilado=0;
    }

    public float getxAxis() {
        return xAxis;
    }

    public float getyAxis() {
        return yAxis;
    }

    public float getzAxis() {
        return zAxis;
    }

    public float getxAxis2() {
        return xAxis2;
    }

    public float getyAxis2() {
        return yAxis2;
    }

    public float getzAxis2() {
        return zAxis2;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public float getPesoTotalApilado() {
        return pesoTotalApilado;
    }

    public void setPesoTotalApilado(float pesoTotalApilado) {
        this.pesoTotalApilado = pesoTotalApilado;
    }
}
