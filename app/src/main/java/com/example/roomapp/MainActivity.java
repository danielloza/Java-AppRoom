package com.example.roomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.roomapp.adapter.MainAdapter;
import com.example.roomapp.data.MainData;
import com.example.roomapp.data.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Initialize Var

    EditText etText;
    Button btAdd, btReset;
    RecyclerView rvData;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = findViewById(R.id.etText);
        btAdd = findViewById(R.id.btAdd);
        btReset = findViewById(R.id.btReset);
        rvData = findViewById(R.id.rvData);

        //initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList = database.mainDAO().getAll();

        //initialize linera layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //set layout manager
        rvData.setLayoutManager(linearLayoutManager);
        //init adapter
        mainAdapter = new MainAdapter(MainActivity.this,dataList);
        //set adapter
        rvData.setAdapter(mainAdapter);


        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get string from editext

                String sText = etText.getText().toString().trim();
                //condition
                if (!sText.equals("")){
                    //not empty
                    //initialize mainData
                    MainData mainData = new MainData();
                    //setText on MainData
                    mainData.setDato(sText);
                    //insert Text on DataBase
                    database.mainDAO().insert(mainData);
                    //clear editext
                    etText.setText("");
                    //notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDAO().getAll());
                    mainAdapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete all data from database
                database.mainDAO().reset(dataList);
                //notify when all data is inserted
                dataList.clear();
                dataList.addAll(database.mainDAO().getAll());
                mainAdapter.notifyDataSetChanged();

            }
        });

    }
}