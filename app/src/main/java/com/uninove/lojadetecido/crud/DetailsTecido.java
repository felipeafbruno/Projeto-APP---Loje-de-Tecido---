package com.uninove.lojadetecido.crud;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uninove.lojadetecido.R;
import com.uninove.lojadetecido.intent.Tecido;
import com.uninove.lojadetecido.utils.Message;

public class DetailsTecido extends AppCompatActivity {
    //Declaração dos campos de dados do tecido
    TextView idTecido, tipoTecido, corTecido, metrageTecido, valorTecido;

    //Declaração do botão de edição de registro e exclusão.
    Button btEditarTecida, btExcluirTecido;

    //Dclaração do objeto que vai criar ao banco e executar as querys.
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tecido);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("Loja de Tecidos - Detalhes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Abrindo um banco de dados existem.
        db = openOrCreateDatabase("db_loja_de_tecido", Context.MODE_PRIVATE, null);

        //Instanciando os campos.
        idTecido = findViewById(R.id.textViewIdTecido);
        tipoTecido = findViewById(R.id.textViewTipoTecido);
        corTecido = findViewById(R.id.textViewCorTecido);
        metrageTecido = findViewById(R.id.textViewMetragemTecido);
        valorTecido = findViewById(R.id.textViewValorTecido);

        //Intanciando o botão de editar.
        btEditarTecida = findViewById(R.id.btEditarTecido);

        //Intanciando o botão de excluir.
        btExcluirTecido = findViewById(R.id.btExcluirTecido);

        //Recuperando a Intent enviada pela classe ListTecido
        final Intent itDetailsTecido = getIntent();

        //Recuperando o objeto tecido na intent.
        final Tecido tecido = (Tecido) itDetailsTecido.getExtras().getSerializable("objTecido");

        //Inserindo valores do tecido nos campos.
        idTecido.setText(String.valueOf(tecido.getId()));
        tipoTecido.setText(tecido.getTipo());
        corTecido.setText(tecido.getCor());
        metrageTecido.setText(String.valueOf(tecido.getMetragem()));
        valorTecido.setText(String.valueOf(tecido.getValor()));

        btEditarTecida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itEditarTecido = new Intent(getApplicationContext(), EditRecordTecido.class);
                itEditarTecido.putExtra("objTecido", tecido);
                startActivity(itEditarTecido);
            }
        });

        btExcluirTecido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Query para deletar um registro da tabela tecido.
                db.execSQL("DELETE FROM tecido WHERE id = " + Integer.parseInt(idTecido.getText().toString()));

                //Cria uma caixa de mensagem e mostra os dados incluídos.
                Message mensagem = new Message(DetailsTecido.this);

                mensagem.show(
                        "Dados excluídos com sucesso!!!",
                        tecido.getDados(),
                        R.drawable.ic_add
                );

                if(itDetailsTecido.getExtras().get("classListTecido") == ListTecido.class){
                    //Voltando para intent anterior activity_list_tecido.
                    Intent it = new Intent(DetailsTecido.this, ListTecido.class);
                    //Limpando todas as activity do topo para que o botão de retorno siga para a old activity seguinte e não fique loop voltando para a activity de edição.
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(it);
                }

                if(itDetailsTecido.getExtras().get("classSearchTecido") == SearchTecido.class){
                    //Voltando para intent anterior activity_list_tecido.
                    //Limpando todas as activity do topo para que o botão de retorno siga para a old activity seguinte e não fique loop voltando para a activity de edição.
                    Intent it = new Intent(DetailsTecido.this, SearchTecido.class);
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(it);
                }
            }
        });
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
