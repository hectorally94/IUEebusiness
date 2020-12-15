package com.iue.iueproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {
    private List<AdminRegclass> listData;
    private List<AdminRegclass> exampleListFull;
    private Context mContext;
    LayoutInflater layoutInflater;



    public MyAdapter(Context context, List<AdminRegclass> listData) {
        this.listData = listData;
        this.mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
        exampleListFull = new ArrayList<>(listData);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data,parent,false);
       // View view= LayoutInflater.from(mContext).inflate(R.layout.list_data,parent,false);
        View view = layoutInflater.inflate(R.layout.list_data, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdminRegclass ld=listData.get(position);
        holder.txtid.setText(ld.getBusinessName());
        holder.Adminemail.setText(ld.getEmail());
        Glide.with(mContext).load(ld.getuSerimageURL()).into(holder.mImageIv);
      // Picasso.get().load(ld.getuSerimageURL()).into(holder.mImageIv);
        //Picasso.get().load(ld.getuSerimageURL().toString()).into(holder.mImageIv);
        //.mImageIv.setImageResource(String.valueOf(.valueOf(ld.getuSerimageURL().getBytes()))); //image
       // int intVal = Integer.valueOf(String.valueOf(ld.getuSerimageURL.getValue()));

        holder.reclaclik.setOnClickListener((v)->{
            Intent intent=new Intent(mContext, MyproductList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("a",listData.get(position).getBusinessName());
            intent.putExtra("b",listData.get(position).getuSerimageURL());
            intent.putExtra("c",listData.get(position).getEmail());
            mContext.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
        @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AdminRegclass> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (AdminRegclass item : exampleListFull) {
                    if (item.getBusinessName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listData.clear();
            listData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtid,Adminemail;
        private ImageView mImageIv;
        RelativeLayout reclaclik;

        public ViewHolder(View itemView) {
            super(itemView);
            txtid=(TextView)itemView.findViewById(R.id.idtxt);
           mImageIv=(ImageView)itemView.findViewById(R.id.rImageView);
           Adminemail= itemView.findViewById(R.id.emailAdmin);
            reclaclik=itemView.findViewById(R.id.reclaclik);
              

        }
    }


}