package com.example.coursework_ordersdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewItemsActivity extends AppCompatActivity {
    EditText order_code_input, operator_code_input, hotel_code_input, order_date_input, number_of_ordered_rooms_input;
    Button add_order_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_items);

        order_code_input = findViewById(R.id.order_code_input);
        operator_code_input = findViewById(R.id.operator_code_input);
        hotel_code_input = findViewById(R.id.hotel_code_input);
        order_date_input = findViewById(R.id.order_date_input);
        number_of_ordered_rooms_input = findViewById(R.id.number_of_ordered_rooms_input);

        add_order_button = findViewById(R.id.add_order_button);
        add_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrdersDBHelper ordersDB = new OrdersDBHelper(AddNewItemsActivity.this);
                ordersDB.addOrder(
                        Integer.parseInt(order_code_input.getText().toString().trim()),
                        Integer.parseInt(operator_code_input.getText().toString().trim()),
                        Integer.parseInt(hotel_code_input.getText().toString().trim()),
                        order_date_input.getText().toString().trim(),
                        Integer.parseInt(number_of_ordered_rooms_input.getText().toString().trim())
                );
            }
        });
    }
}