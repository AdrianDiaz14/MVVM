package com.example.model_view_viewmodel2;

public class calculadoraVelocidad {

    public static class Solicitud {
        public double distancia;
        public int tiempo;

        public Solicitud(double distancia, int tiempo) {
            this.distancia = distancia;
            this.tiempo = tiempo;
        }
    }

    interface Callback {
        void cuandoEsteCalculadaLaVelocidad(double velocidad);
        void cuandoHayaErrorDeTiempoInferiorAlMinimo(double tiempoMinimo);
        void cuandoHayaErrorDeDistanciaSuperiorAlMaximo(int distanciaMaxima);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    public void calcular(Solicitud solicitud, Callback callback) {

        callback.cuandoEmpieceElCalculo();

        double tiempoMinimo = 0;
        int distanciaMaxima = 0;
        try {
            Thread.sleep(10000);   // simular operacion de larga duracion (10s)
            tiempoMinimo = 1;
            distanciaMaxima = 10000;
        } catch (InterruptedException e) {
        }

        boolean error = false;
        if (solicitud.tiempo < tiempoMinimo) {
            callback.cuandoHayaErrorDeTiempoInferiorAlMinimo(tiempoMinimo);
            error = true;
        }

        if (solicitud.distancia > distanciaMaxima) {
            callback.cuandoHayaErrorDeDistanciaSuperiorAlMaximo(distanciaMaxima);
            error = true;
        }

        if (!error) {
            callback.cuandoEsteCalculadaLaVelocidad((solicitud.distancia / solicitud.tiempo)*3.6);
        }
        callback.cuandoFinaliceElCalculo();
    }
}