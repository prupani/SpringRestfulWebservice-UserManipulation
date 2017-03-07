package com.rubicon.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

/**
 * This class has HttpClients test for the REST Api's
 * 
 * @author priyankarupani
 *
 */
public class UserWebServiceTest {

	private final String APP_URL = "http://localhost:8080/";
	private final String CREATE_USER_URL = "http://localhost:8080/createUser";

	/**
	 * Tests the Create user API.
	 */
	@Test
	public void createUserTest() {
		try {
			System.out.println("*************************************************************  CREATE USER  TEST ***************************************************************");
			URIBuilder builder = new URIBuilder();
			builder.setScheme("http").setHost("localhost:8080").setPath("/createUser");
			URI uri = builder.build();
			HttpResponse response = httpPostUsingURI(uri, "{\"name\":\"test\",\"age\":\"10\",\"gender\":\"male\"}");
			assertEquals(response.getStatusLine().getStatusCode(), 200);
			System.out.println("*************************************************************  CREATE USER  TEST SUCCESS ***************************************************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests the GET user API.
	 */
	@Test
	public void getUserTest() {
		try {
			System.out.println("*************************************************************  GET USER  TEST ***************************************************************");
			URIBuilder builder = new URIBuilder();
			builder.setScheme("http").setHost("localhost:8080").setPath("/createUser");
			URI uri = builder.build();
			HttpResponse createResponse = httpPostUsingURI(uri,
					"{\"name\":\"test\",\"age\":\"10\",\"gender\":\"male\"}");
			HttpEntity entity = createResponse.getEntity();
			InputStream instream = entity.getContent();
			String id = convertStreamToString(instream);
			id = id.trim();
			assertEquals(createResponse.getStatusLine().getStatusCode(), 200);

			builder.setScheme("http").setHost("localhost:8080").setPath("/getUser").setParameter("id", id);
			uri = builder.build();
			HttpResponse getResponse = httpGetUsingURI(uri);
			assertEquals(getResponse.getStatusLine().getStatusCode(), 200);
			System.out.println("*************************************************************  GET USER  TEST SUCCESS ***************************************************************");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests the UPDATE user API
	 */
	@Test
	public void updateUserTest() {
		try {
			System.out.println("*************************************************************  UPDATE USER  TEST ***************************************************************");
			URIBuilder builder = new URIBuilder();
			builder.setScheme("http").setHost("localhost:8080").setPath("/createUser");
			URI uri = builder.build();
			HttpResponse createResponse = httpPostUsingURI(uri,"{\"name\":\"test\",\"age\":\"10\",\"gender\":\"male\"}");
			HttpEntity entity = createResponse.getEntity();
			InputStream instream = entity.getContent();
			String id = convertStreamToString(instream);
			id = id.trim();
			assertEquals(createResponse.getStatusLine().getStatusCode(), 200);


			builder = new URIBuilder();
			builder.setScheme("http").setHost("localhost:8080").setPath("/updateUser").setParameter("id", id);
			uri = builder.build();
			HttpResponse getResponse = httpPutUsingURI(uri, "{\"name\":\"test2\",\"age\":\"10\",\"gender\":\"male\"}");
			assertEquals(getResponse.getStatusLine().getStatusCode(), 200);
			System.out.println("*************************************************************  UPDATE USER  TEST SUCCESS***************************************************************");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests the DELETE User API.
	 */
	@Test
	public void deleteUserTest() {
		try {
			System.out.println("*************************************************************  DELETE USER  TEST ***************************************************************");
			// create
			URIBuilder builder = new URIBuilder();
			builder.setScheme("http").setHost("localhost:8080").setPath("/createUser");
			URI uri = builder.build();
			HttpResponse createResponse = httpPostUsingURI(uri,
					"{\"name\":\"test\",\"age\":\"10\",\"gender\":\"male\"}");
			HttpEntity entity = createResponse.getEntity();
			InputStream instream = entity.getContent();
			String id = convertStreamToString(instream);
			id = id.trim();
			assertEquals(createResponse.getStatusLine().getStatusCode(), 200);

			builder = new URIBuilder();
			builder.setScheme("http").setHost("localhost:8080").setPath("/deleteUser").setParameter("id", id);
			uri = builder.build();
			HttpResponse deleteURI = httpDeleteUsingURI(uri);
			assertEquals(deleteURI.getStatusLine().getStatusCode(), 200);
			System.out.println("*************************************************************  DELETE USER  TEST SUCCESS***************************************************************");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests the delete all users API.
	 */
	@Test
	public void deleteAllUsersTest() {
		try {
			System.out.println("*************************************************************  DELETE ALL USER  TEST ***************************************************************");
			URIBuilder builder = new URIBuilder();

			builder.setScheme("http").setHost("localhost:8080").setPath("/createUser");
			URI uri = builder.build();
			HttpResponse createResponse = httpPostUsingURI(uri,
					"{\"name\":\"test1\",\"age\":\"10\",\"gender\":\"male\"}");
			HttpEntity entity = createResponse.getEntity();
			InputStream instream = entity.getContent();
			String id = convertStreamToString(instream);
			id = id.trim();
			assertEquals(createResponse.getStatusLine().getStatusCode(), 200);

			builder.setScheme("http").setHost("localhost:8080").setPath("/createUser");
			uri = builder.build();
			createResponse = httpPostUsingURI(uri, "{\"name\":\"test2\",\"age\":\"20\",\"gender\":\"male\"}");
			assertEquals(createResponse.getStatusLine().getStatusCode(), 200);

			builder.setScheme("http").setHost("localhost:8080").setPath("/createUser");
			uri = builder.build();
			createResponse = httpPostUsingURI(uri, "{\"name\":\"test3\",\"age\":\"30\",\"gender\":\"male\"}");
			assertEquals(createResponse.getStatusLine().getStatusCode(), 200);

			builder.setScheme("http").setHost("localhost:8080").setPath("/deleteAllUsers");
			uri = builder.build();
			HttpResponse deleteURI = httpDeleteUsingURI(uri);
			assertEquals(deleteURI.getStatusLine().getStatusCode(), 200);
			System.out.println("*************************************************************  DELETE ALL USER  TEST SUCCESS ***************************************************************");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests if the application is running or not.
	 */
	@Test
	public void testAppRunning() {
		try {
			System.out.println("*************************************************************  TEST APP RUNNING ***************************************************************");
			URIBuilder builder = new URIBuilder();
			builder.setScheme("http").setHost("localhost:8080");
			URI uri = builder.build();
			HttpResponse response = httpGetUsingURI(uri);
			assertEquals(response.getStatusLine().getStatusCode(), 200);
			System.out.println("*************************************************************  TEST APP RUNNING SUCCESS ***************************************************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param uri
	 * @return
	 */
	public static HttpResponse httpGetUsingURI(URI uri) {

		HttpResponse response = null;
		try {
			HttpGet httpget = new HttpGet(uri);
			HttpClient client = HttpClientBuilder.create().build();
			response = client.execute(httpget);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 
	 * @param uri
	 * @return
	 */
	public static HttpResponse httpDeleteUsingURI(URI uri) {

		HttpResponse response = null;
		try {
			HttpDelete httpdelete = new HttpDelete(uri);
			HttpClient client = HttpClientBuilder.create().build();
			response = client.execute(httpdelete);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	public static HttpResponse httpPostUsingURI(URI  uri, String data) {
		HttpResponse response = null;
		try {
			HttpPost post = new HttpPost(uri);
            post.setEntity(new StringEntity(data));
			HttpClient client = HttpClientBuilder.create().build();
			response = client.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 
	 * @param uri
	 * @param data
	 * @return
	 */
	public static HttpResponse httpPutUsingURI(URI uri, String data) {

		HttpResponse response = null;
		try {
			HttpPut httpput = new HttpPut(uri);
			httpput.setEntity(new StringEntity(data));
			HttpClient client = HttpClientBuilder.create().build();
			response = client.execute(httpput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 
	 * @param is
	 * @return
	 */
	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
