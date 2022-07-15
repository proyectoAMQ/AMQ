package com.example.amq.gridViewAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.amq.R;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.DtHabitacion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.List;
import java.util.prefs.Preferences;

public class GridViewAdapterAlojamiento extends BaseAdapter {
    List<DtAlojamiento> dtAlojs;
    Context context;
    LayoutInflater inflter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


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
                    //Posición relativa al los items ya ingresados
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
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("precioXNoche", hab.getPrecioNoche().toString() );
                        editor.apply();

                        Bundle bundle = new Bundle();
                        bundle.putInt("idHabitacion", hab.getId() ) ;
                        bundle.putString("nomAloj", dtAlojs.get(i).getNombre());
                        Navigation.findNavController(view).navigate(R.id.infoAlojHab, bundle);
                    }
                });

                id = nueva_habView.getId();
            }

        }

        TextView nombreAloj = (TextView) view.findViewById(R.id.nombreAloj);
        nombreAloj.setText( dtAlojs.get(i).getNombre() );

        TextView descAloj = (TextView) view.findViewById(R.id.descAloj);
        descAloj.setText( dtAlojs.get(i).getDescripcion() );

        ImageView imagenAloj = (ImageView) view.findViewById(R.id.imagen_aloj);

        db.collection("fotos").document(dtAlojs.get(i).getNombre()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    new GridViewAdapterAlojamiento.DownloadImageFromInternet(imagenAloj).execute(documentSnapshot.getString("url1"));
                }
            }
        });

        return view;
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
