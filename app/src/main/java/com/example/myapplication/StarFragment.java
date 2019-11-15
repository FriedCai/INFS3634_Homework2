package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class StarFragment extends Fragment {
    private RecyclerView recyclerviw;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Cats> list = new ArrayList<>();
    private CatRoomDatabase db;
    private CatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_star, container, false);
        recyclerviw = root.findViewById(R.id.recyclerviw);
        recyclerviw.setLayoutManager(new LinearLayoutManager(getActivity()));
         adapter = new CatAdapter(getActivity(), list);
        recyclerviw.setAdapter(adapter);
        adapter.setOnItemClickListener(new CatAdapter.ItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),CatDetailActivity.class);
                intent.putExtra("data",list.get(position));
                startActivity(intent);
            }
        });
        swipeRefreshLayout = root.findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadAsyncTask(db.catDao()).execute();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       db = Room.databaseBuilder(getActivity(), CatRoomDatabase.class, "cats").build();
        new LoadAsyncTask(db.catDao()).execute();

    }
    private  class LoadAsyncTask extends AsyncTask<Void, Void,  List<Cats>> {

        private CatDao mAsyncTaskDao;

        LoadAsyncTask(CatDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected  List<Cats> doInBackground(Void... voids) {
            return mAsyncTaskDao.loadAllCats();
        }

        @Override
        protected void onPostExecute(List<Cats> cats) {
            super.onPostExecute(cats);
            swipeRefreshLayout.setRefreshing(false);
            list.clear();
            list.addAll(cats);
            adapter.notifyDataSetChanged();
        }
    }
}
