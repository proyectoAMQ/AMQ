package com.example.amq.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.amq.R;
import com.example.amq.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String estrellasR;
    private String paisR;
    private String rangoR;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        Spinner estrellas = view.findViewById(R.id.dropEstrellas);
        String[] items = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapterE = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        estrellas.setAdapter(adapterE);
        estrellas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("estrellas", (String) parent.getItemAtPosition(position));
                estrellasR = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner pais = view.findViewById(R.id.dropPais);
        String[] itemsPRUEBA = new String[]{"URUGUAY", "ARGENTINA", "BRASIL"};
        ArrayAdapter<String> adapterP = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, itemsPRUEBA);
        pais.setAdapter(adapterP);
        pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("pais", (String) parent.getItemAtPosition(position));
                paisR = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner precio = view.findViewById(R.id.dropPrecio);
        String[] itemsR = new String[]{"0-50 U$D", "51-100 U$D", "101-200 U$D", "201-300 U$D", "+301 U$D"};
        ArrayAdapter<String> adapterR = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, itemsR);
        precio.setAdapter(adapterR);
        precio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("precio", (String) parent.getItemAtPosition(position));
                rangoR = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button buscar = view.findViewById(R.id.buscarAlojamientos);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("estrellas", estrellasR);
                bundle.putString("pais", paisR);
                bundle.putString("rango", rangoR);
                navController.navigate(R.id.action_navigation_home_to_listarAlojamientos, bundle);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}