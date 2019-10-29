package com.uninove.lojadetecido.crud;

import android.content.Context;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uninove.lojadetecido.R;
import com.uninove.lojadetecido.intent.Tecido;

import java.util.ArrayList;

public class ListTecido extends AppCompatActivity {
    //Declaração das classes necessárias para criar a lista.
    ListView listViewTecidos;
    ArrayList<Tecido> tecidos = new ArrayList<>();
    ArrayAdapter<Tecido> adaptador;

    //Dclaração do objeto que vai criar ao banco e executar as querys.
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tecido);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("Loja de Tecidos - Listagem");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Abrindo um banco de dados existem.
        db = openOrCreateDatabase("db_loja_de_tecido", Context.MODE_PRIVATE, null);

        //Instânciando a ListView.
        listViewTecidos = findViewById(R.id.listViewTecidos);

        //Carregando ListView.
        carregarListViewTecido(listViewTecidos, db);

        //Criando evento de click no item.
        //Enviando o item objeto tecido para a activity_detail_tecido.
        listViewTecidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            Tecido tecido = (Tecido) listViewTecidos.getItemAtPosition(pos);
            Intent itDetailsTecido = new Intent(getApplicationContext(), DetailsTecido.class);
            itDetailsTecido.putExtra("objTecido", tecido);
            itDetailsTecido.putExtra("classListTecido", ListTecido.class);
            startActivity(itDetailsTecido);
            }
        });
    }

    //Método que carrega a ListView.
    public ListView carregarListViewTecido(ListView lv, SQLiteDatabase db) {
        //Antes de carregar a lista de tecidos limpo para carrega como novos objetos se existerem.
        tecidos.clear();

        //Carregando os resgistros em ordem alfabética na ArrayList e inserindo a List no adaptador.
        //Curso cria a query para obter os registros do banco de dados.
        Cursor cursor = db.rawQuery("SELECT * FROM tecido ORDER BY tipo ASC", null);

        //Populando a ArrayList de Tecido.
        while(cursor.moveToNext()) {
            tecidos.add(new Tecido(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getFloat(3),
                cursor.getFloat(4)
            ));
        }

        //Configura o adaptador
        adaptador = new ArrayAdapter<>(
            getApplicationContext(),
            android.R.layout.simple_list_item_1,
            tecidos
        );


        //Anexa o adaptador à ListView
        listViewTecidos.setAdapter(adaptador);

        return listViewTecidos;
    }

    // Configura o botão (seta) na ActionBar (Barra Superior)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
