package com.example.homelibrary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homelibrary.Data.Book;
import com.example.homelibrary.R;

import java.util.List;

public class MarketRecyclerAdapter extends RecyclerView.Adapter<MarketRecyclerAdapter.MarketViewHolder> {

    List<Book> Books;
    Context context;

    public MarketRecyclerAdapter(Context context,List<Book> books) {
        Books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public MarketRecyclerAdapter.MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.market_item, parent, false);
        return new MarketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketRecyclerAdapter.MarketViewHolder holder, int position) {
        Book book = Books.get(position);
        String uri = book.getСoverUrl();
        if (uri != null) {
            Glide.with(context).load(book.getСoverUrl()) // это URL обложки
                    .into(holder.Cover);
        } else {
            holder.Cover.setImageResource(R.drawable.image_test);//Если вдруг картинка не загрузится
        }
        holder.Description.setText(book.getDescription());//Установка описания
    }

    @Override
    public int getItemCount() {
        return Books.size();
    }

    public class MarketViewHolder extends RecyclerView.ViewHolder {

        TextView Description;
        ImageView Cover;
        public MarketViewHolder(@NonNull View itemView) {
            super(itemView);
            Cover = itemView.findViewById(R.id.Cover);
            Description = itemView.findViewById(R.id.Description);
        }
    }
}
