package com.example.amq.ui.vistas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amq.MainActivity;
import com.example.amq.R;
import com.example.amq.models.Producto;
import com.example.amq.rest.IAmqApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView text;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_alojamientos, container, false);

        edtCodigo = (EditText) view.findViewById(R.id.edtCodigo);
        tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);
        tvPrecio = (TextView) view.findViewById(R.id.tvPrecio);
        btnBuscar = (Button) view.findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                find(edtCodigo.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text = view.findViewById(R.id.edtCodigo);
    }

    private void find( String codigo ){
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://192.168.1.3:8080/")
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        IAmqApi productoAPI = retrofit.create(IAmqApi.class);

        /*=>*/
        Call<List<Producto>> call = productoAPI.findList(codigo);
        call.enqueue( new Callback<List<Producto>>(){

            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                try{
                    if(response.isSuccessful()){
                        List<Producto> p = response.body();
                        Log.d("listado: ", p.toString());
                    }
                }catch(Exception e){
                    Toast.makeText(ListarAlojamientos.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(ListarAlojamientos.this.getContext(), "la red ta muerta", Toast.LENGTH_SHORT).show();
            }
        });
    }

}