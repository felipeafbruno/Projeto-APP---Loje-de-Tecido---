package com.uninove.lojadetecido.intent;

import java.io.Serializable;

public class Tecido implements Serializable {
    private int id;
    private String tipo;
    private String cor;
    private float metragem;
    private float valor;

    /**
     *  Método construtor vazio.
     */
     public Tecido() {
     }

    /**
     * Método construtor com assinatura sem paramêtro id.
     *
     * @param tipo
     * @param cor
     * @param metragem
     * @param valor
     */
     public Tecido(String tipo, String cor, float metragem, float valor) {
         this.tipo = tipo;
         this.cor = cor;
         this.metragem = metragem;
         this.valor = valor;
     }

    /**
     * Método construtor com assinatura com paramêtro id.
     * @param id
     * @param tipo
     * @param cor
     * @param metragem
     * @param valor
     */
     public Tecido(int id, String tipo, String cor, float metragem, float valor) {
         this.id = id;
         this.tipo = tipo;
         this.cor = cor;
         this.metragem = metragem;
         this.valor = valor;
     }

    //Getters e Setters
    /**
     * Métodos para retorna ou obter valores dos atributos da classe.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public float getMetragem() {
        return metragem;
    }

    public void setMetragem(float metragem) {
        this.metragem = metragem;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    /**
     * Método sobrescrito para retorna Tipo e Cor do tecido para mostrar na ListView.
     * @return this.tipo + " - " + this.cor
     */
    public String toString() {
        return this.tipo + " - " + this.cor;
    }

    public String getDados() {
        return "TIPO: " + tipo + "\n" +
                "COR: " + cor + "\n" +
                "METRAGEM: " + metragem + "\n" +
                "VALOR: " + valor;
    }
}
