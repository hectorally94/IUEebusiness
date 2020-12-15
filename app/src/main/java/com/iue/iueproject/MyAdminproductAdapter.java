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

public class MyAdminproductAdapter extends RecyclerView.Adapter<MyAdminproductAdapter.ViewHolder>implements Filterable {
    private List<addproductclass> listData;
    private List<addproductclass> exampleListFull;
    private Context mContext;
    LayoutInflater layoutInflater;



    public MyAdminproductAdapter(Context context, List<addproductclass> listData) {
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
        View view = layoutInflater.inflate(R.layout.list_product, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        addproductclass ld=listData.get(position);
        holder.Nameprod.setText(ld.getNameproduct());
        holder.price.setText(ld.getPriceproduct());
        holder.size.setText(ld.getSizeproduct());
        holder.description.setText(ld.getProductdescription());
        Glide.with(mContext).load(ld.getImageproduct()).into(holder.mImageIv);
        // Picasso.get().load(ld.getuSerimageURL()).into(holder.mImageIv);
        //Picasso.get().load(ld.getuSerimageURL().toString()).into(holder.mImageIv);
        //.mImageIv.setImageResource(String.valueOf(.valueOf(ld.getuSerimageURL().getBytes()))); //image
        // int intVal = Integer.valueOf(String.valueOf(ld.getuSerimageURL.getValue()));

        holder.reclaclik.setOnClickListener((v)->{
            Intent intent=new Intent(mContext, EditActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("key",listData.get(position).mykey);
            intent.putExtra("email",listData.get(position).email);
            intent.putExtra("a",listData.get(position).getNameproduct());
            intent.putExtra("b",listData.get(position).getPriceproduct());
            intent.putExtra("c",listData.get(position).getSizeproduct());
            intent.putExtra("d",listData.get(position).getProductdescription());
            intent.putExtra("e",listData.get(position).getImageproduct());
              //  i.putExtra("id",listdata.id);
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
            List<addproductclass> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (addproductclass item : exampleListFull) {
                    if (item.getNameproduct().toLowerCase().contains(filterPattern)) {
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

        private TextView description,size,price,Nameprod;
        private ImageView mImageIv;
      
        String id;
        RelativeLayout reclaclik;
        public ViewHolder(View itemView) {
            super(itemView);
            String idmyky;
            Nameprod=(TextView)itemView.findViewById(R.id.idNameprod);
            price=(TextView)itemView.findViewById(R.id.idpriceprod);
            size=(TextView)itemView.findViewById(R.id.idsizeprod);
            description=(TextView)itemView.findViewById(R.id.iddescriptprod);
            mImageIv=(ImageView)itemView.findViewById(R.id.rImageViewprod);
            reclaclik=itemView.findViewById(R.id.reclaclik);


        }
    }
}