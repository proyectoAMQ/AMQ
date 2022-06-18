package com.example.amq.gridViewAdapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.amq.R;
import com.example.amq.models.DtAlojamiento;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context c;
    List<DtAlojamiento> dtAloj;

    // Gets the context so it can be used later
    public GridViewAdapter(Context c, List<DtAlojamiento> dtAloj) {
        this.c = c;
        this.dtAloj = dtAloj;
    }

    // Total number of things contained within the adapter
    public int getCount() {
        return dtAloj.size();
    }

    // Require for structure, not really used in my code.
    public Object getItem(int position) {
        return null;
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for
    // manual control.
    public long getItemId(int position) {
        return position;
    }

    public View getView(int i,
                        View view, ViewGroup viewGroup) {
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_view_item_alojamiento, viewGroup, false);
        }

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("CLICKEASTE", "onClick: CLICKKSKSKS!");

                NavController navController = Navigation.findNavController(view);

                Bundle bundle = new Bundle();
                bundle.putString("idAloj", dtAloj.get(i).getId().toString());
                navController.navigate(R.id.navigation_home, bundle);
            }
        });

        TextView nombreAloj = view.findViewById(R.id.nombreAloj);
        nombreAloj.setText(dtAloj.get(i).getNombre());
        TextView descAloj = view.findViewById(R.id.descAloj);
        descAloj.setText(dtAloj.get(i).getDescripcion());
        return view;
    }
}