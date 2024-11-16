package com.example.stockbotiquin;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StockActivity extends AppCompatActivity {

    DatabaseHelper db;
    RecyclerView recyclerViewStock;
    RemedioAdapter stockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        db = new DatabaseHelper(this);
        recyclerViewStock = findViewById(R.id.recyclerViewStock);
        recyclerViewStock.setLayoutManager(new LinearLayoutManager(this));

        stockAdapter = new RemedioAdapter(obtenerTodosLosRemedios(), null);
        recyclerViewStock.setAdapter(stockAdapter);
    }

    private List<Remedio> obtenerTodosLosRemedios() {
        List<Remedio> remedios = new ArrayList<>();
        Cursor cursor = db.obtenerRemedios();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                remedios.add(new Remedio(
                        cursor.getString(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre")),
                        cursor.getInt(cursor.getColumnIndex("cantidad")),
                        cursor.getString(cursor.getColumnIndex("fechaVencimiento")),
                        cursor.getInt(cursor.getColumnIndex("mg")),
                        cursor.getString(cursor.getColumnIndex("presentacion")),
                        cursor.getString(cursor.getColumnIndex("descripcion"))
                ));
            }
            cursor.close();
        }
        return remedios;
    }
}
