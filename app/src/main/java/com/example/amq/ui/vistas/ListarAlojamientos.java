package com.example.amq.ui.vistas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
=======
import com.example.amq.MainActivity;
>>>>>>> parent of 6828979 (Fragment home)
import com.example.amq.R;
import com.example.amq.databinding.ActivityMainBinding;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtFiltrosAlojamiento;
import com.example.amq.rest.EndpointAMQ;
import com.example.amq.rest.IAmqApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarAlojamientos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarAlojamientos extends Fragment {

    EditText edtCodigo;
    TextView tvNombre;
    TextView tvDescripcion;
    TextView tvPrecio;
    Button btnBuscar;
<<<<<<< HEAD
    TextView text;
    ListView alojsGridView;
=======
>>>>>>> parent of 6828979 (Fragment home)

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView text;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ActivityMainBinding binding;

    public ListarAlojamientos() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListarAlojamientos newInstance(String param1, String param2) {
        ListarAlojamientos fragment = new ListarAlojamientos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String resultB = result.getString("amount");
                text.setText(resultB);
                System.out.println(resultB);
            }
        });
<<<<<<< HEAD
=======



>>>>>>> parent of 6828979 (Fragment home)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_alojamientos, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void find( String codigo ){
        IAmqApi iAmqApi = EndpointAMQ.getIAmqApi();

        DtFiltrosAlojamiento filtrosAloj = new DtFiltrosAlojamiento(true);
        /*=>*/
        Call<List<DtAlojamiento>> call = iAmqApi.listarAlojamientos( filtrosAloj);
        call.enqueue( new Callback<List<DtAlojamiento>>(){

            @Override
            public void onResponse(Call<List<DtAlojamiento>> call, Response<List<DtAlojamiento>> response) {
                try{
                    if(response.isSuccessful()){
                        alojsGridView.addView(new View(getContext() ) );
                        List<DtAlojamiento> listaAlojs = response.body();
                        for(DtAlojamiento dtAloj : listaAlojs){

                        }


                    }
                }catch(Exception e){
                    Toast.makeText(ListarAlojamientos.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<DtAlojamiento>> call, Throwable t) {
                Toast.makeText(ListarAlojamientos.this.getContext(), "la red ta muerta", Toast.LENGTH_SHORT).show();
            }
        });
    }

}