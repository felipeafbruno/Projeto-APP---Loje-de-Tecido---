package com.uninove.lojadetecido.utils;

import android.app.AlertDialog;
import android.content.Context;

public class Message {
    private Context context;

    //Construtor da classe com paramêtros.
    public Message(Context context) {
        this.context = context;
    }

    //Método que exibe uma mensagem quando executada uma adição ou edição.
    public void show(String titulo, final String texto, int icone) {
        AlertDialog.Builder msg = new AlertDialog.Builder(context);
        msg.setIcon(icone);
        msg.setTitle(titulo);
        msg.setMessage(texto);
        msg.setPositiveButton("OK", null);

        msg.show();
    }
}
