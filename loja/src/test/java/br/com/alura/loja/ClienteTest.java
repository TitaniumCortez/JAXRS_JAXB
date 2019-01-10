package br.com.alura.loja;

import java.io.IOException;


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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class ClienteTest {
	HttpServer server;
	Client cliente;
	WebTarget target;
	@Before
	public void initServer() throws IOException {
		server = Servidor.inicializaServidor();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		System.out.println("Servidor Rodando!");
		this.cliente = ClientBuilder.newClient(config);
		this.target = cliente.target("http://localhost:8080");
	}

	@After
	public void downServer() {
		server.stop();
	}

	@Test
	public void testaQueBuscaUmCarrinhoTrazOCarrinhoEsperado() {
		Client cliente = ClientBuilder.newClient();
		WebTarget target = cliente.target("http://localhost:8080");
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);		
		System.out.println(carrinho.getRua());
	}

	@Test
	public void testaAdicionaUmCarrinho() {
		
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314l, "Tablet", 999, 1));
		carrinho.setRua("Santa Eunice");
		carrinho.setCidade("Diadema");

		
		Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML); // representa o objecto que será enviado
		Response response = target.path("/carrinhos").request().post(entity);
		Assert.assertEquals(201, response.getStatus());

		String location = response.getHeaderString("Location");
		Carrinho carrinhoCarregado = cliente.target(location).request().get(Carrinho.class);
		Assert.assertEquals("Tablet", carrinhoCarregado.getProdutos().get(0).getNome());

	}
}
