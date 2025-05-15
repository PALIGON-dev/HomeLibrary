package com.example.homelibrary.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.homelibrary.Data.AppDatabase;
import com.example.homelibrary.Data.Book;
import com.example.homelibrary.Data.User;
import com.example.homelibrary.R;

public class BookFragment extends Fragment {

    private SharedPreferences preferences;

    private AppDatabase db;

    public static BookFragment newInstance(Book book) {//Метод для сохранения нажатой книги чтобы правильно вызвать фрагмент
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putInt("book_id", book.getBookId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {//Получаем данные о книги из Bundle хранилища
            int bookId = getArguments().getInt("book_id");

            db = Room.databaseBuilder(requireContext(), AppDatabase.class, "HomeLibraryDB")
                    .allowMainThreadQueries()
                    .build();

            Book book = db.bookDAO().getBookById(bookId);//Получаем книгу из БД
            ImageView imageCover = view.findViewById(R.id.imageCover);//Установка значений из полученной книги
            TextView textTitle = view.findViewById(R.id.textTitle);
            TextView textAuthor = view.findViewById(R.id.textAuthor);
            TextView textDescription = view.findViewById(R.id.textDescription);
            TextView textDate = view.findViewById(R.id.textDate);
            ImageButton btnBack = view.findViewById(R.id.btnBack);
            ImageButton btnDelete = view.findViewById(R.id.btnDelete);
            ImageButton btnArchive = view.findViewById(R.id.btnArchive);
            Glide.with(requireContext()).load(book.getCoverUrl()) // это URL обложки
                    .into(imageCover);
            textTitle.setText(book.getTitle());
            textAuthor.setText("Автор:"+book.getAuthors());
            textDescription.setText("Описание:"+book.getDescription());
            textDate.setText("Дата публикации:"+book.getPublishedDate());

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//Возрат к библиотеке по нажатию назад
                    LibFragment fragment = new LibFragment();
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bookId = getArguments().getInt("book_id");
                    Book book = db.bookDAO().getBookById(bookId);//Получаем книгу из БД
                    db.bookDAO().deleteBook(book);
                    preferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    User user = db.userDao().getUser(preferences.getInt("user_id",-1));
                    user.setActive(user.getActive()-1);//Уменьшаем значение активных книг
                    db.userDao().updateUser(user);//Сохраняем значени в БД
                    Toast.makeText(getActivity(), "Книга удалена", Toast.LENGTH_SHORT).show();
                    LibFragment fragment = new LibFragment();//Возрат к библиотеке после удаления элемента
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

            btnArchive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bookId = getArguments().getInt("book_id");
                    Book book = db.bookDAO().getBookById(bookId);//Получаем книгу из БД
                    db.bookDAO().deleteBook(book);
                    User user = db.userDao().getUser(preferences.getInt("user_id",-1));
                    user.setArchive(user.getArchive()+1);//Увеличиваем значение прочтенных книг
                    db.userDao().updateUser(user);//Сохраняем значени в БД
                    Toast.makeText(getActivity(), "Поздравляем с прочтением!", Toast.LENGTH_SHORT).show();
                    LibFragment fragment = new LibFragment();//Возрат к библиотеке после удаления элемента
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainerView, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

    }
}