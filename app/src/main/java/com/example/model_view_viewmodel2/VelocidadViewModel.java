package com.example.model_view_viewmodel2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VelocidadViewModel extends AndroidViewModel {

    Executor executor;

    calculadoraVelocidad simulador;

    MutableLiveData<Double> velocidad = new MutableLiveData<>();
    MutableLiveData<Double> errorTiempo = new MutableLiveData<>();
    MutableLiveData<Integer> errorDistancia = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();

    public VelocidadViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new calculadoraVelocidad();
    }

    public void calcular(double distancia, int tiempo) {

        final calculadoraVelocidad.Solicitud solicitud = new calculadoraVelocidad.Solicitud(distancia, tiempo);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                simulador.calcular(solicitud, new calculadoraVelocidad.Callback() {
                    @Override
                    public void cuandoEsteCalculadaLaVelocidad(double velocidadResultante) {
                        errorTiempo.postValue(null);
                        errorDistancia.postValue(null);
                        velocidad.postValue(velocidadResultante);
                    }

                    @Override
                    public void cuandoHayaErrorDeTiempoInferiorAlMinimo(double tiempoMinimo) {
                        errorTiempo.postValue(tiempoMinimo);
                    }

                    @Override
                    public void cuandoHayaErrorDeDistanciaSuperiorAlMaximo(int distanciaMaxima) {
                        errorDistancia.postValue(distanciaMaxima);
                    }

                    @Override
                    public void cuandoEmpieceElCalculo() {
                        calculando.postValue(true); // Activar la ProgressBar
                    }

                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false); // Desactivar la ProgressBar
                    }
                });
            }
        });
    }
}