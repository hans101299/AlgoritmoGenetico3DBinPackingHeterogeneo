package estructuras;

import java.util.ArrayList;

public class EMS {
    private float p1[] = new float[3];
    private float p2[] = new float[3];

    public EMS(float[] p1, float[] p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public float[] getP1() {
        return p1;
    }

    public float[] getP2() {
        return p2;
    }

    public boolean posicionar(Rotacion rotacion, ArrayList<Empaque> solucion, float limitePeso, float pesoCargado) {
        float r1p1[] = {p1[0],p1[1]};
        float r1p2[] = {p1[0]+rotacion.getAncho(),p1[1]+rotacion.getLargo()};
        float xOverlap,yOverlap;
        float areaOverlap,areaPaquete,factorArea;

        if(p1[0]+rotacion.getAncho()>p2[0] || p1[1]+rotacion.getLargo()>p2[1] || p1[2]+rotacion.getAlto()>p2[2]) return false;
        if(pesoCargado+rotacion.getPeso()>limitePeso) return false;

        for (Empaque empaque: solucion) {
            if(empaque.getzAxis()+empaque.getPaquete().getAlto()>(p1[2]+rotacion.getAlto())) continue;
            float r2p1[]={empaque.getxAxis(),empaque.getyAxis()};
            float r2p2[]={empaque.getxAxis()+empaque.getPaquete().getAncho(),empaque.getyAxis()+empaque.getPaquete().getLargo()};
            if (r2p1[0]>r1p2[0] || r2p2[0]<r1p1[0]){
                continue;
            }
            else {
                xOverlap=Math.min(r1p2[0],r2p2[0])-Math.max(r1p1[0],r2p1[0]);
            }
            if(r2p1[1]>r1p2[1] || r2p2[1]<r1p1[1]){
                continue;
            }
            else {
                yOverlap=Math.min(r1p2[1],r2p2[1])-Math.max(r1p1[1],r2p1[1]);
            }
            areaOverlap=xOverlap*yOverlap;
            areaPaquete=empaque.getPaquete().getAncho()*empaque.getPaquete().getLargo();
            factorArea=areaOverlap/areaPaquete;
            if(empaque.getPesoTotalApilado()+factorArea*rotacion.getPeso()>empaque.getPaquete().getLimiteApilacion()) {
                return false;
            }
        }
        return true;
    }
}
