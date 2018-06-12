package br.com.alura.loja;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import junit.framework.Assert;

public class ProjetoTest {
	private static String SITE = "http://localhost:8080";
	@Test
	public void testePesquisaProjeto() {
		HttpServer servidor = null;
		String projetoEmXml = "";				
		try{
			servidor = criaServidor();
			servidor.start();
			projetoEmXml = obtemPesquisaProjeto();
			System.out.println(projetoEmXml);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(servidor != null){
				servidor.stop();
			}
		}
		
		Assert.assertTrue(projetoEmXml.contains("<nome>Minha Loja</nome>"));
	}
	
	private String obtemPesquisaProjeto() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(SITE);
		String conteudo = target.path("/projetos").request().get(String.class);
		return conteudo;
	}

	private HttpServer criaServidor(){
		URI uri = URI.create(SITE);
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		return server;		
	}

}
