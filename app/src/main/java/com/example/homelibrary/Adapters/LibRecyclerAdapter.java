package com.example.homelibrary.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homelibrary.Data.local.AppDatabase;
import com.example.homelibrary.Data.Models.Book;
import com.example.homelibrary.R;

import java.util.ArrayList;
import java.util.List;

public class LibRecyclerAdapter extends RecyclerView.Adapter<LibRecyclerAdapter.BookViewHolder> {


    private List<Book> Books = new ArrayList<>();
    private Context context;
    private AppDatabase db;

    private SharedPreferences preferences;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    private OnBookClickListener listener;

    public LibRecyclerAdapter(Context context, AppDatabase db, SharedPreferences preferences, OnBookClickListener listener) {
        this.context = context;
        this.db = db;
        this.preferences = preferences;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lib_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = Books.get(position);
        String uri = book.getCoverUrl();

        if (uri != null) {
            Glide.with(context).load(book.getCoverUrl()) // это URL обложки
                    .into(holder.coverImageView);
        } else {
            Log.i("Failure","Не загрузилась картинка");
            holder.coverImageView.setImageResource(R.drawable.image_test);//Если вдруг картинка не загрузится
        }

        holder.nameTextView.setText(book.getTitle());//Установка названия

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Books.size();
    }

    public void setBooks(List<Book> books) {
        this.Books = books;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImageView;
        TextView nameTextView, authorTextView, dateTextView, rateTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.LibCover);
            nameTextView = itemView.findViewById(R.id.Name);
        }
    }

    public void onBookClick(Book book){
        
    }
}
