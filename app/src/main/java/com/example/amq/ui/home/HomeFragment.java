package com.example.amq.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.amq.GridViewAdapter.GridViewAdapterAlojamiento;
import com.example.amq.R;
import com.example.amq.databinding.FragmentHomeBinding;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtFiltrosAloj;
import com.example.amq.models.DtPais;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private int paisR;
    private String rangoR;
    private List<String> paises = new ArrayList<String>();
    private List<DtPais> paisesDT = new ArrayList<DtPais>();

    private FragmentHomeBinding binding;

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

        Spinner pais = view.findViewById(R.id.dropPais);
        ArrayAdapter<String> adapterP = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, paises);
        pais.setAdapter(adapterP);
        pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("pais", (String) parent.getItemAtPosition(position));
                for (DtPais p : paisesDT) {
                    if (p.getNombre().equals((String) parent.getItemAtPosition(position))) {
                        paisR = p.getId();
                    }
                }
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


        listarPaises(view);

        Button buscar = view.findViewById(R.id.buscarAlojamientos);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("pais", paisR);
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

    private void listarPaises( View view ){
        IAmqApi amqApi = AMQEndpoint.getIAmqApi();

        Call<List<DtPais>> call = amqApi.getPaises();
        call.enqueue( new Callback<List<DtPais>>(){
            @Override
            public void onResponse(Call<List<DtPais>> call, Response<List<DtPais>> response) {
               paisesDT = response.body();
               for (DtPais p : paisesDT){
                   paises.add(p.getNombre());
               }
                Spinner paisesView = view.findViewById(R.id.dropPais);

                String[] itemsR = new String[paises.size()];
                itemsR = paises.toArray( itemsR );

                ArrayAdapter<String> adapterR = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, paises);
                paisesView.setAdapter(adapterR);
                paisesView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("id pais", (String) parent.getItemAtPosition(position));
                        Log.e("id pais", String.valueOf(position ) );
                        paisR = getIdPais( (String) parent.getItemAtPosition(position) );
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<DtPais>> call, Throwable t) {
                Log.d("Fail", "Fallo");
            }
        });
    }

    private int getIdPais(String nombrePais){
        for(DtPais p : paisesDT){
            if( p.getNombre()!=null &&  p.getNombre().equals(nombrePais)){
                return p.getId();
            }
        }
        return 0;
    }
}