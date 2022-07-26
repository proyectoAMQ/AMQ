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
import android.widget.LinearLayout;
import android.widget.TextView;
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

    private Calendar calFInicio = null;
    private Calendar calFFin = null;
    private String fInicio = null;
    private String fFin = null;
    private Integer cantDias = null;

    private boolean esInicioCalendario = true;

    private LinearLayout layFechas;
    private LinearLayout layCalendario;
    private CalendarView calendarioView;
    private Button btnConfirmar;
    private TextView txtFechaInicio;
    private TextView txtFechaFin;
    private TextView txtCalFechaInicio;
    private TextView txtCalFechaFin;

    private SharedPreferences.Editor editor;


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

        layFechas = getActivity().findViewById(R.id.reservar_layFechas);
        layCalendario = getActivity().findViewById(R.id.reservar_layCalendario);
        btnConfirmar = getActivity().findViewById(R.id.reservar_btnConfirmar);
        txtFechaInicio = getActivity().findViewById(R.id.reservar_editFechaInicio);
        txtFechaFin = getActivity().findViewById(R.id.reservar_editFechaFin);
        txtCalFechaInicio = getActivity().findViewById(R.id.reservar_txtCalFechaInicio);
        txtCalFechaFin = getActivity().findViewById(R.id.reservar_txtCalFechaFin);

        txtFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esInicioCalendario = true;
                layFechas.setVisibility(View.GONE);
                layCalendario.setVisibility(View.VISIBLE);
                txtCalFechaInicio.setVisibility(View.VISIBLE);
            }
        });

        txtFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esInicioCalendario = false;
                layFechas.setVisibility(View.GONE);
                layCalendario.setVisibility(View.VISIBLE);
                txtCalFechaFin.setVisibility(View.VISIBLE);
            }
        });

        btnConfirmar = (Button)getView().findViewById(R.id.reservar_btnConfirmar);

        calendarioView = (CalendarView) getView().findViewById(R.id.reservar_calendario);
        calendarioView.setOnDateChangeListener( new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView
                                                        , int year, int month, int day) {
                String strYear = String.valueOf(year);
                String strMonth = String.valueOf(month+1);
                String strDay = String.valueOf(day);

                if( esInicioCalendario ){
                    fInicio =strYear+"-"+
                            (month<10?"0":"" )+ strMonth+"-"+
                            (day<10?"0":"" )+ strDay;
                    calFInicio = Calendar.getInstance();
                    calFInicio.set(year, month, day);
                    txtFechaInicio.setText(fInicio);
                }
                else{
                    fFin =strYear+"-"+
                            (month<10?"0":"" )+ strMonth+"-"+
                            (day<10?"0":"" )+ strDay;
                    calFFin = Calendar.getInstance();
                    calFFin.set(year, month, day);
                    txtFechaFin.setText(fFin);
                }
                if( calFFin!=null && calFInicio!=null ){
                    long milisDif = calFFin.getTimeInMillis() - calFInicio.getTimeInMillis();
                    cantDias = (int) TimeUnit.MILLISECONDS.toDays((milisDif)) + 1;

                    Log.i("Dias", String.valueOf(cantDias) );
                    if(cantDias>0){
                        btnConfirmar.setVisibility(View.VISIBLE);
                    }
                    else{
                        Toast.makeText(
                                getContext(),
                                "La fecha de inicio debe ser menor a la fecha de fin",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
                layCalendario.setVisibility(View.GONE);
                layFechas.setVisibility(View.VISIBLE);
                txtCalFechaInicio.setVisibility(View.GONE);
                txtCalFechaFin.setVisibility(View.GONE);

                Log.i("Fecha Inicio", fInicio);
            }
        });

        btnConfirmar.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if( fInicio==null ){
                    Toast.makeText(getActivity(),"Debe seleccionar una fecha de inicio",Toast.LENGTH_LONG).show();
                }
                else if(fFin==null){
                    Toast.makeText(getActivity(),"Debe seleccionar una fecha de fin",Toast.LENGTH_LONG).show();
                }
                else if(cantDias<1){
                    Toast.makeText(getActivity(),"Las fechas ingresadas son incorrectas",Toast.LENGTH_LONG).show();
                }else{
                    Log.i("Fecha inicio:", fInicio );
                    Log.i("Fecha fin", fFin );
                    editor.putString("fInicio", fInicio );
                    editor.putString("fFin", fFin );
                    editor.putInt("cantDias", cantDias );
                    editor.apply();

                    Navigation.findNavController(view).navigate(R.id.pago_activity, new Bundle() );
                }
            }
        });
    }
}