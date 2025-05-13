package com.example.homelibrary.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.homelibrary.Adapters.MarketRecyclerAdapter;
import com.example.homelibrary.Data.AppDatabase;
import com.example.homelibrary.Data.Book;
import com.example.homelibrary.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MarketFragment extends Fragment {

    RecyclerView SearchView;
    Button buttonSearch;
    EditText editTextSearch;
    AppDatabase db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        SearchView = view.findViewById(R.id.SearchView);
        SearchView.setLayoutManager(new LinearLayoutManager(requireContext()));


        db = Room.databaseBuilder(getContext(), AppDatabase.class, "HomeLibraryDB")
                .allowMainThreadQueries()
                .build();//Создание экземпляра БД
        MarketRecyclerAdapter adapter = new MarketRecyclerAdapter(requireContext(),db);//Создание адаптера
        SearchView.setAdapter(adapter);//Установка адаптера

        buttonSearch.setOnClickListener(v -> {
            String query = editTextSearch.getText().toString();//Запрос из поисковой строки
            fetchBooksFromGoogle(query, this.getContext(), adapter::setBooks);// Обновляем список
        });

    }




    public void fetchBooksFromGoogle(String query, Context context, Consumer<List<Book>> callback) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query;
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                List<Book> books = new ArrayList<>();
                if (response.isSuccessful()) {//Успешный запрос = есть результат
                    try {
                        JSONObject root = new JSONObject(response.body().string());
                        JSONArray items = root.getJSONArray("items");

                        for (int i = 0; i < items.length(); i++) {
                            JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
                            String title = volumeInfo.optString("title", "Название не найдено");
                            String author = volumeInfo.has("authors") ? volumeInfo.getJSONArray("authors").getString(0) : "Неизвестно";
                            String description = volumeInfo.optString("description", "Описание не найдено");
                            String cover = volumeInfo.has("imageLinks") ? volumeInfo.getJSONObject("imageLinks").optString("thumbnail", null) : null;
                            String publishedDate = volumeInfo.optString("publishedDate","Дата не найдена");

                            if (cover != null && cover.startsWith("http://"))
                                cover = cover.replace("http://", "https://");
                            //Замена запроса с http на https для загрузки картинок(особенности Google Books API)

                            books.add(new Book(title, author, description,publishedDate, cover));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Передаём результат в поток
                new Handler(Looper.getMainLooper()).post(() -> callback.accept(books));
            }
        });
    }

}