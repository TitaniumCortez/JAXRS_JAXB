package br.com.alura.loja;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



import br.com.alura.loja.modelo.Projeto;

public class ProjetoTest {

	HttpServer server;

	@Before
	public void initServer() throws IOException {
		server = Servidor.inicializaServidor();
		System.out.println("Servidor Rodando!");
	}

	@After
	public void downServer() {
		server.stop();
	}

	@Test
	public void testaQueBuscaUmProjetoEsperado() {
		Client cliente = ClientBuilder.newClient();
		WebTarget target = cliente.target("http://localhost:8080");
		Projeto projeto = target.path("/projeto/1").request().get(Projeto.class);		
		Assert.assertEquals("Minha loja", projeto.getNome());
	}

}
