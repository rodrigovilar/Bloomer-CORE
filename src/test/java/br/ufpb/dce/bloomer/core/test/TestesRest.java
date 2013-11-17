package br.ufpb.dce.bloomer.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
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
	
	private Resource resource;

	@Before
	public void init() {
		resource = new Resource(USUARIOS);
	}
	
	@Test
	public void testingRestMethods() {

			assertEquals("[]", resource.get());
			
			Usuario user = criarUsuarioPadrao();			
			assertEquals(201, resource.post(user.toJson()));
			
			String jsonArray = resource.get();
			List<Usuario> usuarios = Usuario.fromJsonArrayToUsuarios(jsonArray);

			Long ID = usuarios.get(0).getId();
			String json = resource.get(ID.toString());
			Usuario newUser = Usuario.fromJsonToUsuario(json);
			assertEquals(user, newUser);

			// PUT edited user
			Usuario editedUser = editarUsuario(newUser);
			assertEquals (200, resource.put(user.toJson()));
			
			String jsonArray2 = resource.get();
			List<Usuario> usuarios2 = Usuario.fromJsonArrayToUsuarios(jsonArray2);
			String json2 = resource.get(ID.toString());
			Usuario editedUser2 = Usuario.fromJsonToUsuario(json2);
			assertTrue(editedUser.isEqualsUsuario(editedUser2));

	}

	private Usuario editarUsuario(Usuario newUser) {
		Usuario editedUser = newUser;
		editedUser.setNome("Rodrigo");
		editedUser.setDataNascimento(new GregorianCalendar(1981, 02, 05)); // YYYYMMDD
		editedUser.setSexo(Sexo.Masculino);
		editedUser.setLogin("rodrigovilar");
		editedUser.setSenha("654321");
		return editedUser;
	}

	private Usuario criarUsuarioPadrao() {
		Usuario user = new Usuario();
		user.setNome("Rafael");
		user.setDataNascimento(new GregorianCalendar(1992, 01, 22)); // yyyyMMdd
		user.setSexo(Sexo.Masculino);
		user.setLogin("marcusrafael");
		user.setSenha("123456");
		return user;
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

