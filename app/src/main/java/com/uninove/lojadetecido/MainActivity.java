package com.uninove.lojadetecido;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uninove.lojadetecido.crud.InsertTecido;
import com.uninove.lojadetecido.crud.ListTecido;
import com.uninove.lojadetecido.crud.SearchTecido;

public class MainActivity extends AppCompatActivity {
    //Declaração dos Botões de inserção, listagem e pesquisa.
    Button btInserirTecido, btListarTecidos, btProcurarTecido, btFecharAPP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciando os botões.
        btInserirTecido = findViewById(R.id.btInserirTecido);
        btListarTecidos = findViewById(R.id.btListarTecidos);
        btProcurarTecido = findViewById(R.id.btPesquisarTecido);
        btFecharAPP = findViewById(R.id.btFecharApp);

        //Definindo o evento de clique no botão btInserirTecido.
        btInserirTecido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent para a activity_insert_tecido.
                Intent itInsertTecido = new Intent(getApplicationContext(), InsertTecido.class);
                startActivity(itInsertTecido);
            }
        });

        //Definindo o evento de clique no botão btListarTecidos.
        btListarTecidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent para a activity activity_list_tecido.
                Intent itListTecido = new Intent(getApplicationContext(), ListTecido.class);
                startActivity(itListTecido);
            }
        });

        //Definindo o evento de clique no botão btListarTecidos.
        btProcurarTecido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent para a activity activity_list_tecido.
                Intent itProcurarTecido = new Intent(getApplicationContext(), SearchTecido.class);
                startActivity(itProcurarTecido);
            }
        });

        //Definindo evneto de clique no botão para fechar o app.
        btFecharAPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fecha a activity atual e também todas as outras activities abaixo dela que estam na pilha.
                finishAffinity();
            }
        });


    }
}
