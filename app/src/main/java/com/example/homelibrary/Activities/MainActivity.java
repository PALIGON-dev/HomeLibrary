package com.example.homelibrary.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.homelibrary.Data.local.AppDatabase;
import com.example.homelibrary.Data.Models.User;
import com.example.homelibrary.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private Fragment LibraryFragment;
    private Fragment MarketFragment;
    private Fragment SettingsFragment;

    SharedPreferences preferences;

    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "HomeLibraryDB")
                .allowMainThreadQueries()
                .build();

        preferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("logged_in",false);//Входил ли пользователь
        User user = appDatabase.userDao().getUser(preferences.getInt("user_id",-1));

        if (!isLoggedIn | user == null) {
            // Перейти на страницу авторизации если пользователь не авторизован
            startActivity(new Intent(this, EntryActivity.class));
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.book); //Выделяет "Подборки" при старте
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, com.example.homelibrary.Fragments.MarketFragment.class, null)
                .commit();
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.home){// Обработка выбора раздела "Библиотека"
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainerView, com.example.homelibrary.Fragments.LibFragment.class, null)
                                    .commit();
                            return true;
                        } else if (item.getItemId() == R.id.book) {// Обработка выбора раздела "Подборки"
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainerView, com.example.homelibrary.Fragments.MarketFragment.class, null)
                                    .commit();
                            return true;
                        } else if (item.getItemId() == R.id.settings) {// Обработка выбора раздела "Настройки"
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainerView, com.example.homelibrary.Fragments.SettingsFragment.class, null)
                                    .commit();
                            return true;
                        }
                        return false;
                    }
                });
        }
}