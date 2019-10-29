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

public class EditRecordTecido extends AppCompatActivity {
    //Dclaração do objeto que vai criar ao banco e executar as querys.
    SQLiteDatabase db;

    //Declaração do botão para atualizar os dados do tecido.
    Button btAtualizarTecido;

    //Declaração dos campos de dados do tecido
    TextView idTecido, tipoTecido, corTecido, metrageTecido, valorTecido;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tecido);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("Loja de Tecidos - Edição");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Instanciando os campos.
        idTecido = findViewById(R.id.textViewIdTecido);
        tipoTecido = findViewById(R.id.textViewTipoTecido);
        corTecido = findViewById(R.id.textViewCorTecido);
        metrageTecido = findViewById(R.id.textViewMetragemTecido);
        valorTecido = findViewById(R.id.textViewValorTecido);

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

        //Instanciando o botão btSalvarTecido.
        btAtualizarTecido = findViewById(R.id.btAtualizarTecido);

        btAtualizarTecido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Tecido novoTecido = new Tecido();

            //Guardando os novos valores vindo dos cammpos da view no objeto tecido.
            novoTecido.setId(Integer.parseInt(idTecido.getText().toString()));
            novoTecido.setTipo(tipoTecido.getText().toString());
            novoTecido.setCor(corTecido.getText().toString());
            novoTecido.setMetragem(Float.parseFloat(String.valueOf(metrageTecido.getText())));
            novoTecido.setValor(Float.parseFloat(String.valueOf(valorTecido.getText())));

            //Abertura do Banco de dados.
            db = openOrCreateDatabase("db_loja_de_tecido", Context.MODE_PRIVATE, null);
            //Executando query para atulizar o registro na tabela tecido.
            db.execSQL("UPDATE tecido SET " +
                    "tipo='" + novoTecido.getTipo() + "'," +
                    "cor='" + novoTecido.getCor() + "'," +
                    "metragem='" + novoTecido.getMetragem() + "'," +
                    "valor='" + novoTecido.getValor() + "' " +
                    "WHERE id=" + tecido.getId()
            );

            //Cria uma caixa de mensagem e mostra os dados incluídos.
            Message mensagem = new Message(EditRecordTecido.this);

            mensagem.show(
                    "Dados atualizados com sucesso!!!",
                    tecido.getDados(),
                    R.drawable.ic_add
            );

            //Volta pra a activity principal.
            Intent itListarTecido = new Intent(getApplicationContext(), ListTecido.class);
            //Limpando todas as activity do topo para que o botão de retorno siga para a old activity seguinte e não fique loop voltando para a activity de edição.
            itListarTecido.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(itListarTecido);
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
