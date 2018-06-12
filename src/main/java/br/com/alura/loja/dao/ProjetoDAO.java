package br.com.alura.loja.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import br.com.alura.loja.modelo.Projeto;

public class ProjetoDAO {
	private static Map<Long, Projeto> banco = new HashMap<Long, Projeto>();
	private static AtomicLong contador = new AtomicLong(1);
	
	static{
		banco.put(1L, new Projeto(1L, "Minha Loja", 2014));
		banco.put(2L, new Projeto(2L, "Alura", 2012));
	}
	
	public void adiciona(Projeto projeto){
		Long id = contador.incrementAndGet();
		projeto.setId(id);
		banco.put(id, projeto);
	}
	
	public Projeto busca(Long id){
		return banco.get(id);
	}
	
	public Projeto remove(Long id){
		return banco.remove(id);
	}
}
