package br.ufpb.dce.bloomer.core.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import br.ufpb.dce.bloomer.core.model.Sexo;
import br.ufpb.dce.bloomer.core.model.Usuario;

public class TestesRest {

	private static String USUARIOS = "http://localhost:8080/Bloomer-CORE/usuarios";
	private CloseableHttpClient httpclient = HttpClients.createDefault();

	private HttpGet get = new HttpGet(USUARIOS);
	private HttpPost post = new HttpPost(USUARIOS);
	private HttpPut put = new HttpPut(USUARIOS);
	private HttpDelete delete = new HttpDelete(USUARIOS);

	private HttpResponse response;

	@Test
	public void testingRestMethods() {

		try {

			// Get without users
			response = httpclient.execute(get);
			assertEquals("[]", EntityUtils.toString(response.getEntity()));

			// Post of user
			Usuario user = new Usuario();
			user.setNome("Rafael");
			user.setDataNascimento(new GregorianCalendar(1992, 01, 22)); // YYYYMMDD
			user.setSexo(Sexo.Masculino);
			user.setLogin("marcusrafael");
			user.setSenha("123456");

			post.setEntity(new StringEntity(user.toJsonTest()));
			response = httpclient.execute(post);
			assertEquals("HTTP/1.1 201 Created", response.getStatusLine()
					.toString());

			// Verify user from get method
			get.setURI(new URI(USUARIOS + "/18")); //TODO Make user id dynamic
			response = httpclient.execute(get);
			String json = EntityUtils.toString(response.getEntity());
			Usuario newUser = Usuario.fromJsonToUsuario(json);
			assertEquals(user.getNome(), newUser.getNome());
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(0);
			cal.set(user.getDataNascimento().get(Calendar.YEAR), user
					.getDataNascimento().get(Calendar.MONTH), user
					.getDataNascimento().get(Calendar.DAY_OF_MONTH));
			assertEquals(cal.get(Calendar.YEAR), newUser.getDataNascimento().get(Calendar.YEAR));
			assertEquals(cal.get(Calendar.MONTH), newUser.getDataNascimento().get(Calendar.MONTH));
			assertEquals(cal.get(Calendar.DAY_OF_MONTH), newUser.getDataNascimento().get(Calendar.DAY_OF_MONTH));
			assertEquals(user.getSexo(), newUser.getSexo());
			assertEquals(user.getLogin(), newUser.getLogin());
			assertEquals(user.getSenha(), newUser.getSenha());

			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
