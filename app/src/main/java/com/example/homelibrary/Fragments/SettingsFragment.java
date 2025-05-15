package com.example.homelibrary.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.homelibrary.Activities.EntryActivity;
import com.example.homelibrary.Data.AppDatabase;
import com.example.homelibrary.Data.Book;
import com.example.homelibrary.Data.User;
import com.example.homelibrary.R;

public class SettingsFragment extends Fragment {

    TextView Books,Level,Email;
    ImageView Image;
    Button Ex;

    AppDatabase appDatabase;

    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Books = view.findViewById(R.id.UserBooksCount);
        Level = view.findViewById(R.id.UserLevel);
        Image = view.findViewById(R.id.UserImage);
        Ex = view.findViewById(R.id.ExitButton);
        Email = view.findViewById(R.id.UserEmail);

        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,"HomeLibraryDB")
                .addCallback(myCallBack).allowMainThreadQueries()
                .build();


        preferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        User user = appDatabase.userDao().getUser(preferences.getInt("user_id",-1));//Получение пользователя по id
        int BooksRead = user.getActive()+user.getArchive();
        Books.setText("Кол-во книг:"+String.valueOf(BooksRead));
        Email.setText("Почта:"+user.getEmail());
        if (BooksRead>=0 && BooksRead<=5){
            Level.setText("Ваш уровень:Юный читатель");
        }
        else if (BooksRead>=6 && BooksRead <=10){
            Level.setText("Ваш уровень:Книголюб");
        }
        else if (BooksRead>=11 && BooksRead <=15){
            Level.setText("Ваш уровень:Книжный червь");
        }

        Ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                // Перенаправляем на экран входа
                startActivity(new Intent(getActivity(), EntryActivity.class));
            }
        });
    }
}