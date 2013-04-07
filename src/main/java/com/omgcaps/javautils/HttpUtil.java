package com.omgcaps.javautils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;

/**
 * 
 * HttpUtil delivers a nice utility for any HTTP request in Java.
 * 
 * @author omgcaps
 * 
 */
public class HttpUtil {

	private static HttpURLConnection connection = null;

	/**
	 * Perform a GET request.
	 * 
	 * @param url
	 *            Url to execute the request.
	 * @return Response from server.
	 * @throws IOException
	 */
	public static String get(String url) throws IOException {
		return get(url, null);
	}

	/**
	 * Perform a GET request.
	 * 
	 * @param url
	 *            Url to execute the request.
	 * @param params
	 *            Array of parameters to pass.
	 * @return Response from server.
	 * @throws IOException
	 */
	public static String get(String url, HashMap<String, String> params)
			throws IOException {
		String newUrl;
		boolean firstVar = true;

		if (url == null)
			return null;
		newUrl = url;

		if (params != null)
			for (String getVar : params.keySet()) {
				if (firstVar) {
					newUrl += "?" + getVar + "=" + params.get(getVar);
					firstVar = false;
				}

				newUrl += "&" + getVar + "=" + params.get(getVar);
			}

		return request("GET", newUrl, null, null);
	}

	/**
	 * Perform a POST request.
	 * 
	 * @param url
	 *            Url to execute the request.
	 * @param params
	 *            Array of parameters to pass.
	 * 
	 * @return Response from server.
	 * @throws IOException
	 * 
	 */
	public static String post(String url, HashMap<String, String> params)
			throws IOException {

		String body = "";

		if (params != null)
			for (String postVar : params.keySet())
				body += postVar + "=" + params.get(postVar) + "&";

		// remove the trailing &.
		body = body.substring(0, (body.length() - 1));

		return request("POST", url, body, null);
	}

	/**
	 * Perform a PUT request.
	 * 
	 * @param url
	 *            Url to execute the request.
	 * @param params
	 *            Array of parameters to pass.
	 * @return Response from server.
	 * @throws IOException
	 */
	public static String put(String url, HashMap<String, String> params)
			throws IOException {
		String body = null;

		if (params != null) {
			body = "";
			for (String putVar : params.keySet()) {
				body += putVar + "=" + params.get(putVar) + "&";
			}
		}

		// remove the trailing &.
		if (body != null)
			body = body.substring(0, (body.length() - 1));

		return request("PUT", url, body, null);
	}

	/**
	 * Perform a DELETE request.
	 * 
	 * @param url
	 *            Url to execute the request.
	 * @param params
	 *            Array of parameters to pass.
	 * @return Response from server.
	 * @throws IOException
	 */
	public static String delete(String url, HashMap<String, String> params)
			throws IOException {
		String body = null;

		if (params != null) {
			body = "";
			for (String deleteVar : params.keySet()) {
				body += deleteVar + "=" + params.get(deleteVar) + "&";
			}
		}

		// remove the trailing &.
		if (body != null)
			body = body.substring(0, (body.length() - 1));

		return request("DELETE", url, body, null);
	}

	/**
	 * Perform a generic request.
	 * 
	 * @param method
	 *            Request method.
	 * @param url
	 *            Url to execute the request.
	 * @param body
	 *            Body of request.
	 * @param headers
	 *            Request headers.
	 * @return Response from server.
	 * @throws IOException
	 */
	public static String request(String method, String url, String body,
			HashMap<String, String> params) throws IOException {

		URL u = new URL(url);

		connection = (HttpURLConnection) u.openConnection();

		// Delete method does not support dooutput.
		if (method != "DELETE")
			connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod(method);

		if (body != null) {
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			out.writeBytes(body);
		}

		if (params != null)
			for (String param : params.keySet())
				connection.addRequestProperty(param, params.get(param));

		connection.connect();

		return IOUtils.toString(connection.getInputStream());
	}
}
