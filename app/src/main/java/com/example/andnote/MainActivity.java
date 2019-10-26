package com.example.andnote;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    TextView txtTemp, txtDes, txtQoute,txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) navListener);
       /* txtTemp = findViewById(R.id.txtTemp);
        txtDes = findViewById(R.id.txtDescription);
        txtQoute = findViewById(R.id.txtQoute);
        txtDate = findViewById(R.id.txtDate);
        find_weather();
        getDate();*/
        //find_quote();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
           new BottomNavigationView.OnNavigationItemSelectedListener() {
               @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                   Fragment selectedFrag = null;
                   switch (item.getItemId()){
                       case R.id.nav_home:
                           selectedFrag = new HomeFragment();
                           break;
                       case R.id.nav_reminder:
                           selectedFrag = new ReminderFragment();
                           break;
                       case R.id.nav_To_Do:
                           selectedFrag = new TodolistFragment();
                           break;
                       case R.id.nav_Note:
                           selectedFrag = new NoteFragment();
                           break;
                       case R.id.nav_setting:
                           selectedFrag = new SettingFragment();
                           break;
                   }
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFrag).commit();
                   return true;
               }
           };

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

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 32) / 1.800;
                    centi = Math.round(centi);
                    int i = (int) centi;
                    txtTemp.setText(String.valueOf(i));
                    txtDes.setText(description);

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

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jor);
    }

    public void getDate() {
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        txtDate.setText(date);

    }

}
