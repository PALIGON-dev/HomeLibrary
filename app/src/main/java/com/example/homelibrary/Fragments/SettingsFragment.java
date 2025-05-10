package com.example.homelibrary.Fragments;

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
import android.widget.TextView;

import com.example.homelibrary.Data.User;
import com.example.homelibrary.Data.UserDB;
import com.example.homelibrary.R;

public class SettingsFragment extends Fragment {

    TextView Name,Books,Level;
    ImageView Image;
    Button Ex,Reg;

    UserDB userDB;
    EditText EdName,EdPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Name = view.findViewById(R.id.UserName);
        Books = view.findViewById(R.id.UserBooksCount);
        Level = view.findViewById(R.id.UserLevel);
        Image = view.findViewById(R.id.UserImage);
        Ex = view.findViewById(R.id.ExitButton);

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

        userDB = Room.databaseBuilder(getActivity().getApplicationContext(), UserDB.class,"UsersData")
                .addCallback(myCallBack).build();


    }
}