package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.server.Servidor;
import junit.framework.Assert;

public class ClienteTest {
	private static String SITE = "http://localhost:8080";
	private HttpServer server;
	private Client client;
	private WebTarget target;
	
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado(){
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}
	
	@Test
	public void adicionaCarrinho(){
		Carrinho carrinho = criaCarrinhoTeste();
		String xml = carrinho.toXML();
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
		Response response = target.path("/carrinhos").request().post(entity);
		Assert.assertEquals(201, response.getStatus());
		
		String location = response.getHeaderString("Location");
		String conteudo = client.target(location).request().get(String.class);
		Assert.assertTrue(conteudo.contains("Microfone"));
	}
	
	private Carrinho criaCarrinhoTeste() {
		Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Microfone", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
		return carrinho;
	}

	@After
	public void paraServidor(){
		server.stop();
		System.out.println("Servidor off");
	}
	
	@Before
	public void before(){
		server = Servidor.inicializaServidor();
		ClientConfig clientConfig =  new ClientConfig();
		clientConfig.register(new LoggingFilter());
		client = ClientBuilder.newClient(clientConfig);
		target = client.target(SITE);
	}
	
}
