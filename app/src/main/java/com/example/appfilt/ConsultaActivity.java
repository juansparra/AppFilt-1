package com.example.appfilt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ConsultaActivity  extends AppCompatActivity {
    private Button buttonSubmit;
    private ListView listViewData;
    private List<User> userList;

    private MainActivity.UserListAdapter userListAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewData = findViewById(R.id.list_view_cart);

        // Crear instancia de la base de datos o utilizar una existente
        DatabaseHelper databaseHelper = new DatabaseHelper(ConsultaActivity.this);

        // Obtener la lista de productos desde la base de datos
        userList = databaseHelper.getAllUsers();

        // Crear el adaptador para la lista de productos
        userListAdapter = new MainActivity.UserListAdapter(this, userList);

        // Establecer el adaptador en el ListView
        listViewData.setAdapter(userListAdapter);
    }
}