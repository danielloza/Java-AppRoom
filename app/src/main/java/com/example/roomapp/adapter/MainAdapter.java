package com.example.roomapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomapp.R;
import com.example.roomapp.data.MainData;
import com.example.roomapp.data.RoomDB;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    //Variable
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    //constructor


    public MainAdapter(Activity context,List<MainData> dataList) {
        this.dataList = dataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        //initialize mainData
        MainData mainData = dataList.get(position);
        //initialize DB
        database= RoomDB.getInstance(context);
        //Set text on texView
        holder.textView.setText(mainData.getDato());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize Main data
                MainData d = dataList.get(holder.getAdapterPosition());

                int sID = d.getId();

                String sText = d.getDato();

                //Dialog
                Dialog dialog = new Dialog(context);
                //set content view
                dialog.setContentView(R.layout.dialog_update);
                //initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //set layout
                dialog.getWindow().setLayout(width,height);
                //show dialog
                dialog.show();

                //init and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdtae = dialog.findViewById(R.id.btUpdate);

                //set text on editext
                editText.setText(sText);

                btUpdtae.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dissmis dialog
                        dialog.dismiss();
                        //Get update text from edit text
                        String uText = editText.getText().toString().trim();
                        //update text database
                        database.mainDAO().update(sID,uText);
                        //Notify when update
                        dataList.clear();
                        dataList.addAll(database.mainDAO().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.ivDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize Main data
                MainData d = dataList.get(holder.getAdapterPosition());

                //delete database
                database.mainDAO().delete(d);
                //Notify when delete
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        //variable
        TextView textView;
        ImageView ivEdit;
        ImageView ivDelet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Assign variable
            textView = itemView.findViewById(R.id.textview);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelet = itemView.findViewById(R.id.ivDelet);
        }
    }
}
