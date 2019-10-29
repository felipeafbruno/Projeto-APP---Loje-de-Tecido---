package com.uninove.lojadetecido.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uninove.lojadetecido.R;
import com.uninove.lojadetecido.intent.Tecido;
import com.uninove.lojadetecido.utils.Message;

public class InsertTecido extends AppCompatActivity {
    //Dclaração do objeto que vai criar ao banco e executar as querys.
    SQLiteDatabase db;

    //Declaração do botão para salvar os dados do tecido.
    Button btSalvarTecido;

    //Declaração dos campos de dados do tecido
    TextView tipoTecido, corTecido, metrageTecido, valorTecido;

    //Declaração das varáveis que iram receber os dados extraídos dos campos.
    String tipo, cor;
    float metragem, valor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_tecido);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("Loja de Tecidos - Inserção");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Abertura ou Criação do Banco de Dados.
        db = openOrCreateDatabase("db_loja_de_tecido", Context.MODE_PRIVATE, null);

        //Criar a tabela se não existir ou caso exista apenas carregar a tabela para o uso.
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS tecido(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tipo VARCHAR NOT NULL, " +
            "cor VARCHAR NOT NULL," +
            "metragem REAL NOT NULL," +
            "valor REAL NOT NULL" + ")"
        );

        //Instanciando o botão btSalvarTecido.
        btSalvarTecido = findViewById(R.id.btSalvarTecido);

        //Instanciando campos de dados do tecido.
        tipoTecido = findViewById(R.id.textViewTipoTecido);
        corTecido = findViewById(R.id.textViewCorTecido);
        metrageTecido = findViewById(R.id.textViewMetragemTecido);
        valorTecido = findViewById(R.id.textViewValorTecido);

        //Definindo o evento de click no botão btSalvarTecido.
        btSalvarTecido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Criação de um objeto aluno.
            Tecido tecido = new Tecido();

            //Armazenando os dados dos campos nas variáveis.
            tipo = tipoTecido.getText().toString();
            cor = corTecido.getText().toString();
            metragem = Float.parseFloat(metrageTecido.getText().toString());
            valor = Float.parseFloat(valorTecido.getText().toString());

            //Objeto aluno recebendo os dados.
            tecido.setTipo(tipo);
            tecido.setCor(cor);
            tecido.setMetragem(metragem);
            tecido.setValor(valor);

            //Criando o objeto para receber os dados dos camps e armazenar na tabela.
            ContentValues values = new ContentValues();
            values.put("tipo", tecido.getTipo());
            values.put("cor", tecido.getCor());
            values.put("metragem", tecido.getMetragem());
            values.put("valor", tecido.getValor());

            //Inserindo os dados na teabela tecido.
            db.insert("tecido", null, values);

            //Cria uma caixa de mensagem e mostra os dados incluídos.
            Message mensagem = new Message(InsertTecido.this);

            mensagem.show(
                "Dados incluídos com sucesso!!!",
                tecido.getDados(),
                R.drawable.ic_add
            );

            clearText();
            }
        });
    }

    //Método para limpar os campos do formulário e fechar o teclado.
    public void clearText() {
        tipoTecido.setText("");
        corTecido.setText("");
        metrageTecido.setText("");
        valorTecido.setText("");

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
