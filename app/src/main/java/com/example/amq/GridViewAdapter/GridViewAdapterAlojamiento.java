package com.example.amq.GridViewAdapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.amq.R;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtHabitacion;
import com.example.amq.ui.vistas.ListarAlojamientos;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapterAlojamiento extends BaseAdapter {
    List<DtAlojamiento> dtAlojs;
    Context context;
    LayoutInflater inflter;


    public GridViewAdapterAlojamiento(List<DtAlojamiento> dtAlojs, Context context) {
        this.dtAlojs = dtAlojs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dtAlojs.size();
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
                inflate(R.layout.grid_view_item_alojamiento, viewGroup, false);

        RelativeLayout nueva_habView;

        ViewGroup hab_grdItemAloj = (ViewGroup) view.findViewById(R.id.insertHabitaciones);
        int id = 0;
        if( dtAlojs.get(i).getHabs() != null) {
            for (DtHabitacion hab : dtAlojs.get(i).getHabs()) {
                nueva_habView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.habitacion_item, viewGroup, false);
                nueva_habView.setId(View.generateViewId());
                nueva_habView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                if (id != 0) {
                    //Posiciòn relativa al los items ya ingresados
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.BELOW, id);
                    nueva_habView.setLayoutParams(params);
                }

                TextView descHab = (TextView) nueva_habView.findViewById(R.id.hab_desc);
                TextView precioHab = (TextView) nueva_habView.findViewById(R.id.hab_precio);

                descHab.setText(hab.getDescripcion());
                precioHab.setText(hab.getPrecioNoche().toString());
                hab_grdItemAloj.addView(nueva_habView);

                nueva_habView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("idHabitacion", String.valueOf(hab.getId() ) );
                        Navigation.findNavController(view).navigate(R.id.navigation_home, bundle);
                    }
                });

                id = nueva_habView.getId();
            }

        }





        TextView nombreAloj = (TextView) view.findViewById(R.id.nombreAloj);
        nombreAloj.setText( dtAlojs.get(i).getNombre() );

        TextView descAloj = (TextView) view.findViewById(R.id.descAloj);
        descAloj.setText( dtAlojs.get(i).getDescripcion() );
        return view;
    }
}
