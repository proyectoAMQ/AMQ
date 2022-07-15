package com.example.amq.ui.vistas;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.amq.R;
import com.example.amq.alerts.Alert;
import com.example.amq.models.DtEnviarCalificacion;
import com.example.amq.models.DtFactura;
import com.example.amq.models.DtFecha;
import com.example.amq.models.DtReservaAlojHab;
import com.example.amq.models.PagoEstado;
import com.example.amq.models.ReservaEstado;
import com.example.amq.models.paypal.Amount;
import com.example.amq.models.paypal.Breakdown;
import com.example.amq.models.paypal.DtError;
import com.example.amq.models.paypal.DtRefund;
import com.example.amq.models.paypal.DtRefundReponse;
import com.example.amq.models.paypal.ItemTotal;
import com.example.amq.paypal.PaypalFunctions;
import com.example.amq.rest.AMQEndpoint;
import com.example.amq.rest.IAmqApi;
import com.example.amq.rest.IPaypalApi;
import com.example.amq.rest.PaypalEndpoint;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservaInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservaInfo extends Fragment {

    private SharedPreferences preferences;
    private String jwToken = null;

    private int idReserva=0;
    private DtReservaAlojHab reserva;
    private boolean isEditable_calificar=false;
    private View reservaInfoView;
    private Button btnCalificar;
    private Button btnCancelar;

    private ImageSlider slider;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView calAnfitrion;
    TextView resena;
    TextView estado;

    Double devolucion_monto = null;

    public ReservaInfo() {
        // Required empty public constructor
    }

    public static ReservaInfo newInstance() {
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
        jwToken = preferences.getString("jwToken", "");
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
        btnCancelar = view.findViewById(R.id.reserva_btnCancelar);

        slider = view.findViewById(R.id.image_sliderReserva);
        final List<SlideModel> imagenesFire = new ArrayList<>();
        db.collection("fotos")
                .document(reserva.getAloj_nombre())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            imagenesFire.add(new SlideModel(documentSnapshot.getString("url1"), ScaleTypes.FIT));
                            imagenesFire.add(new SlideModel(documentSnapshot.getString("url2"), ScaleTypes.FIT));
                            imagenesFire.add(new SlideModel(documentSnapshot.getString("url3"), ScaleTypes.FIT));
                            slider.setImageList(imagenesFire, ScaleTypes.FIT);
                        }
                    }
        });

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
        estado.setText(reserva.getRes_estado().toString());

        if( reserva.getRes_estado() == ReservaEstado.EJECUTADA ) {
            LinearLayout califLayout =  view.findViewById(R.id.reserva_layout_calif);
            califLayout.setVisibility(View.VISIBLE);
            if (reserva.getRes_calificacion() != null) {
                int calHu = reserva.getRes_calificacion().getCalificacionHuesped();
                calHuesped.setText(String.valueOf(calHu < 1 ? "-" : calHu));

                int calAn = reserva.getRes_calificacion().getCalificacionAnfitrion();
                calAnfitrion.setText(String.valueOf(calAn < 1 ? "-" : calAn));

                String resenaAloj = reserva.getRes_calificacion().getResena();
                resena.setText(resenaAloj == null ? "-" : resenaAloj);
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

                    resena.setEnabled(true);
                    resena.setHint("Ingrese reseña de alojamiento");
                    resena.setText(
                            resena.getText().toString().trim().equals("-")
                                    ? ""
                                    : resena.getText().toString()
                    );
                    calAnf.setHint("Califique al anfitrión");
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
                Alert.alertConfirm(
                        reservaInfoView,
                        "Error de conectividad",
                        "Se produjo un error de conectividad con el servidor, para continuar presione OK",
                        -1
                );
            }
        });

    }

    private void setBtnCancelarAprobada(View view){
        Button btnCancelar = view. findViewById(R.id.reserva_btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelarReserva_checkConectividad();
            }
        });
    }

    private void cancelarReserva_checkConectividad() {
        //Verifica conectividad com amq y caducidad de la sesion
        IAmqApi amqApi = AMQEndpoint.getIAmqApi();
        Call<Object> call = amqApi.esValidoTokenHuesped(jwToken);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.code()==403){
                    Alert.alertConfirm(
                            reservaInfoView,
                            "Sesion inválida",
                            "La sesión caducó, precione OK para continuar.",
                            R.id.login_fragment
                    );
                }
                else{
                    try {
                        devolucionPaypal();
                    }
                    catch(Exception e){
                        Alert.alertConfirm(
                                reservaInfoView,
                                "Error paypal",
                                "Se produjo un error al realizar la devolución através de paypal.",
                                -1
                        );
                        Log.e("Devolución paypal: ",
                                e.getMessage()==null ? "Error inesperado" : e.getMessage());
                    }
                }
            }


            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String mensaje;
                if(t instanceof ConnectException){
                    mensaje = "Error de conectividad con servidor AMQ";

                }else{
                    mensaje = "Error desconocido en servidor AMQ";
                }
                Alert.alertConfirm(
                        reservaInfoView,
                        "Error de servidor",
                        mensaje,
                        -1
                );

                Log.e("ERROR" , t.getMessage());
            }
        });
    }

    private void devolucionPaypal() throws Exception{
        List<DtFactura> facturas = reserva.getFacturas();
        String pagoRealizado_orden = null;
        Double pago_monto = null;

        //Obtiene datos de facturación
        for( DtFactura fact : facturas){
            if( fact.getPagoEstado() == PagoEstado.REALIZADO ){
                pagoRealizado_orden = fact.getIdPaypal();
                pago_monto = fact.getMonto();
            }
            else if( fact.getPagoEstado() == PagoEstado.PENDIENTE ){
                pago_monto = fact.getMonto();
            }
        }

        if( pagoRealizado_orden==null || pago_monto==null || pago_monto==0 ){
            throw new Exception("El estado de facturación es inconsistente, por favor póngase en " +
                    "contacto con un administrador.");
        }

        devolucion_monto = pago_monto/2;

        DtRefund dtRefund = new DtRefund(
                new Amount(
                        "USD",
                        devolucion_monto.toString(),
                        new Breakdown(
                                new ItemTotal(
                                        "USD",
                                        devolucion_monto.toString()
                                )
                        )
                ),
                String.valueOf(reserva.getRes_id())+"-"+ String.valueOf( System.currentTimeMillis() ),
                false,
                "Refund de reserva APROBADA",
                "Refund de reserva APROBADA"
        );

        IPaypalApi iPaypalApi = PaypalEndpoint.getIPaypalApi();
        String paypal_access_token = PaypalFunctions.getAccessToken();

        Call<DtRefundReponse> call = iPaypalApi.refund(
                paypal_access_token,
                String.valueOf( System.currentTimeMillis() ),
                dtRefund,
                pagoRealizado_orden
        );

        call.enqueue(new Callback<DtRefundReponse>() {
            @Override
            public void onResponse(Call<DtRefundReponse> call, Response<DtRefundReponse> response) {
                if( response.code()==201 || response.code()==200){
                    DtRefundReponse refund = response.body();
                    String id = refund.getId();

                    Calendar cal = Calendar.getInstance();
                    int dia = cal.get(Calendar.DAY_OF_MONTH);
                    int mes = cal.get(Calendar.MONTH) +1;
                    int anio = cal.get(Calendar.YEAR);

                    DtFactura dtFactura = new DtFactura(
                            0, PagoEstado.DEVOLUCION, devolucion_monto,
                            new DtFecha(dia, mes, anio),
                            false,
                            0.0,
                            refund.getId()
                    );

                    devolucionBack(dtFactura);
                }
                else{
                    ResponseBody e = response.errorBody();
                    try{
                        Log.e("errorResponse","" + e.string() );
                    }catch(Exception ex){

                    }

                    Alert.alertConfirm(
                            reservaInfoView,
                            "Error paypal",
                            "Error al realizar la devolución através de paypal",
                            -1
                    );
                    Log.e("Paypal refund ", "No se pudo relizar la devolución código http: "
                            + String.valueOf(response.code()
                            + response.errorBody().toString() )
                    );
                }
            }

            @Override
            public void onFailure(Call<DtRefundReponse> call, Throwable t) {
                String mensaje=null;
                if(t instanceof ConnectException){
                    mensaje = "Error de conectividad con servidor Paypal";
                }
                else{
                    mensaje = "Error desconocido en servidor Paypal";
                }
                Alert.alertConfirm(
                        reservaInfoView,
                        "Error paypal",
                        mensaje,
                        -1
                );
                Log.e("Paypal refund ", "No se pudo relizar la devolución " +
                        t.getMessage() == null ? "" : t.getMessage());
            }
        });
    }

    private void devolucionBack(DtFactura dtFactura){
        IAmqApi iAmqApi = AMQEndpoint.getIAmqApi();
        Call<Object> call = iAmqApi.cancelarReservaAprobada(jwToken , reserva.getRes_id(), dtFactura);
        btnCancelar.setEnabled(false);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if( response.code()==200 ) {
                    Object o = response.body();
                    estado.setText("RECHAZADA");
                    btnCancelar.setVisibility(View.GONE);
                    reservaInfoView.findViewById(R.id.reserva_layout_cancelar).setEnabled(false);
                    Alert.alertConfirm(
                            reservaInfoView,
                            "Reserva cancelada",
                            "La reserva con id "+String.valueOf(reserva.getRes_id()+
                                    " ha sido cancelada, presione OK para continuar"),
                            -1
                    );
                }
                else if( response.code()==403){
                    Alert.alertConfirm(
                            reservaInfoView,
                            "Sesión inválida",
                            "La sesión ha caducado, presione OK, para continuar",
                            R.id.login_fragment
                    );
                    Log.e("ERROR", "Sesión caduca después de realizar devolución paypal");
                }
                else{
                    btnCancelar.setEnabled(true);
                    Alert.alertConfirm(
                            reservaInfoView,
                            "Erro desconocido",
                            "Se produjo un error desconocido al realizar cancelación en AMQ, " +
                                    "presione OK para continuar",
                            R.id.login_fragment
                    );
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Alert.alertConfirm(
                        reservaInfoView,
                        "Error desconocido",
                        "Se produjo un error desconocido al realizar cancelación en AMQ, " +
                                "presione OK para continuar.",
                        R.id.login_fragment
                );
                Log.e( "ERROR", t.getMessage() );
            }
        });
    }

}