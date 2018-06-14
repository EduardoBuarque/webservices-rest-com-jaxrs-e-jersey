package br.com.alura.loja;

import java.io.IOException;

import org.glassfish.grizzly.http.server.HttpServer;

import br.com.alura.loja.server.Servidor;

public class SobeServidor {

	public static void main(String[] args) {
		HttpServer server = Servidor.inicializaServidor();
		try {
			server.start();
			System.in.read();
			server.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
