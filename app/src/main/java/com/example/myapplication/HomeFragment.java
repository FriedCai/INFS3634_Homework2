package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private EditText etKey;
    private Button btSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        etKey = root.findViewById(R.id.et_key);
        btSearch = root.findViewById(R.id.bt_search);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = etKey.getText().toString().trim();
                if (TextUtils.isEmpty(key)){
                    Toast.makeText(getActivity(),"empty",Toast.LENGTH_LONG).show();

                }else{
                    Intent intent = new Intent(getActivity(),SearchActivity.class);
                    intent.putExtra("key",key);
                    startActivity(intent);
                }
            }
        });
    }


}

