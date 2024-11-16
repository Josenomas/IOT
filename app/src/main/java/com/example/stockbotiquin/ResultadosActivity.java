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

public class ResultadosActivity extends AppCompatActivity {

    DatabaseHelper db;
    RecyclerView recyclerView;
    RemedioAdapter remedioAdapter;
    List<Remedio> listaRemedios;

    EditText etId, etNombre, etCantidad, etFechaVencimiento, etMg, etPresentacion, etDescripcion;
    Button btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etCantidad = findViewById(R.id.etCantidad);
        etFechaVencimiento = findViewById(R.id.etFechaVencimiento);
        etMg = findViewById(R.id.etMg);
        etPresentacion = findViewById(R.id.etPresentacion);
        etDescripcion = findViewById(R.id.etDescripcion);

        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // Cargar datos en la lista y configurar el adaptador
        listaRemedios = obtenerRemedios();
        remedioAdapter = new RemedioAdapter(listaRemedios, remedio -> cargarDatosEnCampos(remedio));
        recyclerView.setAdapter(remedioAdapter);

        // Configurar acciones de los botones
        btnActualizar.setOnClickListener(v -> actualizarRemedio());
        btnEliminar.setOnClickListener(v -> eliminarRemedio());
    }

    private List<Remedio> obtenerRemedios() {
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

    private void cargarDatosEnCampos(Remedio remedio) {
        etId.setText(remedio.getId());
        etNombre.setText(remedio.getNombre());
        etCantidad.setText(String.valueOf(remedio.getCantidad()));
        etFechaVencimiento.setText(remedio.getFechaVencimiento());
        etMg.setText(String.valueOf(remedio.getMg()));
        etPresentacion.setText(remedio.getPresentacion());
        etDescripcion.setText(remedio.getDescripcion());
    }

    private void actualizarRemedio() {
        String id = etId.getText().toString();
        String nombre = etNombre.getText().toString();
        String cantidadStr = etCantidad.getText().toString();
        String fechaVencimiento = etFechaVencimiento.getText().toString();
        String mgStr = etMg.getText().toString();
        String presentacion = etPresentacion.getText().toString();
        String descripcion = etDescripcion.getText().toString();

        if (id.isEmpty() || nombre.isEmpty() || cantidadStr.isEmpty() || fechaVencimiento.isEmpty() || mgStr.isEmpty() || presentacion.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            int mg = Integer.parseInt(mgStr);

            if (db.actualizarRemedio(id, nombre, cantidad, fechaVencimiento, mg, presentacion, descripcion)) {
                Toast.makeText(this, "Remedio actualizado", Toast.LENGTH_SHORT).show();
                actualizarLista();
            } else {
                Toast.makeText(this, "Error al actualizar remedio", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Cantidad y MG deben ser números válidos", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarRemedio() {
        String id = etId.getText().toString();
        if (id.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el ID del remedio", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.eliminarRemedio(id) > 0) {
            Toast.makeText(this, "Remedio eliminado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            actualizarLista();
        } else {
            Toast.makeText(this, "Error al eliminar remedio", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etId.setText("");
        etNombre.setText("");
        etCantidad.setText("");
        etFechaVencimiento.setText("");
        etMg.setText("");
        etPresentacion.setText("");
        etDescripcion.setText("");
    }

    private void actualizarLista() {
        listaRemedios.clear();
        listaRemedios.addAll(obtenerRemedios());
        remedioAdapter.notifyDataSetChanged();
    }
}
