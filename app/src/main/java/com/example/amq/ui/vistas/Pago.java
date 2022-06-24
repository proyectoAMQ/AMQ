package com.example.amq.ui.vistas;
// Important: Don't forget to include the call to System.loadLibrary
// as shown at the bottom of this code sample.

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chilkatsoft.CkDateTime;
import com.chilkatsoft.CkJsonObject;
import com.chilkatsoft.CkRest;
import com.chilkatsoft.CkString;
import com.chilkatsoft.CkStringBuilder;
import com.example.amq.R;
import com.example.amq.models.DtAlojamiento;
import com.example.amq.models.paypal.Amount;
import com.example.amq.models.paypal.ApplicationContext;
import com.example.amq.models.paypal.Breakdown;
import com.example.amq.models.paypal.DtCreateOrder;
import com.example.amq.models.paypal.DtOrderResponse;
import com.example.amq.models.paypal.Item;
import com.example.amq.models.paypal.ItemTotal;
import com.example.amq.models.paypal.PurchaseUnit;
import com.example.amq.models.paypal.UnitAmount;
import com.example.amq.paypal.PaypalFunctions;
import com.example.amq.paypal.PaypalWebView;
import com.example.amq.rest.IPaypalApi;
import com.example.amq.rest.PaypalEndpoint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pago extends Activity {
    private String access_token = "";
    private Object ArrayList;
    private WebView browser=null;
    private Context context;

    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();

        setContentView(R.layout.activity_pago);

        access_token = PaypalFunctions.getAccessToken();


        browser = findViewById(R.id.pago_browser);


        browser.setWebViewClient(new PaypalWebView());
        IPaypalApi iPaypalApi = PaypalEndpoint.getIPaypalApi();
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);



        Item item = new Item(
                "Reserva Alojamiento",
                "(Nombrealoj)+Huesped+(id)",
                "1",
                 new UnitAmount(
                    "USD",
                    "100.00"
                )

        );

        List<Item> items = new ArrayList<>();
        items.add(item);

        PurchaseUnit purchaseUnit = new PurchaseUnit(
                null,
                null,
                items,
                new Amount(
                        "USD",
                        "100.0",
                        new Breakdown(new ItemTotal("USD", "100.00"))
                )
        );
        List<PurchaseUnit> purchaseUnits = new ArrayList<>();
        purchaseUnits.add(purchaseUnit);

        DtCreateOrder order = new DtCreateOrder(
                "AUTHORIZE",
                purchaseUnits,
                new ApplicationContext(
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/9/99/White_Background_%28To_id_screen_dust_during_cleanup%29.jpg/1200px-White_Background_%28To_id_screen_dust_during_cleanup%29.jpg",
                        "https://images.unsplash.com/photo-1565710545039-46c9a7d22959?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1287&q=80"
                )
        );


        Call<DtOrderResponse> call = iPaypalApi.createOrder( access_token, order );
        call.enqueue( new Callback< DtOrderResponse >(){
            @Override
            public void onResponse(Call<DtOrderResponse> call, Response<DtOrderResponse> response) {
                DtOrderResponse dtOrderResponse = response.body();
                String redirectLink = dtOrderResponse.getLinks().get(1).getHref();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("id_order", dtOrderResponse.getId() );
                editor.apply();

                browser.loadUrl(redirectLink);
            }

            @Override
            public void onFailure(Call<DtOrderResponse> call, Throwable t) {

            }
        });
    }
}