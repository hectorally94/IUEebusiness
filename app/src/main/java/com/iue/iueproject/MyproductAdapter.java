package com.iue.iueproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyproductAdapter extends RecyclerView.Adapter<MyproductAdapter.ViewHolder>{
    private List<addproductclass> listData;
    private Context mContext;
    LayoutInflater layoutInflater;



    public MyproductAdapter(Context context, List<addproductclass> listData) {
        this.listData = listData;
        this.mContext = context;
        layoutInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data,parent,false);
        // View view= LayoutInflater.from(mContext).inflate(R.layout.list_data,parent,false);
        View view = layoutInflater.inflate(R.layout.list_product, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        addproductclass ld=listData.get(position);
        holder.txtid.setText(ld.getNameproduct());
        Glide.with(mContext).load(ld.getImageproduct()).into(holder.mImageIv);
        // Picasso.get().load(ld.getuSerimageURL()).into(holder.mImageIv);
        //Picasso.get().load(ld.getuSerimageURL().toString()).into(holder.mImageIv);
        //.mImageIv.setImageResource(String.valueOf(.valueOf(ld.getuSerimageURL().getBytes()))); //image
        // int intVal = Integer.valueOf(String.valueOf(ld.getuSerimageURL.getValue()));


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtid;
        private ImageView mImageIv;

        public ViewHolder(View itemView) {
            super(itemView);
            txtid=(TextView)itemView.findViewById(R.id.idtxtprod);
            mImageIv=(ImageView)itemView.findViewById(R.id.rImageViewprod);


        }
    }
}