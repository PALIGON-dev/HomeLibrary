package com.example.homelibrary.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homelibrary.Data.AppDatabase;
import com.example.homelibrary.Data.Book;
import com.example.homelibrary.Data.User;
import com.example.homelibrary.R;

import java.util.ArrayList;
import java.util.List;

public class MarketRecyclerAdapter extends RecyclerView.Adapter<MarketRecyclerAdapter.MarketViewHolder> {

    private List<Book> Books = new ArrayList<>();
    private Context context;
    private AppDatabase database;
    private SharedPreferences preferences;

    private int bookCounter=0;

    public MarketRecyclerAdapter(Context context,AppDatabase db) {
        this.database = db;
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
        String uri = book.getCoverUrl();
        if (uri != null) {
            Glide.with(context).load(book.getCoverUrl()) // это URL обложки
                    .into(holder.Cover);
        } else {
            Log.i("Failure","Не загрузилась картинка");
            holder.Cover.setImageResource(R.drawable.image_test);//Если вдруг картинка не загрузится
        }
        holder.Name.setText(book.getTitle());//Установка названия

        holder.Add.setOnClickListener(v -> {//Обработка добавления книги в списко(БД)
            preferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);//Получаем данные пользователя
            User user = database.userDao().getUser(preferences.getInt("user_id",-1));
            book.setOwnerIds(user.getId());//Присваиваем книге владельца
            database.bookDAO().addBook(book);//Добавляем книгу
            user.setActive(user.getActive()+1);//Обновляем счетчик активных книг у пользователя
            database.userDao().updateUser(user);//Обновляем данные в БД
            Toast.makeText(context, "Книга добавлена", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

    }

    @Override
    public int getItemCount() {
        return Books.size();
    }

    public void setBooks(List<Book> books) {
        this.Books = books;
        notifyDataSetChanged();
    }

    public class MarketViewHolder extends RecyclerView.ViewHolder {

        TextView Name;
        Button Add;
        ImageView Cover;
        public MarketViewHolder(@NonNull View itemView) {
            super(itemView);
            Cover = itemView.findViewById(R.id.MarketCover);
            Name = itemView.findViewById(R.id.name);
            Add = itemView.findViewById(R.id.btnAdd);
        }
    }
}
