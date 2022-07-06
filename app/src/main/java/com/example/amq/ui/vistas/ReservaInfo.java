package com.example.amq.ui.vistas;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amq.R;
import com.example.amq.models.DtEnviarCalificacion;
import com.example.amq.models.DtReservaAlojHab;
import com.example.amq.models.ReservaEstado;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;

import java.util.prefs.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservaInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservaInfo extends Fragment {

    SharedPreferences preferences;
    private int idReserva;
    private DtReservaAlojHab reserva;
    private boolean isEditable_calificar=false;
    private View reservaInfoView;
    Button btnCalificar;

    TextView calAnfitrion;
    TextView resena;
    TextView estado;



    public ReservaInfo() {
        // Required empty public constructor
    }

    public static ReservaInfo newInstance(String param1, String param2) {
        ReservaInfo fragment = new ReservaInfo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            idReserva = getArguments().getInt("idReserva");
            reserva =(DtReservaAlojHab) getArguments().getSerializable("reserva");
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserva_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reservaInfoView = view;
        btnCalificar = view.findViewById(R.id.reserva_btncalificar);

        TextView nomAloj = view.findViewById(R.id.reserva_aloj_nombre);
        TextView descAloj = view.findViewById(R.id.reserva_aloj_desc);
        TextView ciudadAlojPais = view.findViewById(R.id.reserva_aloj_ciudad_pais);
        TextView direcAloj = view.findViewById(R.id.reserva_aloj_direccion);
        TextView idRes = view.findViewById(R.id.reserva_id);
        TextView calHuesped = view.findViewById(R.id.reserva_calif_hu);
        calAnfitrion = view.findViewById(R.id.reserva_calif_anf);
        resena = view.findViewById(R.id.reserva_aloj_res);
        estado = view.findViewById(R.id.reserva_estado);

        nomAloj.setText( reserva.getAloj_nombre() );
        descAloj.setText( reserva.getAloj_descripcion() );
        ciudadAlojPais.setText( reserva.getAloj_direccion().getCiudad() + " (" +
                reserva.getAloj_direccion().getPais().getNombre() +") " );
        direcAloj.setText( reserva.getAloj_direccion().getCalle()+" " +
                reserva.getAloj_direccion().getNumero()+"" );
        idRes.setText( String.valueOf(reserva.getRes_id() ) );
        estado.setText( reserva.getRes_estado().toString() );

        if( reserva.getRes_estado() == ReservaEstado.EJECUTADA ) {
            LinearLayout califLayout =  view.findViewById(R.id.reserva_layout_calif);
            califLayout.setVisibility(View.VISIBLE);
            if (reserva.getRes_calificacion() != null) {
                int calHu = reserva.getRes_calificacion().getCalificacionHuesped();
                calHuesped.setText(String.valueOf(calHu < 1 ? "" : calHu));

                int calAn = reserva.getRes_calificacion().getCalificacionAnfitrion();
                calAnfitrion.setText(String.valueOf(calAn < 1 ? "" : calAn));

                String resenaAloj = reserva.getRes_calificacion().getResena();
                resena.setText(resenaAloj == null ? "" : resenaAloj);
            } else {
                calHuesped.setText("-");
                calAnfitrion.setText("-");
                resena.setText("-");
            }
            setBtnCalificar(view);
        }
        else if( reserva.getRes_estado()== ReservaEstado.APROBADO ){
            view.findViewById(R.id.reserva_layout_cancelar).setVisibility(View.VISIBLE);
            setBtnCancelarAprobada( view );
        }
    }

    //######################################################################################//
    //################################ Funciones auxiliares ################################//
    //######################################################################################//

    private void setBtnCalificar(View view) {
        btnCalificar = view.findViewById(R.id.reserva_btncalificar);
        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView calAnf = reservaInfoView.findViewById(R.id.reserva_calif_anf);
                TextView resena = reservaInfoView.findViewById(R.id.reserva_aloj_res);

                if(!isEditable_calificar){
                    calAnf.setEnabled(true);
                    calAnf.setText(
                            calAnf.getText().toString().trim().equals("-")
                                    ? ""
                                    : calAnf.getText().toString()
                    );
                    calAnf.setHint("Ingrese calificación");

                    resena.setEnabled(true);
                    resena.setText(
                            resena.getText().toString().trim().equals("-")
                                    ? ""
                                    : resena.getText().toString()
                    );
                    calAnf.setHint("Ingrese reseña de alojamiento");
                    ((Button)view).setText("Guardar calificación");
                    isEditable_calificar=true;
                }
                else{
                    Integer valorCalAnf;

                    try {
                        valorCalAnf =
                                calAnf.getText().toString().trim().equals("-")
                                        ? 0
                                        : Integer.parseInt(calAnf.getText().toString());
                    }catch(Exception e){
                        valorCalAnf=0;
                    }
                    String resenaAloj =
                            resena.getText().toString().trim().equals("-")
                            ? ""
                            : resena.getText().toString() ;
                    calificar(
                            reserva.getRes_id(),
                            preferences.getInt("idUsuario",0),
                            valorCalAnf,
                            resenaAloj
                    );
                    btnCalificar.setText("Calificar");
                    isEditable_calificar = false;
                    resena.setEnabled(false);
                    calAnf.setEnabled(false);
                }
            }
        });
    }

    private void calificar( int idRes, int idAnf, int calif, String resena ){
        String jwToken =  preferences.getString("jwToken" , "aa" );
        IAmqApi iAmqApi = AMQEndpoint.getIAmqApi();
        DtEnviarCalificacion dtEnviarCalificacion = new DtEnviarCalificacion(
                idAnf, idRes, calif, resena
        );
        Call<Object> call = iAmqApi.calificar(jwToken,  dtEnviarCalificacion);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.code()==200){
                    Object o = response.body();
                    Toast.makeText(getContext(), "Calificación enviada.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "Error inesperado.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getContext(), "Se produjo un error", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setBtnCancelarAprobada(View view){
        Button btnCancelar = view. findViewById(R.id.reserva_btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = preferences.getString("jwToken","aa");
                IAmqApi iAmqApi = AMQEndpoint.getIAmqApi();
                Call<Object> call = iAmqApi.cancelarReservaAprobada(token , reserva.getRes_id());
                btnCancelar.setEnabled(false);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if( response.code()==200 ) {
                            Object o = response.body();
                            estado.setText("RECHAZADA");
                            btnCancelar.setVisibility(View.GONE);
                            reservaInfoView.findViewById(R.id.reserva_layout_cancelar).setEnabled(false);
                            Toast.makeText(getContext(), "Reserva cancelada.", Toast.LENGTH_LONG).show();
                        }
                        else if( response.code()==403){
                            btnCancelar.setEnabled(true);
                            Toast.makeText(getContext(), "Su sesión caducó", Toast.LENGTH_LONG).show();
                        }
                        else{
                            btnCancelar.setEnabled(true);
                            Toast.makeText(getContext(), "Error: "+response.headers().get("AMQ_ERROR"), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(getContext(), "Error en el servidor.", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

}