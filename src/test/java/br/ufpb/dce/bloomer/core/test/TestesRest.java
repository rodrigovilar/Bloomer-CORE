package br.ufpb.dce.bloomer.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.junit.After;
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
			// GET without users
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(get);
			assertEquals("[]", EntityUtils.toString(response.getEntity()));

			// POST an user
			Usuario user = new Usuario();
			user.setNome("Rafael");
			user.setDataNascimento(new GregorianCalendar(1992, 01, 22)); // yyyyMMdd
			user.setSexo(Sexo.Masculino);
			user.setLogin("marcusrafael");
			user.setSenha("123456");
			post.setEntity(new StringEntity(user.toJson()));
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(post);
			assertEquals("HTTP/1.1 201 Created", response.getStatusLine()
					.toString());

			// GET 'id' from user added
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(get);
			String jsonArray = EntityUtils.toString(response.getEntity());
			String jsonFromUser = jsonArray.substring(1,
					(jsonArray.length() - 1)); // Remove '[' and ']' from array
			Usuario userFromJson = Usuario.fromJsonToUsuario(jsonFromUser);
			long ID = userFromJson.getId(); // Id of last user added

			// Compare two users
			get.setURI(new URI(USUARIOS + "/" + ID));
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(get);
			String json = EntityUtils.toString(response.getEntity());
			Usuario newUser = Usuario.fromJsonToUsuario(json);
			assertTrue(newUser.isEqualsUsuario(user));

			// PUT edited user
			Usuario editedUser = newUser;
			editedUser.setNome("Rodrigo");
			editedUser.setDataNascimento(new GregorianCalendar(1981, 02, 05)); // YYYYMMDD
			editedUser.setSexo(Sexo.Masculino);
			editedUser.setLogin("rodrigovilar");
			editedUser.setSenha("654321");
			put.setEntity(new StringEntity(editedUser.toJson()));
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(put);
			assertEquals("HTTP/1.1 200 OK", response.getStatusLine().toString());

			// GET 'id' from user added
			get.setURI(new URI(USUARIOS));
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(get);
			String jsonArray2 = EntityUtils.toString(response.getEntity());
			String jsonFromUser2 = jsonArray2.substring(1,
					(jsonArray2.length() - 1)); // Remove '[' and ']' from array
			Usuario userFromJson2 = Usuario.fromJsonToUsuario(jsonFromUser2);
			long ID2 = userFromJson2.getId(); // Id of last user added

			// Compare two users
			get.setURI(new URI(USUARIOS + "/" + ID2));
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(get);
			String json2 = EntityUtils.toString(response.getEntity());
			Usuario newUser2 = Usuario.fromJsonToUsuario(json2);
			assertTrue(editedUser.isEqualsUsuario(newUser2));

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@After
	public void after() {
		try {
			// GET to check if exists some user on database
			get.setURI(new URI(USUARIOS));
			httpclient = HttpClients.createDefault();
			response = httpclient.execute(get);
			
			// DELETE the user if exists 
			if (!("[]").equals(EntityUtils.toString(response.getEntity()))) {
				httpclient = HttpClients.createDefault();
				response = httpclient.execute(get);
				String jsonArray = EntityUtils.toString(response.getEntity());
				String jsonFromUser = jsonArray.substring(1,
						(jsonArray.length() - 1));
				Usuario userFromJson = Usuario.fromJsonToUsuario(jsonFromUser);
				long ID = userFromJson.getId(); // Last user added 'id'
				httpclient = HttpClients.createDefault();
				delete.setURI(new URI(USUARIOS + "/" + ID));
				httpclient = HttpClients.createDefault();
				response = httpclient.execute(delete);
				assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
						.toString());
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
