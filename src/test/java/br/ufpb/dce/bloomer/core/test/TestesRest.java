package br.ufpb.dce.bloomer.core.test;

import java.util.Calendar;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import br.ufpb.dce.bloomer.core.model.Sexo;
import br.ufpb.dce.bloomer.core.model.Usuario;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;

public class TestesRest {

	public static int getMonthNumber(String month) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new java.text.SimpleDateFormat("MMM",
					java.util.Locale.ENGLISH).parse(month));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cal.get(Calendar.MONTH);
	}

	@Test
	public void test() throws Exception {
		String json;
		Usuario usuario = null;

		WebClient client = new WebClient();
		Page page = client
				.getPage("http://localhost:8080/Bloomer-CORE/usuarios");
		WebResponse response = page.getWebResponse();
		if (response.getContentType().equals("application/json")) {
			json = response.getContentAsString();

			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory factory = objectMapper.getJsonFactory();

			JsonNode usuarioJSON = objectMapper.readTree(factory
					.createJsonParser(json));
			usuario = new Usuario();
			if (usuarioJSON.has("id")) {
				usuario.setId(usuarioJSON.get("id").asLong());
			}
			if (usuarioJSON.has("nome")) {
				usuario.setNome(usuarioJSON.get("nome").asText());
			}
			if (usuarioJSON.has("dataNascimento")) {
				String data_str = usuarioJSON.get("dataNascimento").asText();
				String[] data = data_str.split("/");
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data[0]));
				cal.set(Calendar.MONTH, getMonthNumber(data[1]));
				cal.set(Calendar.YEAR, Integer.parseInt(data[2]));
				usuario.setDataNascimento(cal);
			}
			if (usuarioJSON.has("sexo")) {
				usuario.setSexo(Sexo.valueOf(usuarioJSON.get("sexo").asText()));
			}
			if (usuarioJSON.has("login")) {
				usuario.setLogin(usuarioJSON.get("login").asText());
			}
			if (usuarioJSON.has("senha")) {
				usuario.setSenha(usuarioJSON.get("senha").asText());
			}
			if (usuarioJSON.has("version")) {
				usuario.setVersion(usuarioJSON.get("version").asInt());
			}

			System.out.println(json);

		}
		Assert.assertTrue(1 == usuario.getId().intValue());

	}

	@Test
	public void testPOST() {
//		WebRequest wr = new WebRequest(, HttpMethod.POST);
	}

}
