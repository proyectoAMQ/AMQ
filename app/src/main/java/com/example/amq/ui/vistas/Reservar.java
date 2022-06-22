package com.example.amq.ui.vistas;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.amq.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reservar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reservar extends Fragment {

    private Calendar calFInicio;
    private String fInicio = null;

    private Calendar calFFin;
    private String fFin = null;
    private Integer cantDias = null;

    SharedPreferences.Editor editor;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Reservar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reservar.
     */
    // TODO: Rename and change types and number of parameters
    public static Reservar newInstance(String param1, String param2) {
        Reservar fragment = new Reservar();
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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( getContext());
        editor = preferences.edit();
        editor.apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_reservar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarView calViewFInicio = (CalendarView) getView().findViewById(R.id.reservar_fInicio);
        calViewFInicio.setOnDateChangeListener( new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView
                                                        , int year, int month, int day) {
                fInicio =String.valueOf(year)+"-"+
                                (month<10?"0":"" )+ String.valueOf(month+1)+"-"+
                                (day<10?"0":"" )+ String.valueOf(day);
                calFInicio = Calendar.getInstance();
                calFInicio.set(year, month, day);
                if( calFFin!=null ){
                    long milisDif = calFFin.getTimeInMillis() - calFInicio.getTimeInMillis();
                    cantDias = (int) TimeUnit.MILLISECONDS.toDays(Math.abs(milisDif)) + 1;


                    editor.putInt("cantDias", cantDias );
                    editor.apply();
                    Log.i("Dias", String.valueOf(cantDias) );
                }
                Log.i("Fecha Inicio", fInicio);
            }
        });

        CalendarView calViewFFin = (CalendarView) getView().findViewById(R.id.reservar_fFin);
        calViewFFin.setOnDateChangeListener( new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView
                    , int year, int month, int day) {

                fFin =String.valueOf(year)+"-"+
                        (month<10?"0":"" )+ String.valueOf(month+1)+"-"+
                        (day<10?"0":"" )+ String.valueOf(day);
                calFFin = Calendar.getInstance();
                calFFin.set(year, month, day);


                Log.i("Fecha Fin", fFin);

            }
        });

        Button btnConfirmar = (Button)getView().findViewById(R.id.reservar_confirmar);
        btnConfirmar.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if( fInicio==null ){
                    Toast.makeText(getActivity(),"Debe seleccionar una fecha de inicio",Toast.LENGTH_SHORT).show();
                }
                else if(fFin==null){
                    Toast.makeText(getActivity(),"Debe seleccionar una fecha de fin",Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("Fecha inicio:", fInicio );
                    Log.i("Fecha fin", fFin );
                    editor.putString("fInicio", fInicio );
                    editor.putString("fFin", fFin );
                    editor.apply();

                    Navigation.findNavController(view).navigate(R.id.pago_activity, new Bundle() );
                }
            }
        });
    }
}