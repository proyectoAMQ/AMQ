package com.example.amq.paypal;

import android.util.Log;

import com.chilkatsoft.CkDateTime;
import com.chilkatsoft.CkJsonObject;
import com.chilkatsoft.CkRest;
import com.chilkatsoft.CkString;
import com.chilkatsoft.CkStringBuilder;

public class PaypalFunctions {
    private static final String TAG = "Chilkat";
    public PaypalFunctions() {
    }

    public static String getAccessToken(){
        CkRest rest = new CkRest();

        // Make the initial connection.
        // A single REST object, once connected, can be used for many PayPal REST API calls.
        // The auto-reconnect indicates that if the already-established HTTPS connection is closed,
        // then it will be automatically re-established as needed.
        boolean bAutoReconnect = true;
        boolean success = rest.Connect("api-m.sandbox.paypal.com",443,true,bAutoReconnect);
        if (success != true) {
            Log.i(TAG, rest.lastErrorText());
            return null;
        }

        // Duplicate this request:

        // 	curl https://api.sandbox.paypal.com/v1/oauth2/token \
        // 	  -H "Accept: application/json" \
        // 	  -H "Accept-Language: en_US" \
        // 	  -u "Client-Id:Secret" \
        // 	  -d "grant_type=client_credentials"

        rest.AddHeader("Accept","application/json");
        rest.AddHeader("Accept-Language","en_US");

        // For additional help on where to find  your client ID and API secret, see PayPal Client_ID and API_Secret
        rest.SetAuthBasic(
                "AaDJ6_EQjWrVxLQBV78NuolnYrHG8MQYOoNmbLkP-NB6g1UWGyH8JvPh0btTUliGIqxVbo9vZd_SqqWK",
                "EMiLeDMYWL5SCKbd_T7iXxSziICycPKeX9rlr36rXMm-NhCUtmQQMIUE99RXrwNii3VO3ksqqfeTY90t");

        rest.AddQueryParam("grant_type","client_credentials");

        String responseStr = rest.fullRequestFormUrlEncoded("POST","/v1/oauth2/token");
        if (rest.get_LastMethodSuccess() != true) {
            Log.i(TAG, rest.lastErrorText());
            return null;
        }

        Log.i(TAG, rest.lastRequestHeader());

        // A sample response:

        // 	{
        // 	  "scope": "https://api.paypal.com/v1/payments/.* https://api.paypal.com/v1/vault/credit-card https://api.paypal.com/v1/vault/credit-card/.*",
        // 	  "access_token": "EEwJ6tF9x5WCIZDYzyZGaz6Khbw7raYRIBV_WxVvgmsG",
        // 	  "token_type": "Bearer",
        // 	  "app_id": "APP-6XR95014BA15863X",
        // 	  "expires_in": 28800
        // 	}

        CkJsonObject json = new CkJsonObject();
        json.Load(responseStr);
        json.put_EmitCompact(false);

        // Check the response status code.  A 200 indicates success..
        if (rest.get_ResponseStatusCode() != 200) {
            Log.i(TAG, json.emit());
            Log.i(TAG, "Failed.");
            return null;
        }

        // Given that the access token expires in approx 8 hours,
        // let's record the date/time this token was created.
        // This will allow us to know beforehand if the token
        // is expired (and we can then fetch a new token).
        CkDateTime dateTime = new CkDateTime();
        boolean bLocalTime = false;
        int dtNow = dateTime.GetAsUnixTime(bLocalTime);
        json.AppendInt("tokenCreateTimeUtc",dtNow);

        // Examine the access token and save to a file.
        Log.i(TAG, "Access Token: " + json.stringOf("access_token"));
        Log.i(TAG, "Full JSON Response:");
        Log.i(TAG, json.emit());

        CkString str= new CkString();

        json.StringOf("access_token",str);

        String access_token = str.getString();
        Log.i("acces_token", access_token);



        CkStringBuilder sbResponse = new CkStringBuilder();
        sbResponse.Append(json.emit());
        boolean bEmitBom = false;
        sbResponse.WriteFile("qa_data/tokens/paypal.json","utf-8",bEmitBom);

        return "Bearer "+ access_token;
    }

    static {
        System.loadLibrary("chilkat");

        // Note: If the incorrect library name is passed to System.loadLibrary,
        // then you will see the following error message at application startup:
        //"The application <your-application-name> has stopped unexpectedly. Please try again."
    }
}
