package com.example.homelibrary.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homelibrary.Adapters.LibRecyclerAdapter;
import com.example.homelibrary.Adapters.MarketRecyclerAdapter;
import com.example.homelibrary.Data.Book;
import com.example.homelibrary.R;

import java.util.Arrays;
import java.util.List;

public class MarketFragment extends Fragment {

    RecyclerView Market;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Market = view.findViewById(R.id.MarketList);
        Market.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        Book Test = new Book("Big Dildo","Me","Cool Book","03.05.2025",5.0,R.drawable.image_test);
        List<Book> books = Arrays.asList(Test,Test,Test,Test,Test,Test,Test,Test,Test,Test,Test,Test,Test);
        MarketRecyclerAdapter adapter = new MarketRecyclerAdapter(getContext(),books);
        Market.setAdapter(adapter);
    }
}