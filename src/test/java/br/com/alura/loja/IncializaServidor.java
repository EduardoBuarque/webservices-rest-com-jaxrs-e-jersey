package br.com.alura.loja;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;

import br.com.alura.loja.server.Servidor;

public class IncializaServidor {

	public static void main(String[] args) {
		HttpServer server = Servidor.inicializaServidor();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.stop();
		System.out.println("Servidor off");
	}
}
