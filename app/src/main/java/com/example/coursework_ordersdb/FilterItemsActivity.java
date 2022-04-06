package com.example.coursework_ordersdb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FilterItemsActivity extends AppCompatActivity {
    EditText order_code_input, operator_code_input, hotel_code_input, order_date_input, number_of_ordered_rooms_input;
    Button filter_button;
    String order_id, order_code, operator_code, hotel_code, order_date, number_of_ordered_rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_items);

        order_code_input = findViewById(R.id.order_code_input3);
        operator_code_input = findViewById(R.id.operator_code_input3);
        hotel_code_input = findViewById(R.id.hotel_code_input3);
        order_date_input = findViewById(R.id.order_date_input3);
        number_of_ordered_rooms_input = findViewById(R.id.number_of_ordered_rooms_input3);

        filter_button = findViewById(R.id.filter_order_button);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Filter Orders");
        }

        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrdersDBHelper ordersDBHelper = new OrdersDBHelper(FilterItemsActivity.this);
                order_code = order_code_input.getText().toString().trim();
                operator_code = operator_code_input.getText().toString().trim();
                hotel_code = hotel_code_input.getText().toString().trim();
                order_date = order_date_input.getText().toString().trim();
                number_of_ordered_rooms = number_of_ordered_rooms_input.getText().toString().trim();

                String result = ordersDBHelper.createFilterQuery(
                        order_id,
                        order_code,
                        operator_code,
                        hotel_code,
                        order_date,
                        number_of_ordered_rooms
                );
                Intent intent = new Intent(FilterItemsActivity.this, MainActivity.class);
                intent.putExtra("filter_query", result);
                startActivity(intent);
            }
        });
    }
}