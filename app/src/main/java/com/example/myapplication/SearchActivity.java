package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RequestQueue queue;
    public static final String TAG = "MyTag";
    private RecyclerView recyclerviw;
    private List<Cats> list = new ArrayList<>();
    private CatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String key = getIntent().getStringExtra("key");
        recyclerviw = findViewById(R.id.recyclerviw);
        recyclerviw.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CatAdapter(this,list);
        recyclerviw.setAdapter(adapter);
        adapter.setOnItemClickListener(new CatAdapter.ItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                Intent intent = new Intent();
                intent.setClass(SearchActivity.this,CatDetailActivity.class);
                intent.putExtra("data",list.get(position));
                startActivity(intent);
            }
        });
         queue = Volley.newRequestQueue(this);
        String url =" https://api.thecatapi.com/v1/breeds/search?q="+key;
        StringRequest stringRequest = new StringRequest( url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Cats[] objectsArray = gson.fromJson(response, Cats[].class);
                        List<Cats> objectsList = Arrays.asList(objectsArray);
                        list.clear();
                        list.addAll(objectsList);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this,"error",Toast.LENGTH_LONG).show();
            }
        });

        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }
    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}
