package com.example.appfilt;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase";
    private static final int DATABASE_VERSION = 8;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_EPELLIDO = "apellido";
    private static final String COLUMN_FECHA_NACIMIENTO = "fechaNacimiento";
    private static final String COLUMN_DIRECCION = "direccion";
    private static final String COLUMN_TELEFONO = "telefeno";



    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT," +
            COLUMN_EPELLIDO + " TEXT," +
            COLUMN_FECHA_NACIMIENTO + " TEXT," +
            COLUMN_DIRECCION + " TEXT," + // Agrega la nueva columna aqui
            COLUMN_TELEFONO + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_DIRECCION + " TEXT");
        }
    }
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, user.getNombre());
        values.put(COLUMN_EPELLIDO, user.getApellido());
        values.put(COLUMN_FECHA_NACIMIENTO, user.getFechaNacimiento());
        values.put(COLUMN_DIRECCION, user.getFechaNacimiento());
        values.put(COLUMN_TELEFONO, user.getFechaNacimiento());


        long id = db.insert(TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE));
                    @SuppressLint("Range") String apellido = cursor.getString(cursor.getColumnIndex(COLUMN_EPELLIDO));
                    @SuppressLint("Range")String fechaNacimiento = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_NACIMIENTO));
                    @SuppressLint("Range") String direccion = cursor.getString(cursor.getColumnIndex(COLUMN_DIRECCION));
                    @SuppressLint("Range")  String telefono = cursor.getString(cursor.getColumnIndex(COLUMN_TELEFONO));
                    User user = new User(id, nombre, apellido, fechaNacimiento, direccion, telefono);
                    userList.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            // Manejar el error
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }

        return userList;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, user.getNombre());
        values.put(COLUMN_EPELLIDO, user.getApellido());
        values.put(COLUMN_FECHA_NACIMIENTO, user.getFechaNacimiento());
        values.put(COLUMN_DIRECCION, user.getDireccion());
        values.put(COLUMN_TELEFONO, user.getTelefono());



        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});

        db.close();
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});

        db.close();
    }
}