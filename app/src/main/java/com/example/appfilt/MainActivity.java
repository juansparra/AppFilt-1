package com.example.appfilt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextFechaNacimiento;

    private EditText editTextDireccion;

    private EditText editTextTelefono;

    private Button buttonSubmit;
    private ListView listViewData;
    private List<User> userList;
    private UserListAdapter userListAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNombre = findViewById(R.id.edittext_nombre);
        editTextApellido = findViewById(R.id.edittext_apellido);
        editTextFechaNacimiento = findViewById(R.id.edittext_fechaNacimiento);
        editTextDireccion = findViewById(R.id.edittext_direccion);
        editTextTelefono = findViewById(R.id.edittext_telefono);


        buttonSubmit = findViewById(R.id.button_submit);
        listViewData = findViewById(R.id.listview_data);

        userList = new ArrayList<>();
        userListAdapter = new UserListAdapter(this, userList);
        listViewData.setAdapter(userListAdapter);

        databaseHelper = new DatabaseHelper(this);

        loadData();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editTextNombre.getText().toString();
                String apellido = editTextApellido.getText().toString();
                String fechaNacimiento = editTextFechaNacimiento.getText().toString();
                String direccion = editTextDireccion.getText().toString();
                String telefono = editTextTelefono.getText().toString();


                if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(fechaNacimiento)){
                    Toast.makeText(MainActivity.this, "Por favor llena todos los espacios", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User(0, nombre, apellido, fechaNacimiento,direccion,telefono);

                long id = databaseHelper.addUser(user);

                if (id != -1) {
                    user.setId((int) id);
                    userList.add(user);
                    userListAdapter.notifyDataSetChanged();

                    editTextNombre.setText("");
                    editTextApellido.setText("");
                    editTextFechaNacimiento.setText("");
                    editTextDireccion.setText("");
                    editTextTelefono.setText("");



                    Toast.makeText(MainActivity.this, "Usuario añadido con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al añadir el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadData() {
        userList.clear();
        userList.addAll(databaseHelper.getAllUsers());
        userListAdapter.notifyDataSetChanged();
    }

    private class UserListAdapter extends BaseAdapter {
        private Context context;
        private List<User> userList;

        public UserListAdapter(Context context, List<User> userList) {
            this.context = context;
            this.userList = userList;
        }

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public Object getItem(int position) {
            return userList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return userList.get(position).getId();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item_user, null);
            }
            TextView textViewNombre = view.findViewById(R.id.textview_nombre);
            TextView textViewApellido = view.findViewById(R.id.textview_apellido);
            TextView textViewFechaNacimiento = view.findViewById(R.id.textview_fechaNacimiento);
            TextView textViewDireccion = view.findViewById(R.id.textview_direccion);
            TextView textViewTelefono = view.findViewById(R.id.textview_telefono);



            Button buttonDelete = view.findViewById(R.id.button_delete);
            Button buttonUpdate = view.findViewById(R.id.button_update);

            final User user = userList.get(position);

            textViewNombre.setText(user.getNombre());
            textViewApellido.setText(user.getApellido());
            textViewFechaNacimiento.setText(user.getFechaNacimiento());
            textViewDireccion.setText(user.getDireccion());
            textViewTelefono.setText(user.getTelefono());


            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("¿Estás seguro de que quieres eliminar esto?");

                    builder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseHelper.deleteUser(user);
                            userList.remove(user);
                            userListAdapter.notifyDataSetChanged();
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            });

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Usuario actualizar");

                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    final EditText editTextNombre = new EditText(context);
                    editTextNombre.setText(user.getNombre());
                    linearLayout.addView(editTextNombre);

                    final EditText editTextApellido = new EditText(context);
                    editTextApellido.setText(user.getApellido());
                    linearLayout.addView(editTextApellido);

                    final EditText editTextFechaNacimiento = new EditText(context);
                    editTextFechaNacimiento.setText(user.getFechaNacimiento());
                    linearLayout.addView(editTextFechaNacimiento);

                    final EditText editTextDireccion = new EditText(context);
                    editTextDireccion.setText(user.getDireccion());
                    linearLayout.addView(editTextDireccion);

                    final EditText editTextTelefono = new EditText(context);
                    editTextTelefono.setText(user.getTelefono());
                    linearLayout.addView(editTextTelefono);





                    builder.setView(linearLayout);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String nombre = editTextNombre.getText().toString();
                            String apellido = editTextApellido.getText().toString();
                            String fechaNacimiento = editTextFechaNacimiento.getText().toString();
                            String direccion = editTextDireccion.getText().toString();
                            String telefono = editTextTelefono.getText().toString();


                            if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(fechaNacimiento) || TextUtils.isEmpty(direccion) || TextUtils.isEmpty(telefono)) {
                                Toast.makeText(MainActivity.this, "Por favor llena todos los espacios", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            user.setNombre(nombre);
                            user.setApellido(apellido);
                            user.setFechaNacimiento(fechaNacimiento);
                            user.setDireccion(direccion);
                            user.setTelefono(telefono);



                            databaseHelper.updateUser(user);
                            userListAdapter.notifyDataSetChanged();
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

            return view;
        }
    }
}