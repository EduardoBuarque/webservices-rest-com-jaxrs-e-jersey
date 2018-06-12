package br.com.alura.loja.modelo;

public class Projeto {
	private String nome;
	private long id;
	private int anoDeInicio;
	
	public Projeto() {
	}
	
	public Projeto(String nome, long id, int anoDeInicio) {
		this.nome = nome;
		this.id = id;
		this.anoDeInicio = anoDeInicio;
	}

	public String getNome() {
		return nome;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAnoDeInicio() {
		return anoDeInicio;
	}	
	
}
