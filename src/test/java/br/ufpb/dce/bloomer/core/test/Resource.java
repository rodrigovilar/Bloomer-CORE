package br.ufpb.dce.bloomer.core.test;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Resource {

	private CloseableHttpClient httpclient;

	private HttpGet getAll;
	private HttpGet getUnique;
	private HttpPost post;
	private HttpPut put;
	private HttpDelete delete;

	private String url;

	public Resource(String url) {
		this.url = url;
		getAll = new HttpGet(url);
		post = new HttpPost(url);
		put = new HttpPut(url);
		delete = new HttpDelete(url);		
	}
	
	public String get() {
		httpclient = HttpClients.createDefault();
		HttpResponse response = httpclient.execute(getAll);
		return EntityUtils.toString(response.getEntity());
	}
	
	public String get(String id){
		getUnique.setURI(new URI(url + "/" + id));
		httpclient = HttpClients.createDefault();
		response = httpclient.execute(getUnique);
		return EntityUtils.toString(response.getEntity());
	}
	
	public int post(String json) {
		post.setEntity(new StringEntity(json));
		httpclient = HttpClients.createDefault();
		HttpResponse response = httpclient.execute(post);
		return response.getStatusLine().getStatusCode();
	}
	
	public int put (String json){
		
		put.setEntity(new StringEntity(json));
		httpclient = HttpClients.createDefault();
		HttpResponse response = httpclient.execute(put);
		return response.getStatusLine().getStatusCode();
		
	}
}
