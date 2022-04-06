package com.example.coursework_ordersdb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText order_code_input, operator_code_input, hotel_code_input, order_date_input, number_of_ordered_rooms_input;
    Button update_button;
    String order_id, order_code, operator_code, hotel_code, order_date, number_of_ordered_rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        order_code_input = findViewById(R.id.order_code_input2);
        operator_code_input = findViewById(R.id.operator_code_input2);
        hotel_code_input = findViewById(R.id.hotel_code_input2);
        order_date_input = findViewById(R.id.order_date_input2);
        number_of_ordered_rooms_input = findViewById(R.id.number_of_ordered_rooms_input2);

        update_button = findViewById(R.id.update_order_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Update Order: #" + order_id + " in DB");
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrdersDBHelper ordersDBHelper = new OrdersDBHelper(UpdateActivity.this);
                order_code = order_code_input.getText().toString().trim();
                operator_code = operator_code_input.getText().toString().trim();
                hotel_code = hotel_code_input.getText().toString().trim();
                order_date = order_date_input.getText().toString().trim();
                number_of_ordered_rooms = number_of_ordered_rooms_input.getText().toString().trim();
                ordersDBHelper.updateData(
                        order_id,
                        Integer.parseInt(order_code),
                        Integer.parseInt(operator_code),
                        Integer.parseInt(hotel_code),
                        order_date,
                        Integer.parseInt(number_of_ordered_rooms)
                );
            }
        });

    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("order_id") &&
                getIntent().hasExtra("order_code") &&
                getIntent().hasExtra("operator_code") &&
                getIntent().hasExtra("hotel_code") &&
                getIntent().hasExtra("order_date") &&
                getIntent().hasExtra("number_of_ordered_rooms")) {
            order_id = getIntent().getStringExtra("order_id");
            order_code = getIntent().getStringExtra("order_code");
            operator_code = getIntent().getStringExtra("operator_code");
            hotel_code = getIntent().getStringExtra("hotel_code");
            order_date = getIntent().getStringExtra("order_date");
            number_of_ordered_rooms = getIntent().getStringExtra("number_of_ordered_rooms");

            order_code_input.setText(order_code);
            operator_code_input.setText(operator_code);
            hotel_code_input.setText(hotel_code);
            order_date_input.setText(order_date);
            number_of_ordered_rooms_input.setText(number_of_ordered_rooms);
        } else {
            Toast.makeText(this, "No data!", Toast.LENGTH_LONG).show();
        }
    }


}