package com.example.stockbotiquin;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BuscarRemedioActivity extends AppCompatActivity {

    EditText etBuscarNombre;
    Button btnBuscar;
    RecyclerView recyclerViewResultadosBusqueda;
    RemedioAdapter remedioAdapter;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_remedio);

        // Vincular vistas
        etBuscarNombre = findViewById(R.id.etBuscarNombre);
        btnBuscar = findViewById(R.id.btnBuscar);
        recyclerViewResultadosBusqueda = findViewById(R.id.recyclerViewResultadosBusqueda);

        // Configuración de RecyclerView
        db = new DatabaseHelper(this);
        recyclerViewResultadosBusqueda.setLayoutManager(new LinearLayoutManager(this));

        // Configuración del botón Buscar
        btnBuscar.setOnClickListener(v -> {
            String nombre = etBuscarNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese un nombre para buscar", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamar al método para buscar remedios
            List<Remedio> resultados = buscarRemediosPorNombre(nombre);

            if (resultados.isEmpty()) {
                Toast.makeText(this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
            } else {
                // Configurar el adaptador con los resultados
                remedioAdapter = new RemedioAdapter(resultados, null);
                recyclerViewResultadosBusqueda.setAdapter(remedioAdapter);
            }
        });
    }

    // Método para buscar remedios por nombre
    private List<Remedio> buscarRemediosPorNombre(String nombre) {
        List<Remedio> remedios = new ArrayList<>();
        Cursor cursor = db.buscarRemedios(nombre);
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
