package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class CatDetailActivity extends AppCompatActivity {
    private Cats cats;
    private ImageView catIv;
    private RequestQueue queue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
         cats = (Cats) getIntent().getSerializableExtra("data");
         catIv = findViewById(R.id.cativ);
        TextView tv_name = findViewById(R.id.tv_name);
        TextView temperament = findViewById(R.id.temperament);
        TextView Weight = findViewById(R.id.Weight);
        TextView Origin = findViewById(R.id.Origin);
        TextView liftspan = findViewById(R.id.liftspan);
        TextView Wikipedialink = findViewById(R.id.Wikipedialink);
        TextView dogfriend = findViewById(R.id.dogfriend);
        TextView description = findViewById(R.id.description);

        tv_name.setText(cats.getName());
        temperament.setText(cats.getTemperament());
        Weight.setText(cats.getWeight().getMetric());
        Origin.setText(cats.getOrigin());
        liftspan.setText(cats.getLife_span());
        Wikipedialink.setText(cats.getWikipedia_url());
        dogfriend.setText(cats.getDog_friendly()+"");
        description.setText(cats.getDescription());
        findViewById(R.id.bt_star).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("tag","onClick");
                CatRoomDatabase db = Room.databaseBuilder(CatDetailActivity.this, CatRoomDatabase.class, "cats").build();
                new InsertAsyncTask(db.catDao()).execute(cats);
            }
        });
        requestImage();
    }

    private void requestImage() {
        queue = Volley.newRequestQueue(this);
        String url ="https://api.thecatapi.com/v1/images/search?breed_id="+cats.getId();
        StringRequest stringRequest = new StringRequest( url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        CatImage[] objectsArray = gson.fromJson(response, CatImage[].class);
                        List<CatImage> catImages = Arrays.asList(objectsArray);
                        CatImage data = catImages.get(0);
                        Glide.with(CatDetailActivity.this)
                                .load(data.getUrl()).into(catIv);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CatDetailActivity.this,"error",Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

    private  class InsertAsyncTask extends AsyncTask<Cats, Void, Boolean> {

        private CatDao mAsyncTaskDao;

        InsertAsyncTask(CatDao dao) {
            mAsyncTaskDao = dao;
            Log.e("tag","dao");
        }


        @Override
        protected Boolean doInBackground(Cats... cats) {
            try {
                mAsyncTaskDao.insert(cats[0]);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean bo) {
            super.onPostExecute(bo);
            if (bo) {
                Toast.makeText(CatDetailActivity.this, "success", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(CatDetailActivity.this, "failed", Toast.LENGTH_LONG).show();

            }
        }
    }
}
