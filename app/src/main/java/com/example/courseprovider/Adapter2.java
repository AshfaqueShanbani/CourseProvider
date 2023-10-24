package com.example.courseprovider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.holder> {

    public Adapter2(Context context, List<DTO_For_book> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
    }

    Context context;
    List<DTO_For_book> arraylist;


    @NonNull
    @Override
    public Adapter2.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courselist, parent, false);
        return new Adapter2.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter2.holder holder, int position) {
        DTO_For_book item = arraylist.get(position);
        holder.name.setText(item.getCoursenam().toString());
        String id=item.getId();
        String subid=item.getSubid();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Intent intent=new Intent(context,AnswerSheet.class);
                intent.putExtra("id",id);
                intent.putExtra("subid",subid);
                context.startActivity(intent);}
                catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arraylist.size() ;
    }
    public static class holder extends RecyclerView.ViewHolder {
        private TextView name;
        public holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.coursena);
        }
    }
}
