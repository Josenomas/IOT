package com.example.stockbotiquin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText etId, etNombre, etCantidad, etFechaVencimiento, etMg, etDescripcion;
    Spinner spPresentacion;
    Button btnAgregar, btnVer, btnVerStock, btnBuscarRemedio; // Nuevo botón

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        // Inicialización de elementos
        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etCantidad = findViewById(R.id.etCantidad);
        etFechaVencimiento = findViewById(R.id.etFechaVencimiento);
        etMg = findViewById(R.id.etMg);
        spPresentacion = findViewById(R.id.spPresentacion);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnVer = findViewById(R.id.btnVer);
        btnVerStock = findViewById(R.id.btnVerStock);
        btnBuscarRemedio = findViewById(R.id.btnBuscarRemedio); // Nuevo botón

        // Configuración del Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.presentacion_opciones,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPresentacion.setAdapter(adapter);

        // Botón para agregar remedios
        btnAgregar.setOnClickListener(v -> agregarRemedio());

        // Botón para ver remedios
        btnVer.setOnClickListener(v -> abrirResultadosActivity());

        // Botón para ver el stock de remedios
        btnVerStock.setOnClickListener(v -> abrirStockActivity());

        // Botón para buscar remedios
        btnBuscarRemedio.setOnClickListener(v -> abrirBuscarRemedioActivity());
    }

    private void agregarRemedio() {
        String id = etId.getText().toString();
        String nombre = etNombre.getText().toString();
        String cantidadStr = etCantidad.getText().toString();
        String fechaVencimiento = etFechaVencimiento.getText().toString();
        String mgStr = etMg.getText().toString();
        String presentacion = spPresentacion.getSelectedItem().toString();
        String descripcion = etDescripcion.getText().toString();

        if (id.isEmpty() || nombre.isEmpty() || cantidadStr.isEmpty() || fechaVencimiento.isEmpty() || mgStr.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            int mg = Integer.parseInt(mgStr);

            if (db.insertarRemedio(id, nombre, cantidad, fechaVencimiento, mg, presentacion, descripcion)) {
                Toast.makeText(this, "Remedio agregado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al agregar remedio", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Cantidad y MG deben ser números válidos", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirResultadosActivity() {
        Intent intent = new Intent(MainActivity.this, ResultadosActivity.class);
        startActivity(intent);
    }

    private void abrirStockActivity() {
        Intent intent = new Intent(MainActivity.this, StockActivity.class);
        startActivity(intent);
    }

    private void abrirBuscarRemedioActivity() {
        Intent intent = new Intent(MainActivity.this, BuscarRemedioActivity.class);
        startActivity(intent);
    }

    private void limpiarCampos() {
        etId.setText("");
        etNombre.setText("");
        etCantidad.setText("");
        etFechaVencimiento.setText("");
        etMg.setText("");
        etDescripcion.setText("");
        spPresentacion.setSelection(0);
    }
}
