package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Projeto;
import br.com.alura.loja.server.Servidor;
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
		String conteudo = target.path("/projetos/1").request().get(String.class);
		return conteudo;
	}
	
	@Test
	public void adicionaProjetoNovo(){
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(SITE);
		Projeto projeto = new Projeto(3L,"App.Delphus",2018);
		String xml = projeto.toXML();
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		
		Response response = target.path("/projetos").request().post(entity);
		
		Assert.assertEquals(201, response.getStatus());
		
		String location = response.getHeaderString("Location");
		String conteudo = client.target(location).request().get(String.class);
		Assert.assertTrue(conteudo.contains("App.Delphus"));
		
	}

	@After
	public void paraServidor(){
		server.stop();
	}
	
	@Before
	public void before(){
		server = Servidor.inicializaServidor();
	}
}
