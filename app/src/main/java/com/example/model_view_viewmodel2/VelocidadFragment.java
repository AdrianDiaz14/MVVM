package com.example.model_view_viewmodel2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.model_view_viewmodel2.databinding.FragmentVelocidadBinding;

public class VelocidadFragment extends Fragment {
    private FragmentVelocidadBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentVelocidadBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final VelocidadViewModel miHipotecaViewModel = new ViewModelProvider(this).get(VelocidadViewModel.class);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double distancia = Double.parseDouble(binding.distancia.getText().toString());
                int tiempo = Integer.parseInt(binding.tiempo.getText().toString());

                miHipotecaViewModel.calcular(distancia, tiempo);
            }
        });

        miHipotecaViewModel.errorTiempo.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double tiempoMinimo) {
                if (tiempoMinimo != null) {
                    binding.tiempo.setError("El tiempo no puede ser inferor a " + tiempoMinimo + " segundos");
                } else {
                    binding.tiempo.setError(null);
                }
            }
        });

        miHipotecaViewModel.errorDistancia.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer distanciaMaxima) {
                if (distanciaMaxima != null) {
                    binding.distancia.setError("La distancia no puede ser superior a " + distanciaMaxima + " metros");
                } else {
                    binding.distancia.setError(null);
                }
            }
        });

        miHipotecaViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.velocidad.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.velocidad.setVisibility(View.VISIBLE);
                }
            }
        });

        miHipotecaViewModel.velocidad.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double velocidad) {
                binding.velocidad.setText(String.format("%.1f km/h", velocidad));
            }
        });
    }
}