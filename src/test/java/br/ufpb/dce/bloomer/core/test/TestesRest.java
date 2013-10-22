package br.ufpb.dce.bloomer.core.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;
import junit.framework.Assert;
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
import org.junit.Before;
import org.junit.Test;

public class TestesRest {

	public String DefaultURI;
	public CloseableHttpClient httpclient;
	public HttpGet httpget;
	public HttpPost httppost;
	public HttpDelete httpdelete;
	public HttpPut httpput;

	@Before
	public void initialize() {
		DefaultURI = "/Bloomer-CORE/usuarios";
		httpclient = HttpClients.createDefault();
		httpget = new HttpGet("http://localhost:8080" + DefaultURI);
		httppost = new HttpPost("http://localhost:8080" + DefaultURI);
		httpput = new HttpPut("http://localhost:8080" + DefaultURI);
	}

	@Test
	public void testingGETDefault() {
		try {

			HttpResponse response = httpclient.execute(httpget);
			response.setHeader("content-type", "application/json");
			Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
					.toString());

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testingGETSpecific() {
		try {

			int userID = 1;
			httpget = new HttpGet("http://localhost:8080"
					+ "/Bloomer-CORE/usuarios/" + userID);
			HttpResponse response = httpclient.execute(httpget);
			response.setHeader("content-type", "application/json");
			Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
					.toString());

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testingDELETE() {

		this.testingPOST();
		try {

			int last = this.findLastAdd();
			httpdelete = new HttpDelete("http://localhost:8080" + DefaultURI
					+ "/" + last);
			HttpResponse response = httpclient.execute(httpdelete);
			Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
					.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testingPOST() {

		StringEntity params;

		try {

			params = new StringEntity("{\"nome\":\"MARCUS\","
					+ "\"dataNascimento\":\"01/January/1980\","
					+ "\"sexo\":\"Masculino\"," + "\"login\":\"rafa123\","
					+ "\"senha\":\"123456\"}");

			httppost.setHeader("content-type", "application/json");
			httppost.setEntity(params);
			HttpResponse response = httpclient.execute(httppost);
			Assert.assertEquals("HTTP/1.1 201 Created", response
					.getStatusLine().toString());

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testingPUT() {

		try {

			int last = this.findLastAdd();
			int lastVersion = this.findLastVersion();

			StringEntity params = new StringEntity("{\"id\":" + (last) + ","
					+ "\"nome\":\"XAVIER\","
					+ "\"dataNascimento\":\"01/January/2000\","
					+ "\"sexo\":\"Masculino\"," + "\"login\":\"rafa123\","
					+ "\"senha\":\"123456\"," + "\"version\":" + (lastVersion)
					+ "}");

			httpput.setHeader("content-type", "application/json");
			httpput.setEntity(params);

			HttpResponse response = httpclient.execute(httpput);
			Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()
					.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int findLastVersion() throws Exception {

		try {

			HttpResponse response = httpclient.execute(httpget);
			String usersJsons = EntityUtils.toString(response.getEntity());

			if (!isJson(usersJsons)) {

				throw new Exception("Is not a JSON collection!");
			} else if (isEmptyJson(usersJsons)) {

				throw new Exception(
						"No USER on database or parser to json error!");
			} else {

				String editedJson = usersJsons.substring(2,
						(usersJsons.length() - 2)); // Remove '[{' and '}]' of
													// json array

				int ID = Integer.parseInt(editedJson.substring(
						(editedJson.length() - 1), editedJson.length()));

				return ID;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int findLastAdd() throws Exception {

		try {

			HttpResponse response = httpclient.execute(httpget);
			String usersJsons = EntityUtils.toString(response.getEntity());

			if (!isJson(usersJsons)) {

				throw new Exception("Is not a JSON collection!");
			} else if (isEmptyJson(usersJsons)) {

				throw new Exception(
						"No USER on database or parser to json error!");
			} else {

				String editedJson = usersJsons.substring(2,
						(usersJsons.length() - 2)); // Remove '[{' and '}]' of
													// json array

				if (!(editedJson.contains("}"))) {
					// Return user json ID
					int ID = Integer.parseInt(editedJson.substring(5, 6));
					return ID;
				} else {
					String json[] = editedJson.split(Pattern.quote("},{"));
					String lastJson = json[json.length - 1];
					// Return LAST user json ID
					// First number caracther | It solves problem with numbers
					// bigger than 9
					int end = lastJson.indexOf(",");
					int ID = Integer.parseInt(lastJson.substring(5, end));
					return ID;
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean isJson(String json) {
		return (json.startsWith("[")) && (json.endsWith("]"));
	}

	public boolean isEmptyJson(String json) {
		return (json.equals("[]"));
	}

}
