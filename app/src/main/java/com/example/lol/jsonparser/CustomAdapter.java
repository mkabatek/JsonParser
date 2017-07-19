package com.example.lol.jsonparser;

/*
 * CustomAdapter.java
 * Updated by lol on 7/18/17.
 * Author: Michael Kabatek
 * A custom adapter to populate a recycler view with
 * image, title, and author from an array of json data
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private final ArrayList<Book> books;

    public CustomAdapter(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        if(books != null){
            return books.size();
        }
        else{
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final int i = position;

        /* Set the data that changes in each row, like `title` and `size`
         *    This is where you give rows there unique values.
         */
        holder.title.setText(books.get(position).title);
        holder.author.setText(books.get(position).author);

        //Use Picasso class to download image
        //Validate if string is empty
        if (books.get(position).image.isEmpty()) {
            //If the url is empty display a placeholder
            Picasso.with(holder.image.getContext())
                    .load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.image);

        }else{
            //Display downloaded image from url
            Picasso.with(holder.image.getContext())
                    .load(books.get(position).image)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.image);
        }

        //Click listener to indicate if item has been clicked
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), books.get(i).title, Toast.LENGTH_LONG).show();
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView title;
        public final TextView author;
        public final ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title  = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            image  = itemView.findViewById(R.id.image);

        }
    }

}

