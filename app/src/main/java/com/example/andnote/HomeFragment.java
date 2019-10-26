package com.example.andnote;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    TextView txtTemp, txtDes, txtQoute,txtDate;
    ImageView viewWeather;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        txtTemp = view.findViewById(R.id.txtTemp);
        txtDes = view.findViewById(R.id.txtDescription);
        txtQoute = view.findViewById(R.id.txtQoute);
        txtDate = view.findViewById(R.id.txtDate);
        viewWeather = view.findViewById(R.id.viewWeather);
        Resources res = getResources();
        find_weather();
        getDate();
        return  view;
        //find_quote();
    }

    public void find_weather() {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=10.75&lon=106.67&appid=669ea756c30ce7bc98cd584d3d7d73ed&units=Imperial";
        JsonObjectRequest jor = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);

                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");

                    String main = object.getString("main");

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 32) / 1.800;
                    centi = Math.round(centi);
                    int i = (int) centi;
                    txtTemp.setText(String.valueOf(i));
                    CheckWeather(main);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jor);
    }

    public void getDate() {
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        txtDate.setText(date);

    }
    public void CheckWeather( String main )
    {

        if( main.equals("Thunderstorm") )
        {
            txtDes.setText(R.string.andNote_weather_Thunderstorm);
        }
        else if( main=="Drizzle")
        {
            txtDes.setText(R.string.andNote_weather_Drizzle);
        }
        else if( main.equals("Rain"))
        {
            Drawable drawable = getResources().getDrawable(R.drawable.clouds);
            viewWeather.setImageDrawable(drawable);
            txtDes.setText(R.string.andNote_weather_Rain);
        }
       else if( main.equals("Snow"))
        {
            txtDes.setText(R.string.andNote_weather_Snow);
        }
        else if( main.equals("Clouds"))
        {
            Drawable drawable = getResources().getDrawable(R.drawable.clouds);
            viewWeather.setImageDrawable(drawable);

            txtDes.setText(R.string.andNote_weather_Cloud);
        }
        else if( main.equals("Clear"))
        {
            txtDes.setText(R.string.andNote_weather_Clear);
        }
        else
        {
            txtDes.setText(R.string.andNote_weather_Clear);
        }
    }

}
