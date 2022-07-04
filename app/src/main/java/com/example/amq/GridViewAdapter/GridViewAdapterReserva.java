package com.example.amq.GridViewAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.amq.R;
import com.example.amq.models.DtReservaAlojHab;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GridViewAdapterReserva extends BaseAdapter {
    List<DtReservaAlojHab> dtReservas;
    Context context;
    LayoutInflater inflter;

    private int idReserva=0;


    public GridViewAdapterReserva(List<DtReservaAlojHab> dtReservas, Context context) {
        this.dtReservas = dtReservas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dtReservas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).
                    inflate(R.layout.grid_view_item_reserva, viewGroup, false);



        LinearLayout layoutRes = view.findViewById(R.id.reservas_click_layout);
        layoutRes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                TextView tvId = (TextView) ((LinearLayout)view.getParent()).findViewById(R.id.item_reserva_id);
                Bundle bundle = new Bundle();
                bundle.putSerializable( "reserva", dtReservas.get(i));
                bundle.putInt("idReserva", Integer.parseInt(  tvId.getText().toString() )) ;
                bundle.putInt("idReserva", Integer.parseInt(  tvId.getText().toString() )) ;
                Navigation.findNavController(view).navigate(R.id.reservas_fragment_info, bundle);
            }
        });

        TextView nombreAloj = (TextView) view.findViewById(R.id.item_reserva_nombreAloj);
        nombreAloj.setText( dtReservas.get(i).getAloj_nombre() + " " +
                 String.valueOf( dtReservas.get(i).getRes_calificacion()== null ? 0 : dtReservas.get(i).getRes_calificacion().getId()  ) );

        Locale locale = new Locale("es", "AR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String strFInicio = dateFormat.format( dtReservas.get(i).getRes_fechaInicio() );
        String strFFin = dateFormat.format( dtReservas.get(i).getRes_fechaFin() );

        TextView viewFInicio = (TextView) view.findViewById(R.id.item_reserva_fInicio);
        viewFInicio.setText(strFInicio);

        TextView viewFFin = (TextView) view.findViewById(R.id.item_reserva_fFin);
        viewFFin.setText(strFFin);

        TextView idRes = (TextView) view.findViewById(R.id.item_reserva_id);
        idRes.setText( String.valueOf(dtReservas.get(i).getRes_id() ) );

        idReserva = dtReservas.get(i).getRes_id();

        return view;
    }
}
