package com.example.coursework_ordersdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.OrdersViewHolder> {
    private final Context context;
    private final ArrayList<String> order_id, order_code, operator_code, hotel_code, order_date, number_of_ordered_rooms;
    Activity activity;

    CustomAdapter(Activity activity, Context context,
                  ArrayList<String> order_id,
                  ArrayList<String> order_code,
                  ArrayList<String> operator_code,
                  ArrayList<String> hotel_code,
                  ArrayList<String> order_date,
                  ArrayList<String> number_of_ordered_rooms){
        this.context = context;
        this.order_id = order_id;
        this.order_code = order_code;
        this.operator_code = operator_code;
        this.hotel_code = hotel_code;
        this.order_date = order_date;
        this.number_of_ordered_rooms = number_of_ordered_rooms;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_row, parent, false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        holder.order_id_text.setText(String.valueOf(order_id.get(position)));
        holder.order_code_text.setText(String.valueOf(order_code.get(position)));
        holder.operator_code_text.setText(String.valueOf(operator_code.get(position)));
        holder.hotel_code_text.setText(String.valueOf(hotel_code.get(position)));
        holder.order_date_text.setText(String.valueOf(order_date.get(position)));
        holder.number_of_ordered_rooms_text.setText(String.valueOf(number_of_ordered_rooms.get(position)));

        holder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("order_id", String.valueOf(order_id.get(position)));
                intent.putExtra("order_code", String.valueOf(order_code.get(position)));
                intent.putExtra("operator_code", String.valueOf(operator_code.get(position)));
                intent.putExtra("hotel_code", String.valueOf(hotel_code.get(position)));
                intent.putExtra("order_date", String.valueOf(order_date.get(position)));
                intent.putExtra("number_of_ordered_rooms", String.valueOf(number_of_ordered_rooms.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog(String.valueOf(order_id.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return order_id.size();
    }

    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        TextView order_id_text, order_code_text, operator_code_text, hotel_code_text, order_date_text, number_of_ordered_rooms_text;
        ImageButton edit_button, delete_button;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id_text = itemView.findViewById(R.id.order_id_text);
            order_code_text = itemView.findViewById(R.id.order_code_text);
            operator_code_text = itemView.findViewById(R.id.operator_code_text);
            hotel_code_text = itemView.findViewById(R.id.hotel_code_text);
            order_date_text = itemView.findViewById(R.id.order_date_text);
            number_of_ordered_rooms_text = itemView.findViewById(R.id.number_of_ordered_rooms_text);
            edit_button = itemView.findViewById(R.id.edit_button);
            delete_button = itemView.findViewById(R.id.delete_button);
        }
    }

    void confirmDialog(String row_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Order #" + row_id + "?");
        builder.setMessage("Are you sure to delete order #" + row_id+ " from DB?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                OrdersDBHelper db = new OrdersDBHelper(context);
                db.deleteOneRow(row_id);
                Intent intent = new Intent(context, MainActivity.class);
                activity.startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}
