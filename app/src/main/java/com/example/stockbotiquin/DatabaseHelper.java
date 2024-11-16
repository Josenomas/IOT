package com.example.stockbotiquin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StockBotiquin.db";
    public static final String TABLE_NAME = "remedios";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id TEXT PRIMARY KEY, nombre TEXT, cantidad INTEGER, fechaVencimiento TEXT, mg INTEGER, presentacion TEXT, descripcion TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertarRemedio(String id, String nombre, int cantidad, String fechaVencimiento, int mg, String presentacion, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("nombre", nombre);
        contentValues.put("cantidad", cantidad);
        contentValues.put("fechaVencimiento", fechaVencimiento);
        contentValues.put("mg", mg);
        contentValues.put("presentacion", presentacion);
        contentValues.put("descripcion", descripcion);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor obtenerRemedios() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean actualizarRemedio(String id, String nombre, int cantidad, String fechaVencimiento, int mg, String presentacion, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);
        contentValues.put("cantidad", cantidad);
        contentValues.put("fechaVencimiento", fechaVencimiento);
        contentValues.put("mg", mg);
        contentValues.put("presentacion", presentacion);
        contentValues.put("descripcion", descripcion);
        int result = db.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});
        return result > 0;
    }

    public int eliminarRemedio(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?", new String[]{id});
    }

    // Método para buscar remedios por término
    public Cursor buscarRemedios(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM remedios WHERE nombre LIKE ?";
        return db.rawQuery(query, new String[]{"%" + nombre + "%"});
    }


}

