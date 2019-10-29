package com.uninove.lojadetecido.crud;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uninove.lojadetecido.R;
import com.uninove.lojadetecido.intent.Tecido;
import com.uninove.lojadetecido.utils.Message;

import java.util.ArrayList;

public class SearchTecido extends AppCompatActivity {
    //Declaração do campo de procura.
    TextView pesquisarTecido;

    //Declaração dos botões e procura por tipo e procura por cor.
    Button btPesquisarTecidoTipo, btPesquisaTecidoCor;

    //Declaração das classes necessárias para criar a lista.
    ListView listViewTecidos;
    ArrayList<Tecido> tecidos = new ArrayList<>();
    ArrayAdapter<Tecido> adaptador;

    //Dclaração do objeto que vai criar ao banco e executar as querys.
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tecido);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("Loja de Tecidos - Listagem");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Instanciando os botão de pesquisa.
        btPesquisarTecidoTipo = findViewById(R.id.btPesquisaTecidoTipo);
        btPesquisaTecidoCor = findViewById(R.id.btPesquisaTecidoCor);

        //Isntanciando campo de pasquisa.
        pesquisarTecido = findViewById(R.id.editPesquisaTecido);

        //Instânciando a ListView.
        listViewTecidos = findViewById(R.id.listViewTecidos);

        //Abrindo um banco de dados existem.
        db = openOrCreateDatabase("db_loja_de_tecido", Context.MODE_PRIVATE, null);

        //Instancianndo o campo ListView.
        listViewTecidos = findViewById(R.id.listViewTecidos);

        //Evento de click para o botão de pesquisa por tipo de tecido.
        btPesquisarTecidoTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Valores do campo da tabela e dado de pesquisa que sera enviado como paramêtro para o método de carregamento de listview.
            String campoTabela = "tipo";
            String dadoPesquisa = pesquisarTecido.getText().toString();

            carregarListViewTecido(listViewTecidos, db, campoTabela, dadoPesquisa);

            clearText();
            }


        });

        //Evento de click para o botão de pesquisa por cor do tecido.
        btPesquisaTecidoCor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Valores do campo da tabela e dado de pesquisa que sera enviado como paramêtro para o método de carregamento de listview.
            String campoTabela = "cor";
            String dadoPesquisa = pesquisarTecido.getText().toString();

            carregarListViewTecido(listViewTecidos, db, campoTabela, dadoPesquisa);

            clearText();
            }
        });

        //Criando evento de click no item.
        //Enviando o item objeto tecido para a activity_detail_tecido.
        listViewTecidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Tecido tecido = (Tecido) listViewTecidos.getItemAtPosition(pos);
                Intent itDetailsTecido = new Intent(getApplicationContext(), DetailsTecido.class);
                itDetailsTecido.putExtra("objTecido", tecido);
                //Enviando o nome da activity quero voltar quando for excluir ou editar algum registro da tabela.
                itDetailsTecido.putExtra("classActivityReturn", "activity_search_tecido");
                startActivity(itDetailsTecido);
            }
        });
    }

    //Método que carrega a ListView.
    public ListView carregarListViewTecido(ListView lv, SQLiteDatabase db, String campoTabela, String dadoPesquisa) {
        //Antes de carregar a lista de tecidos limpo para carrega como novos objetos se existerem.
        tecidos.clear();

        //Carregando os resgistros em ordem alfabética na ArrayList e inserindo a List no adaptador.
        //Curso cria a query para obter os registros do banco de dados.
        Cursor cursor = db.rawQuery("SELECT * FROM tecido WHERE " +campoTabela+ " = '" + dadoPesquisa+ "' ORDER BY tipo ASC", null);

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

    //Método para limpar os campos do formulário e fechar o teclado.
    public void clearText() {
        pesquisarTecido.setText("");

        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
