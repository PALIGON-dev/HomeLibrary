package com.example.homelibrary.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.homelibrary.Data.AppDatabase;
import com.example.homelibrary.Data.User;
import com.example.homelibrary.R;

public class EntryActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText editTextEmail, editTextPassword;
    AppDatabase appDatabase;

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

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "HomeLibraryDB")
                .allowMainThreadQueries()
                .build();

        btnLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            if (appDatabase.userDao().validate(email, password) != null) {
                // Успешный вход, пользователь с такой почтой и паролем нашелся
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Неверный email или пароль", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            User newUser = new User(email, password);
            appDatabase.userDao().addUser(newUser);
            Toast.makeText(EntryActivity.this, "Аккаунт успешно зарегистрирован", Toast.LENGTH_SHORT).show();
        });
    }
}
