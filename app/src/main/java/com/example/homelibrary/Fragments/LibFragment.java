package com.example.homelibrary.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homelibrary.Adapters.LibRecyclerAdapter;
import com.example.homelibrary.Data.AppDatabase;
import com.example.homelibrary.Data.Book;
import com.example.homelibrary.R;

import java.util.List;

public class LibFragment extends Fragment {
    RecyclerView Library;

    AppDatabase db;

    SharedPreferences preferences;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lib, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        Library = view.findViewById(R.id.LibList);
        Library.setLayoutManager(new GridLayoutManager(requireContext(),2));
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "HomeLibraryDB")
                .allowMainThreadQueries()
                .build();
        int userId = preferences.getInt("user_id", -1);
        List<Book> books = db.bookDAO().getBooksByOwner(userId);
        LibRecyclerAdapter adapter = new LibRecyclerAdapter(getContext(), db, preferences, this::onBookClick);
        adapter.setBooks(books);
        Library.setAdapter(adapter);
        ;

    }

    public void onBookClick(Book book) {
        BookFragment fragment = BookFragment.newInstance(book);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit();
    }
}