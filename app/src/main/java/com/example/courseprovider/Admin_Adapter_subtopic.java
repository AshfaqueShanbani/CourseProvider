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

public class Admin_Adapter_subtopic extends RecyclerView.Adapter<Admin_Adapter_subtopic.holder> {

    public Admin_Adapter_subtopic(Context context, List<DTO_For_book> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
    }

    Context context;
    List<DTO_For_book> arraylist;


    @NonNull
    @Override
    public Admin_Adapter_subtopic.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courselist, parent, false);
        return new Admin_Adapter_subtopic.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_Adapter_subtopic.holder holder, int position) {
        DTO_For_book item = arraylist.get(position);
        holder.name.setText(item.getCoursenam().toString());
        String id=item.getId();
        String subid=item.getSubid();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Intent intent=new Intent(context, Answer_edit_class.class);
                intent.putExtra("id",id);
                intent.putExtra("subid",subid);
                context.startActivity(intent);}

                catch (Exception e) {Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();


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
        private TextView prname;
        public holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.coursena);
        }
    }
}
