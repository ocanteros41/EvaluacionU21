package com.ocanteros.evaluacionu21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditarMapa extends AppCompatActivity implements View.OnClickListener{

    private Button btnEditar;
    private Button btnEliminar;
    private Button btnVerEnMapa;

    private TextView tbxDatos;
    private TextView tbxLugar;

    private DataBaseConnection conn;


    private String datoObtendio2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mapa);

        btnEditar = (Button) findViewById(R.id.btnEditar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnVerEnMapa = (Button) findViewById(R.id.btnVerEnMapa);

        btnEditar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnVerEnMapa.setOnClickListener(this);

        tbxDatos = (TextView) findViewById(R.id.tbxDato);
        tbxLugar = (TextView) findViewById(R.id.tbxLugar);

        tbxLugar.setEnabled(false);

        Bundle datos =  getIntent().getExtras();

        String datoObtendio1 = datos.getString("datos");

        tbxDatos.setText(datoObtendio1);

        Bundle lugar = getIntent().getExtras();
        datoObtendio2 = lugar.getString("lugar");

        tbxLugar.setText(datoObtendio2);

        conn =  new DataBaseConnection(getApplicationContext(), "db_mapa",null, 1 );

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnEditar){

            SQLiteDatabase database  = conn.getWritableDatabase();

            try {

                database.execSQL("UPDATE mapa SET datos = '"+ tbxDatos.getText().toString() +"' WHERE lugar = '"+ tbxLugar.getText().toString() +"'");
                database.close();

                Toast.makeText(getApplicationContext(), "Datos Actualizados", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
            }

        }

        if(v.getId() == R.id.btnEliminar){

            try {
                SQLiteDatabase database  = conn.getWritableDatabase();

                String lugar = tbxLugar.getText().toString();

                database.execSQL("DELETE FROM mapa WHERE lugar = '"+ lugar + "'");
                database.close();

                tbxLugar.setText("");
                tbxDatos.setText("");

                Toast.makeText(getApplicationContext(), "Lugar eliminado", Toast.LENGTH_SHORT).show();

            }catch (Exception e){

                Toast.makeText(getApplicationContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();

            }

        }

        if(v.getId() == R.id.btnVerEnMapa){


            String lugar =  tbxLugar.getText().toString();
            String dato =  tbxDatos.getText().toString();

            Intent mapsIntent = new Intent(EditarMapa.this, MapsActivity.class);

            mapsIntent.putExtra("datos", dato.toString());
            mapsIntent.putExtra("lugar", lugar.toString());

            startActivity(mapsIntent);

        }

    }

}