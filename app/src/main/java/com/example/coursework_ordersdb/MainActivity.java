package com.example.coursework_ordersdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button, filter_button;
    ImageView empty_image;
    TextView empty_text;

    OrdersDBHelper ordersDB;
    ArrayList<String> order_id, order_code_input, operator_code_input, hotel_code_input, order_date_input, number_of_ordered_rooms_input;
    CustomAdapter customAdapter;

    String filterQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        filter_button = findViewById(R.id.filter_button);
        empty_image = findViewById(R.id.empty_image);
        empty_text = findViewById(R.id.empty_text);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewItemsActivity.class);
                startActivity(intent);
            }
        });

        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FilterItemsActivity.class);
                startActivity(intent);
            }
        });

        ordersDB = new OrdersDBHelper(MainActivity.this);

        order_id = new ArrayList<>();
        order_code_input = new ArrayList<>();
        operator_code_input = new ArrayList<>();
        hotel_code_input = new ArrayList<>();
        order_date_input = new ArrayList<>();
        number_of_ordered_rooms_input = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, order_id, order_code_input, operator_code_input, hotel_code_input, order_date_input, number_of_ordered_rooms_input);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArrays() {
        Cursor cursor;
        if (getIntent().hasExtra("filter_query")) {
            filterQuery = getIntent().getStringExtra("filter_query");
            cursor = ordersDB.filterData(filterQuery);
        } else {
            cursor = ordersDB.readAllData();
        }

        if (cursor.getCount() == 0) {
            empty_text.setVisibility(View.VISIBLE);
            empty_image.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                order_id.add(cursor.getString(0));
                order_code_input.add(cursor.getString(1));
                operator_code_input.add(cursor.getString(2));
                hotel_code_input.add(cursor.getString(3));
                order_date_input.add(cursor.getString(4));
                number_of_ordered_rooms_input.add(cursor.getString(5));
            }
            empty_text.setVisibility(View.GONE);
            empty_image.setVisibility(View.GONE);
        }
    }
}