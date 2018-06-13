package br.com.alura.loja;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Projeto;
import junit.framework.Assert;

public class ProjetoTest {
	private static String SITE = "http://localhost:8080";
	private HttpServer server;
	
	@Test
	public void testePesquisaProjeto() {
		String projetoEmXml = "";				
		projetoEmXml = obtemPesquisaProjeto();	
		Projeto projeto =  (Projeto) new XStream().fromXML(projetoEmXml);
		Assert.assertEquals("Minha Loja", projeto.getNome());
	}
	
	private String obtemPesquisaProjeto() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(SITE);
		String conteudo = target.path("/projetos").request().get(String.class);
		return conteudo;
	}
	
	@After
	public void paraServidor(){
		server.stop();
	}
	
	@Before
	public void before(){
		URI uri = URI.create(SITE);
		ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
		server = GrizzlyHttpServerFactory.createHttpServer(uri, config);				
	}
}
