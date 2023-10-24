package com.example.courseprovider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.holder> {

    public AdminAdapter(Context context, List<DTO> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
    }

    Context context;
    List<DTO> arraylist;


    @NonNull
    @Override
    public AdminAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courselist, parent, false);
        return new AdminAdapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.holder holder, int position) {
        DTO item = arraylist.get(position);
        holder.name.setText(item.getCoursenam().toString());
        String id=item.getId();
        String imageUrl = item.getImageuri();
        Picasso.get().load(imageUrl).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Intent intent=new Intent(context,Add_Subtopic.class);
                intent.putExtra("id",id);
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
        private ImageView image;
        public holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.coursena);
            image=itemView.findViewById(R.id.courseid);
        }
    }
}
