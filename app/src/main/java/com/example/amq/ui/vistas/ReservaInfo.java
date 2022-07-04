package com.example.amq.ui.vistas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amq.R;
import com.example.amq.models.DtReservaAlojHab;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservaInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservaInfo extends Fragment {

    private int idReserva;
    private DtReservaAlojHab reserva;

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
        TextView nomAloj = view.findViewById(R.id.reserva_aloj_nombre);
        TextView descAloj = view.findViewById(R.id.reserva_aloj_desc);
        TextView ciudadAlojPais = view.findViewById(R.id.reserva_aloj_ciudad_pais);
        TextView direcAloj = view.findViewById(R.id.reserva_aloj_direccion);
        TextView idRes = view.findViewById(R.id.reserva_id);
        TextView calHuesped = view.findViewById(R.id.reserva_calif_hu);
        TextView calAnfitrion = view.findViewById(R.id.reserva_calif_anf);
        TextView resena = view.findViewById(R.id.reserva_aloj_res);

        nomAloj.setText( reserva.getAloj_nombre() );
        descAloj.setText( reserva.getAloj_descripcion() );
        ciudadAlojPais.setText( reserva.getAloj_direccion().getCiudad() + " (" +
                reserva.getAloj_direccion().getPais().getNombre() +") " );
        direcAloj.setText( reserva.getAloj_direccion().getCalle()+" " +
                reserva.getAloj_direccion().getNumero()+"" );
        idRes.setText( String.valueOf(reserva.getRes_id() ) );

        if( reserva.getRes_calificacion() !=null ) {
            calHuesped.setText(String.valueOf(reserva.getRes_calificacion().getCalificacionHuesped()));
            calAnfitrion.setText(String.valueOf(reserva.getRes_calificacion().getCalificacionAnfitrion()));
            resena.setText(reserva.getRes_calificacion().getResena());
        }
        else{
            calHuesped.setText( "-");
            calAnfitrion.setText( "-");
            resena.setText( "-");
        }

    }
}