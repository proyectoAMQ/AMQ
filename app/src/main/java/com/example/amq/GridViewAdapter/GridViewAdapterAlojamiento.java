package com.example.amq.GridViewAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.amq.R;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.ui.vistas.ListarAlojamientos;

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



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            }
        });

        TextView nombreAloj = (TextView) view.findViewById(R.id.nombreAloj);
        nombreAloj.setText( dtAlojs.get(i).getNombre() );

        TextView descAloj = (TextView) view.findViewById(R.id.descAloj);
        descAloj.setText( dtAlojs.get(i).getDescripcion() );
        return view;
    }
}
