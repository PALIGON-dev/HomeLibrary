package com.example.homelibrary.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.homelibrary.Data.User;
import com.example.homelibrary.Data.UserDB;
import com.example.homelibrary.R;

public class EntryActivity extends AppCompatActivity {

    Button btnLogin,btnRegister;
    EditText editTextEmail,editTextPassword;
    UserDB userDB;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_entry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

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

        userDB = Room.databaseBuilder(getApplicationContext(), UserDB.class,"UsersData")
                .addCallback(myCallBack).allowMainThreadQueries().build();

        btnLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            if (userDB.getUserDAO().validate(email, password) != null) {//Успешный вход, пользователь с таким именем и паролем нашелся
                    startActivity(new Intent(this, MainActivity.class));//Переход в основную активность
                    finish();
                } else {
                    Toast.makeText(this, "Неверный email или пароль", Toast.LENGTH_SHORT).show();
                }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Регистрация пользователя с добавлением в БД
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                User newUser = new User(email,password);
                userDB.getUserDAO().addUser(newUser);
                Toast.makeText(EntryActivity.this, "Аккаунт успешно зарегестрирован", Toast.LENGTH_SHORT).show();
            }
        });

    }
}