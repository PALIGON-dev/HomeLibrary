package com.example.homelibrary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.homelibrary.Data.Book;
import com.example.homelibrary.R;

import java.util.List;

public class LibRecyclerAdapter extends RecyclerView.Adapter<LibRecyclerAdapter.BookViewHolder> {

    private Context context;
    private List<Book> Lib;

    public LibRecyclerAdapter(Context context, List<Book> Lib) {
        this.context = context;
        this.Lib = Lib;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lib_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = Lib.get(position);
        holder.nameTextView.setText(book.getName());
        holder.coverImageView.setImageResource(book.getCover());
    }

    @Override
    public int getItemCount() {
        return Lib.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImageView;
        TextView nameTextView, authorTextView, dateTextView, rateTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.Cover);
            nameTextView = itemView.findViewById(R.id.Name);
        }
    }
}
