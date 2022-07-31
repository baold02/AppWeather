package com.example.appdubaothoitiet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnTiep;
    TextView txtName, txtTenQuocGia, txtNhietDo, txtTrangThai, TxtDoAm, txtMay,txtGio, txtDate;
    ImageView imgDoAm, ImgMay, ImgGio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSearch = findViewById(R.id.edtTP);
        txtName = findViewById(R.id.tvTen);
        btnSearch = findViewById(R.id.btnSearch);
        txtTenQuocGia = findViewById(R.id.tvQG);
        txtNhietDo = findViewById(R.id.tvNhietDo);
        txtTrangThai = findViewById(R.id.tvTrangThai);
        TxtDoAm = findViewById(R.id.tvDoAm);
        txtMay = findViewById(R.id.tvMay);
        txtGio = findViewById(R.id.tvGio);
        txtDate = findViewById(R.id.TvNgayCapNhat);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                GetCurrentWeatherData(city);
            }
        });

    }

    public void GetCurrentWeatherData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String URL = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=3fb42ca653bf56d1aa8328020950c5fe";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ketqua",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtName.setText("Ten thanh pho:"+name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            txtDate.setText(Day);

                            JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            String status =  jsonObject1.getString("main");
                            String icon = jsonObject1.getString("icon");
                            //Picasso.get(MainActivity,thi
                            txtTrangThai.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
                            txtNhietDo.setText(Nhietdo+"C");
                                TxtDoAm.setText(doam+"%");

                            JSONObject jsonObjectWWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWWind.getString("speed");
                            txtGio.setText(gio+"m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClouds.getString("all");
                            txtMay.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String contruy = jsonObjectSys.getString("country");
                            txtTenQuocGia.setText("ten quoc gia"+ contruy);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}