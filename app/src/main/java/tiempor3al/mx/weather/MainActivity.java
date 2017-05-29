package tiempor3al.mx.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final  String TAG ="openweatherimagen";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView clima = (ImageView)findViewById(R.id.clima);
        final TextView temperatura = (TextView) findViewById(R.id.temperatura);
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL="http://localhost/prueba/clima";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            if (response.has("weather")) {
                                JSONArray weatherArray = response.getJSONArray("weather");
                                JSONObject weather = weatherArray.getJSONObject(0);

                                if (weather.has("icon")) {
                                    String icon = (String) weather.get("icon");
                                    int identificador = getResources().getIdentifier("imagen_" + icon, "drawable", getPackageName());
                                    clima.setImageDrawable(getResources().getDrawable(identificador, null));

                                    Log.d(TAG, icon);
                                }
                            }
                            if (response.has("weather")){
                                JSONObject main = response.getJSONObject("main");
                                Double temp = main.getDouble("temp");
                                temperatura.setText("" + temp + " \u00b0");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener()

                {

                    @Override
                    public void onErrorResponse (VolleyError error){
                        Log.d(TAG, error.getMessage());

                    }
                });
        queue.add(jsonObjectRequest);
    }
}
